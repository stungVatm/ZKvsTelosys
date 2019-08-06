/*
 * Created on 2019-06-28 ( Date ISO 2019-06-28 - Time 08:07:05 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */
package org.demo.persistence;

import java.util.List;

import org.demo.data.record.AdminRecord;

/**
 * Persistence Interface for AdminRecord.
 */
public interface AdminPersistence { 

	/**
	 * Tries to find an entity using its Id / Primary Key
	 * @param id
	 * @return entity
	 */
	AdminRecord findById( Integer id  ) ;

	/**
	 * Finds all entities.
	 * @return all entities
	 */
	List<AdminRecord> findAll();

	/**
	 * Counts all the records present in the database
	 * @return
	 */
	public long countAll() ;


	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	AdminRecord save(AdminRecord entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return true if the entity has been updated, false if not found and not updated
	 */
	boolean update(AdminRecord entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	AdminRecord create(AdminRecord entity);

	/**
	 * Deletes an entity using its Id / Primary Key
	 * @param id
	 * @return true if the entity has been deleted, false if not found and not deleted
	 */
	boolean deleteById( Integer id );

	/**
	 * Deletes an entity using the Id / Primary Key stored in the given object
	 * @param the entity to be deleted (supposed to have a valid Id/PK )
	 * @return true if the entity has been deleted, false if not found and not deleted
	 */
	boolean delete( AdminRecord entity );

	/**
	 * Returns true if an entity exists with the given Id / Primary Key 
	 * @param id
	 * @return
	 */
	public boolean exists( Integer id ) ;

	/**
	 * Returns true if the given entity exists
	 * @param entity
	 * @return
	 */
	public boolean exists( AdminRecord entity ) ;

}
