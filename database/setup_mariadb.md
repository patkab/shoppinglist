Create mariadb container:

    docker run -p 3306 --name shoppinglist-mariadb -v /Users/patric/Development/mariadb/shoppinglist-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=pwd -d mariadb

Log into container:

    docker exec -it  shoppinglist-mariadb bash

Install nano and set bind address:

    apt update
    apt install nano
    nano /etc/mysql/mariadb.conf.d/50-server.cnf
    [...] bind-address = 0.0.0.0

Create database und user, if they don't exist yet:

    mysql -u root -p
    create database shoppinglist;
    grant all on shoppinglist.* to 'patric'@'%' identified by 'number77' with grant option;
    exit

Exit container:

    exit