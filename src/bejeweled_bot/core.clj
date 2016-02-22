(ns bejeweled-bot.core
  (:import java.awt.Robot)
  (:import java.awt.event.InputEvent)
  (:gen-class))


(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
(import '[org.opencv.core Mat Size CvType]
        '[org.opencv.highgui Highgui]
        '[org.opencv.imgproc Imgproc])

;byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
;Mat mat = new Mat(width, height, CvType.CV_8UC3);
;mat.put(0, 0, data);

(defn -main
  "I'm a doc string"
  [& args]
  ;(def derp (Mat. 512 512 ns/CvType/CV_8UC3))
  (def sourceImg (Highgui/imread "resources/area.png"))
  (def diamond (Highgui/imread "resources/diamond.png"))
  (Highgui/imwrite "resources/output.png" diamond)
  (def cols (.cols sourceImg))
  (def rows (.rows sourceImg))
  (def result (Mat.))
  (.create result rows, cols, CvType/CV_32FC1)
  (def displayImg (Mat.))
  (.copyTo sourceImg displayImg)
  (Imgproc/matchTemplate sourceImg diamond result Imgproc/TM_CCOEFF_NORMED)
  (def threshold 0.8)

  ;  normalize( result, result, 0, 1, NORM_MINMAX, -1, Mat() );
  ;(Highgui/imwrite "resources/output.png" result)

  (let [robo (Robot.)]
    (.mousePress robo InputEvent/BUTTON1_MASK)
    (.mouseMove robo 1600 100)
    (.mouseRelease robo InputEvent/BUTTON1_MASK)
    ))

(-main)
