(ns bejeweled-bot.core
  (:import java.awt.Robot)
  (:import java.awt.event.InputEvent)
  (:gen-class))

(defn -main
  "I'm a doc string"
  [& args]
  (println "Hello, World!")
  (let [robo (Robot.)]
    (.mousePress robo InputEvent/BUTTON1_MASK)
    (.mouseMove robo 1600 100)
    (.mouseRelease robo InputEvent/BUTTON1_MASK)
    ))

;(-main)
