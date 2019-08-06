/*
 * Created on 2019-07-13 ( Date ISO 2019-07-13 - Time 16:20:21 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */

package org.demo.persistence.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Named;

import org.demo.data.record.ClassesRecord;
import org.demo.persistence.ClassesPersistence;
import org.demo.persistence.impl.jdbc.commons.DataSourceProvider;
import org.demo.persistence.impl.jdbc.commons.GenericJdbcDAO;

/**
 * Classes persistence implementation
 * 
 * @author Telosys
 *
 */
@Named("ClassesPersistence")
public class ClassesPersistenceJdbc extends GenericJdbcDAO<ClassesRecord> implements ClassesPersistence {

	private final static String SQL_SELECT_ALL = "select id, nameCl, codeCl, timeopenCl, timecloseCL, note, stt from classes";

	private final static String SQL_SELECT = "select id, nameCl, codeCl, timeopenCl, timecloseCL, note, stt from classes where id = ?";

	private final static String SQL_INSERT = "insert into classes ( nameCl, codeCl, timeopenCl, timecloseCL, note, stt ) values ( ?, ?, ?, ?, ?, ? )";

	private final static String SQL_UPDATE = "update classes set nameCl = ?, codeCl = ?, timeopenCl = ?, timecloseCL = ?, note = ?, stt = ? where id = ?";

	private final static String SQL_DELETE = "delete from classes where id = ?";

	private final static String SQL_COUNT_ALL = "select count(*) from classes";

	private final static String SQL_COUNT = "select count(*) from classes where id = ?";

	// ----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public ClassesPersistenceJdbc() {
		super();
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(ClassesRecord record, long value) {
		record.setId((int) value);
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, ClassesRecord classes) throws SQLException {
		// --- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, classes.getId()); // "id" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, ClassesRecord classes) throws SQLException {
		// --- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?,
		// y=?, ..." )
		// "id" is auto-incremented => no set in insert
		setValue(ps, i++, classes.getNamecl()); // "nameCl" : java.lang.String
		setValue(ps, i++, classes.getCodecl()); // "codeCl" : java.lang.String
		setValue(ps, i++, classes.getTimeopencl()); // "timeopenCl" : java.util.Date
		setValue(ps, i++, classes.getTimeclosecl()); // "timecloseCL" : java.util.Date
		setValue(ps, i++, classes.getNote()); // "note" : java.lang.String
		setValue(ps, i++, classes.getStt()); // "stt" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, ClassesRecord classes) throws SQLException {
		// --- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, classes.getNamecl()); // "nameCl" : java.lang.String
		setValue(ps, i++, classes.getCodecl()); // "codeCl" : java.lang.String
		setValue(ps, i++, classes.getTimeopencl()); // "timeopenCl" : java.util.Date
		setValue(ps, i++, classes.getTimeclosecl()); // "timecloseCL" : java.util.Date
		setValue(ps, i++, classes.getNote()); // "note" : java.lang.String
		setValue(ps, i++, classes.getStt()); // "stt" : java.lang.Integer
		// --- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, classes.getId()); // "id" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary
	 * value(s)
	 * 
	 * @param id;
	 * @return the new instance
	 */
	private ClassesRecord newInstanceWithPrimaryKey(Integer id) {
		ClassesRecord classes = new ClassesRecord();
		classes.setId(id);
		return classes;
	}

	// ----------------------------------------------------------------------
	@Override
	protected ClassesRecord newInstance() {
		return new ClassesRecord();
	}

	// ----------------------------------------------------------------------
	@Override
	protected ClassesRecord populateBean(ResultSet rs, ClassesRecord classes) throws SQLException {

		// --- Set data from ResultSet to Bean attributes
		classes.setId(rs.getInt("id")); // java.lang.Integer
		if (rs.wasNull()) {
			classes.setId(null);
		}
		; // not primitive number => keep null value if any
		classes.setNamecl(rs.getString("nameCl")); // java.lang.String
		classes.setCodecl(rs.getString("codeCl")); // java.lang.String
		classes.setTimeopencl(rs.getTime("timeopenCl")); // java.util.Date
		classes.setTimeclosecl(rs.getTime("timecloseCL")); // java.util.Date
		classes.setNote(rs.getString("note")); // java.lang.String
		classes.setStt(rs.getInt("stt")); // java.lang.Integer
		if (rs.wasNull()) {
			classes.setStt(null);
		}
		; // not primitive number => keep null value if any
		return classes;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public ClassesRecord findById(Integer id) {
		ClassesRecord classes = newInstanceWithPrimaryKey(id);
		if (super.doSelect(classes)) {
			return classes;
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

	public List<ClassesRecord> findAll() {
		return super.doSelectAll();
	}

	// ----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in
	 * its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the
	 * database<br>
	 * If not found, the given instance remains unchanged
	 * 
	 * @param classes
	 * @return true if found, false if not found
	 */
	// @Override
	public boolean load(ClassesRecord classes) {
		return super.doSelect(classes);
	}

	// ----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database
	 * 
	 * @param classes
	 */
	public long insert(ClassesRecord classes) {
		Long key = super.doInsertAutoIncr(classes);
		return key.longValue();
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public ClassesRecord create(ClassesRecord classes) {
		insert(classes);
		return classes;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public boolean update(ClassesRecord classes) {
		int r = super.doUpdate(classes);
		return r > 0;

	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public ClassesRecord save(ClassesRecord classes) {
		if (super.doExists(classes)) {
			super.doUpdate(classes);
		} else {
			super.doInsert(classes);
		}
		return classes;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public boolean deleteById(Integer id) {
		ClassesRecord classes = newInstanceWithPrimaryKey(id);
		int r = super.doDelete(classes);
		return r > 0;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public boolean delete(ClassesRecord classes) {
		int r = super.doDelete(classes);
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
		ClassesRecord classes = newInstanceWithPrimaryKey(id);
		return super.doExists(classes);
	}

	// ----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database
	 * 
	 * @param classes
	 * @return
	 */
	// @Override
	public boolean exists(ClassesRecord classes) {
		return super.doExists(classes);
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

	// Bonus method
	public List<ClassesRecord> findAll(String search, Boolean cBOpen, Boolean cBClose) {

		List<ClassesRecord> list = new LinkedList<ClassesRecord>();
		Connection conn = null;
		String sqlQuery = "SELECT * FROM classes where (nameCl like '%" + search + "%' or note like '%" + search
				+ "%')";
		if (cBOpen == false) {
			sqlQuery += " and stt != 1";
		}
		if (cBClose == false) {
			sqlQuery += " and stt != 0";
		}
		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
			// --- Execute SQL SELECT
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClassesRecord bean = newInstance();
				populateBean(rs, bean);
				list.add(bean);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return list;
	}

}