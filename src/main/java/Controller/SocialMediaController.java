package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.MessageService;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.*;

public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public AccountService accountService;
    public MessageService messageService;

    // Map JSON object into PoJo
    private ObjectMapper mapper = new ObjectMapper();

   /**
    * Default Constructory initializing both Service Objects
    */
    public SocialMediaController()
    {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * Starts Server and Creates Endpoints
     * @return returns Javalin server with endpoints to allow for easier isolation for testing
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        // Define Endpoints and assign Handlers

        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::newMessageHandler);
        
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}",this::getMessagebyIDHandler);
        app.get("accounts/{account_id}/messages", this::getMessagebyAccountIDHandler);
        
        app.delete("messages/{message_id}", this::deleteMessagebyIDHandler);

        app.patch("messages/{message_id}", this::updateMessagebyIDHandler);
        
        return app;
    }

    /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    /**
     * User registration
     * @param context handles username and password information
     */
    private void registerHandler (Context context) throws JsonProcessingException
    {
        Account user = mapper.readValue(context.body(), Account.class); //Parse JSON into Account Object
        
        //Assess if user password and username is valid and doesn't exist
        Account newUser = accountService.addAccount(user);
            if(newUser != null){
                context.json(mapper.writeValueAsString(newUser));
            }else{
                context.status(400);
            }
    }
     /**
      * User Login
     * @param context holds username and password information
     */
    private void loginHandler(Context context) throws JsonProcessingException
    {
        Account user = mapper.readValue(context.body(), Account.class);
        Account validUser = accountService.getAccountbyUP(user.getUsername(), user.getPassword());
        
        // Check if user exists and if request body is valid
        if(validUser != null)
        {
            context.json(mapper.writeValueAsString(validUser));
        }
        else{
            context.status(401);
        }
    }

     /**
      * Creating a Message
     * @param context message object with no message_id
     */
    private void newMessageHandler(Context context) throws JsonProcessingException
    {
        Message message = mapper.readValue(context.body(), Message.class);
        Message validMessage = messageService.newMessage(message);
        
        //Check if message is formatted
        if(validMessage != null)
        {
            context.json(mapper.writeValueAsString(validMessage));
        }
        else{
            context.status(400);
        }
        
    }

     /**
     * View all Messages by All Accounts
     * 
     * @param context "empty" context to represent the resposne object
     */
    private void getAllMessagesHandler(Context context) throws JsonProcessingException
    {
        List<Message> messageList = messageService.getAllMessages();
        context.json(messageList);
    }

     /**
     * View Message by A Message Id
     * @param context Holds parameter in URI for message_id
     */
    private void getMessagebyIDHandler(Context context) throws JsonProcessingException
    {
        String message_id = context.pathParam("message_id");
        Message message = messageService.getMessagebyID(Integer.parseInt(message_id));
        
        //Check if id exists
        if(message != null)
        {
            context.json(message);
        }
    }
     /**
     * Delete Message by Message Id
     * @param context Holds parameter in URI for message_id
     */
    private void deleteMessagebyIDHandler(Context context) throws JsonProcessingException
    {
        String message_id = context.pathParam("message_id");
        Message message = messageService.deleteMessagebyID(Integer.parseInt(message_id));
        
         //Check if id exists
        if(message != null)
        {
            context.json(message);
        }
    }
    /**
     * Update Message with new text via Message Id
     * @param context Holds message object in request body and message_id in URI
     */
    private void updateMessagebyIDHandler(Context context) throws JsonProcessingException
    {
        String message_id = context.pathParam("message_id");
        Message incoming = mapper.readValue(context.body(), Message.class);
        Message message = messageService.updateMessagebyID(Integer.parseInt(message_id), incoming.getMessage_text());

        // check if the message id exists.
        if(message != null)
        {
            context.json(message);
        }
        else
        {
            context.status(400);
        }
         
    }

    /**
     * View all Message by Account Id
     * @param context Holds parameter in URI for Account id
     */
    private void getMessagebyAccountIDHandler(Context context)
    {
        String account_id = context.pathParam("account_id");
        List<Message> messageList = messageService.getAllMessagesforAccount(Integer.parseInt(account_id));
        context.json(messageList);
    }




}