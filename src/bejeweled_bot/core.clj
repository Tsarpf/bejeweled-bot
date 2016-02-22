(ns bejeweled-bot.core
  (:import java.awt.Robot)
  (:import java.awt.event.InputEvent)
  (:gen-class))


(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
(import '[org.opencv.core Mat Size CvType Core]
        '[org.opencv.highgui Highgui]
        '[org.opencv.imgproc Imgproc])

;byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
;Mat mat = new Mat(width, height, CvType.CV_8UC3);
;mat.put(0, 0, data);

(defn -main
  "I'm a doc string"
  [& args]
  (def sourceImg (Highgui/imread "resources/area.png"))
  (def diamond (Highgui/imread "resources/diamond.png"))
  (def cols (+ (- (.cols sourceImg) (.cols diamond)) 1))
  (def rows (+ (- (.rows sourceImg) (.rows diamond)) 1))
  (def result (Mat.))
  (.create result rows, cols, CvType/CV_32FC1)
  (def displayImg (Mat.))
  (.copyTo sourceImg displayImg)
  (Imgproc/matchTemplate sourceImg diamond result Imgproc/TM_CCOEFF_NORMED)
  ;(Imgproc/matchTemplate sourceImg diamond result Imgproc/TM_CCORR_NORMED)
  (def threshold 0.8)

  (Core/normalize result result 0 255 Core/NORM_MINMAX -1 (Mat.))
  (Highgui/imwrite "resources/output.png" result)

  ;(let [robo (Robot.)]
  ;  (.mousePress robo InputEvent/BUTTON1_MASK)
  ;  (.mouseMove robo 1600 100)
  ;  (.mouseRelease robo InputEvent/BUTTON1_MASK)
  ;  ))

(-main)
