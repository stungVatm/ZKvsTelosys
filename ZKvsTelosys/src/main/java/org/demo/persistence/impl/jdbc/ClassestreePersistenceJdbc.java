/*
 * Created on 2019-07-09 ( Date ISO 2019-07-09 - Time 11:31:26 )
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

import org.demo.data.record.ClassestreeRecord;
import org.demo.data.record.StudentsRecord;
import org.demo.persistence.ClassestreePersistence;
import org.demo.persistence.impl.jdbc.commons.DataSourceProvider;
import org.demo.persistence.impl.jdbc.commons.GenericJdbcDAO;

/**
 * Classestree persistence implementation
 * 
 * @author Telosys
 *
 */
@Named("ClassestreePersistence")
public class ClassestreePersistenceJdbc extends GenericJdbcDAO<ClassestreeRecord> implements ClassestreePersistence {

	private final static String SQL_SELECT_ALL = "select id, NameCl, idPerent from classestree";

	private final static String SQL_SELECT = "select id, NameCl, idPerent from classestree where id = ?";

	private final static String SQL_INSERT = "insert into classestree ( NameCl, idPerent ) values ( ?, ? )";

	private final static String SQL_UPDATE = "update classestree set NameCl = ?, idPerent = ? where id = ?";

	private final static String SQL_DELETE = "delete from classestree where id = ?";

	private final static String SQL_COUNT_ALL = "select count(*) from classestree";

	private final static String SQL_COUNT = "select count(*) from classestree where id = ?";

	// ----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public ClassestreePersistenceJdbc() {
		super();
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(ClassestreeRecord record, long value) {
		record.setId((int) value);
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, ClassestreeRecord classestree)
			throws SQLException {
		// --- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, classestree.getId()); // "id" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, ClassestreeRecord classestree) throws SQLException {
		// --- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?,
		// y=?, ..." )
		// "id" is auto-incremented => no set in insert
		setValue(ps, i++, classestree.getNamecl()); // "NameCl" : java.lang.String
		setValue(ps, i++, classestree.getIdperent()); // "idPerent" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, ClassestreeRecord classestree) throws SQLException {
		// --- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, classestree.getNamecl()); // "NameCl" : java.lang.String
		setValue(ps, i++, classestree.getIdperent()); // "idPerent" : java.lang.Integer
		// --- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, classestree.getId()); // "id" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary
	 * value(s)
	 * 
	 * @param id;
	 * @return the new instance
	 */
	private ClassestreeRecord newInstanceWithPrimaryKey(Integer id) {
		ClassestreeRecord classestree = new ClassestreeRecord();
		classestree.setId(id);
		return classestree;
	}

	// ----------------------------------------------------------------------
	@Override
	protected ClassestreeRecord newInstance() {
		return new ClassestreeRecord();
	}

	// ----------------------------------------------------------------------
	@Override
	protected ClassestreeRecord populateBean(ResultSet rs, ClassestreeRecord classestree) throws SQLException {

		// --- Set data from ResultSet to Bean attributes
		classestree.setId(rs.getInt("id")); // java.lang.Integer
		if (rs.wasNull()) {
			classestree.setId(null);
		}
		; // not primitive number => keep null value if any
		classestree.setNamecl(rs.getString("NameCl")); // java.lang.String
		classestree.setIdperent(rs.getInt("idPerent")); // java.lang.Integer
		if (rs.wasNull()) {
			classestree.setIdperent(null);
		}
		; // not primitive number => keep null value if any
		return classestree;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public ClassestreeRecord findById(Integer id) {
		ClassestreeRecord classestree = newInstanceWithPrimaryKey(id);
		if (super.doSelect(classestree)) {
			return classestree;
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

	public List<ClassestreeRecord> findAll() {
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
	 * @param classestree
	 * @return true if found, false if not found
	 */
	// @Override
	public boolean load(ClassestreeRecord classestree) {
		return super.doSelect(classestree);
	}

	// ----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database
	 * 
	 * @param classestree
	 */
	public long insert(ClassestreeRecord classestree) {
		Long key = super.doInsertAutoIncr(classestree);
		return key.longValue();
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public ClassestreeRecord create(ClassestreeRecord classestree) {
		insert(classestree);
		return classestree;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public boolean update(ClassestreeRecord classestree) {
		int r = super.doUpdate(classestree);
		return r > 0;

	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public ClassestreeRecord save(ClassestreeRecord classestree) {
		if (super.doExists(classestree)) {
			super.doUpdate(classestree);
		} else {
			super.doInsert(classestree);
		}
		return classestree;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public boolean deleteById(Integer id) {
		ClassestreeRecord classestree = newInstanceWithPrimaryKey(id);
		int r = super.doDelete(classestree);
		return r > 0;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */

	public boolean delete(ClassestreeRecord classestree) {
		int r = super.doDelete(classestree);
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
		ClassestreeRecord classestree = newInstanceWithPrimaryKey(id);
		return super.doExists(classestree);
	}

	// ----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database
	 * 
	 * @param classestree
	 * @return
	 */
	// @Override
	public boolean exists(ClassestreeRecord classestree) {
		return super.doExists(classestree);
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

	// plus Write

	public List<ClassestreeRecord> findAllByIdParent(int keyParent) {
		List<ClassestreeRecord> list = new LinkedList<ClassestreeRecord>();
		Connection conn = null;
		String queryMYSQ;
		if (keyParent == 0) {
			queryMYSQ = "select * from classesTree where idPerent is null";
		} else {
			queryMYSQ = "select * from classesTree where idPerent = " + keyParent + "";
		}

		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(queryMYSQ);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClassestreeRecord bean = newInstance();
				populateBean(rs, bean);
				list.add(bean);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
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

	public List<ClassestreeRecord> findAllChildren() {
		List<ClassestreeRecord> list = new LinkedList<ClassestreeRecord>();
		Connection conn = null;
		String queryMYSQ = "select * from classestree where id not in (select DISTINCT idPerent from classestree where idPerent is not null );";

		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(queryMYSQ);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClassestreeRecord bean = newInstance();
				populateBean(rs, bean);
				list.add(bean);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
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
}
