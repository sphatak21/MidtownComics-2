package org.ucvts.comics.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ucvts.comics.model.Customer;
import org.ucvts.comics.model.Product;

public class CustomerDAO {
    public static Customer getCustomer(long customerID) throws SQLException{
        Customer customer = null;

        Connection conn = DAO.getConnection();

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");

        pstmt.setLong(1, customerID);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            customer = new Customer();

            customer.setCustomerId(rs.getLong(1));
            customer.setFirstName(rs.getString(2));
            customer.setLastName(rs.getString(3));
            customer.setPhone(rs.getLong(4));
            customer.setEmail(rs.getString(5));
            customer.setStreetAddress(rs.getString(6));
            customer.setCity(rs.getString(7));
            customer.setState(rs.getString(8));
            customer.setPostalCode(rs.getString(9));
        }

        rs.close();
        pstmt.close();
        conn.close();

        return customer;

    }
    public static List<Customer> getCustomers() throws SQLException{
        Customer customer = null;
        List<Customer> customers = new ArrayList<>();
        Connection conn = DAO.getConnection();

        // earlier, we created a prepared statement (i.e., a parameterized
        // statement). this is just a regular statement, which is used for
        // simpler queries that don't require additional values.

        Statement stmt = conn.createStatement();

        // statements are executed the same way prepared statements are. their
        // results are stored in a result set, too. the only difference is we
        // pass the SQL directly into the executeQuery method.

        ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

        // remember, next returns true if the cursor is advanced to the next
        // row. last time, we called next in an if statement to advance the
        // cursor to the first and only row.
        //
        // this query is designed to return more than one row, so we call next
        // in the condition of a while loop instead. this allows us to repeatedly
        // build products from every row in the result set. the while loop will
        // exist after the last row is processed and next returns false.

        while (rs.next()) {
            customer = new Customer();

            customer.setCustomerId(rs.getLong(1));
            customer.setFirstName(rs.getString(2));
            customer.setLastName(rs.getString(3));
            customer.setPhone(rs.getLong(4));
            customer.setEmail(rs.getString(5));
            customer.setStreetAddress(rs.getString(6));
            customer.setCity(rs.getString(7));
            customer.setState(rs.getString(8));
            customer.setPostalCode(rs.getString(9));

            customers.add(customer);
        }

        rs.close();
        stmt.close();
        conn.close();

        return customers;
    }
    public static void insertCustomer(Customer customer) throws SQLException {
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO customers (" +
                        "   firstname, " +
                        "   lastname, " +
                        "   phone, " +
                        "   email, " +
                        "   street, " +
                        "   city, " +
                        "   state, " +
                        "   postalcode " +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        );

        // we've got quite a few more placeholders to fill in this time. they
        // are numbered in the order in which they appear in the SQL statement.

        pstmt.setString(1, customer.getFirstName());
        pstmt.setString(2, customer.getLastName());
        pstmt.setLong(3, customer.getPhone());
        pstmt.setString(4, customer.getEmail());
        pstmt.setString(5, customer.getStreetAddress());
        pstmt.setString(6, customer.getCity());
        pstmt.setString(7, customer.getState());
        pstmt.setString(8, customer.getPostalCode());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
    public static void updateCustomer(Customer customer) throws SQLException{
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE customers SET " +
                        "   firstName = ?, " +
                        "   lastName = ?, " +
                        "   phone = ?, " +
                        "   email = ?, " +
                        "   streetAddress = ?," +
                        "   city = ?," +
                        "   state = ?," +
                        "   postalCode = ?,"+
                        "WHERE id = ?"
        );

        pstmt.setString(1, customer.getFirstName());
        pstmt.setString(2, customer.getLastName());
        pstmt.setLong(3, customer.getPhone());
        pstmt.setString(4, customer.getStreetAddress());
        pstmt.setString(5, customer.getCity());
        pstmt.setString(6, customer.getState());
        pstmt.setString(7, customer.getPostalCode());
        pstmt.setLong(8, customer.getCustomerId());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
    public static void deleteCustomer(Customer customer) throws SQLException{
        Connection conn = DAO.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM customers WHERE id = ?");

        pstmt.setLong(1, customer.getCustomerId());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

}
