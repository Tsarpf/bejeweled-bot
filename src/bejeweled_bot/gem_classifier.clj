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
  "Takes a map with red/green/blue values, returns the closest gem type match"
  [item]
  (def deltas 
    (reduce
      (fn [acc gem]
        (assoc acc (first gem)
               (+
                (abs (- (get item :red)(:red (second gem))))
                (abs (- (get item :green)(:green (second gem))))
                (abs (- (get item :blue)(:blue (second gem)))))))
      nil
      rgb-to-gem))

  ;get the gem type with smallest delta. Remove 'first' to return the actual delta as well.
  (first (apply min-key second deltas)))

(defn rgb-to-gems
  [rgb-array]
  (vec (map
    (fn [row]
      (vec (map
        (fn [item]
          (classify item))
        row)))
    rgb-array)))
