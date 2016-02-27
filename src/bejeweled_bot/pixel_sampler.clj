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
  ;(println (str "asking for: " xy))
  (let [color-int (.getRGB image (xy 0) (xy 1))
        color (Color. color-int)
        red (.getRed color)
        green (.getGreen color)
        blue (.getBlue color)]
    ;(println "cheers")
    {:red red :green green :blue blue}))
  
(defn sample-pixels
  ""
  [xl yl xr yr image]
  ;(println (str "sampling: xl " xl " yl " yl " xr " xr " yr "yr))
  (def robo (Robot.))
  (def jewel-length 40)
  (def offset-x 43)
  (def offset-y 39)

  (def sample-coords (map-indexed
    (fn 
      [idx item]
      (map-indexed
        (fn
          [idxfdsa itemfdsa]
          [itemfdsa item])
        (apply vector (range offset-x (+ offset-x (* 8 jewel-length)) jewel-length))))
    (apply vector (range offset-y (+ offset-y (* 8 jewel-length)) jewel-length))))
  (map 
    (fn [row]
      (map
        (fn [x] 
          (get-samples x image))
          ;(Core/circle image (Point. (x 0) (x 1)) 5 (Scalar. 0 0 255)))
        row
        ))
    sample-coords))
