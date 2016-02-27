(ns bejeweled-bot.pixel-sampler
  (:import java.awt.Rectangle)
  (:import java.awt.Image)
  (:import java.awt.Robot)
  (:import java.awt.Color)
  (:import javax.imageio.ImageIO)
  (:import java.io.File))

(import '[org.opencv.core Mat Size CvType Core Point Scalar]
        '[org.opencv.highgui Highgui]
        '[org.opencv.imgproc Imgproc])

(defn get-samples
  "Gets pixel color from given coordinates"
  [xy image]
  (let [color-int (.getRGB image (xy 0) (xy 1))
        color (Color. color-int)
        red (.getRed color)
        green (.getGreen color)
        blue (.getBlue color)]
    {:red red :green green :blue blue}))
  
(defn sample-pixels
  ""
  [xl yl xr yr image]
  (def robo (Robot.))
  (def jewel-length 40)
  (def offset-x 43)
  (def offset-y 39)

  (def sample-coords (map-indexed
    (fn 
      [idx y]
      (map-indexed
        (fn
          [idxfdsa x]
          [x y])
        (apply vector (range offset-x (+ offset-x (* 8 jewel-length)) jewel-length))))
    (apply vector (range offset-y (+ offset-y (* 8 jewel-length)) jewel-length))))
  (map 
    (fn [row]
      (map
        (fn [x] 
          (get-samples x image))
        row
        ))
    sample-coords))
