package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.MessageService;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public AccountService accountService;
    public MessageService messageService;
    private ObjectMapper mapper = new ObjectMapper();

    public SocialMediaController()
    {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        // POST "Register" Endpoint
        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::newMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}",this::getMessagebyIDHandler);
        app.delete("messages/{message_id}", this::deleteMessagebyIDHandler);
        app.patch("messages/{message_id}", this::updateMessagebyIDHandler);
        app.get("accounts/{account_id}/message", this::getMessagebyAccountIDHandler);
        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler (Context context) throws JsonProcessingException
    {
        Account user = mapper.readValue(context.body(), Account.class); //Parse JSON into Account Object
        
        Account newUser = accountService.addAccount(user);
            if(newUser != null){
                context.json(newUser.toString());
            }else{
                context.status(400);
            }
    }
    private void loginHandler(Context context) throws JsonProcessingException
    {
        Account user = mapper.readValue(context.body(), Account.class);
        Account validUser = accountService.getAccountbyUP(user.getUsername(), user.getPassword());
        if(validUser != null)
        {
            context.json(validUser.toString());
        }
        else{
            context.status(401);
        }
    }
    private void newMessageHandler(Context context) throws JsonProcessingException
    {
        Message message = mapper.readValue(context.body(), Message.class);
        Message validMessage = messageService.newMessage(message);
        if(validMessage != null)
        {
            context.json(validMessage.toString());
        }
        context.status(400);
    }
    private void getAllMessagesHandler(Context context) throws JsonProcessingException
    {
        List<Message> messageList = messageService.getAllMessages();
        context.json(messageList);
    }
    private void getMessagebyIDHandler(Context context) throws JsonProcessingException
    {
        String message_id = context.pathParam("message_id");
        Message message = messageService.getMessagebyID(Integer.parseInt(message_id));
        context.json(message);
    }
    private void deleteMessagebyIDHandler(Context context)
    {
        String message_id = context.pathParam("message_id");
        Message message = messageService.deleteMessagebyID(Integer.parseInt(message_id));
        context.json(message);
    }
    private void updateMessagebyIDHandler(Context context) throws JsonProcessingException
    {
        String message_id = context.pathParam("message_id");
        String message_text = mapper.readValue(context.body(), String.class);
        Message message = messageService.updateMessagebyID(Integer.parseInt(message_id), message_text);
        if(message != null)
        {
            context.json(message);
        }
        context.status(400);
        
    }
    private void getMessagebyAccountIDHandler(Context context)
    {
        String account_id = context.pathParam("account_id");
        List<Message> messageList = messageService.getAllMessagesforAccount(Integer.parseInt(account_id));
        context.json(messageList);
    }




}