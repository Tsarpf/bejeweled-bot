(ns bejeweled-bot.gem-classifier-test
  (:require [clojure.test :refer :all]
            [bejeweled-bot.gem-classifier :refer :all]))


(deftest classify-test
  (testing "classies correctly"
    (let 
      [rgb {:red 230, :green 101, :blue 33}
       gem :sapphire]
      (is (= gem (classify rgb))))
    (let 
      [rgb {:red 254, :green 245, :blue 35}
       gem :topaz]
      (is (= gem (classify rgb))))))

(deftest classify-test-non-exact-match
  (testing "classies correctly even with close enough but not exact rgb"
    (let 
      [rgb {:red 250, :green 101, :blue 33}
       gem :sapphire]
      (is (= gem (classify rgb))))
    (let 
      [rgb {:red 254, :green 205, :blue 35}
       gem :topaz]
      (is (= gem (classify rgb))))))
