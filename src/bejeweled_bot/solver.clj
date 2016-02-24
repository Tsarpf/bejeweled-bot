(ns bejeweled-bot.solver)

(defn getFirst
  [gem-array]
  (first (drop-while #(= nil %) (solve gem-array))))

(defn solve
  "puikula"
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
  [x y gem-type gem-array]
  (let 
    [right-x (+ x 1)
     down-y (+ y 1)
     right-gem-type (get-in gem-array [(+ x 1) y])
     down-gem-type (get-in gem-array [x (+ y 1)])
     right-swap (assoc-in 
                     (assoc-in gem-array [(+ x 1) y] gem-type)
                     [x y] right-gem-type)
     down-swap (assoc-in 
                     (assoc-in gem-array [x (+ y 1)] gem-type)
                     [x y] down-gem-type)]
    (cond
      (would-match right-x y gem-type right-swap) {:x x :y y :dir "right" :debug 1 :gemtype gem-type}
      (would-match x down-y gem-type down-swap) {:x x :y y :dir "down" :debug 2 :gemtype gem-type}
      (would-match x y right-gem-type right-swap) {:x x :y y :dir "right" :debug 3 :gemtype right-gem-type}
      (would-match x y down-gem-type down-swap) {:x x :y y :dir "down" :debug 4 :gemtype down-gem-type}
      :else nil)))


(def x 0)
(def y 1)
(def down-y (+ y 1))
(def gem-type :opal)
(def down-gem-type (get-in testArray [x (+ y 1)]))
(def swapd (assoc-in (assoc-in testArray [x (+ y 1)] gem-type) [x y] down-gem-type))
(def down-swap swapd)
(def gem-array swapd)
(would-match x down-y gem-type down-swap) {:x x :y y :dir "down" :debug 2 :gemtype gem-type}
(defn would-match [x y gem-type gem-array]
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

(def testArray
  [[:ruby        :opal     :ruby       :ruby :topaz    :amethyst   :sapphire :sapphire]
   [:ruby        :diamond  :amethyst   :diamond    :opal     :smaragdine :sapphire :smaragdine]
   [:topaz       :diamond  :smaragdine :topaz      :opal     :diamond    :amethyst :ruby]
   [:sapphire    :opal     :sapphire   :topaz      :sapphire :smaragdine :ruby     :ruby]
   [:amethyst    :opal     :diamond    :sapphire   :amethyst :ruby       :opal     :opal]
   [:topaz       :amethyst :amethyst   :smaragdine :amethyst :topaz      :sapphire :amethyst]
   [:smaragdine  :topaz    :opal       :opal       :sapphire :ruby       :amethyst :sapphire]
   [:amethyst    :sapphire :diamond    :amethyst   :diamond  :topaz      :opal     :opal]])

(getFirst testArray)
