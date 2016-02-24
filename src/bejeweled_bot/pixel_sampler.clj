(ns bejeweled-bot.pixel-sampler
  (:import java.awt.Rectangle)
  (:import java.awt.Image)
  (:import java.awt.Robot)
  (:import javax.imageio.ImageIO)
  (:import java.io.File)
  (:gen-class))

(defn get-samples
  "Gets pixel color from given coordinates"
  [xy]
  ;(def image (.createScreenCapture robo (Rectangle. xl yl xr yr)))
  (def image (ImageIO/read (File. "resources/test-screenshot.png")))
  (def color (.getRGB image (xy 0) (xy 1)))
  (xy 0)(xy 1))

(defn sample-pixels
  ""
  [xl yl xr yr]
  (def robo (Robot.))
  (def jewel-length 40)
  (def offset-x 43)
  (def offset-y 39)

  (map-indexed
    (fn 
      [idx item]
      (map-indexed
        (fn
          [idxfdsa itemfdsa]
          ([idx idxfdsa]))
        (apply vector (range offset-x (+ offset-x (* 8 jewel-length)) jewel-length)))
      (apply vector (range offset-y (+ offset-y (* 8 jewel-length)) jewel-length))
      ))
