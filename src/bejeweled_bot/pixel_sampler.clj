(ns bejeweled-bot.pixel-sampler
  (:import java.awt.Rectangle)
  (:import java.awt.Image)
  (:import java.awt.Robot)
  (:import javax.imageio.ImageIO)
  (:import java.io.File)
  (:gen-class))

(def image (ImageIO/read (File. "resources/whole.png")))
(def xy [37 49])
(defn get-samples
  "Gets pixel color from given coordinates"
  [xy]
  ;(def image (.createScreenCapture robo (Rectangle. xl yl xr yr)))
  (.getRGB image (xy 0) (xy 1)))
  ;(xy 0)(xy 1))

(def targetCols 328)
(def targetRows 319)
(def col 36)
(def row 120)

(defn sample-pixels
  ""
  [xl yl xr yr]
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
    (apply vector (range offset-y (+ offset-y (* 8 jewel-length)) jewel-length))
    ))

  (def colors 
    (map 
      (fn [row]
        (map
          (fn [item]
            (get-samples item))
          row))
      sample-coords))

  (println (vector (flatten (flatten (take 1 colors))))
  )

(println (sample-pixels col row (+ col targetCols) (+ row targetRows)))


