package Service;
import DAO.*;
import Model.*;
import java.util.*;
import Service.AccountService;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountService accountService;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages()
    {
        return messageDAO.selectAllMessages();
    }

    public Message getMessagebyID(int message_id)
    {
        return messageDAO.selectMessage(message_id);
    }

    public Message deleteMessagebyID(int message_id)
    {
        return messageDAO.deleteMessage(message_id);
    }
    
    public Message updateMessagebyID(int message_id, String message_text)
    {
        Message originalMessage = messageDAO.selectMessage(message_id);
        if
        (
            (originalMessage != null) && //Does message_id Exit 
            ((message_text != null) && (message_text.trim() != "")) && // Is the text blank or null?
            (message_text.length() <= 255) // Is the length less or equal to 255 characters?
        )
        {
            return messageDAO.updateMessage(message_id, message_text);
        }
        return null;
    }

    public List<Message> getAllMessagesforAccount(int Account_id)
    {
        return messageDAO.selectAllMessages(Account_id);
    }

    public Message newMessage(Message message)
    {
        int user_id = message.getPosted_by();
        
        if
        (
        ((message.getMessage_text() != null) && (message.getMessage_text().trim() != "")) &&
        (message.getMessage_text().length() < 255) &&
        (accountService.getAccountbyID(user_id) != null)
        )
        {
            return messageDAO.insertMessage(message);
        }
        return null;
    }
}
