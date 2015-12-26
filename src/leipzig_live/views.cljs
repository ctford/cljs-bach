(ns leipzig-live.views
  (:require
    [leipzig-live.actions :as action]
    [quil.core :as quil :include-macros true]
    [quil.middleware :as middleware]
    [reagent.core :as reagent]))

(defn scale [k to ms]
  (let [maximum (->> ms (map k) (apply max))
        minimum (->> ms (map k) (apply min))
        range (- maximum minimum)]
    (->> ms
         (map #(update % k - minimum))
         (map #(update % k / range))
         (map #(update % k * to)))))

(defn draw-graph [state-atom]
  (quil/sketch :setup (constantly nil)
               :draw (fn [_]
                       (quil/background 255)
                       (let [scaled (->> (:music @state-atom)
                                         (scale :time 560)
                                         (scale :pitch 260))]
                       (doseq [{:keys [time pitch]} scaled]
                         (quil/ellipse
                           (-> time (+ 20))
                           (-> pitch (+ 20) - (+ 300))
                           30
                           30))))
               :host "graph"
               :no-start true
               :middleware [middleware/fun-mode]
               :size [600 300]))

(defn graph [handle! state-atom]
  (reagent/create-class
    {:render (fn [] [:canvas#graph {:width 300 :height 300}])
     :component-did-mount #(draw-graph state-atom)}))

(defn editor-did-mount [handle! _]
  (fn [this]
    (let [pane (.fromTextArea
                 js/CodeMirror
                 (reagent/dom-node this)
                 #js {:mode "clojure"})]
      (.on pane "change" #(-> % .getValue action/->Refresh handle!)))))

(defn editor [handle! state]
  (reagent/create-class
    {:render (fn [] [:textarea {:default-value (:text state)
                                :auto-complete "off"}])
     :component-did-mount (editor-did-mount handle! state)}))

(defn home-page [handle! state-atom]
  (let [state @state-atom
        button (if-not (:looping? state)
                 [:button {:on-click #(handle! (action/->Play))} "Play"]
                 [:button {:on-click #(handle! (action/->Stop))} "Stop"])
        error (:error state)]
    [:div
     [:div {:class "graph"} [graph handle! state-atom]]
     [:div {:class "controls"} button]
     [:div {:class (if error "error" "")} [editor handle! state]]]))
