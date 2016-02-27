(ns bejeweled-bot.robot-moves
  (:import java.awt.event.InputEvent)
  (:import org.opencv.core.Core)
  (:import org.opencv.core.Point)
  (:import org.opencv.core.Scalar)
  (:import org.opencv.highgui.Highgui)
  (:import java.awt.Robot))

(def robo (Robot.))
(def jewel-length 40)
(def offset-x 43)
(def offset-y 39)

(defn debug-draw
  [from to img]
  (
    (Core/rectangle img (Point. (from 0) (to 0)) (Point. (to 0) (to 1)) (Scalar. 0 0 255))
    (Highgui/imwrite "resources/move-debug-output.png" img))
   )

(defn drag
  "Drags mouse according to given positions"
  [move area-offset-x area-offset-y]
  (println move)

  (let [x (+ area-offset-x offset-x (* jewel-length (move :x)))
        y (+ area-offset-y offset-y (* jewel-length (move :y)))
        to-x (+ jewel-length x)
        to-y (+ jewel-length y)]
    (.mouseMove robo x y)
    (.mousePress robo InputEvent/BUTTON1_MASK)
    (if (= (get move :dir) "right")
      (.mouseMove robo to-x y)
      (.mouseMove robo x to-y))
    (.mouseRelease robo InputEvent/BUTTON1_MASK)
    ;(debug-draw [x y] [to-x to-y] img)
    "moved"))

