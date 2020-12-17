package org.ucvts.comics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ucvts.comics.model.OrderItem;

public class OrderItemDAO {



    public static OrderItem getOrderItem(long orderitemid) throws SQLException {
        OrderItem orderitem = null;

        // first, we need to establish a connection to the database. we
        // call getConnection, which will return a new connection object.

        Connection conn = DAO.getConnection();

        // a statement is a query we'll execute on the database. this
        // includes select, insert, update, and delete statements. a
        // prepared statement is a parameterized statement that allows
        // us to pass in values to predefined placeholders.

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orderitems WHERE id = ?"); // TODO changed from productId to id? not sure if correct

        // we need to provide an actual value for our placeholder.

        pstmt.setLong(1, orderitemid);

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
            orderitem = new OrderItem(rs.getLong(1), ProductDAO.getProduct(rs.getLong(4)), rs.getInt(2));

        }

        // we're done with the result set, prepared statement, and connection
        // objects, so we should close them. this is a form of memory management.

        rs.close();
        pstmt.close();
        conn.close();

        return orderitem;
    }

    public static OrderItem getOrderItem(long productid, long orderid) throws SQLException {
        OrderItem orderitem = null;
        Connection conn = DAO.getConnection();

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orderitems WHERE productid = ? AND orderid = ?");


        pstmt.setLong(1, productid);
        pstmt.setLong(2, orderid);

        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            orderitem = new OrderItem(rs.getLong(1), ProductDAO.getProduct(rs.getLong(4)), rs.getInt(2));
        }

        rs.close();
        pstmt.close();
        conn.close();


        return orderitem;

    }

    public static Long getOrderId(long itemid) throws SQLException {
        Long orderid = null;
        Connection conn = DAO.getConnection();

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orderitems WHERE id = ?");


        pstmt.setLong(2, itemid);

        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            orderid = rs.getLong(1);
        }

        rs.close();
        pstmt.close();
        conn.close();


        return orderid;

    }


    /**
     * Retrieves all Products from the database.
     *
     * @return a list of products retrieved from the database
     * @throws SQLException
     */

    public static List<OrderItem> getOrderItems() throws SQLException {
        List<OrderItem> orderitems = new ArrayList<>();
        Connection conn = DAO.getConnection();

        // earlier, we created a prepared statement (i.e., a parameterized
        // statement). this is just a regular statement, which is used for
        // simpler queries that don't require additional values.

        Statement stmt = conn.createStatement();

        // statements are executed the same way prepared statements are. their
        // results are stored in a result set, too. the only difference is we
        // pass the SQL directly into the executeQuery method.

        ResultSet rs = stmt.executeQuery("SELECT * FROM orderitems");

        // remember, next returns true if the cursor is advanced to the next
        // row. last time, we called next in an if statement to advance the
        // cursor to the first and only row.
        //
        // this query is designed to return more than one row, so we call next
        // in the condition of a while loop instead. this allows us to repeatedly
        // build products from every row in the result set. the while loop will
        // exist after the last row is processed and next returns false.

        while (rs.next()) {
            OrderItem orderitem = new OrderItem(rs.getLong(1), ProductDAO.getProduct(rs.getLong(4)), rs.getInt(2));

            orderitems.add(orderitem);
        }

        rs.close();
        stmt.close();
        conn.close();

        return orderitems;
    }


    public static void deleteOrderItems(long orderid) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM orderitems WHERE orderid = ?");

        // we're deleting a product from the table, so we only need the primary key
        // (in this case, the id column). a primary key, which can be a combination
        // of columns (called a composite key) is the value that is guaranteed to
        // uniquely identify a row in the table.

        pstmt.setLong(1, orderid);

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public static List<OrderItem> getOrderItems(long orderid) throws SQLException {
        List<OrderItem> orderitems = new ArrayList<>();
        Connection conn = DAO.getConnection();

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orderitems WHERE orderid = ?");

        pstmt.setLong(1, orderid);


        ResultSet rs = pstmt.executeQuery();



        while (rs.next()) {
            OrderItem orderitem = new OrderItem(rs.getLong(1), ProductDAO.getProduct(rs.getLong(4)), rs.getInt(2));

            orderitems.add(orderitem);
        }

        rs.close();
        pstmt.close();
        conn.close();

        return orderitems;
    }






    public static void insertOrderItem(OrderItem orderitem, long orderid) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO orderitems (" +
                        "   quantity, " +
                        "   orderid, " +
                        "   productid " +
                        ") VALUES (?, ?, ?)"
        );

        // we've got quite a few more placeholders to fill in this time. they
        // are numbered in the order in which they appear in the SQL statement.

        pstmt.setInt(1, orderitem.getQuantity());
        pstmt.setLong(2, orderid);
        pstmt.setLong(3, orderitem.getProduct().getProductId());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }



    public static void updateOrderItem(OrderItem orderitem) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE orderitems SET " +
                        "   quantity = ?, " +
                        //"   orderid = ?, " +
                        "   productid = ? " +
                        "WHERE id = ?"
        );

        pstmt.setInt(1, orderitem.getQuantity());
        //pstmt.setLong(2, getOrderId(orderitem.getItemId()));
        pstmt.setLong(2, orderitem.getProduct().getProductId());
        pstmt.setLong(3, orderitem.getItemId());


        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }



    public static void deleteOrderItem(OrderItem orderitem) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM orderitems WHERE id = ?");

        // we're deleting a product from the table, so we only need the primary key
        // (in this case, the id column). a primary key, which can be a combination
        // of columns (called a composite key) is the value that is guaranteed to
        // uniquely identify a row in the table.

        pstmt.setLong(1, orderitem.getItemId());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
