/*
 * Created on 2019-06-28 ( Date ISO 2019-06-28 - Time 08:07:42 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */

package org.demo.persistence.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Named;

import org.demo.data.record.StudentsRecord;
import org.demo.persistence.StudentsPersistence;
import org.demo.persistence.impl.jdbc.commons.DataSourceProvider;
import org.demo.persistence.impl.jdbc.commons.GenericJdbcDAO;

/**
 * Students persistence implementation
 * 
 * @author Telosys
 *
 */
@Named("StudentsPersistence")
public class StudentsPersistenceJdbc extends GenericJdbcDAO<StudentsRecord> implements StudentsPersistence {

	private final static String SQL_SELECT_ALL = "select id, nameSt, codeSt, classId, age, address, phone, email, note, birthday from students";

	// viet them
	private final static String SQL_SELECT_ALL_CONDITION = "select id, nameSt, codeSt, classId, age, address, phone, email, note, birthday from students where nameSt = ? LIMIT 1, 2";

	private final static String SQL_SELECT = "select id, nameSt, codeSt, classId, age, address, phone, email, note, birthday from students where id = ?";

	private final static String SQL_INSERT = "insert into students ( nameSt, codeSt, classId, age, address, phone, email, note, birthday ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	private final static String SQL_UPDATE = "update students set nameSt = ?, codeSt = ?, classId = ?, age = ?, address = ?, phone = ?, email = ?, note = ?, birthday = ? where id = ?";

	private final static String SQL_DELETE = "delete from students where id = ?";

	private final static String SQL_COUNT_ALL = "select count(*) from students";

	private final static String SQL_COUNT = "select count(*) from students where id = ?";

