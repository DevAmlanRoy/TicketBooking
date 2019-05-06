package logic;

import java.sql.*;
import java.util.Date;

public class Database {
	Statement statement;
	public boolean[] seatIsBooked = new boolean[45];
	private static Connection connection = null;
	private String createTable = null,journeyDate = null, tableName = null;
	int route = 0, shift = 0;
	ResultSet result=null;
	

	public void insertData(String id, String name, String contact) throws SQLException {

		if (connection == null) {
			databaseConnector();
		}
		String bookingDate = new Date().toString();
		PreparedStatement preparedStatement = connection
		.prepareStatement("UPDATE " + tableName + " SET isBooked=?, name=?,contact=?,bookingDate=? WHERE id='"+id+"';");
		preparedStatement.setString(1, ""+1);
		preparedStatement.setString(2, name);
		preparedStatement.setString(3, contact);
		preparedStatement.setString(4, bookingDate);
		preparedStatement.execute();
	}

	public void databaseConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:ticket.db");
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println("Class:DataBase Line:36 \n "+e.toString());
		}
	}

	public boolean checkIfBooked(String seatNumber) {
		boolean booked=false;
		 try {
			statement= connection.createStatement();
			result = statement.executeQuery("SELECT isBooked FROM '" + tableName +"' WHERE ID ='"+seatNumber+"'");
			
				if(result.getInt("isBooked")==1)
					booked= true;
				else
					booked= false;
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booked;
		 
	}
	public void initializeDatabase(String tableName, String journeydate, 
			Integer route, Integer shift)
			 {
		this.route=route;
		this.shift=shift;
		this.journeyDate=journeydate;
		this.tableName = tableName;
		
		if (connection == null)
			databaseConnector();
		try {
		 statement= connection.createStatement();
		String sql="SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName+"'";
		//System.out.println(sql);
		result = statement.executeQuery(sql);
		if (!result.next()) {
				createTable = "CREATE TABLE '" + tableName 
				+ "' (id varchar(2)," + "Date varchar(20),"
				+ "Route varchar(50)," + "Shift varchar(20)," 
				+ "isBooked integer," + "Name varchar(50)," + "Contact varchar(15),"
				+ "BookingDate varchar(20)," + "primary key(id));";
				statement.execute(createTable);
			PreparedStatement preparedStatement;
			for (int  row = 1; row <= 11; row++) {
				for (int column = 1; column < 5; column++) {
					char seatRow=(char)(64+row);
					preparedStatement= connection
					.prepareStatement("INSERT INTO '" + 
					tableName + "' values(?,?,?,?,?,?,?,?);");
					preparedStatement.setString(2, journeyDate);
					preparedStatement.setString(3, "" + route);
					preparedStatement.setString(4, "" + shift);
					preparedStatement.setString(5, "" + 0);
					preparedStatement.setString(6, "");
					preparedStatement.setString(7, "");
					preparedStatement.setString(8, "");
					switch (column) {
					case 1:
						preparedStatement.setString(1, seatRow+"1");
						break;
					case 2:
						preparedStatement.setString(1, seatRow+"2");
						break;
					case 3:
						preparedStatement.setString(1, seatRow+"3");
						break;
					case 4:
						preparedStatement.setString(1, seatRow+"4");
						break;
					default:
						break;
					}
					preparedStatement.execute();
				}
			}
			System.out.println(tableName + "Data inserted.");
		}
		result = statement.executeQuery("SELECT isBooked FROM " + tableName);
		int index=1;
		while (result.next()) {
			if(result.getInt("isBooked")==1)
				seatIsBooked[index]=true;
			else
				seatIsBooked[index]=false;
			index++;
			
		}
		}catch (SQLException e) {
			 e.printStackTrace();
		}
	}
}
