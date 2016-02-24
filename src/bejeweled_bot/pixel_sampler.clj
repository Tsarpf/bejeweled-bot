(ns bejeweled-bot.pixel-sampler
  (:import java.awt.Rectangle)
  (:import java.awt.Image)
  (:import java.awt.Robot)
  (:import java.awt.Color)
  (:import javax.imageio.ImageIO)
  (:import java.io.File)
  (:gen-class))

(import '[org.opencv.core Mat Size CvType Core Point Scalar]
        '[org.opencv.highgui Highgui]
        '[org.opencv.imgproc Imgproc])

(def image (ImageIO/read (File. "resources/whole.png")))
(def xy [37 49])
(defn get-samples
  "Gets pixel color from given coordinates"
  [xy]
  ;(def image (.createScreenCapture robo (Rectangle. xl yl xr yr)))
  (let [color-int (.getRGB image (xy 0) (xy 1))
        color (Color. color-int)
        red (.getRed color)
        green (.getGreen color)
        blue (.getBlue color)]
    {:red red :green green :blue blue}))
  
  ;(xy 0)(xy 1))

;(def targetCols 328)
;(def targetRows 319)
;(def col 36)
;(def row 120)

(defn sample-pixels
  ""
  [xl yl xr yr image]
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
          [(+ itemfdsa xl) (+ item yl)])
        (apply vector (range offset-x (+ offset-x (* 8 jewel-length)) jewel-length))))
    (apply vector (range offset-y (+ offset-y (* 8 jewel-length)) jewel-length))))
  (map 
    (fn [row]
      (map
        (fn [x] 
          (get-samples x))
          ;(Core/circle image (Point. (x 0) (x 1)) 5 (Scalar. 0 0 255)))
        row
        ))
    sample-coords))
  
  ;(Highgui/imwrite "resources/output3.png" image)
  ;(println sample-coords)
  ;(println (flatten sample-coords))
  ;(println 0)
  ;(def colors 
  ;  (map 
  ;    (fn [row]
  ;      (map 
  ;        (fn [item]
  ;          (get-samples item))
  ;        row))
  ;    sample-coords))

  ;(map #(map (fn [item] (println item)) %) colors)
  ;(println (colors 0))
  ;(println (vector colors)))


;(println (sample-pixels col row (+ col targetCols) (+ row targetRows)))


;
;(flatten sample-coords)))
;        (map
;          (fn [item]
;            (get-samples item))
;          row))
