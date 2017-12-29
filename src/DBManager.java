import java.sql.*;

public class DBManager {
    public static final String mClassName="DBManager";

    private String driver = "org.mariadb.jdbc.Driver";
    private String url = "jdbc:mariadb://localhost:3306/test";
    private String uId = "root";
    private String uPass = "1234";

    private Connection con;
    private PreparedStatement psta;
    private ResultSet rs;

    public DBManager()
    {
        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, uId, uPass);
            if(con != null)
            {
                System.out.println("데이타베이스 접속 성공");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("데이타베이스 접속 실패");
            e.printStackTrace();
        }
    }

}
