(ns bejeweled-bot.core
  (:gen-class))

(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
(import '[org.opencv.core Mat Size CvType Core Point Scalar]
        '[org.opencv.highgui Highgui]
        '[org.opencv.imgproc Imgproc])

(use '[bejeweled-bot.gamearea-locator :only [find-area]])
(use '[bejeweled-bot.pixel-sampler :only [sample-pixels]])
(use '[bejeweled-bot.gem-classifier :only [rgb-to-gems]])
(use '[bejeweled-bot.solver :only [solve]])
(use '[bejeweled-bot.robot-moves :only [drag]])

(defn -main
  "I'm a doc string"
  [& args]
  (def area find-area)
  (def col (get find-area 0))
  (def row (get find-area 1))
  (def targetCols (get find-area 2))
  (def targetRows (get find-area 3))
  (def seees (sample-pixels row col (+ col targetCols) (+ row targetRows) displayImg)) ;displayImg needs a value
  (println (solve (rgb-to-gems seees)))
  (println (map (fn [x] (drag (solve (rgb-to-gems seees)))))))

  ;;(Core/rectangle displayImg (Point. col row) (Point. (+ col targetCols) (+ row targetRows)) (Scalar. 0 0 255))
  ;(Core/rectangle result (Point. col row) (Point. (+ col targetCols) (+ row targetRows)) (Scalar. 0 0 255))
  ;;(Highgui/imwrite "resources/output.png" displayImg)
  ;(Highgui/imwrite "resources/output2.png" result)
;(time (-main))
