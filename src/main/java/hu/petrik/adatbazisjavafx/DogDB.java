package hu.petrik.adatbazisjavafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DogDB {
    Connection conn;

    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_DBNAME = "java";
    public static String DB_USER = "root";
    public static String DB_PASS = "";

    public DogDB() throws SQLException {
        String url = String.format("jdbc:%s//%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }

    public boolean createDog(Dog dog) {

    }

    public void readDogs() {

    }

    public void updateDog() {

    }

    public void deleteDog() {

    }

}
