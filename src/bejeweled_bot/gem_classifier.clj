(ns bejeweled-bot.gem-classifier)

(def rgb-to-gem
  {:sapphire    {:red 230 :green 101 :blue 33}
   :topaz       {:red 254 :green 245 :blue 35}
   :ruby        {:red 249 :green 26  :blue 54}
   :amethyst    {:red 239 :green 15  :blue 239}
   :opal        {:red 224 :green 224 :blue 224}
   :smaragdine  {:red 16  :green 164 :blue 33}
   :diamond     {:red 16  :green 139 :blue 254}})

(defn abs [n] (max n (- n)))

(defn classify
  "Takes a map with red/green/blue values, returns the gem type or nil if no gem types match"
  [item]
  (def deltas 
    (reduce 
      (fn [acc gem]
        (assoc acc gem
               (+
                (abs (- (get item :red)(get (get rgb-to-gem gem) :red)))
                (abs (- (get item :green)(get (get rgb-to-gem gem) :green)))
                (abs (- (get item :blue)(get (get rgb-to-gem gem) :blue))))))
      rgb-to-gem))
  (println
    (reduce
      (fn [x y]
        (min (get deltas x)(get deltas y)))
      255 ;some relatively big number 
      deltas)))

(defn rgb-to-gems
  [rgb-array]

  (map
    (fn [row]
      (fn [item]
        (classify item))
      row)
    rgb-array))
