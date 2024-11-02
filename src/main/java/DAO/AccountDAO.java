package DAO;

import java.sql.*;

import Util.ConnectionUtil;
import Model.Account;
/**
 * Data Access Object for Accounts
 * Implementation of Database Access Logic for H2 Database
 */
public class AccountDAO {
    
    // ConnectionUtil is a Singleton design pattern, there will only be one active connection
    private Connection conn = ConnectionUtil.getConnection();

    /**
     * Choose Account by account id
     * @param account_id
     * @return Account
     */
    public Account SelectAccount(int account_id)
    {
        String sql = "SELECT * FROM account WHERE account_id = ?;";
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return  new Account(
                        rs.getInt("account_id"), 
                        rs.getString("username"), 
                        rs.getString("password"));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Choose Account by Username and password
     * Overloaded version of previous Method
     * @param Username
     * @param Password
     * @return Account
     */
    public Account SelectAccount(String Username, String Password)
    {
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?;";
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Username);
            ps.setString(2, Password);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Account acc = new Account(
                        rs.getInt("account_id"), 
                        rs.getString("username"), 
                        rs.getString("password"));
                return acc;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    /**
     * Choose Account by Username
     * Overloaded version of previous Method
     * @param Username
     * @return Account
     */
    public Account SelectAccount(String Username)
    {
        String sql = "SELECT * FROM Account WHERE username = ?;";
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Username);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Account acc = new Account(
                        rs.getInt("account_id"), 
                        rs.getString("username"), 
                        rs.getString("password"));
                return acc;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Insert Account with Account Object
     * @param user
     * @return new Account 
     */
    public Account insertAccount(Account user)
    {
        {
            String sql = "INSERT INTO Account(username, password) VALUES (?, ?);";
            try
            {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                int rows = ps.executeUpdate();
                if(rows <= 0)
                {
                    return null;
                }
                return SelectAccount(user.getUsername(), user.getPassword());
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
    
            return null;
        }
    }
}
