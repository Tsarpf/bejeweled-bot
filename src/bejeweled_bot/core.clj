(ns bejeweled-bot.core
  (:import java.awt.Robot)
  (:import java.awt.event.InputEvent)
  (:import '[org.opencv.core Mat Size Highgui])
  (:import '[org.opencv.highgui Highgui])
  (:gen-class))

(defn -main
  "I'm a doc string"
  [& args]
  (def sourceimg (Highgui/imread "resources/area.png"))
  (def diamond (Highgui/imread "resources/diamond.png"))
  (let [robo (Robot.)]
    (.mousePress robo InputEvent/BUTTON1_MASK)
    (.mouseMove robo 1600 100)
    (.mouseRelease robo InputEvent/BUTTON1_MASK)
    ))

(-main)
