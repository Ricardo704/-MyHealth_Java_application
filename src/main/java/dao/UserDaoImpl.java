package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Record;
import model.User;

/**
 * UserDaoImpl class includes methods to implement operations in the database and return object or value we need.
 */
public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";
	private final String TABLE_NAME1 = "records";

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(10) NOT NULL," + " firstname VARCHAR(10) ," + " lastname VARCHAR(10) ," + "PRIMARY KEY (username))";
			String sql1 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + "(recordID VARCHAR(10) NOT NULL," + "weight VARCHAR(10)," + "temperature VARCHAR(10),"
					+ "bloodPressure_low VARCHAR(10)," + "bloodPressure_high VARCHAR(10)," + "notes VARCHAR(512)," + "username VARVHAR(10),"
					 + " date DATE, "+ "PRIMARY KEY (recordID, username) FOREIGN KEY (username) REFERENCES users (username))";
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);
		} 
	}


	@Override
	public User getUser(String username, String password) throws SQLException {
		User user = new User(username, password, "", "");
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ? ";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstname(rs.getString("firstname"));
					user.setLastname(rs.getString("lastname"));
					return user;
				}
				return null;
			} 
		}
	}

	@Override
	public User createUser(String username, String password, String firstname, String lastname) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, firstname);
			stmt.setString(4, lastname);
			stmt.executeUpdate();
			return new User(username, password, firstname, lastname);
		} 
	}

	@Override
	public boolean checkUsername(String username) throws SQLException{
		User user = new User(username, "", "", "");
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql);){
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				return false;
			} else {
				return true;
			}
		}

	}

	@Override
	public boolean updateUser(String username, String firstname, String lastname) throws SQLException{
		User user = new User(username, "", firstname, lastname);
		String sql = "UPDATE " + TABLE_NAME + " SET firstname = ?, lastname = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql);){
			stmt.setString(1,firstname);
			stmt.setString(2,lastname);
			stmt.setString(3,user.getUsername());
			int result = stmt.executeUpdate();
			return result == 1;
		}

	}

	@Override
	public ArrayList<Record> getRecords(String username) throws SQLException{
		ArrayList<Record> records = new ArrayList<>();

		String sql = "SELECT * FROM " + TABLE_NAME1 + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql);){
			stmt.setString(1, username);

			try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()){
					Record record = new Record(0, 0.0, 0.0, 0.0, 0.0, "", "", username);
					record.setRecordID(rs.getInt("recordID"));
					record.setUsername(rs.getString("username"));
					record.setWeight(rs.getDouble("weight"));
					record.setTemperature(rs.getDouble("temperature"));
					record.setBloodPressure_low(rs.getDouble("bloodPressure_low"));
					record.setBloodPressure_high(rs.getDouble("bloodPressure_high"));
					record.setNotes(rs.getString("notes"));
					record.setDate(rs.getString("date"));
					records.add(record);
				}
				return records;
			}
		}
	}

	@Override
	public Record recordIdMax( String username) throws SQLException{
		Record record = new Record(0, 0.0, 0.0, 0.0, 0.0, "", "", username);
		String sql = "SELECT max(recordID) FROM " + TABLE_NAME1 ;
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql);){
			try (ResultSet rs = stmt.executeQuery()){
				if (rs.next()){
					record.setRecordID(rs.getInt(1));
					return record;
				}
				return null;
			}
		}
	}

	@Override
	public Record createRecord(Integer recordID, Double weight, Double temperature, Double bloodPressure_low, Double bloodPressure_high,
							   String notes, String username) throws SQLException{
		Record record = new Record(recordID, weight, temperature, bloodPressure_low, bloodPressure_high, notes, "", username);
		String sql = "INSERT INTO records VALUES (?, ?, ?, ?, ?, ?, ? , DATE() )";
		try(Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setInt(1, recordID);
			stmt.setDouble(2, weight);
			stmt.setDouble(3, temperature);
			stmt.setDouble(4, bloodPressure_low);
			stmt.setDouble(5, bloodPressure_high);
			stmt.setString(6, notes);
			stmt.setString(7, record.getUsername());
			stmt.executeUpdate();
			return new Record(recordID, weight, temperature, bloodPressure_low, bloodPressure_high, notes, "", username);
		}
	}

	@Override
	public boolean updateRecord(Double weight, Double temperature, Double bloodPressure_low, Double bloodPressure_high,
								String notes, String date, String username, Integer recordID)throws SQLException{
		Record record = new Record(recordID, weight,temperature,bloodPressure_low,bloodPressure_high, notes, date,username);
		String sql = "UPDATE " + TABLE_NAME1 + " SET weight = ?, temperature = ?, bloodPressure_low = ?, bloodPressure_high = ?, notes = ?, date = DATE() WHERE username = ? AND recordID = ?";
		try(Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setDouble(1, weight);
			stmt.setDouble(2, temperature);
			stmt.setDouble(3, bloodPressure_low);
			stmt.setDouble(4, bloodPressure_high);
			stmt.setString(5, notes);
			stmt.setString(6, record.getUsername());
			stmt.setInt(7, record.getRecordID());
			int result = stmt.executeUpdate();
			return result == 1;
		}
	}

	@Override
	public boolean deleteRecord(Integer recordID, String username) throws SQLException{
		Record record = new Record(recordID, 0.0, 0.0, 0.0, 0.0, "", "", username);
		String sql = "DELETE FROM " + TABLE_NAME1 + " WHERE recordID = ? AND username = ?";
		try(Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setInt(1, recordID);
			stmt.setString(2, username);
			int result = stmt.executeUpdate();
			return result == 1;
		}
	}


}
