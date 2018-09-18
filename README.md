# xis-crawler
* How to run
```shell
$ git clone https://github.com/CreamBears/xis-crawler.git
$ cd xis-crawler
$ echo [id] > login.conf
$ echo [password] >> login.conf
$ echo [host] > mysql.conf
$ echo [port] >> mysql.conf
$ echo [db] >> mysql.conf
$ echo [user] >> mysql.conf
$ echo [password] >> mysql.conf
$ mysql -h [host] -u [user] -p < mysql/init.sql
$ sbt
...
sbt:xis-crawler> run
```
