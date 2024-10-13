"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = rainbowBrackets;
var _view = require("@codemirror/view");
function _defineProperty(obj, key, value) { key = _toPropertyKey(key); if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }
function _toPropertyKey(arg) { var key = _toPrimitive(arg, "string"); return typeof key === "symbol" ? key : String(key); }
function _toPrimitive(input, hint) { if (typeof input !== "object" || input === null) return input; var prim = input[Symbol.toPrimitive]; if (prim !== undefined) { var res = prim.call(input, hint || "default"); if (typeof res !== "object") return res; throw new TypeError("@@toPrimitive must return a primitive value."); } return (hint === "string" ? String : Number)(input); }
function generateColors() {
  return ['red', 'orange', 'yellow', 'green', 'blue', 'indigo', 'violet'];
}
const rainbowBracketsPlugin = _view.ViewPlugin.fromClass(class {
  constructor(view) {
    _defineProperty(this, "decorations", void 0);
    this.decorations = this.getBracketDecorations(view);
  }
  update(update) {
    if (update.docChanged || update.selectionSet || update.viewportChanged) {
      this.decorations = this.getBracketDecorations(update.view);
    }
  }
  getBracketDecorations(view) {
    const {
      doc
    } = view.state;
    const decorations = [];
    const stack = [];
    const colors = generateColors();
    for (let pos = 0; pos < doc.length; pos += 1) {
      const char = doc.sliceString(pos, pos + 1);
      if (char === '(' || char === '[' || char === '{') {
        stack.push({
          type: char,
          from: pos
        });
      } else if (char === ')' || char === ']' || char === '}') {
        const open = stack.pop();
        if (open && open.type === this.getMatchingBracket(char)) {
          const color = colors[stack.length % colors.length];
          decorations.push(_view.Decoration.mark({
            class: `rainbow-bracket-${color}`
          }).range(open.from, open.from + 1), _view.Decoration.mark({
            class: `rainbow-bracket-${color}`
          }).range(pos, pos + 1));
        }
      }
    }
    decorations.sort((a, b) => a.from - b.from || a.startSide - b.startSide);
    return _view.Decoration.set(decorations);
  }

  // eslint-disable-next-line class-methods-use-this
  getMatchingBracket(closingBracket) {
    switch (closingBracket) {
      case ')':
        return '(';
      case ']':
        return '[';
      case '}':
        return '{';
      default:
        return null;
    }
  }
}, {
  decorations: v => v.decorations
});
function rainbowBrackets() {
  return [rainbowBracketsPlugin];
}
