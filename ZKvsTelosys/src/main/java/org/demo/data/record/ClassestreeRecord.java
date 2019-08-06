/*
 * Created on 2019-07-09 ( Date ISO 2019-07-09 - Time 11:31:14 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */

package org.demo.data.record;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.*;

import org.demo.persistence.impl.jdbc.ClassestreePersistenceJdbc;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

/**
 * Java bean for entity "classestree" <br>
 * Contains only "wrapper types" (no primitive types) <br>
 * Can be used both as a "web form" and "persistence record" <br>
 * 
 * @author Telosys Tools Generator
 *
 */
public class ClassestreeRecord implements Serializable {

	public ClassestreeRecord() {

	}

	public ClassestreeRecord(Integer id, String namecl, Integer idperent) {
		super();
		this.id = id;
		this.namecl = namecl;
		this.idperent = idperent;
	}

	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer id; // Integer // Id or Primary Key

	@Size(max = 50)
	private String namecl; // String

	private Integer idperent; // Integer

	/**
	 * Default constructor
	 */

	// ----------------------------------------------------------------------
	// GETTER(S) & SETTER(S) FOR ID OR PRIMARY KEY
	// ----------------------------------------------------------------------
	/**
	 * Set the "id" field value This field is mapped on the database column "id" (
	 * type "INT", NotNull : true )
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Get the "id" field value This field is mapped on the database column "id" (
	 * type "INT", NotNull : true )
	 * 
	 * @return the field value
	 */
	public Integer getId() {
		return this.id;
	}

	// ----------------------------------------------------------------------
	// GETTER(S) & SETTER(S) FOR OTHER DATA FIELDS
	// ----------------------------------------------------------------------

	/**
	 * Set the "namecl" field value This field is mapped on the database column
	 * "NameCl" ( type "VARCHAR", NotNull : false )
	 * 
	 * @param namecl
	 */
	public void setNamecl(String namecl) {
		this.namecl = namecl;
	}

	/**
	 * Get the "namecl" field value This field is mapped on the database column
	 * "NameCl" ( type "VARCHAR", NotNull : false )
	 * 
	 * @return the field value
	 */
	public String getNamecl() {
		return this.namecl;
	}

	/**
	 * Set the "idperent" field value This field is mapped on the database column
	 * "idPerent" ( type "INT", NotNull : false )
	 * 
	 * @param idperent
	 */
	public void setIdperent(Integer idperent) {
		this.idperent = idperent;
	}

	/**
	 * Get the "idperent" field value This field is mapped on the database column
	 * "idPerent" ( type "INT", NotNull : false )
	 * 
	 * @return the field value
	 */
	public Integer getIdperent() {
		return this.idperent;
	}

	// ----------------------------------------------------------------------
	// toString METHOD
	// ----------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append("|");
		sb.append(namecl);
		sb.append("|");
		sb.append(idperent);
		return sb.toString();
	}

	public ClassestreeRecord returnParent() {
		ClassestreePersistenceJdbc clstrPerJDBC = new ClassestreePersistenceJdbc();
		if (this.getIdperent() != null) {
			return clstrPerJDBC.findById(this.getIdperent());
		}
		return null;
	}

	@SuppressWarnings("null")
	public List<ClassestreeRecord> listParent() {
		List<ClassestreeRecord> list = new LinkedList<ClassestreeRecord>();
		ClassestreeRecord parent = this.returnParent();
		if (parent != null) {
			list = parent.listParent();
			list.add(parent);
			return list;
		} else {
			return list;
		}

	}

}