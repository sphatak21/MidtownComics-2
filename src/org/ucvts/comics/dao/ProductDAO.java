package org.ucvts.comics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ucvts.comics.model.Product;

public class ProductDAO {

    public static Product getProduct(long productId) throws SQLException {
        Product product = null;

        // first, we need to establish a connection to the database. we
        // call getConnection, which will return a new connection object.

        Connection conn = DAO.getConnection();

        // a statement is a query we'll execute on the database. this
        // includes select, insert, update, and delete statements. a
        // prepared statement is a parameterized statement that allows
        // us to pass in values to predefined placeholders.

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM products WHERE id = ?");

        // we need to provide an actual value for our placeholder.

        pstmt.setLong(1, productId);

        // when we execute our query (a select statement), it's going to
        // return zero or more rows. we'll store that result in what is
        // called a result set.

        ResultSet rs = pstmt.executeQuery();

        // a result set has something called a cursor that points at the
        // current row. initially, this cursor is positioned before the
        // first row (i.e., it points at nothing). we need to call next
        // to tell the cursor to advance to the first row.
        //
        // next returns a boolean value. if it returns true, that means
        // the cursor successfully advanced to the next row (which, in this
        // case, is the first row).
        //
        // our query is designed to return a single row. if next returns
        // true, that means we've got a row. we'll use that row to build
        // a product object.

        if (rs.next()) {
            product = new Product();

            product.setProductId(rs.getLong(1));
            product.setTitle(rs.getString(2));
            product.setAuthor(rs.getString(3));
            product.setReleaseDate(rs.getLong(4));
            product.setIssue(rs.getInt(5));
            product.setUnitPrice(rs.getDouble(6));
            product.setCopies(rs.getInt(7));
        }

        // we're done with the result set, prepared statement, and connection
        // objects, so we should close them. this is a form of memory management.

        rs.close();
        pstmt.close();
        conn.close();

        return product;
    }

    /**
     * Retrieves all Products from the database.
     *
     * @return a list of products retrieved from the database
     * @throws SQLException
     */

    public static List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        Connection conn = DAO.getConnection();

        // earlier, we created a prepared statement (i.e., a parameterized
        // statement). this is just a regular statement, which is used for
        // simpler queries that don't require additional values.

        Statement stmt = conn.createStatement();

        // statements are executed the same way prepared statements are. their
        // results are stored in a result set, too. the only difference is we
        // pass the SQL directly into the executeQuery method.

        ResultSet rs = stmt.executeQuery("SELECT * FROM products");

        // remember, next returns true if the cursor is advanced to the next
        // row. last time, we called next in an if statement to advance the
        // cursor to the first and only row.
        //
        // this query is designed to return more than one row, so we call next
        // in the condition of a while loop instead. this allows us to repeatedly
        // build products from every row in the result set. the while loop will
        // exist after the last row is processed and next returns false.

        while (rs.next()) {
            Product product = new Product();

            product.setProductId(rs.getLong(1));
            product.setTitle(rs.getString(2));
            product.setAuthor(rs.getString(3));
            product.setReleaseDate(rs.getLong(4));
            product.setIssue(rs.getInt(5));
            product.setUnitPrice(rs.getDouble(6));
            product.setCopies(rs.getInt(7));

            products.add(product);
        }

        rs.close();
        stmt.close();
        conn.close();

        return products;
    }

    /**
     * Inserts a Product into the database.
     *
     * @param product the product to insert into the database
     * @throws SQLException
     */

    public static void insertProduct(Product product) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO products (" +
                        "   title, " +
                        "   author, " +
                        "   releasedate, " +
                        "   issue, " +
                        "   unitprice, " +
                        "   copies " +
                        ") VALUES (?, ?, ?, ?, ?, ?)"
        );

        // we've got quite a few more placeholders to fill in this time. they
        // are numbered in the order in which they appear in the SQL statement.

        pstmt.setString(1, product.getTitle());
        pstmt.setString(2, product.getAuthor());
        pstmt.setLong(3, product.getReleaseDate());
        pstmt.setInt(4, product.getIssue());
        pstmt.setDouble(5, product.getUnitPrice());
        pstmt.setInt(6, product.getCopies());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    /**
     * Updates an existing Product in the database
     *
     * @param product the new product used to update the old one
     * @throws SQLException
     */

    public static void updateProduct(Product product) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE products SET " +
                        "   title = ?, " +
                        "   author = ?, " +
                        "   releasedate = ?, " +
                        "   issue = ?, " +
                        "   unitprice = ?, " +
                        "   copies = ? " +
                        "WHERE id = ?"
        );

        pstmt.setString(1, product.getTitle());
        pstmt.setString(2, product.getAuthor());
        pstmt.setLong(3, product.getReleaseDate());
        pstmt.setInt(4, product.getIssue());
        pstmt.setDouble(5, product.getUnitPrice());
        pstmt.setInt(6, product.getCopies());
        pstmt.setLong(7, product.getProductId());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    /**
     * Deletes an existing Product from the database.
     *
     * @param product the product to delete
     * @throws SQLException
     */

    public static void deleteProduct(Product product) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM products WHERE id = ?");

        // we're deleting a product from the table, so we only need the primary key
        // (in this case, the id column). a primary key, which can be a combination
        // of columns (called a composite key) is the value that is guaranteed to
        // uniquely identify a row in the table.

        pstmt.setLong(1, product.getProductId());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}

