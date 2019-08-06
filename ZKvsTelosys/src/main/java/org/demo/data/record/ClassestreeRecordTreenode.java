package org.demo.data.record;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

public class ClassestreeRecordTreenode extends DefaultTreeModel<ClassestreeRecord> {

	public ClassestreeRecordTreenode(TreeNode<ClassestreeRecord> root) {
		super(root);
		// TODO Auto-generated constructor stub
	}
	
	List<ClassestreeRecord> lst = new ArrayList<ClassestreeRecord>();

    private TreeModel<TreeNode<ClassestreeRecord>> myTreeNode;
    
	public void buildTree() {
		ClassestreeRecord cc;
		for (int i = 0; i < lst.size(); i++) {
			cc = lst.get(i);
			
		}
	}

}
