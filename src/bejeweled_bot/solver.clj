(ns bejeweled-bot.solver)
(use 'bejeweled-bot.gems)

(defn solve
  "puikula"
  [gem-array]
  (map-indexed
  (fn [colIdx col]
    (map-indexed
      (fn [rowIdx item]
        (check-matches colIdx rowIdx item))
      col))
  gem-array))

(defn check-matches [x y gem-type]
  ())


(def testArray
  [[:ruby        :opal     :ruby       :diamond    :topaz    :amethyst   :sapphire :sapphire]
   [:ruby        :diamond  :amethyst   :diamond    :opal     :smaragdine :sapphire :smaragdine]
   [:topaz       :sapphire :smaragdine :topaz      :opal     :diamond    :amethyst :ruby]
   [:sapphire    :opal     :sapphire   :topaz      :sapphire :smaragdine :ruby     :ruby]
   [:amethyst    :opal     :diamond    :sapphire   :amethyst :ruby       :opal     :opal]
   [:topaz       :opal     :amethyst   :smaragdine :amethyst :topaz      :sapphire :amethyst]
   [:smaragdine  :opal     :opal       :opal       :sapphire :ruby       :amethyst :sapphire]
   [:amethyst    :opal     :diamond    :amethyst   :diamond  :topaz      :opal     :opal]])

(solve testArray)
