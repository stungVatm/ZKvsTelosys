package com.sopen.controller.test;

import java.util.Collection;
import java.util.LinkedList;

import org.demo.data.record.ClassestreeRecord;
import org.demo.persistence.impl.jdbc.ClassestreePersistenceJdbc;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

public class ClassesTreeNode extends DefaultTreeNode<ClassestreeRecord> {

	
	
    int count;
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

	ClassestreePersistenceJdbc clsTrPerJDBC = new ClassestreePersistenceJdbc();
    public ClassesTreeNode(ClassestreeRecord category, int count) {
        super(category, new LinkedList<TreeNode<ClassestreeRecord>>()); // assume not a leaf-node
        this.count = count;
    }
    
    public String getDescription() {
        return getData().getNamecl();
    }
 
    public int getCount() {
        return count;
    }
 
    public boolean isLeaf() {
        return getData() != null && clsTrPerJDBC.findAllByIdParent(getData().getId()) .isEmpty();
    }
	
}
