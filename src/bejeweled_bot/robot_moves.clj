(ns bejeweled-bot.robot-moves
  (:import java.awt.event.InputEvent)
  (:import java.awt.Robot))

(def robo (Robot.))
(def jewel-length 40)
(def offset-x 43)
(def offset-y 39)

(defn drag
  "Drags mouse according to given positions"
  [move area-offset-x area-offset-y]
  (println move)
  (def x (+ area-offset-x offset-x (* jewel-length (move :x))))
  (def y (+ area-offset-y offset-y (* jewel-length (move :y))))
  (.mouseMove robo x y)
  (.mousePress robo InputEvent/BUTTON1_MASK)
  (if (= (get move :dir) "right")
    (.mouseMove robo (+ jewel-length x) y)
    (.mouseMove robo x (+ jewel-length y)))
  (.mouseRelease robo InputEvent/BUTTON1_MASK)
  "moved")

