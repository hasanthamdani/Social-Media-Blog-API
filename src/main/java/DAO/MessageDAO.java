package DAO;

import Model.Message;
import java.sql.*;
import Util.ConnectionUtil;
import java.util.*;

public class MessageDAO 
{
    private Connection conn = ConnectionUtil.getConnection();

    public List<Message> selectAllMessages()
    {
            String sql = "SELECT * FROM Message;";
            List<Message> messageList = new ArrayList<>();
            try
            {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while(rs.next())
                {
                    messageList.add(new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch")));
                }
                return messageList;
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
    
            return null;
    }
    public List<Message> selectAllMessages(int account_id)
    {
        String sql = "SELECT * FROM Message WHERE posted_by = ?;";
        List<Message> messageList = new ArrayList<>();
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                messageList.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getInt("time_posted_epoch")));
            }
            return messageList;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }
    public Message selectMessage(int message_id)
    {
        String sql = "SELECT * FROM Account WHERE message_id = ?;";
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Message mess = new Message(
                        rs.getInt("message_id"), 
                        rs.getInt("posted_by"), 
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                return mess;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message deleteMessage(int message_id)
    {
        String sql = "DELETE * FROM Account WHERE message_id = ?;";
        
        try
        {
            Message original = selectMessage(message_id);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message_id);
            int rows = ps.executeUpdate();
            if(rows != 1)
            {
                return null;
            }
            return original;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message updateMessage(int message_id, String newtext)
    {
        String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";
        
        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newtext);
            ps.setInt(2, message_id);
            int rows = ps.executeUpdate();
            if(rows != 1)
            {
                return null;
            }
            return selectMessage(message_id);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
