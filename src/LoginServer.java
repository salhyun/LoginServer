import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.ResultSet;

public class LoginServer {
    public static final String mClassName="LoginServer";

    public static void main(String[] args)
    {
        ServerSocket serverSocket=null;

        //DBManager dbManager = new DBManager();

        MyOracleDB myOracleDB = new MyOracleDB("salhyun", "333333333");
        int c = myOracleDB.getTableCount("account");

        ResultSet resultSet = myOracleDB.queryTable("account", "name");

        myOracleDB.disconnectDriver();

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
