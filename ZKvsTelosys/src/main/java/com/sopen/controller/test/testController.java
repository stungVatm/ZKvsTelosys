package com.sopen.controller.test;

import java.util.LinkedList;
import java.util.List;

import org.demo.data.record.ClassestreeRecord;
import org.demo.persistence.impl.jdbc.ClassestreePersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

public class testController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Wire
	@Wire
	Tree categoriesTree;
	// declare
	ClassestreePersistenceJdbc clsTrPerJDBC = new ClassestreePersistenceJdbc();
	private TreeModel<TreeNode<ClassestreeRecord>> classesModel;
	private List<ClassestreeRecord> listData = new LinkedList<ClassestreeRecord>();

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadDataPlus();
	}


	public void loadDataPlus() {
		listData = clsTrPerJDBC.findAll();
		//lay ra doi tuong record
		ClassestreeRecord clsRec = clsTrPerJDBC.findById(1);
		//bien cai doi tuong record => node tree
		ClassesTreeNodePlus rootNode = constructClassesTreeNodePlus(clsRec);
		classesModel = new DefaultTreeModel<ClassestreeRecord>(rootNode);
		categoriesTree.setModel(classesModel);
		// phan trang
		categoriesTree.setMold("paging");
		categoriesTree.setPageSize(10);
	}

	// method xuat ra 1 Node day du tu 1 rootNode ban dau
	private ClassesTreeNodePlus constructClassesTreeNodePlus(ClassestreeRecord clsRec) {
		ClassesTreeNodePlus classesNode = new ClassesTreeNodePlus(clsRec);
		renderNode(classesNode);
		ClassesTreeNodePlus rootNode = new ClassesTreeNodePlus(null); // won't show
		rootNode.add(classesNode);
		return rootNode;
	}
	// method " truy hoi " duyet toan bo
	public void renderNode(ClassesTreeNodePlus classesNode) {
		for (ClassestreeRecord recTree : findAllByIdParent(classesNode.getID())) {
			ClassesTreeNodePlus recTreeNode = new ClassesTreeNodePlus(recTree);
			classesNode.add(recTreeNode);
			renderNode(recTreeNode);
		}
	}

	// Give list children of the node
	public List<ClassestreeRecord> findAllByIdParent(int keyParent) {
		List<ClassestreeRecord> list = new LinkedList<ClassestreeRecord>();
		
		for (int i = 0; i < listData.size(); i++) {
			ClassestreeRecord clsTreeRc = listData.get(i);
			if (clsTreeRc.getIdperent() == null) {
				listData.remove(clsTreeRc);
				i--;
			} else if (clsTreeRc.getIdperent() == keyParent) {
				list.add(clsTreeRc);
				listData.remove(clsTreeRc);
				i--;
			}
		}
		return list;
	}
}
