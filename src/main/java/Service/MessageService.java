package Service;
import DAO.*;
import Model.*;
import java.util.*;

/**
 * Business Logic for Account Handling
 * Calling of AccountDAO methods while handling formatting and input validation
 */

public class MessageService {
    public MessageDAO messageDAO;
    public AccountService accountService;

    /**
     * Default, No-args Constructor
     */
    public MessageService(){
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    /**
     * Select all Messages from all Accounts
     * @return List<Message> 
     */
    public List<Message> getAllMessages()
    {
        return messageDAO.selectAllMessages();
    }

    /**
     * Select Message by ID
     * @param message_id
     * @return Message
     */
    public Message getMessagebyID(int message_id)
    {
        return messageDAO.selectMessage(message_id);
    }

    /**
     * Delete Message by ID
     * @param message_id
     * @return Message
     */
    public Message deleteMessagebyID(int message_id)
    {
        return messageDAO.deleteMessage(message_id);
    }
    
    /**
     * Update Message by ID
     * @param message_id
     * @param message_text
     * @return Message
     */
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
    /**
     * Select all Messages from Account ID
     * @param Account_id
     * @return List<Message>
     */
    public List<Message> getAllMessagesforAccount(int Account_id)
    {
        return messageDAO.selectAllMessages(Account_id);
    }

    /**
     * Insert new Message from Context Body
     * @param message
     * @return Message
     */
    public Message newMessage(Message message)
    {
        int user_id = message.getPosted_by();
        /*
         * Message text: Non-null, non-empty, Less than 255 chars
         * Account Id: Exists
         */
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
