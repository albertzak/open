import { keymap, EditorView, ViewPlugin, Decoration, DecorationSet, MatchDecorator, WidgetType } from '@codemirror/view';
import { syntaxTree } from '@codemirror/language'
import { EditorState, StateEffect, EditorSelection, StateField, Annotation, Extension, Transaction } from '@codemirror/state';

export function toggleLineComment(view) {
  const doc = view.state.doc;
  const { from, to } = view.state.selection.main;
  const lines = [];

  for (let pos = from; pos <= to; ) {
    const line = doc.lineAt(pos);
    lines.push(line);
    pos = line.to + 1; // Move to the start of the next line
  }

  const changes = [];
  const firstLine = lines[0];
  const commentMarker = getCommentMarker(view.state, firstLine.from);

  if (!commentMarker) {
    return false; // No comment marker found for the current language
  }

  const allCommented = lines.every(line => line.text.trim().startsWith(commentMarker));

  for (const line of lines) {
    if (allCommented) {
      // Uncomment lines
      const index = line.text.indexOf(commentMarker);
      if (index !== -1) {
        changes.push({ from: line.from + index, to: line.from + index + commentMarker.length });
      }
    } else {
      // Comment lines
      changes.push({ from: line.from, insert: commentMarker });
    }
  }

  view.dispatch({
    changes,
    annotations: StateEffect.addToHistory.of(true) // Add to history so it can be undone/redone
  });

  return true;
}

function getCommentMarker(state, pos) {
  const tree = syntaxTree(state);
  let commentMarker = null;

  tree.iterate({
    from: pos,
    to: pos + 1,
    enter: node => {
      if (node.name === "LineComment" || node.name === "Comment") {
        const lang = node.node.parent && node.node.parent.name;
        switch (lang) {
          case "javascript":
          case "typescript":
          case "css":
          case "html":
            commentMarker = "//";
            break;
          case "clojure":
            commentMarker = ";";
            break;
          case "python":
            commentMarker = "#";
            break;
          case "ruby":
            commentMarker = "#";
            break;
          default:
            commentMarker = "//";
        }
      }
    }
  });

  return commentMarker;
}

const addHighlight = StateEffect.define()

const isQRewrite = Annotation.define()

function leftAlign(str, indent) {
  const extraIndent = str[0] === "'" ? ' ' : ''

  return str.split('\n').map((line, i) => {
    if (i === 0) {
      return line
    } else {
      return [' '.repeat(indent), extraIndent, line].join('')
    }
  }).join('\n')
}

// updates only minimal changed values in visible ranges
export function rewriteQResults(view, qresults, ) {
  const qkeys = Object.keys(qresults)
  let changes = []
  let decorations = []

  for (const range of view.visibleRanges) {
    syntaxTree(view.state).iterate({
      from: range.from,
      to: range.to,
      enter: ({ type, from, to, node }) => {
        const text = view.state.doc.sliceString(from, to)
        const qkey = qkeys.find(qk => text.startsWith('(? :' + qk))
        if (type.name === 'List' && qkey) {
          // ensure value is exactly fifth and last child, incl parens
          //    (? :uniquekey (+ 1 1) 2)
          //    12 3          4       56
          //                          ^
          // to prevent overwriting queried expression while typing
          let childrenCount = 0
          const cursor = node.cursor()
          if (cursor.firstChild()) {
            do {
              // console.log('ch', childrenCount, view.state.doc.sliceString(cursor.node.from, cursor.node.to))
              childrenCount++;
            } while (childrenCount <= 22 && cursor.nextSibling())
          }

          if (!((childrenCount === 6) || (childrenCount === 7))) {
            console.log('?-skipping because not exactly 6 or 7 children', childrenCount, qkey, node)
            return
          }

          const valueNode = node.lastChild.prevSibling // last child



          const currentValue = view.state.doc.sliceString(valueNode.from, valueNode.to)
          const goalValue = qresults[qkey]

          if (currentValue !== goalValue) {

            // console.log(
            //   '?-found stale',
            //   qkey,
            //   currentValue, '->',
            //   goalValue,
            //   valueNode.from,
            //   valueNode.to,
            //   valueNode)

            const indent = valueNode.from - view.state.doc.lineAt(valueNode.from).from
            const alignedGoalValue = leftAlign(goalValue, indent)

            changes.push({
              from: valueNode.from,
              to: valueNode.to,
              insert: alignedGoalValue
            })

            decorations.push(Decoration.mark({
              class: "highlighted-expression"
            }).range(valueNode.from, valueNode.to));
          }

        }
      }
    })
  }

  view.dispatch({
    changes,
    annotations: [
      Transaction.addToHistory.of(false),
      isQRewrite.of("isQRewrite")
    ],
    effects: addHighlight.of(Decoration.set(decorations))
  })
}

