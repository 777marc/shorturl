(ns shorturl.db
  (:require [clojure.java.jdbc :as j]))

(def mysql-db {:host "us-east.connect.psdb.cloud"
               :dbtype "mysql"
               :dbname "shorturl"
               :user "efxak0z7ax6ebb9oxf1o"
               :password "pscale_pw_GjY6j6tKZ9ysWxG2Zf426aJmjTsTPHVYDEHMWqUEfux"})

(j/query mysql-db
         ["select * from redirects;"])