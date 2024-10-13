(ns small.ui
  (:require [reagent.core :as r]
            ["@lezer/highlight" :refer [tags]]))

(def white "var(--symbol, #fff)")
(def yellow "var(--function, #ffda21)")
(def green "var(--string, #93fc62)")
(def darkgreen "#5CA25D")
(def pink "var(--number, #fe517d)")
(def purple "var(--keyword, #c08fff)")
(def blue "#329efd")
(def gray "#999999")


(def red "#ff0000")

(defn bracket [level fallback]
  (str "var(--bracket-" level ", " fallback ")"))

(def editor-theme
  {".cm-content" {:white-space "pre-wrap"
                  :padding "0"
                  :flex "1 1 0"
                  #_#_:font-family "Fira Code"
                  :color "#fff"
                  :caret-color "#f6c844"}
   ".cm-cursor, .cm-dropCursor" {:border-left-color "#f6c844" :border-left-width "2px"}
   "&.cm-focused > .cm-scroller > .cm-selectionLayer .cm-selectionBackground, .cm-selectionBackground, .cm-content ::selection"
   {:background-color "var(--selection, #553592)"}
   "&.cm-focused" {:outline "0 !important"}
   ".cm-line" {:padding "0 9px"
               :line-height "1.6"
               :font-size "16px"}
  ;;  ".cm-cursor" {:color "lime"}
   "& .cm-matchingBracket" {:background-color "var(--bracket-match, rgba(94, 106, 85, 0.8))"
                            :color "inherit"}
   "& .cm-selectionMatch" {:background-color "var(--selection-match, #accea04f)"}
   ".cm-activeLine" {:background-color "var(--active-line, rgba(150, 150, 121, 0.2))"}
   ".cm-activeLineGutter" {:color "#989898" #_#_:font-weight :bold :background-color "#191717"}
   ".cm-gutters" {:border-right "1px solid #fff"
                  :visibility "var(--display-line-numbers)"}
   ".cm-lineNumbers" {:min-width "4ch"
                      :text-align :right}
   ".cm-gutter" {:background-color "#0e0d0d" :color "#4f4f4f"}
   ".cm-gutterElement" {:padding "0 9px"
                        :line-height "1.6"
                        :font-size "16px"}

   ".rainbow-bracket-red" {:color (bracket 1 white)}
   ".rainbow-bracket-red > span" {:color (bracket 1 white)}
   ".rainbow-bracket-orange" {:color (bracket 2 blue)}
   ".rainbow-bracket-orange > span" {:color (bracket 2 blue)}
   ".rainbow-bracket-yellow" {:color (bracket 3 pink)}
   ".rainbow-bracket-yellow > span" {:color (bracket 3 pink)}
   ".rainbow-bracket-green" {:color (bracket 4 darkgreen)}
   ".rainbow-bracket-green > span" {:color (bracket 4 darkgreen)}
   ".rainbow-bracket-blue" {:color (bracket 5 purple)}
   ".rainbow-bracket-blue > span" {:color (bracket 5 purple)}
   ".rainbow-bracket-indigo" {:color (bracket 6 gray)}
   ".rainbow-bracket-indigo > span" {:color (bracket 6 gray)}
   ".rainbow-bracket-violet" {:color (bracket 7 purple)}
   ".rainbow-bracket-violet > span" {:color (bracket 8 purple)}})

(def editor-highlight
  (let [t tags]
    [{:tag [t.keyword]
      :color yellow}
     {:tag [t.name t.deleted t.character t.propertyName t.macroName]
      :color green}
     {:tag [(t.function t.variableName) t.labelName],
      :color red}
     {:tag [t.color (t.constant t.name) (t.standard t.name)],
      :color red}
     {:tag [(t.definition t.name) t.separator],
      :color white}
     {:tag [t.typeName t.bool t.className t.number t.changed t.annotation t.modifier t.self t.namespace],
      :color pink}
     {:tag [t.operator t.operatorKeyword t.url t.escape t.regexp t.link (t.special t.string)],
      :color green}
     {:tag [t.meta t.comment]
      :fontWeight :bold
      :filter "saturate(0.9)"
      :opacity 0.7}
     {:tag t.strong,
      :fontWeight "bold"}
     {:tag t.emphasis,
      :fontStyle "italic"}
     {:tag t.strikethrough,
      :textDecoration "line-through"}
     {:tag t.link,
      :color red,
      :textDecoration "underline"}
     {:tag t.heading,
      :fontWeight "bold",
      :color red}
     {:tag [t.atom (t.special t.variableName)],
      :color purple}
     {:tag [t.processingInstruction t.string t.inserted],
      :color green}
     {:tag t.invalid,
      :color red}]))

(defn error-boundary [_id _comp]
  (let [error (r/atom nil)
        retry-timeout (r/atom nil)]
    (r/create-class
     {:component-did-catch
      (fn [_this e info]
        (js/console.log e info)
        (reset! error e)
        (when @retry-timeout
          (js/clearTimeout @retry-timeout))
        (reset! retry-timeout
                (js/setTimeout (fn [] (reset! error nil)) 5000)))
      :get-derived-state-from-error
      (fn [e]
        (reset! error e)
        #js {})
      :reagent-render
      (fn [id comp]
        (if @error
          [:pre
           [:b (str id " " @error)]]
          comp))})))

(defn linux? []
  (some? (re-find #"(Linux)|(X11)" js/navigator.userAgent)))

(defn mac? []
  (and (not (linux?))
       (some? (re-find #"(Mac)|(iPhone)|(iPad)|(iPod)" js/navigator.platform))))

(def cmd (if (mac?) "⌘" "Ctrl"))
(def shift "⇧")
(def ret "↵")
(def alt "⌥")
(def ctrl "⌃")


(defn floating [children]
  [:div
   {:style
    {:position :fixed
     :background "#313030"
     :bottom 0
     :right 0
     :padding 15}}
   children])

(defn k [& ks]
  [:div {:style {:display :flex
                 :font-size "90%"
                 :align-items :flex-start
                 :justify-content :space-between}}
   [:dt {:style {:margin 0}}
    (->> (butlast ks)
         (interpose
          [:span
           " "
           [:span {:style {:opacity 0.7
                           :font-size "80%"}}
            "+"]
           " "])
         (into [:<>]))]
   [:dl {:style {:opacity 0.8
                 :margin 0
                 :padding-left 15}}
    (last ks)]])

(defn shortcuts []
  (let [hover? (r/atom false)]
    (fn []
      [:div {:style {:opacity 0.8}
             :on-mouse-enter #(reset! hover? true)
             :on-mouse-leave #(reset! hover? false)}
       [floating
        [:div
         [k cmd "1" "toggle liveness"]
         [k alt "?" "toggle probe"]
         [k cmd "s" "eval all"]
         (when @hover?
           [:div
            [:hr]
            #_
            [:div {:style {:opacity 0.5}}
             [k alt ret "extract"]
             [k "%" "as-if"]
             [k cmd ret "eval"]
             [:hr]]

            [k cmd "/" "comment line"]
            [k cmd "3" "comment block"]
            [k cmd "4" "2x comment block"]
            [k cmd ";" "gen id"]
            [k cmd "↑ ↓" "inc/dec number"]
            [k cmd "b" "toggle boolean"]
            [:hr]
            [k alt "↑ ↓" "expand sel"]
            [k ctrl "→ ←" "slurp/barf fwd"]
            [k ctrl alt "→ ←" "slurp/barf back"]
            [k alt "s" "unwrap"]])]]])))
