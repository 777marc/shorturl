(ns shorturl.db
  (:require [clojure.java.jdbc :as j]
            [honey.sql :as sql]
            [honey.sql.helpers :refer :all]
            [shorturl.env :refer [env]]))

(def mysql-db {:host (env :HOST)
               :dbtype "mysql"
               :dbname (env :DBNAME)
               :user (env :USER)
               :password (env :PASSWORD)})

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
  (-> (query (-> (select :*)
                 (from :redirects)
                 (where [:= :slug slug])
                 (sql/format))) first :url))

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
