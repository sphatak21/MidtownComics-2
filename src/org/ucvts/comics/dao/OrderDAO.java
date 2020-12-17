package org.ucvts.comics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ucvts.comics.model.Order;

public class OrderDAO {

    /**
     * Retrieves a Product from the database.
     *
     * @param productId the productId of the product to retrieve
     * @return the product retrieved from the database
     * @throws SQLException
     */

    public static Order getOrder(long orderId) throws SQLException {
        Order order = null;

        // first, we need to establish a connection to the database. we
        // call getConnection, which will return a new connection object.

        Connection conn = DAO.getConnection();

        // a statement is a query we'll execute on the database. this
        // includes select, insert, update, and delete statements. a
        // prepared statement is a parameterized statement that allows
        // us to pass in values to predefined placeholders.

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders WHERE id = ?");

        // we need to provide an actual value for our placeholder.

        pstmt.setLong(1, orderId);

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
            order = new Order();

            order.setOrderId(rs.getLong(1));
            order.setOrderDate(rs.getLong(2));
            order.setStatus(rs.getString(3));
            order.setTotal(rs.getDouble(4));
            order.setCustomer(CustomerDAO.getCustomer(rs.getLong(5)));
        }

        // we're done with the result set, prepared statement, and connection
        // objects, so we should close them. this is a form of memory management.

        rs.close();
        pstmt.close();
        conn.close();

        return order;
    }

    /**
     * Retrieves all Products from the database.
     *
     * @return a list of products retrieved from the database
     * @throws SQLException
     */

    public static List<Order> getOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        Connection conn = DAO.getConnection();

        // earlier, we created a prepared statement (i.e., a parameterized
        // statement). this is just a regular statement, which is used for
        // simpler queries that don't require additional values.

        Statement stmt = conn.createStatement();

        // statements are executed the same way prepared statements are. their
        // results are stored in a result set, too. the only difference is we
        // pass the SQL directly into the executeQuery method.

        ResultSet rs = stmt.executeQuery("SELECT * FROM orders");

        // remember, next returns true if the cursor is advanced to the next
        // row. last time, we called next in an if statement to advance the
        // cursor to the first and only row.
        //
        // this query is designed to return more than one row, so we call next
        // in the condition of a while loop instead. this allows us to repeatedly
        // build products from every row in the result set. the while loop will
        // exist after the last row is processed and next returns false.

        while (rs.next()) {
            Order order = new Order();

            order.setOrderId(rs.getLong(1));
            order.setOrderDate(rs.getLong(2));
            order.setStatus(rs.getString(3));
            order.setTotal(rs.getDouble(4));
            //System.out.println("$" + order.getTotal());
            //System.out.println(order.getOrderId());
            order.setCustomer(CustomerDAO.getCustomer(rs.getLong(5)));

            orders.add(order);
        }

        rs.close();
        stmt.close();
        conn.close();

        return orders;
    }



    public static void insertOrder(Order order) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO orders (" +
                        "   orderdate, " +
                        "   status, " +
                        "   total, " +
                        "   customerid " +
                        ") VALUES (?, ?, ?, ?)"
        );

        // we've got quite a few more placeholders to fill in this time. they
        // are numbered in the order in which they appear in the SQL statement.

        pstmt.setLong(1, order.getOrderDate());
        pstmt.setString(2, order.getStatus());
        pstmt.setDouble(3, order.getTotal());
        pstmt.setLong(4, order.getCustomer().getCustomerId());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public static Long insertOrderReturnId(Order order) throws SQLException {
        Long key = null;
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO orders (" +
                        "   orderdate, " +
                        "   status, " +
                        "   total, " +
                        "   customerid " +
                        ") VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );

        // we've got quite a few more placeholders to fill in this time. they
        // are numbered in the order in which they appear in the SQL statement.


        pstmt.setLong(1, order.getOrderDate());
        pstmt.setString(2, order.getStatus());
        pstmt.setDouble(3, order.getTotal());
        pstmt.setLong(4, order.getCustomer().getCustomerId());


        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        while (rs.next()) {
            key = rs.getLong(1);
        }
        pstmt.close();
        conn.close();

        return key;
    }



    public static void updateOrder(Order order) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE orders SET " +
                        "   orderdate = ?, " +
                        "   status = ?, " +
                        "   total = ?, " +
                        "   customerid = ? " +
                        "WHERE id = ?"
        );

//        System.out.println(order.getTotal() + " <-- order total when editing");
//        System.out.println(order.getStatus() + " <-- order status when editing");
//        System.out.println(order.getOrderId());

        pstmt.setLong(1, order.getOrderDate());
        pstmt.setString(2, order.getStatus());
        pstmt.setDouble(3, order.getTotal());
        pstmt.setLong(4, order.getCustomer().getCustomerId());
        pstmt.setLong(5, order.getOrderId());

        pstmt.executeUpdate();

        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM orders");
        while(rs.next()) {
            //System.out.println(rs.getLong(1));
            //System.out.println(rs.getDouble(4));
        }


        pstmt.close();
        conn.close();
    }



    public static void deleteOrder(Order order) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM orders WHERE id = ?");

        // we're deleting a product from the table, so we only need the primary key
        // (in this case, the id column). a primary key, which can be a combination
        // of columns (called a composite key) is the value that is guaranteed to
        // uniquely identify a row in the table.

        pstmt.setLong(1, order.getOrderId());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
