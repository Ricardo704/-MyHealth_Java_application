package model;

import java.sql.SQLException;
import dao.UserDao;
import dao.UserDaoImpl;


public class Model {
	private UserDao userDao;
	private User currentUser;
	private Record currentRecord;
	
	public Model() {
		userDao = new UserDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
	}
	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}

	public Record getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentUser(User user) {
		currentUser = user;
	}

	public void setCurrentRecord(Record record) {
		currentRecord = record;
	}
}