	// ----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public StudentsPersistenceJdbc() {
		super();
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(StudentsRecord record, long value) {
		record.setId((Integer) ((int) value));
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, StudentsRecord students) throws SQLException {
		// --- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, students.getId()); // "id" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, StudentsRecord students) throws SQLException {
		// --- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?,
		// y=?, ..." )
		// "id" is auto-incremented => no set in insert
		setValue(ps, i++, students.getNamest()); // "nameSt" : java.lang.String
		setValue(ps, i++, students.getCodest()); // "codeSt" : java.lang.String
		setValue(ps, i++, students.getClassid()); // "classId" : java.lang.Integer
		setValue(ps, i++, students.getAge()); // "age" : java.lang.Integer
		setValue(ps, i++, students.getAddress()); // "address" : java.lang.String
		setValue(ps, i++, students.getPhone()); // "phone" : java.lang.String
		setValue(ps, i++, students.getEmail()); // "email" : java.lang.String
		setValue(ps, i++, students.getNote()); // "note" : java.lang.String
		setValue(ps, i++, students.getBirthday()); // "birthday" : java.lang.String
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, StudentsRecord students) throws SQLException {
		// --- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, students.getNamest()); // "nameSt" : java.lang.String
		setValue(ps, i++, students.getCodest()); // "codeSt" : java.lang.String
		setValue(ps, i++, students.getClassid()); // "classId" : java.lang.Integer
		setValue(ps, i++, students.getAge()); // "age" : java.lang.Integer
		setValue(ps, i++, students.getAddress()); // "address" : java.lang.String
		setValue(ps, i++, students.getPhone()); // "phone" : java.lang.String
		setValue(ps, i++, students.getEmail()); // "email" : java.lang.String
		setValue(ps, i++, students.getNote()); // "note" : java.lang.String
		setValue(ps, i++, students.getBirthday()); // "birthday" : java.lang.String
		// --- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, students.getId()); // "id" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary
	 * value(s)
	 * 
	 * @param id;
	 * @return the new instance
	 */
	private StudentsRecord newInstanceWithPrimaryKey(Integer id) {
		StudentsRecord students = new StudentsRecord();
		students.setId(id);
		return students;
	}

	// ----------------------------------------------------------------------
	@Override
	protected StudentsRecord newInstance() {
		return new StudentsRecord();
	}

	// ----------------------------------------------------------------------
	@Override
	protected StudentsRecord populateBean(ResultSet rs, StudentsRecord students) throws SQLException {

		// --- Set data from ResultSet to Bean attributes
		students.setId(rs.getInt("id")); // java.lang.Integer
		if (rs.wasNull()) {
			students.setId(null);
		}
		; // not primitive number => keep null value if any
		students.setNamest(rs.getString("nameSt")); // java.lang.String
		students.setCodest(rs.getString("codeSt")); // java.lang.String
		students.setClassid(rs.getInt("classId")); // java.lang.Integer
		if (rs.wasNull()) {
			students.setClassid(null);
		}
		; // not primitive number => keep null value if any
		students.setAge(rs.getInt("age")); // java.lang.Integer
		if (rs.wasNull()) {
			students.setAge(null);
		}
		; // not primitive number => keep null value if any
		students.setAddress(rs.getString("address")); // java.lang.String
		students.setPhone(rs.getString("phone")); // java.lang.String
		students.setEmail(rs.getString("email")); // java.lang.String
		students.setNote(rs.getString("note")); // java.lang.String
		students.setBirthday(rs.getDate("birthday")); // java.lang.String
		return students;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public StudentsRecord findById(Integer id) {
		StudentsRecord students = newInstanceWithPrimaryKey(id);
		if (super.doSelect(students)) {
			return students;
		} else {
			return null; // Not found
		}
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public List<StudentsRecord> findAll() {
		return super.doSelectAll();
	}

	public List<StudentsRecord> findAll(String txtSearch, int nofPage, int number) {

		List<StudentsRecord> list = new LinkedList<StudentsRecord>();
		Connection conn = null;
		int start;
		if (nofPage == 1) {
			start = 0;
		} else {
			start = (nofPage - 1) * number ;
		}
		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"select id, nameSt, codeSt, classId, age, address, phone, email, note, birthday from students where nameSt like '%"+ txtSearch + "%' LIMIT " + start + ", " + number + " ");
			// --- Execute SQL SELECT
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentsRecord bean = newInstance();
				populateBean(rs, bean);
				list.add(bean);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return list;
	}
	
	
	public List<StudentsRecord> findAll(String queryMYSQL) {

		List<StudentsRecord> list = new LinkedList<StudentsRecord>();
		Connection conn = null;
		
		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(queryMYSQL);
			// --- Execute SQL SELECT
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentsRecord bean = newInstance();
				populateBean(rs, bean);
				list.add(bean);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return list;
	}

	// ----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in
	 * its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the
	 * database<br>
	 * If not found, the given instance remains unchanged
	 * 
	 * @param students
	 * @return true if found, false if not found
	 */
	// @Override
	public boolean load(StudentsRecord students) {
		return super.doSelect(students);
	}

	// ----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database
	 * 
	 * @param students
	 */
	public long insert(StudentsRecord students) {
		Long key = super.doInsertAutoIncr(students);
		return key.longValue();
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public StudentsRecord create(StudentsRecord students) {
		insert(students);
		return students;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public boolean update(StudentsRecord students) {
		int r = super.doUpdate(students);
		return r > 0;

	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public StudentsRecord save(StudentsRecord students) {
		if (super.doExists(students)) {
			super.doUpdate(students);
		} else {
			super.doInsert(students);
		}
		return students;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public boolean deleteById(Integer id) {
		StudentsRecord students = newInstanceWithPrimaryKey(id);
		int r = super.doDelete(students);
		return r > 0;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public boolean delete(StudentsRecord students) {
		int r = super.doDelete(students);
		return r > 0;
	}

	// ----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key
	 * value(s)
	 * 
	 * @param id;
	 * @return
	 */
	// @Override
	public boolean exists(Integer id) {
		StudentsRecord students = newInstanceWithPrimaryKey(id);
		return super.doExists(students);
	}

	// ----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database
	 * 
	 * @param students
	 * @return
	 */
	// @Override
	public boolean exists(StudentsRecord students) {
		return super.doExists(students);
	}

	// ----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database
	 * 
	 * @return
	 */
	public long countAll() {
		return super.doCountAll();
	}

	public long countAll(String txt) {

		long result = 0;
		Connection conn = null;
		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(txt);
			// --- Execute SQL COUNT (without where clause)
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}

	// ----------------------------------------------------------------------
	@Override
	protected String getSqlSelect() {
		return SQL_SELECT;
	}

	// ----------------------------------------------------------------------
	@Override
	protected String getSqlSelectAll() {
		return SQL_SELECT_ALL;
	}

	// ----------------------------------------------------------------------
	@Override
	protected String getSqlInsert() {
		return SQL_INSERT;
	}

	// ----------------------------------------------------------------------
	@Override
	protected String getSqlUpdate() {
		return SQL_UPDATE;
	}

	// ----------------------------------------------------------------------
	@Override
	protected String getSqlDelete() {
		return SQL_DELETE;
	}

	// ----------------------------------------------------------------------
	@Override
	protected String getSqlCount() {
		return SQL_COUNT;
	}

	// ----------------------------------------------------------------------
	@Override
	protected String getSqlCountAll() {
		return SQL_COUNT_ALL;
	}

}