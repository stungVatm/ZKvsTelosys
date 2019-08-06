/*
 * Created on 2019-06-28 ( Date ISO 2019-06-28 - Time 08:07:42 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */

package org.demo.persistence.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Named;


import org.demo.data.record.SubjectsRecord ;
import org.demo.persistence.SubjectsPersistence;
import org.demo.persistence.impl.jdbc.commons.GenericJdbcDAO;

/**
 * Subjects persistence implementation 
 * 
 * @author Telosys
 *
 */
@Named("SubjectsPersistence")
public class SubjectsPersistenceJdbc extends GenericJdbcDAO<SubjectsRecord> implements SubjectsPersistence {

	private final static String SQL_SELECT_ALL = 
		"select id, nameSu, codeSu, factor, note from subjects"; 

	private final static String SQL_SELECT = 
		"select id, nameSu, codeSu, factor, note from subjects where id = ?";

	private final static String SQL_INSERT = 
		"insert into subjects ( nameSu, codeSu, factor, note ) values ( ?, ?, ?, ? )";

	private final static String SQL_UPDATE = 
		"update subjects set nameSu = ?, codeSu = ?, factor = ?, note = ? where id = ?";

	private final static String SQL_DELETE = 
		"delete from subjects where id = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from subjects";

	private final static String SQL_COUNT = 
		"select count(*) from subjects where id = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public SubjectsPersistenceJdbc() {
		super();
	}

    //----------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(SubjectsRecord record, long value) {
		record.setId((Integer)((int)value));
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, SubjectsRecord subjects) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, subjects.getId() ) ; // "id" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, SubjectsRecord subjects) throws SQLException {
		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		// "id" is auto-incremented => no set in insert		
		setValue(ps, i++, subjects.getNamesu() ) ; // "nameSu" : java.lang.String
		setValue(ps, i++, subjects.getCodesu() ) ; // "codeSu" : java.lang.String
		setValue(ps, i++, subjects.getFactor() ) ; // "factor" : java.lang.Integer
		setValue(ps, i++, subjects.getNote() ) ; // "note" : java.lang.String
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, SubjectsRecord subjects) throws SQLException {
		//--- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, subjects.getNamesu() ) ; // "nameSu" : java.lang.String
		setValue(ps, i++, subjects.getCodesu() ) ; // "codeSu" : java.lang.String
		setValue(ps, i++, subjects.getFactor() ) ; // "factor" : java.lang.Integer
		setValue(ps, i++, subjects.getNote() ) ; // "note" : java.lang.String
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, subjects.getId() ) ; // "id" : java.lang.Integer
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param id;
	 * @return the new instance
	 */
	private SubjectsRecord newInstanceWithPrimaryKey( Integer id ) {
		SubjectsRecord subjects = new SubjectsRecord();
		subjects.setId( id );
		return subjects ;
	}

	//----------------------------------------------------------------------
	@Override
	protected SubjectsRecord newInstance() {
		return new SubjectsRecord() ;
	}

    //----------------------------------------------------------------------
	@Override
	protected SubjectsRecord populateBean(ResultSet rs, SubjectsRecord subjects) throws SQLException {

		//--- Set data from ResultSet to Bean attributes
		subjects.setId(rs.getInt("id")); // java.lang.Integer
		if ( rs.wasNull() ) { subjects.setId(null); }; // not primitive number => keep null value if any
		subjects.setNamesu(rs.getString("nameSu")); // java.lang.String
		subjects.setCodesu(rs.getString("codeSu")); // java.lang.String
		subjects.setFactor(rs.getInt("factor")); // java.lang.Integer
		if ( rs.wasNull() ) { subjects.setFactor(null); }; // not primitive number => keep null value if any
		subjects.setNote(rs.getString("note")); // java.lang.String
		return subjects ;
	}

	//----------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see interface 
	 */
	public SubjectsRecord findById( Integer id ) {
		SubjectsRecord subjects = newInstanceWithPrimaryKey( id ) ;
		if ( super.doSelect(subjects) ) {
			return subjects ;
		}
		else {
			return null ; // Not found
		}
	}
	//----------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see interface 
	 */
	public List<SubjectsRecord> findAll() {
		return super.doSelectAll();
	}

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param subjects
	 * @return true if found, false if not found
	 */
	//@Override
	public boolean load( SubjectsRecord subjects ) {
		return super.doSelect(subjects) ;
	}

    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param subjects
	 */
	public long insert(SubjectsRecord subjects) {
		Long key = super.doInsertAutoIncr(subjects);
		return key.longValue();
	}

    //----------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see interface 
	 */
	public SubjectsRecord create(SubjectsRecord subjects) {
		insert(subjects);
		return subjects ;
	}	

    //----------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see interface 
	 */
	public boolean update(SubjectsRecord subjects) {
		int r = super.doUpdate(subjects);
		return r > 0 ;

	}	

    //----------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see interface 
	 */
	public SubjectsRecord save(SubjectsRecord subjects) {
		if ( super.doExists(subjects) ) {
			super.doUpdate(subjects);
		}
		else {
			super.doInsert(subjects);
		}
		return subjects ;
	}	

    //----------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see interface 
	 */
	public boolean deleteById( Integer id ) {
		SubjectsRecord subjects = newInstanceWithPrimaryKey( id ) ;
		int r = super.doDelete(subjects);
		return r > 0 ;
	}

    //----------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see interface 
	 */
	public boolean delete( SubjectsRecord subjects ) {
		int r = super.doDelete(subjects);
		return r > 0 ;
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param id;
	 * @return
	 */
	// @Override
	public boolean exists( Integer id ) {
		SubjectsRecord subjects = newInstanceWithPrimaryKey( id ) ;
		return super.doExists(subjects);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param subjects
	 * @return
	 */
	// @Override
	public boolean exists( SubjectsRecord subjects ) {
		return super.doExists(subjects);
	}

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database
	 * @return
	 */
	public long countAll() {
		return super.doCountAll();
	}

    //----------------------------------------------------------------------
	@Override
	protected String getSqlSelect() {
		return SQL_SELECT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlSelectAll() {
		return SQL_SELECT_ALL;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlInsert() {
		return SQL_INSERT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlUpdate() {
		return SQL_UPDATE ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlDelete() {
		return SQL_DELETE ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlCount() {
		return SQL_COUNT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlCountAll() {
		return SQL_COUNT_ALL ;
	}

}