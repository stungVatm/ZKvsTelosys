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

import org.demo.data.record.ScoresRecord;
import org.demo.persistence.ScoresPersistence;
import org.demo.persistence.impl.jdbc.commons.DataSourceProvider;
import org.demo.persistence.impl.jdbc.commons.GenericJdbcDAO;

/**
 * Scores persistence implementation
 * 
 * @author Telosys
 *
 */
@Named("ScoresPersistence")
public class ScoresPersistenceJdbc extends GenericJdbcDAO<ScoresRecord> implements ScoresPersistence {

	private final static String SQL_SELECT_ALL = "select id, studentId, subjectId, score from scores";

	private final static String SQL_SELECT = "select id, studentId, subjectId, score from scores where id = ?";

	private final static String SQL_INSERT = "insert into scores ( studentId, subjectId, score ) values ( ?, ?, ? )";

	private final static String SQL_UPDATE = "update scores set studentId = ?, subjectId = ?, score = ? where id = ?";

	private final static String SQL_DELETE = "delete from scores where id = ?";

	private final static String SQL_COUNT_ALL = "select count(*) from scores";

	private final static String SQL_COUNT = "select count(*) from scores where id = ?";

	// ----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public ScoresPersistenceJdbc() {
		super();
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(ScoresRecord record, long value) {
		record.setId((Integer) ((int) value));
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, ScoresRecord scores) throws SQLException {
		// --- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, scores.getId()); // "id" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, ScoresRecord scores) throws SQLException {
		// --- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?,
		// y=?, ..." )
		// "id" is auto-incremented => no set in insert
		setValue(ps, i++, scores.getStudentid()); // "studentId" : java.lang.Integer
		setValue(ps, i++, scores.getSubjectid()); // "subjectId" : java.lang.Integer
		setValue(ps, i++, scores.getScore()); // "score" : java.lang.Double
	}

	// ----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, ScoresRecord scores) throws SQLException {
		// --- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, scores.getStudentid()); // "studentId" : java.lang.Integer
		setValue(ps, i++, scores.getSubjectid()); // "subjectId" : java.lang.Integer
		setValue(ps, i++, scores.getScore()); // "score" : java.lang.Double
		// --- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, scores.getId()); // "id" : java.lang.Integer
	}

	// ----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary
	 * value(s)
	 * 
	 * @param id;
	 * @return the new instance
	 */
	private ScoresRecord newInstanceWithPrimaryKey(Integer id) {
		ScoresRecord scores = new ScoresRecord();
		scores.setId(id);
		return scores;
	}

	// ----------------------------------------------------------------------
	@Override
	protected ScoresRecord newInstance() {
		return new ScoresRecord();
	}

	// ----------------------------------------------------------------------
	@Override
	protected ScoresRecord populateBean(ResultSet rs, ScoresRecord scores) throws SQLException {

		// --- Set data from ResultSet to Bean attributes
		scores.setId(rs.getInt("id")); // java.lang.Integer
		if (rs.wasNull()) {
			scores.setId(null);
		}
		; // not primitive number => keep null value if any
		scores.setStudentid(rs.getInt("studentId")); // java.lang.Integer
		if (rs.wasNull()) {
			scores.setStudentid(null);
		}
		; // not primitive number => keep null value if any
		scores.setSubjectid(rs.getInt("subjectId")); // java.lang.Integer
		if (rs.wasNull()) {
			scores.setSubjectid(null);
		}
		; // not primitive number => keep null value if any
		scores.setScore(rs.getDouble("score")); // java.lang.Double
		if (rs.wasNull()) {
			scores.setScore(null);
		}
		; // not primitive number => keep null value if any
		return scores;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public ScoresRecord findById(Integer id) {
		ScoresRecord scores = newInstanceWithPrimaryKey(id);
		if (super.doSelect(scores)) {
			return scores;
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
	public List<ScoresRecord> findAll() {
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
	 * @param scores
	 * @return true if found, false if not found
	 */
	// @Override
	public boolean load(ScoresRecord scores) {
		return super.doSelect(scores);
	}

	// ----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database
	 * 
	 * @param scores
	 */
	public long insert(ScoresRecord scores) {
		Long key = super.doInsertAutoIncr(scores);
		return key.longValue();
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public ScoresRecord create(ScoresRecord scores) {
		insert(scores);
		return scores;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public boolean update(ScoresRecord scores) {
		int r = super.doUpdate(scores);
		return r > 0;

	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public ScoresRecord save(ScoresRecord scores) {
		if (super.doExists(scores)) {
			super.doUpdate(scores);
		} else {
			super.doInsert(scores);
		}
		return scores;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public boolean deleteById(Integer id) {
		ScoresRecord scores = newInstanceWithPrimaryKey(id);
		int r = super.doDelete(scores);
		return r > 0;
	}

	// ----------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see interface
	 */
	public boolean delete(ScoresRecord scores) {
		int r = super.doDelete(scores);
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
		ScoresRecord scores = newInstanceWithPrimaryKey(id);
		return super.doExists(scores);
	}

	// ----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database
	 * 
	 * @param scores
	 * @return
	 */
	// @Override
	public boolean exists(ScoresRecord scores) {
		return super.doExists(scores);
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

	// 1 Write plus
	public List<ScoresRecord> findByStudentID(Integer id) {

		List<ScoresRecord> list = new LinkedList<ScoresRecord>();
		Connection conn = null;

		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select id, studentId, subjectId, score from scores where studentId = ? ");
			ps.setObject(1, id);
			// --- Execute SQL SELECT
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ScoresRecord bean = newInstance();
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

	public List<ScoresRecord> findByStdIDvsSubID(Integer idStd, Integer idSub) {

		List<ScoresRecord> list = new LinkedList<ScoresRecord>();
		Connection conn = null;

		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"select id, studentId, subjectId, score from scores where studentId = ? and subjectId = ? ");
			ps.setObject(1, idStd);
			ps.setObject(2, idSub);
			// --- Execute SQL SELECT
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ScoresRecord bean = newInstance();
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
