package com.sopen.controller.test;

import java.util.Collection;
import java.util.LinkedList;

import org.demo.data.record.ClassestreeRecord;
import org.demo.persistence.impl.jdbc.ClassestreePersistenceJdbc;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

public class ClassesTreeNodePlus extends DefaultTreeNode<ClassestreeRecord> {

	
	//truy xuat vao CSDL
	ClassestreePersistenceJdbc clsTrPerJDBC = new ClassestreePersistenceJdbc();
	
    String nameCl;
    int ID;
    
    public String getNameCl() {
		return getData().getNamecl();
	}

	public void setNameCl(String nameCl) {
		this.nameCl = nameCl;
	}

	public int getID() {
		return getData().getId();
	}

	public void setID(int iD) {
		ID = iD;
	}

	
	
	//Contructer
    public ClassesTreeNodePlus(ClassestreeRecord category) {
        super(category, new LinkedList<TreeNode<ClassestreeRecord>>()); // assume not a leaf-node
    }
    
    
    
    public String getDescription() {
        return getData().getNamecl();
    }
 
 
    public boolean isLeaf() {
        return getData() != null && clsTrPerJDBC.findAllByIdParent(getData().getId()) .isEmpty();
    }
	
}
