(ns bejeweled-bot.solver)

(defn getFirst
  [gem-array]
  (first (drop-while #(= nil %) (solve gem-array))))

(defn solve
  [gem-array]
  (map-indexed
    (fn [colIdx col]
      (first 
        (drop-while 
          #(= nil %)
          (map-indexed
            (fn [rowIdx item]
              (check-matches colIdx rowIdx item gem-array))
            col))))
    gem-array))


(defn check-matches 
  "Check for matches from: 1. this left, 2. left to this, 3. this down, 4. down to this"
  [y x gem-type gem-array]
  (let 
    [right-x (+ x 1)
     down-y (+ y 1)
     right-gem-type (get-in gem-array [y right-x])
     down-gem-type (get-in gem-array [down-y x])
     right-swap (assoc-in 
                     (assoc-in gem-array [y right-x] gem-type)
                     [y x] right-gem-type)
     down-swap (assoc-in 
                     (assoc-in gem-array [down-y x] gem-type)
                     [y x] down-gem-type)]
    (cond
      (would-match right-x y gem-type right-swap) {:x x :y y :dir "right" :gemtype gem-type}
      (would-match x down-y gem-type down-swap) {:x x :y y :dir "down" :gemtype gem-type}
      (would-match x y right-gem-type right-swap) {:x x :y y :dir "right" :gemtype right-gem-type}
      (would-match x y down-gem-type down-swap) {:x x :y y :dir "down" :gemtype down-gem-type}
      :else nil)))


(defn would-match [y x gem-type gem-array]
  (and (not (= gem-type nil))
  (or
    (= 
      (get-in gem-array [(+ x 1) y])
      (get-in gem-array [(+ x 2) y])
      gem-type)
    (= 
      (get-in gem-array [(- x 1) y])
      (get-in gem-array [(- x 2) y])
      gem-type)
    (= 
      (get-in gem-array [(- x 1) y])
      (get-in gem-array [(+ x 1) y])
      gem-type)
    (= 
      (get-in gem-array [x (+ y 1)])
      (get-in gem-array [x (+ y 2)])
      gem-type)
    (= 
      (get-in gem-array [x (- y 1)])
      (get-in gem-array [x (- y 2)])
      gem-type)
    (= 
      (get-in gem-array [x (- y 1)])
      (get-in gem-array [x (+ y 1)])
      gem-type))))


;; Repl test code, where are the real tests?
;
;(def x 0)
;(def y 1)
;(def down-y (+ y 1))
;(def gem-type :opal)
;(def down-gem-type (get-in testArray [x (+ y 1)]))
;(def swapd (assoc-in (assoc-in testArray [x (+ y 1)] gem-type) [x y] down-gem-type))
;(def down-swap swapd)
;(def gem-array swapd)
;(would-match x down-y gem-type down-swap) {:x x :y y :dir "down" :debug 2 :gemtype gem-type}
;(def testArray
;  [[:ruby        :opal     :ruby       :diamond    :topaz    :amethyst   :sapphire :sapphire]
;   [:ruby        :diamond  :amethyst   :diamond    :opal     :smaragdine :sapphire :smaragdine]
;   [:topaz       :diamond  :smaragdine :topaz      :opal     :diamond    :amethyst :ruby]
;   [:sapphire    :opal     :sapphire   :topaz      :sapphire :smaragdine :ruby     :ruby]
;   [:amethyst    :opal     :diamond    :smaragdine :amethyst :ruby       :opal     :opal]
;   [:topaz       :amethyst :amethyst   :smaragdine :amethyst :topaz      :sapphire :amethyst]
;   [:smaragdine  :topaz    :opal       :opal       :sapphire :ruby       :amethyst :sapphire]
;   [:amethyst    :sapphire :diamond    :amethyst   :diamond  :topaz      :opal     :opal]])
;
;(getFirst testArray)
