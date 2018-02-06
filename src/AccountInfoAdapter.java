import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountInfoAdapter extends DBTableAdapter {

    public ArrayList<AccountInfo> accountInfos = new ArrayList<AccountInfo>();

    public AccountInfoAdapter()
    {

    }

    public String setDBTable(int sqlcount, int index)
    {
        String result = new String();

        AccountInfo accountInfo = accountInfos.get(index);

        result += " values(";
        result += "'" + accountInfo.name + "'";
        result += ", '" + accountInfo.email + "'";
        result += ", " + accountInfo.level;
        result += ", " + accountInfo.gold;
        result += ")";

        return result;
    }


    public void getDBTable(ResultSet resultSet)
    {
        try {
            while(resultSet.next())
            {
                AccountInfo accountInfo = new AccountInfo();

                accountInfo.name = resultSet.getString("name");
                accountInfo.email = resultSet.getString("email");
                accountInfo.level = resultSet.getInt("level");
                accountInfo.gold = resultSet.getInt("gold");
                accountInfos.add(accountInfo);

                System.out.println("name = " + accountInfo.name + ", email = " + accountInfo.email + "level = " + accountInfo.level + ", gold = " + accountInfo.gold);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getTableCount()
    {
        return accountInfos.size();
    }
}