export const highlightField = StateField.define({
  create() {
    return Decoration.none;
  },
  update(highlights, tr) {
    // console.log('XXX', highlights, tr.effects)

    highlights = highlights.map(tr.changes);
    for (let e of tr.effects) {
      if (e.is(addHighlight)) {

        // console.log('YYY e is add', e)
        highlights = highlights.update({
          // add: e.value
        });
      }
      // } else if (e.is(removeHighlight)) {
      //   highlights = highlights.update({
      //     filter: () => false
      //   });
      // }
    }
    return highlights
  },
  provide: f => EditorView.decorations.from(f)
})




export function toggleBooleanUnderCursor(view) {
  const { main } = view.state.selection;
  const cursorPos = main.head;
  const line = view.state.doc.lineAt(cursorPos);
  const lineText = line.text;
  const startOfLine = line.from;

  // Use a regular expression to find a boolean value (true/false) under the cursor
  const regex = /\b(true|false)\b/g;
  let match;

  while ((match = regex.exec(lineText)) !== null) {
    const matchStart = startOfLine + match.index;
    const matchEnd = matchStart + match[0].length;

    if (matchStart <= cursorPos && cursorPos <= matchEnd) {
      const newValue = match[0] === "true" ? "false" : "true";
      const newText =
        lineText.slice(0, match.index) + newValue + lineText.slice(match.index + match[0].length);

      view.dispatch({
        changes: { from: startOfLine, to: line.to, insert: newText },
        selection: { anchor: cursorPos }
      });

      break;
    }
  }
}

export function modifyNumberUnderCursor(f, view) {
  const { main } = view.state.selection;
  const cursorPos = main.head;
  const line = view.state.doc.lineAt(cursorPos);
  const lineText = line.text;
  const startOfLine = line.from;

  // Use a regular expression to find a number under the cursor
  const regex = /(-?\b\d+\.?\d*\b)/g;
  let match;
  while ((match = regex.exec(lineText)) !== null) {
    const matchStart = startOfLine + match.index;
    const matchEnd = matchStart + match[0].length;

    if (matchStart <= cursorPos && cursorPos <= matchEnd) {
      const oldValue = parseFloat(match[0], 10);
      const newValue = f(oldValue);
      const oldDecimals = match[0].split('.')[1]
      const fixed = oldDecimals && oldDecimals.length
      const newText = newValue.toFixed(fixed)
        lineText.slice(0, match.index) + fixed + lineText.slice(match.index + match[0].length);

      view.dispatch({
        changes: { from: matchStart, to: matchEnd, insert: newText },
        selection: { anchor: cursorPos
          //, head: cursorPos + (newText.length - lineText.length)
        },
      });

      break;
    }
  }
}

// Toggle Clojure form comments (#_)
export function toggleClojureFormComment(view) {
  const { main } = view.state.selection;
  const cursorPos = main.head;
  const doc = view.state.doc;

  // Use regex to find or insert #_ under the cursor
  const line = doc.lineAt(cursorPos);
  const lineText = line.text;
  const match = /#_/g.exec(lineText);

  const matchStart = match && (line.from + match.index);
  const matchEnd = match && (matchStart + match.length);

  if (match && matchStart <= cursorPos && cursorPos <= (matchEnd + 1)) {
    // Remove #_ if found
    view.dispatch({
      changes: { from: line.from + match.index, to: line.from + match.index + 2, insert: "" },
      selection: { anchor: cursorPos  , head: cursorPos },
    });
  } else {
    // Insert #_ at cursor position
    view.dispatch({
      changes: { from: cursorPos, to: cursorPos, insert: "#_" },
      selection: { anchor: cursorPos, head: cursorPos },
    });
  }
}

export function toggleClojureDoubleFormComment(view) {
  const { main } = view.state.selection;
  const cursorPos = main.head;
  const doc = view.state.doc;

  // Use regex to find or insert #_#_ under the cursor
  const line = doc.lineAt(cursorPos);
  const lineText = line.text;
  const match = /#_#_/g.exec(lineText);

  const matchStart = match && (line.from + match.index);
  const matchEnd = match && (matchStart + match.length);

  if (match && matchStart <= cursorPos && cursorPos <= (matchEnd + 1)) {
    // Remove #_#_ if found
    view.dispatch({
      changes: { from: line.from + match.index, to: line.from + match.index + 4, insert: "" },
      selection: { anchor: cursorPos  , head: cursorPos },
    });
  } else {
    // Insert #_#_ at cursor position
    view.dispatch({
      changes: { from: cursorPos, to: cursorPos, insert: "#_#_" },
      selection: { anchor: cursorPos, head: cursorPos },
    });
  }
}

