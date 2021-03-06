/*
 * Created on 2019-07-13 ( Date ISO 2019-07-13 - Time 16:20:29 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */


package org.demo.data.record;

import java.io.Serializable;
import java.sql.Time;

import javax.validation.constraints.*;

import java.util.Date;

/**
 * Java bean for entity "classes" <br>
 * Contains only "wrapper types" (no primitive types) <br>
 * Can be used both as a "web form" and "persistence record" <br>
 * 
 * @author Telosys Tools Generator
 *
 */
public class ClassesRecord implements Serializable
{
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id ; // Integer // Id or Primary Key

    @Size( max = 50 )
    private String namecl ;  // String 
    @Size( max = 50 )
    private String codecl ;  // String 

    private Time timeopencl ;  // Date 

    private Time timeclosecl ;  // Date 
    @Size( max = 50 )
    private String note ;  // String 

    private Integer stt ;  // Integer 

    /**
     * Default constructor
     */
    public ClassesRecord() {
        super();
    }
    
    //----------------------------------------------------------------------
    // GETTER(S) & SETTER(S) FOR ID OR PRIMARY KEY 
    //----------------------------------------------------------------------
    /**
     * Set the "id" field value
     * This field is mapped on the database column "id" ( type "INT", NotNull : true ) 
     * @param id
     */
	public void setId( Integer id ) {
        this.id = id ;
    }
    /**
     * Get the "id" field value
     * This field is mapped on the database column "id" ( type "INT", NotNull : true ) 
     * @return the field value
     */
	public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTER(S) & SETTER(S) FOR OTHER DATA FIELDS 
    //----------------------------------------------------------------------

    /**
     * Set the "namecl" field value
     * This field is mapped on the database column "nameCl" ( type "VARCHAR", NotNull : false ) 
     * @param namecl
     */
    public void setNamecl( String namecl ) {
        this.namecl = namecl;
    }
    /**
     * Get the "namecl" field value
     * This field is mapped on the database column "nameCl" ( type "VARCHAR", NotNull : false ) 
     * @return the field value
     */
    public String getNamecl() {
        return this.namecl;
    }

    /**
     * Set the "codecl" field value
     * This field is mapped on the database column "codeCl" ( type "VARCHAR", NotNull : false ) 
     * @param codecl
     */
    public void setCodecl( String codecl ) {
        this.codecl = codecl;
    }
    /**
     * Get the "codecl" field value
     * This field is mapped on the database column "codeCl" ( type "VARCHAR", NotNull : false ) 
     * @return the field value
     */
    public String getCodecl() {
        return this.codecl;
    }

    /**
     * Set the "timeopencl" field value
     * This field is mapped on the database column "timeopenCl" ( type "TIME", NotNull : false ) 
     * @param timeopencl
     */
    public void setTimeopencl( Time timeopencl ) {
        this.timeopencl = timeopencl;
    }
    /**
     * Get the "timeopencl" field value
     * This field is mapped on the database column "timeopenCl" ( type "TIME", NotNull : false ) 
     * @return the field value
     */
    public Time getTimeopencl() {
        return this.timeopencl;
    }

    /**
     * Set the "timeclosecl" field value
     * This field is mapped on the database column "timecloseCL" ( type "TIME", NotNull : false ) 
     * @param timeclosecl
     */
    public void setTimeclosecl( Time timeclosecl ) {
        this.timeclosecl = timeclosecl;
    }
    /**
     * Get the "timeclosecl" field value
     * This field is mapped on the database column "timecloseCL" ( type "TIME", NotNull : false ) 
     * @return the field value
     */
    public Time getTimeclosecl() {
        return this.timeclosecl;
    }

    /**
     * Set the "note" field value
     * This field is mapped on the database column "note" ( type "VARCHAR", NotNull : false ) 
     * @param note
     */
    public void setNote( String note ) {
        this.note = note;
    }
    /**
     * Get the "note" field value
     * This field is mapped on the database column "note" ( type "VARCHAR", NotNull : false ) 
     * @return the field value
     */
    public String getNote() {
        return this.note;
    }

    /**
     * Set the "stt" field value
     * This field is mapped on the database column "stt" ( type "INT", NotNull : false ) 
     * @param stt
     */
    public void setStt( Integer stt ) {
        this.stt = stt;
    }
    /**
     * Get the "stt" field value
     * This field is mapped on the database column "stt" ( type "INT", NotNull : false ) 
     * @return the field value
     */
    public Integer getStt() {
        return this.stt;
    }

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    @Override
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(namecl);
        sb.append("|");
        sb.append(codecl);
        sb.append("|");
        sb.append(timeopencl);
        sb.append("|");
        sb.append(timeclosecl);
        sb.append("|");
        sb.append(note);
        sb.append("|");
        sb.append(stt);
        return sb.toString(); 
    } 



}
