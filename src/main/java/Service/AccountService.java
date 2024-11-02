package Service;

import DAO.AccountDAO;
import Model.*;
/**
 * Business Logic for Account Handling
 * Calling of AccountDAO methods while handling formatting and input validation
 */
public class AccountService {
    public AccountDAO accountDAO;
    
    /**
     * Default, No-args Constructor
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Select Account by Account ID
     * @param account_id
     * @return Account
     */
    public Account getAccountbyID(int account_id)
    {
        return accountDAO.SelectAccount(account_id);
    }

    /**
     * Select Account by Account username and password
     * @param Username
     * @param Password
     * @return Account
     */
    public Account getAccountbyUP(String Username, String Password)
    {
        // Check if account exists based on Username and Password

        if(accountDAO.SelectAccount(Username, Password) != null)
        {
            return accountDAO.SelectAccount(Username, Password);
        }
        return null;
    }
    /**
     * Select Account by Account Username
     * @param Username
     * @return Account
     */

    public Account getAccountbyU(String Username)
    {
        //Check if the Account exists
        if(accountDAO.SelectAccount(Username) != null)
        {
            return accountDAO.SelectAccount(Username);
        }
        return null;
    }

     /**
     * Add Account using inputted Account
     * @param newPerson
     * @return Account
     */
    public Account addAccount(Account newPerson)
    {
        /*
         * Username: Exists, Non-Empty
         * Password: Length is atleast 4 characters
         * Username account does not already exist
         */

        if
        (
        ((newPerson.getUsername() != null) && (newPerson.getUsername().trim() != "")) && 
        (newPerson.getPassword().length() >= 4) &&
        (accountDAO.SelectAccount(newPerson.getUsername()) == null)
        )
        {
            return accountDAO.insertAccount(newPerson);
        }
        return null;
    }

}
