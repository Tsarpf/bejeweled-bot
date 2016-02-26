(ns bejeweled-bot.gamearea-locator
  (:import java.awt.Robot)
  (:import org.opencv.highgui.Highgui)
  (:import java.awt.Rectangle)
  (:import java.awt.image.BufferedImage)
  (:import java.awt.image.DataBufferByte)
  (:import java.awt.Toolkit))

;        '[org.opencv.highgui Highgui]

(defn mat-from-buffer
  [image]
  (cast DataBufferByte (.getDataBuffer (.getRaster image))))

;byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
;Mat mat = new Mat(width, height, CvType.CV_8UC3);
;mat.put(0, 0, data);

(defn find-area
  "Finds left upper corner of board area"
  []
  ;(def sourceImg (Highgui/imread "resources/whole.png"))
  (def robo (Robot.))
  (def screenshot (.createScreenCapture robo (Rectangle. (.getScreenSize (Toolkit/getDefaultToolkit)))))
  (def bgrScreenshot (BufferedImage. (.getWidth screenshot) (.getHeight screenshot) (BufferedImage/TYPE_3BYTE_BGR)))
  (.drawImage (.getGraphics bgrScreenshot) screenshot, 0, 0, ImageObserver) ;imageobserver is asynchronously notified about the progress of drawing the image. TBD...
  
  (def sourceImg (mat-from-buffer screenshot))
  (def targetArea (Highgui/imread "resources/area.png"))
  (def targetCols (.cols targetArea))
  (def targetRows (.rows targetArea))
  (def cols (+ (- (.cols sourceImg) targetCols) 1))
  (def rows (+ (- (.rows sourceImg) targetRows) 1))
  (def result (Mat.))
  (.create result rows, cols, CvType/CV_32FC1)
  (def displayImg (Mat.))
  (.copyTo sourceImg displayImg)
  (Imgproc/matchTemplate sourceImg targetArea result Imgproc/TM_CCOEFF_NORMED)
  ;(Imgproc/matchTemplate sourceImg targetArea result Imgproc/TM_CCORR_NORMED)
  ;(def threshold 0.8)
  (Core/normalize result result 0 255 Core/NORM_MINMAX -1 (Mat.))
  ;(Highgui/imwrite "resources/output.png" result)

  (def floatArr (make-array Float/TYPE (.total result)))
  (.get result 0 0 floatArr)
  
  (def highest-index (first (apply max-key second (map vector (range) floatArr))))

  (def row (Math/floor (/ (:idx highest-index) cols)))
  (def col (mod (:idx highest-index) cols))
  (vector col row targetCols targetRows))
