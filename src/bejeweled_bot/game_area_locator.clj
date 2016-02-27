(ns bejeweled-bot.game-area-locator
  (:import java.awt.Robot)
  (:import org.opencv.highgui.Highgui)
  (:import org.opencv.imgproc.Imgproc)
  (:import org.opencv.core.CvType)
  (:import org.opencv.core.Mat)
  (:import org.opencv.core.Core)
  (:import org.opencv.core.Point)
  (:import org.opencv.core.Scalar)
  (:import java.awt.Rectangle)
  (:import java.awt.image.BufferedImage)
  (:import java.awt.image.DataBufferInt)
  (:import java.awt.Toolkit))

(def robo (Robot.))

(defn ints-from-image
  [image]
  (int-array (.getData (cast DataBufferInt (.getDataBuffer (.getRaster image))))))


(defn int-to-bytes
  [int-val]
  [
   (unchecked-byte (bit-shift-right (bit-and int-val 0x000000ff) 0))
   (unchecked-byte (bit-shift-right (bit-and int-val 0x0000ff00) 8))
   (unchecked-byte (bit-shift-right (bit-and int-val 0x00ff0000) 16))
   ])

(defn get-screen []
  (.createScreenCapture robo (Rectangle. (.getScreenSize (Toolkit/getDefaultToolkit)))))

(defn get-bytes [int-seq]
  (byte-array (mapcat int-to-bytes int-seq)))

(defn get-image-matrix []
  (let 
    [screenshot (get-screen)
     image-ints (ints-from-image screenshot)
     image-bytes (get-bytes image-ints)
     sourceImg (Mat. (.getHeight screenshot) (.getWidth screenshot) CvType/CV_8UC3)]
    (.put sourceImg 0 0 image-bytes)
    sourceImg))

(defn find-area
  "Finds left upper corner of board area"
  []
  ;Too damn many defs!
  (def sourceImg (get-image-matrix))
  (def targetArea (Highgui/imread "resources/area.png"))
  (def targetCols (.cols targetArea))
  (def targetRows (.rows targetArea))
  (def cols (+ (- (.cols sourceImg) targetCols) 1))
  (def rows (+ (- (.rows sourceImg) targetRows) 1))
  (def result (Mat.))
  (.create result rows cols CvType/CV_8UC3)
  (Imgproc/matchTemplate sourceImg targetArea result Imgproc/TM_CCOEFF_NORMED) ;or Imgproc/TM_CCORR_NORMED
  (Core/normalize result result 0 255 Core/NORM_MINMAX -1 (Mat.))

  (def floatArr (make-array Float/TYPE (.total result)))
  (.get result 0 0 floatArr)
  
  (def highest-index (first (apply max-key second (map vector (range) floatArr))))

  (def row (Math/floor (/ highest-index cols)))
  (def col (mod highest-index cols))
  (let [debug-mat (Mat.)]
    (.create debug-mat (.rows sourceImg) (.cols sourceImg) CvType/CV_8UC3)
    (.copyTo sourceImg debug-mat)
    (Core/rectangle debug-mat (Point. col row) (Point. (+ col targetCols) (+ row targetRows)) (Scalar. 0 0 255))
    (Highgui/imwrite "resources/debug-output.png" debug-mat))

  {:col col :row row :area-columns targetCols :area-rows targetRows})
