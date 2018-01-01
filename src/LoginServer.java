import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.ResultSet;

public class LoginServer {
    public static final String mClassName="LoginServer";

    public static void main(String[] args)
    {
        ServerSocket serverSocket=null;

        //DBManager dbManager = new DBManager();

        DBManager DBManager = new DBManager("salhyun", "333333333");
        int c = DBManager.getTableCount("account");

        //ResultSet resultSet = DBManager.queryTable("account", "name");

        AccountInfo accountInfo = new AccountInfo("mark", "roth@gmail.com", "333444");

        AccountInfoAdapter accountInfoAdapter = new AccountInfoAdapter();
        accountInfoAdapter.accountInfos.add(accountInfo);

        //DBManager.queryInsert("account", accountInfoAdapter);

        //DBManager.querySearch("account", "name", "salhyun", accountInfoAdapter);

        DBManager.disconnectDriver();

        int nPort=9100;
        try {
            serverSocket = new ServerSocket(nPort);
            InetAddress localAddress = InetAddress.getLocalHost();
            String serverIP = localAddress.getHostAddress();
            System.out.println("Login Server started ip = " + serverIP);

            PoolDispatcher poolDispatcher = new PoolDispatcher();
            poolDispatcher.startDispatching(serverSocket);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("End of LoginServer!!");
    }
}
