import java.sql.ResultSet;

public class AccountInfo extends DBTable {
    private static final String mClassName="AccountInfo";

    String name;
    String email;
    String number;

    public AccountInfo()
    {
        name="";
        email="";
        number="";
    }
    public AccountInfo(String _name, String _email, String _number)
    {
        name = _name;
        email = _email;
        number = _number;
    }

    public void stuff(ResultSet resultSet)
    {
    }

}
