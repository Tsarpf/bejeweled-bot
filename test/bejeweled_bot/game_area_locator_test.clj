(ns bejeweled-bot.game-area-locator-test
  (:require [clojure.test :refer :all]
            [bejeweled-bot.game-area-locator :refer :all]))
(use '[clojure.string :only (join split)])


(defn get-bits
  [value]
  (Integer/toString value 2))
(defn print-bits
  [value]
  (println (Integer/toString value 2)))

(deftest get-bytes-test
  (testing "returns correct bits"
    (is (= "1111" (get-bits (get (get-bytes [0x000f0000])2))))
    (is (= "1111" (get-bits (get (get-bytes [0x00000f00])1))))
    (is (= "1111" (get-bits (get (get-bytes [0x0000000f])0)))))
  (testing "returns a list of bytes"
      (is (= '(15 15 15) (seq (get-bytes [0x000f0f0f]))))
    )
  )

(deftest image-ints-test
  (testing "gets ints correctly"
    (let 
      [intsies (ints-from-image (get-screen))
       bytesies (mapcat int-to-bytes intsies)
       a-int (get intsies 0)
       two-ints (take 2 intsies)
       six-bytes (mapcat int-to-bytes two-ints)
       nth-int (nth intsies 500000)
       nth-byte (int-to-bytes nth-int)
       a-byte (nth bytesies 0)
       ]
      (println (map #(+ % 128) nth-byte))
      (println nth-int)
      (doall (map #(print-bits %) nth-byte))
      ;(println three-bytes)
      ;(print-bits 0x000000ff)
      ;(println a-int)      
      ;(println a-byte)      
      ;(println thousandth-int)      
      ;(println (seq a-int))      
      ;(println (seq a-byte))      
      )
    )
  )

;(def screenshot (.createScreenCapture robo (Rectangle. (.getScreenSize (Toolkit/getDefaultToolkit)))))
;(def image-ints (mat-from-buffer screenshot))
;(defn ints-from-image
;  [image]
;  (int-array (.getData (cast DataBufferInt (.getDataBuffer (.getRaster image))))))
