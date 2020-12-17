package org.ucvts.comics.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DAO {

    private static Connection conn;            // a connection to the database
    private static DatabaseMetaData metadata;  // a reference to the database metadata

    /**
     * Creates each table in the database, inserting sample records when
     * and where applicable.
     *
     * @throws SQLException
     */

    public static void buildDatabase() throws SQLException {
        conn = DAO.getConnection();
        metadata = conn.getMetaData();

        DAO.createProductsTable();      // creates products table
        DAO.createCustomersTable();     // creates customers table
        DAO.createOrdersTable();        // creates orders table
        DAO.createOrderItemsTable();    // creates orderitems table

        conn.close();
    }

    /*
     * Creates the products table if it doesn't already exist.
     *
     * @throws SQLException
     */

    private static void createProductsTable() throws SQLException {
        ResultSet rs = metadata.getTables(null, "USER1", "PRODUCTS", null);

        // getTables returns any tables named products. if next returns
        // false, then we haven't yet created the products table. we'll
        // do so now.

        if (!rs.next()) {
            Statement stmt = conn.createStatement();

            stmt.execute(
                    "CREATE TABLE products (" +
                            "   id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                            "   title VARCHAR(128) NOT NULL, " +
                            "   author VARCHAR(64) NOT NULL, " +
                            "   releasedate BIGINT NOT NULL, " +
                            "   issue INT NOT NULL, " +
                            "   unitprice DECIMAL(10, 2) NOT NULL, " +
                            "   copies INT NOT NULL" +
                            ")"
            );

            // we're going to insert the same sample products we used in
            // the Midtown Comics, Pt. 1 tutorial. this time, they'll be
            // in a database instead of in memory (in an ArrayList).

            insertSampleProducts();
        }
    }

    /*
     * Creates the customers table if it doesn't already exist.
     *
     * @throws SQLException
     */

    private static void createCustomersTable() throws SQLException {
        ResultSet rs = metadata.getTables(null, "USER1", "CUSTOMERS", null);

        // getTables returns any tables named customers. if next returns
        // false, then we haven't yet created the customers table. we'll
        // do so now.

        if (!rs.next()) {
            Statement stmt = conn.createStatement();

            stmt.execute(
                    "CREATE TABLE customers (" +
                            "   id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                            "   firstname VARCHAR(64) NOT NULL, " +
                            "   lastname VARCHAR(64) NOT NULL, " +
                            "   phone BIGINT NOT NULL, " +
                            "   email VARCHAR(255) NOT NULL, " +
                            "   street VARCHAR(255) NOT NULL, " +
                            "   city VARCHAR(255) NOT NULL, " +
                            "   state CHAR(2) NOT NULL, " +
                            "   postalcode CHAR(5) NOT NULL" +
                            ")"
            );
        }
    }

    /*
     * Creates the orders table if it doesn't already exist.
     *
     * @throws SQLException
     */

    private static void createOrdersTable() throws SQLException {
        ResultSet rs = metadata.getTables(null, "USER1", "ORDERS", null);

        // getTables returns any tables named orders. if next returns
        // false, then we haven't yet created the orders table. we'll
        // do so now.

        if (!rs.next()) {
            Statement stmt = conn.createStatement();

            stmt.execute(
                    "CREATE TABLE orders (" +
                            "   id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                            "   orderdate BIGINT NOT NULL, " +
                            "   status CHAR(1) NOT NULL, " +
                            "   total DECIMAL(20, 2) NOT NULL, " +
                            "   customerid BIGINT references customers(id)" +
                            ")"
            );
        }
    }

    /*
     * Creates the orderitems table if it doesn't already exist.
     *
     * @throws SQLException
     */

    public static void createOrderItemsTable() throws SQLException {
        ResultSet rs = metadata.getTables(null, "USER1", "ORDERITEMS", null);

        // getTables returns any tables named orderitems. if next returns
        // false, then we haven't yet created the orderitems table. we'll
        // do so now.

        if (!rs.next()) {
            Statement stmt = conn.createStatement();

            stmt.execute(
                    "CREATE TABLE orderitems (" +
                            "   id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                            "   quantity BIGINT NOT NULL, " +
                            "   orderid BIGINT references orders(id), " +
                            "   productid BIGINT references products(id)" +
                            ")"
            );
        }
    }

    /*
     * Inserts sample records into the products table.
     *
     * @throws SQLException
     */

    private static void insertSampleProducts() throws SQLException {
        Statement stmt = conn.createStatement();

        // this will serve as our initial inventory of products

        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19630301, 1, 19.99, 3)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19630510, 2, 19.99, 7)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19630710, 3, 19.99, 9)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19630910, 4, 19.99, 12)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19631010, 5, 19.99, 3)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19631110, 6, 19.99, 7)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19631210, 7, 19.99, 9)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19640110, 8, 19.99, 12)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19640210, 9, 19.99, 3)");
        stmt.executeUpdate("INSERT INTO products (title, author, releasedate, issue, unitprice, copies) VALUES ('The Amazing Spider-Man', 'Stan Lee', 19640310, 10, 19.99, 7)");

        stmt.close();
    }

    /*
     * Establishes and returns a connection to the database.
     *
     * @return a connection to the database
     * @throws SQLException
     */

    static Connection getConnection() throws SQLException {
        Properties props = new Properties();

        // we need to add user and password properties to the
        // connection string to authenticate

        props.put("user", "user1");
        props.put("password", "user1");

        // jdbc:derby is the protocol the driver uses, so it'll need
        // to be part of every connection string. midtowncomics is the
        // name of our database, and create=true means we'll create
        // this database if it doesn't already exist.

        return DriverManager.getConnection("jdbc:derby:midtowncomicsdb;create=true", props);
    }
}