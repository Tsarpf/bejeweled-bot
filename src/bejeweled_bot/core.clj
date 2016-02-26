(ns bejeweled-bot.core
  (:import java.awt.Robot)
  (:import java.awt.event.InputEvent)
  (:gen-class))

(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
(import '[org.opencv.core Mat Size CvType Core Point Scalar]
        '[org.opencv.highgui Highgui]
        '[org.opencv.imgproc Imgproc])

;byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
;Mat mat = new Mat(width, height, CvType.CV_8UC3);
;mat.put(0, 0, data);

(use '[bejeweled-bot.pixel-sampler :only [sample-pixels]])
(use '[bejeweled-bot.gem-classifier :only [rgb-to-gems]])
(use '[bejeweled-bot.solver :only [solve]])

(defn -main
  "I'm a doc string"
  [& args]
  (def sourceImg (Highgui/imread "resources/whole.png"))
  (def diamond (Highgui/imread "resources/area.png"))
  (def targetCols (.cols diamond))
  (def targetRows (.rows diamond))
  (def cols (+ (- (.cols sourceImg) targetCols) 1))
  (def rows (+ (- (.rows sourceImg) targetRows) 1))
  (def result (Mat.))
  (.create result rows, cols, CvType/CV_32FC1)
  (def displayImg (Mat.))
  (.copyTo sourceImg displayImg)
  (Imgproc/matchTemplate sourceImg diamond result Imgproc/TM_CCOEFF_NORMED)
  ;(Imgproc/matchTemplate sourceImg diamond result Imgproc/TM_CCORR_NORMED)
  ;(def threshold 0.8)
  (Core/normalize result result 0 255 Core/NORM_MINMAX -1 (Mat.))
  ;(Highgui/imwrite "resources/output.png" result)

  (def floatArr (make-array Float/TYPE (.total result)))
  (.get result 0 0 floatArr)

  ;please refactor
  (def derp
    (reduce 
    (fn [acc x]
      {:max (max (:max acc) x)
        :idx (cond
               (> x (acc :max)) (acc :currIdx)
               :else (acc :idx))
        :currIdx (+ (acc :currIdx) 1)
        })
    {:max 0 :idx 0 :currIdx 0}
    floatArr))

  (def row (Math/floor (/ (:idx derp) cols)))
  (def col (mod (:idx derp) cols))

  (def seees (sample-pixels col row (+ col targetCols) (+ row targetRows) displayImg))
  (println (solve (rgb-to-gems seees))))





  ;;(Core/rectangle displayImg (Point. col row) (Point. (+ col targetCols) (+ row targetRows)) (Scalar. 0 0 255))
  ;(Core/rectangle result (Point. col row) (Point. (+ col targetCols) (+ row targetRows)) (Scalar. 0 0 255))
  ;;(Highgui/imwrite "resources/output.png" displayImg)
  ;(Highgui/imwrite "resources/output2.png" result)
;(time (-main))
