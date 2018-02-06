import java.sql.*;

//driver name = "oracle.jdbc.driver.OracleDriver"
//url = "jdbc:oracle:thin:@localhost:1521:xe";
//uid = "scott";
//upw = "tiger";
public class DBManager implements AutoCloseable {

	private String driverName = "org.mariadb.jdbc.Driver";// "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:mariadb://sbdb.cparbgtzrkld.ap-northeast-2.rds.amazonaws.com:3306/sb";// "jdbc:mariadb://localhost:3306/test";
	private String uid;
	private String upw;

	private Connection connection = null;
	private Statement statement = null;

	@Override
	public void close() {
		//disconnectDriver();
		System.out.println("closed DBManager Class");
	}

	public DBManager() {
		loadDriver();
		url = "";
		uid = "";
		upw = "";
	}

	public DBManager(String _url, String _uid, String _upw) {
		loadDriver();
		url = _url;
		uid = _uid;
		upw = _upw;
		connectDriver();
	}

	public DBManager(String _uid, String _upw) {
		loadDriver();
		//url = "jdbc:oracle:thin:@localhost:1521:xe";
		uid = _uid;
		upw = _upw;
		connectDriver();
	}

	private void loadDriver() {
		try {
			Class.forName(driverName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectDriver() {
		try {
			connection = DriverManager.getConnection(url, uid, upw);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("connected : " + url);
	}

	public void disconnectDriver() {
		try {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("disconnected : " + url);
	}

	public ResultSet doQuery(String query) {
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	public void querySearch(String table, String whereColumn, String word, DBTableAdapter dbTableAdapter) {
		ResultSet resultSet = null;
		String sqlquery = "select * from " + table;

		if ((whereColumn != null) && (word != null))
			sqlquery += " where " + whereColumn + " = " + "'" + word + "'";

		try {
			resultSet = doQuery(sqlquery);
			dbTableAdapter.getDBTable(resultSet);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public int queryInsert(String table, DBTableAdapter dbTableAdapter) {

		int result=0;
		int tableCount = getTableCount(table);

		String query = "insert into " + table;

		try {
			for (int i = 0; i < dbTableAdapter.getTableCount(); i++) {
				String insertQuery = query + dbTableAdapter.setDBTable(tableCount, i);

				result = statement.executeUpdate(insertQuery);
				if (result == 1)
					System.out.println("insert success!");
				else
					System.out.println("insert fail!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(query);
		return result;
	}
	public int queryDelete(String table, String whereColumn, String word)
	{
		int result=0;
		String query = "delete from " + table + " where " + whereColumn + " = '" + word + "'";

		try {
				result = statement.executeUpdate(query);
				if (result > 0)
					System.out.println("delete count " + result);
				else
					System.out.println("do not delete");
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public ResultSet queryTable(String table, String column)
	{
		ResultSet resultSet = null;
		String sqlquery = "select " + column + " from " + table;

		try {
			resultSet = doQuery(sqlquery);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	public int queryUpdate(String table, String setColumn, String newValue, String where, String whereColumn)
	{
		int result=0;
		String sqlquery = "UPDATE " + table
				+ " SET " + setColumn + " = '" + newValue + "'"
				+ " WHERE " + where + " = '" + whereColumn + "'";

		try {
			result = statement.executeUpdate(sqlquery);
			if (result == 1)
				System.out.println("insert success!");
			else
				System.out.println("insert fail!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getTableCount(String tableName)
	{
		int count=0;
		try {
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int getNumResultSet(ResultSet resultSet)
	{
		int count=0;
		try {
			while (resultSet.next())
			{
				count++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}