(ns bejeweled-bot.core
  (:import java.awt.Robot)
  (:import java.awt.Toolkit)
  (:import java.awt.Rectangle)
  (:gen-class))

(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)

(use '[bejeweled-bot.game-area-locator :only [find-area]])
(use '[bejeweled-bot.pixel-sampler :only [sample-pixels]])
(use '[bejeweled-bot.gem-classifier :only [rgb-to-gems]])
(use '[bejeweled-bot.solver :only [solve]])
(use '[bejeweled-bot.robot-moves :only [drag]])

(def robo (Robot.))
(defn take-screenshot 
  [x y cols rows]
  (println "took shot")
  (.createScreenCapture robo (Rectangle. x y cols rows)))

(defn -main
  "I'm a doc string"
  [& args]
  (println "searching for area...")
  (def area (find-area))
  (def col (area :col))
  (def row (area :row))
  (def targetCols (area :area-columns))
  (def targetRows (area :area-rows))

  (println (str "area found"))

  ;screenshot
  ;sample
  ;classify
  ;solve
  ;move
  (def finished (promise))
  (def timer (future (Thread/sleep 910000) (deliver finished true)))
    (dorun (map 
      (fn [screenshot]
        (let 
          [samples (sample-pixels row col (+ row targetRows) (+ col targetCols) screenshot)
           gems (rgb-to-gems samples)
           moves (solve gems)]
          (dorun (map 
            (fn 
              [item]
              (Thread/sleep 50)
              (drag item col row))
            moves))))
      (take-while 
        (fn [item] (not (realized? finished)))
        (repeatedly 
          #(take-screenshot col row targetCols targetRows))))))

;;(Core/rectangle displayImg (Point. col row) (Point. (+ col targetCols) (+ row targetRows)) (Scalar. 0 0 255))
;(Core/rectangle result (Point. col row) (Point. (+ col targetCols) (+ row targetRows)) (Scalar. 0 0 255))
;;(Highgui/imwrite "resources/output.png" displayImg)
;(Highgui/imwrite "resources/output2.png" result)
;(time (-main))
