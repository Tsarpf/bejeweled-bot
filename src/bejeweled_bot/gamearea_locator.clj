(ns bejeweled-bot.robot-moves
  (:import java.awt.Robot))


(defn find-area
  "Finds left upper corner of board area"
  []
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
  
  (def highest-index (first (apply max-key second (map vector (range) floatArr))))

  (def row (Math/floor (/ (:idx highest-index) cols)))
  (def col (mod (:idx highest-index) cols))
  (vector col row targetCols targetRows))