export function toggleTopLevelBang(view) {
  const cursorPos = view.state.selection.main.head;
  const doc = view.state.doc;

  // Find the line starting with "(" above the cursor
  let lineIndex = doc.lineAt(cursorPos).number;

  while (lineIndex > 0) {
    const line = doc.line(lineIndex);
    const lineText = line.text;

    if (lineText.match(/^!?\s?\(/)) {
      // Toggle "!" in front of "("
      const toggleChar = lineText.startsWith("!") ? "" : "! ";
      const newText = toggleChar + lineText.replace(/^!\s?/, "");

      const cursorShift = (
        lineText.startsWith("! ")
        ? -2
        : lineText.startsWith("!")
        ? -1
        : 2
      )

      view.dispatch({
        changes: { from: line.from, to: line.to, insert: newText },
        selection: { anchor: cursorPos + cursorShift }
      });

      break;
    }

    lineIndex--;
  }
}


export function toggleQueryExpression(view, range) {

  console.log("Toggling query expression");

  const {main} = view.state.selection;
  console.log("Cursor position:", main.head);

  const line = view.state.doc.lineAt(main.head);
  console.log("Current line:", line.text);

  // Search backwards for opening paren
  let i = main.head - line.from;
  console.log("Searching backwards from", i);
  while (i >= 0) {
    const char = line.text[i];
    console.log("Checking character:", char);
    if (char === '(') {
      console.log("Found opening paren at", i);
      break;
    }
    i--;
  }

  const expStart = line.from + i;
  console.log("Expression start:", expStart);

  // Find closing paren
  let parens = 1;
  i = main.head - line.from;
  console.log("Searching forwards from", i);
  while (i < line.text.length) {
    const char = line.text[i];
    console.log("Checking character:", char);
    if (char === '(') parens++;
    if (char === ')') parens--;

    if (parens === 0) {
      const expEnd = line.from + i + 1;
      console.log("Found closing paren at", i, "- expression end:", expEnd);

      // Check if already wrapped in (? ...)
      const wrapped = line.text.slice(expStart - line.from, expEnd - line.from).startsWith("(? ");

      if (wrapped) {
        console.log("Expression already wrapped - removing");
        // Remove existing (? ...)
        const unwrappedText = line.text.slice(expStart - line.from + 4, expEnd - line.from - 1);
        view.dispatch({
          changes: {
            from: expStart,
            to: expEnd,
            insert: unwrappedText
          }
        });
      } else {
        console.log("Expression not wrapped - adding");




        // Add (? ...) wrapping
        const newText = `(? ${line.text.slice(expStart - line.from, expEnd - line.from)})`;

        view.dispatch({
          changes: {
            from: expStart,
            to: expEnd,
            insert: newText
          }
        });
      }

      console.log("Finished toggling expression");
      break;
    }

    i++;
  }
}

function findNextLineWithPrefix(state, dir) {
  const { from, to } = state.selection.main;
  const line = state.doc.lineAt(from + 100);
  const start = line.from;
  const end = state.doc.length;

  for (let i = start + 3; i < end; i++) {
    const line = state.doc.lineAt(i)
    if (line.text.startsWith("'(")) {
      return state.doc.lineAt(line.from - 1);
    }
  }

  return null;
}

function findPrevLineWithPrefix(state) {
  const { from, to } = state.selection.main;
  const line = state.doc.lineAt(from - 10);
  const start = line.from;
  const end = line.to;

  for (let i = end - 3; i >= 1; i--) {
    const line = state.doc.lineAt(i);
    if (line.text.startsWith("'(")) {
      return state.doc.lineAt(line.from - 1);
    }
  }

  return null;
}


const jumpMargin = 2
const scrollMargin = -2

export const jumpToNextLineWithPrefix = (view) => {
  try {
    const n = findNextLineWithPrefix(view.state, 1);
    if (n) {
      view.dispatch({
        selection: EditorSelection.range(n.from - jumpMargin, n.from - jumpMargin),
        effects: EditorView.scrollIntoView(n.from - jumpMargin - scrollMargin, { y: "start", yMargin: 0 })
      })
    }
  } catch (e) {}
}

export const jumpToPrevLineWithPrefix = (view) => {
  try {
    const p = findPrevLineWithPrefix(view.state, 1);
    if (p) {
      view.dispatch({
        selection: EditorSelection.range(p.from - jumpMargin, p.from - jumpMargin),
        effects: EditorView.scrollIntoView(p.from - jumpMargin - scrollMargin, { y: "start", yMargin: 0 })
      })
    }
  } catch (e) {}
}

// EditorState.transactionFilter.of(tr => {
//   const nextLine = findNextLineWithPrefix(tr.state, 1);
//   return nextLine ? tr.setSelection(nextLine) : tr;
// });

// export const jumpToPrevLineWithPrefix = EditorState.transactionFilter.of(tr => {
//   const prevLine = findPrevLineWithPrefix(tr.state);
//   return prevLine ? tr.setSelection(prevLine) : tr;
// });

// export const linePrefixJumping = keymap({
//   'Ctrl-ArrowRight': jumpToNextLineWithPrefix,
//   'Ctrl-ArrowLeft': jumpToPrevLineWithPrefix,
// });


// export function lineWithPrefixJumping() {
//   return [linePrefixJumping];
// }





export function QPlugin(scheduleUpdate) {

  class QPluginClass {
    decorations = Decoration.none

    constructor(view) {
      this.update(view)
    }

    update(vu) {
      if (vu.docChanged || vu.viewportChanged) { // || (syntaxTree(vu.startState) != syntaxTree(vu.state))


        // prevent infite loops of rewrites triggering recomputation
        if (vu.transactions.length === 1 && !vu.transactions[0].annotation(isQRewrite)) {
          scheduleUpdate(vu.view)
        }

        // this.decorations = Decoration.set()
        // vu.dispatch({
        //   effects: addHighlight.of(this.decorations)
        // })
      }
    }
  }

  return ViewPlugin.fromClass(QPluginClass, {
    decorations: v => v.decorations,
  })
}


// (defclass HandleUpdate
//   ;;   (constructor [this] (super))
//   ;;   Object
//   ;;   (update
//   ;;    [this ^js vu]
//   ;;    (when (or (.-docChanged vu) ; on every keystroke/doc-change
//   ;;              (.-viewportChanged vu)
//   ;;              (not= (syntaxTree (.-startState vu))
//   ;;                    (syntaxTree (.-state vu))))
//   ;;      (if (= (str (.. vu -startState -doc))
//   ;;             (str (.. vu -state -doc)))
//   ;;        (prn :ignoring-view-update)
//   ;;        (schedule-update (.-view vu))))))



class IDWidget extends WidgetType {
  constructor(qmark) {
    super()
    this.qmark = qmark
  }

  toDOM(v) {
    const d = document.createElement("span")

    d.innerText = this.qmark
    d.className = "query-emacro-symbol"

    return d
  }
}


const idMatcher = new MatchDecorator({
  regexp: /(\?|\?>|\?>>)\s+:[0-9a-f]{8}\b/g,
  decoration: match => {

    const d = Decoration.replace({
      widget: new IDWidget(match[1])
    })

    d.point = true

    return d
  }
})

class IDDeco {
  placeholders;

  constructor(view) {
    this.placeholders = idMatcher.createDeco(view)
  }
  update(update) {
    this.placeholders = idMatcher.updateDeco(update, this.placeholders)
  }
}

export const hideIds = ViewPlugin.fromClass(IDDeco, {
  decorations: instance => instance.placeholders,
  provide: plugin => EditorView.atomicRanges.of(view => {
    return view.plugin(plugin)?.placeholders || Decoration.none
  })
})


//==========================================================
// Viz Widgets

class BlockWidget extends WidgetType {
  constructor(props) {
    super();
    this.props = props;
  }
  eq() {
    return false; // keeps initial rendered el intact, cm should not manage state inside els, just keep their identity stable
  }
  toDOM() {

    // const id = getWidgetID(this.props);
    // const el = widgetElements[id];

    const el = document.createElement("div")
    el.style.height = 100
    // el.width = 500
    el.style.backgroundColor = 'darkslateblue'

    this.props.renderTo(el)

    return el;
  }
  // ignoreEvent(e) {
  //   return true;
  // }
}

export const addWidget = StateEffect.define({
  map: ({ from, to }, change) => {
    return { from: change.mapPos(from), to: change.mapPos(to) };
  },
});

export const updateWidgets = (view, widgets) => {
  view.dispatch({ effects: addWidget.of(widgets) });
};

function getWidgets(widgetConfigs) {
  return (
    widgetConfigs
      // codemirror throws an error if we don't sort
      .sort((a, b) => a.to - b.to)
      .map((widgetConfig) => {
        return Decoration.widget({
          widget: new BlockWidget(widgetConfig),
          side: 0,
          block: true,
        }).range(widgetConfig.to);
      })
  );
}


const widgetField = StateField.define({
  create() {
    return Decoration.none;
  },
  update(widgets, tr) {
    widgets = widgets.map(tr.changes);
    for (let e of tr.effects) {
      if (e.is(addWidget)) {
        try {
          widgets = widgets.update({
            filter: () => false,
            add: getWidgets(e.value),
          });
        } catch (error) {
          console.log('widgetfield err', error);
        }
      }
    }
    return widgets;
  },
  provide: (f) => EditorView.decorations.from(f),
});

export const widgetPlugin = [widgetField];


window.updateWidgets = updateWidgets





