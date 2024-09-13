package Service;

import DAO.AccountDAO;
import Model.*;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account getAccountbyID(int account_id)
    {
        return accountDAO.SelectAccount(account_id);
    }

    public Account getAccountbyUP(String Username, String Password)
    {
        if(accountDAO.SelectAccount(Username, Password) != null)
        {
            return accountDAO.SelectAccount(Username, Password);
        }
        return null;
    }
    public Account getAccountbyU(String Username)
    {
        if(accountDAO.SelectAccount(Username) != null)
        {
            return accountDAO.SelectAccount(Username);
        }
        return null;
    }
    public Account addAccount(Account newPerson)
    {
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
