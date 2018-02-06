import java.sql.ResultSet;

public class AccountInfo extends DBTable {
    private static final String mClassName="AccountInfo";

    public static final int STATUS_LOGOUT=0;
    public static final int STATUS_LOGIN=1;

    int status;

    String clientIPAddr;
    String clientPort;

    String name;
    String email;
    int level;
    int gold;

    public AccountInfo()
    {
        name="";
        email="";
        level=1;
        gold=0;
    }
    public AccountInfo(String _name, String _email, int _level, int _gold)
    {
        name = _name;
        email = _email;
        level = _level;
        gold = _gold;
    }

    public void stuff(ResultSet resultSet)
    {
    }

}
