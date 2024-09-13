package DAO;

import java.sql.*;

import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    //Get Account by ID
    private Connection conn = ConnectionUtil.getConnection();

    public Account SelectAccount(int account_id)
    {
        String sql = "SELECT * FROM Account WHERE ?;";
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
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

    //Get Account by Username, Password
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
    
    //Get Account by Username
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

    // Add Account
    public Account insertAccount(Account user)
    {
        {
            String sql = "INSERT INTO Account(username, password) VALUES (?, ?);";
            try
            {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
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
    }
}
