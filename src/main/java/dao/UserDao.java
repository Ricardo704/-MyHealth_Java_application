package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Record;
import model.User;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface UserDao {
	void setup() throws SQLException;
	User getUser(String username, String password) throws SQLException;

	User createUser(String username, String password, String firstname, String lastname) throws SQLException;

	boolean checkUsername(String username) throws SQLException;

	boolean updateUser(String username, String firstname, String lastname) throws SQLException;

	ArrayList<Record> getRecords(String username) throws SQLException;

	Record recordIdMax(String username) throws SQLException;

	Record createRecord(Integer recordID, Double weight, Double temperature, Double bloodPressure_low, Double bloodPressure_high,
						String notes, String username) throws SQLException;

	boolean updateRecord(Double weight, Double temperature, Double bloodPressure_low, Double bloodPressure_high,
						 String notes, String date, String username, Integer recordID)throws SQLException;

	boolean deleteRecord(Integer recordID, String username) throws SQLException;
}
