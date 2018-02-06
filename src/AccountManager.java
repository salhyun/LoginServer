import java.util.ArrayList;

public class AccountManager {
    private static AccountManager ourInstance = new AccountManager();

    public static AccountManager getInstance() {
        return ourInstance;
    }

    public ArrayList<AccountInfo> connectedAccountInfos;

    private AccountManager() {
        connectedAccountInfos = new ArrayList<>();
    }

    public void addConnectedAccount(AccountInfo account)
    {
        connectedAccountInfos.add(account);
        System.out.println("add Account = " + account.email);
    }
    public boolean disconnectAccount(AccountInfo account)
    {
        for(AccountInfo it : connectedAccountInfos)
        {
            if(account.name.equals(it.name))
            {
                connectedAccountInfos.remove(it);
                return true;
            }
        }
        return false;
    }

    public boolean findAccount(String email)
    {
        for(AccountInfo it : connectedAccountInfos)
        {
            if(email.equals(it.email))
            {
                return true;
            }
        }
        return false;
    }

}
