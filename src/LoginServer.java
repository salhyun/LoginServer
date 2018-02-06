import java.net.*;
import java.sql.ResultSet;
import java.util.Enumeration;

public class LoginServer {
    public static final String mClassName="LoginServer";

    public static void main(String[] args)
    {
        ServerSocket serverSocket=null;

        //DBManager dbManager = new DBManager();

//        DBManager DBManager = new DBManager("salhyun", "333333333");
//        int c = DBManager.getTableCount("account");
//
//        //ResultSet resultSet = DBManager.queryTable("account", "name");
//
//        AccountInfo accountInfo = new AccountInfo("mark", "roth@gmail.com", "333444");

//        AccountInfoAdapter accountInfoAdapter = new AccountInfoAdapter();
//        accountInfoAdapter.accountInfos.add(accountInfo);
//
//        DBManager.queryInsert("account", accountInfoAdapter);

//        DBManager.querySearch("account", "name", "salhyun", accountInfoAdapter);
//        DBManager.disconnectDriver();

        String message = "I am your father";

        byte []a1 = convertDataType.StringToBytes(message, convertDataType.BIG_EDIAN);
        byte []a2 = convertDataType.StringToBytes(message, convertDataType.Little_EDIAN);

        String b1 = convertDataType.BytesToString(a1, 0, a1.length, convertDataType.BIG_EDIAN);
        String b2 = convertDataType.BytesToString(a2, 0, a2.length, convertDataType.Little_EDIAN);

        int identify = 333;

        byte []a3 = convertDataType.IntToBytes(identify, convertDataType.BIG_EDIAN);
        byte []a4 = convertDataType.IntToBytes(identify, convertDataType.Little_EDIAN);

        int b3 = convertDataType.BytesToInt(a3, convertDataType.BIG_EDIAN);
        int b4 = convertDataType.BytesToInt(a4, convertDataType.Little_EDIAN);

        int nPort=9100;
        try {
            serverSocket = new ServerSocket(nPort);
            InetAddress localAddress = InetAddress.getLocalHost();

            String OfficalIP = getCurrentNetworkIP();
            String LocalIP = localAddress.getHostAddress();
            System.out.println("Local IP = " + LocalIP);
            System.out.println("Offcial IP = " + OfficalIP);

            PoolDispatcher poolDispatcher = new PoolDispatcher(5);
            poolDispatcher.startDispatching(serverSocket);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("End of LoginServer!!");
    }

    public static String getCurrentNetworkIP()
    {
        Enumeration netInterfaces=null;

        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (netInterfaces.hasMoreElements())
        {
            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
            Enumeration address = ni.getInetAddresses();
            if (address == null)
                return getLocalIP();

            while(address.hasMoreElements())
            {
                InetAddress addr = (InetAddress)address.nextElement();
                if(!addr.isLoopbackAddress() && !addr.isSiteLocalAddress() && !addr.isAnyLocalAddress())
                {
                    String ip = addr.getHostAddress();
                    if(ip.indexOf(".") != -1 && ip.indexOf(":") == -1)
                        return  ip;
                }
            }
        }
        return getLocalIP();
    }
    public static String getLocalIP()
    {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
