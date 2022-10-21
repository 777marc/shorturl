(ns shorturl.db
  (:require [clojure.java.jdbc :as j]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all]))

(def mysql-db {:host "us-east.connect.psdb.cloud"
               :dbtype "mysql"
               :dbname "shorturl"
               :user "efxak0z7ax6ebb9oxf1o"
               :password "pscale_pw_GjY6j6tKZ9ysWxG2Zf426aJmjTsTPHVYDEHMWqUEfux"})

(defn query [q]
  (j/query mysql-db q))

(defn insert! [q]
  (j/db-do-prepared mysql-db q))

(defn insert-redirect! [slug url]
  (insert! (-> (insert-into :redirects)
               (columns :slug :url)
               (values
                [[slug url]])
               (sql/format))))

(defn get-url [slug]
  (-> (query (-> (select :url)
                 (from :redirects)
                 (where [:= :slug slug])
                 (sql/format))) first))

(comment
  (query (-> (select :*)
             (from :redirects)
             (sql/format)))

  (insert! (-> (insert-into :redirects)
               (columns :slug :url)
               (values
                [["goog" "https://www.google.com/"]
                 ["yahoo" "https://www.yahoo.com/"]])
               (sql/format)))

  (insert-redirect! "mcode" "https://mendozacode.io")

  (get-url "mcode"))
