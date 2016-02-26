(ns bejeweled-bot.gamearea-locator
  (:import java.awt.Robot)
  (:import org.opencv.highgui.Highgui)
  (:import org.opencv.imgproc.Imgproc)
  (:import org.opencv.core.Mat)
  (:import org.opencv.core.Core)
  (:import java.awt.Rectangle)
  (:import java.awt.image.BufferedImage)
  (:import java.awt.image.DataBufferInt)
  (:import java.awt.Toolkit))

(defn mat-from-buffer
  [image]
  (int-array (.getData (cast DataBufferInt (.getDataBuffer (.getRaster image))))))


(def int-val 0x00ffffff)
(defn int-to-bytes
  [int-val]
  [(byte (- (bit-and int-val 0x000000ff) 128))
   (byte (- (bit-shift-right (bit-and int-val 0x0000ff00) 8) 128))
   (byte (- (bit-shift-right (bit-and int-val 0x00ff0000) 16) 128))])
   ;(byte (bit-shift-right int-val 24))]) ; we don't use alpha

(defn find-area
  "Finds left upper corner of board area"
  []
  ;(def sourceImg (Highgui/imread "resources/whole.png"))
  (def robo (Robot.))
  (def screenshot (.createScreenCapture robo (Rectangle. (.getScreenSize (Toolkit/getDefaultToolkit)))))
  (def image-ints (mat-from-buffer screenshot))
  (def image-bytes (byte-array (mapcat int-to-bytes image-ints)))
  (def sourceImg (Mat. (.getWidth screenshot) (.getHeight screenshot) CvType/CV_8UC3))
  (.put sourceImg 0 0 image-bytes)

  (def targetArea (Highgui/imread "resources/area.png"))
  (def targetCols (.cols targetArea))
  (def targetRows (.rows targetArea))
  (def cols (+ (- (.cols sourceImg) targetCols) 1))
  (def rows (+ (- (.rows sourceImg) targetRows) 1))
  (def result (Mat.))
  (.create result rows, cols, CvType/CV_32FC1)
  ;(def displayImg (Mat.))
  ;(.copyTo sourceImg displayImg)
  (Imgproc/matchTemplate sourceImg targetArea result Imgproc/TM_CCOEFF_NORMED)
  ;(Imgproc/matchTemplate sourceImg targetArea result Imgproc/TM_CCORR_NORMED)
  ;(def threshold 0.8)
  (Core/normalize result result 0 255 Core/NORM_MINMAX -1 (Mat.))
  ;(Highgui/imwrite "resources/output.png" result)

  (def floatArr (make-array Float/TYPE (.total result)))
  (.get result 0 0 floatArr)
  
  (def highest-index (first (apply max-key second (map vector (range) floatArr))))

  (def row (Math/floor (/ highest-index cols)))
  (def col (mod highest-index cols))
  {:col col :row row :area-columns targetCols :area-rows targetRows})

