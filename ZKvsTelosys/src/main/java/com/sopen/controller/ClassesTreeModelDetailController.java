package com.sopen.controller;

import org.demo.data.record.ClassestreeRecord;
import org.demo.persistence.impl.jdbc.ClassestreePersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ClassesTreeModelDetailController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Wire
	@Wire
	Window wdClassesTreeModel_Detail;
	@Wire
	Button btnSave;
	@Wire
	Listbox lstBoxClassesModel;
	@Wire 
	Label labNameClass;
	@Wire
	Textbox txtBNameClass;
	// Declare
	ClassestreeRecord record;
	ClassTreeModelController clsTrModelCtr;

	public ClassTreeModelController getClsTrModelCtr() {
		return clsTrModelCtr;
	}

	public void setClsTrModelCtr(ClassTreeModelController clsTrModelCtr) {
		this.clsTrModelCtr = clsTrModelCtr;
	}

	ClassestreePersistenceJdbc clsTrPerJDBC = new ClassestreePersistenceJdbc();

	public ClassestreeRecord getRecord() {
		return record;
	}

	public void setRecord(ClassestreeRecord record) {
		this.record = record;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		wdClassesTreeModel_Detail.setAttribute("Controller", this);
		for (ClassestreeRecord record : clsTrPerJDBC.findAll()) {
			Listitem newListitem = new Listitem();
			newListitem.setLabel(record.getNamecl()+"("+record.getId()+")");
			newListitem.setAttribute("valueID", record.getId());
			newListitem.setParent(lstBoxClassesModel);
		}

	}

	@Listen("onClick = #btnSave")
	public void createNewClassesModel() {
		lstBoxClassesModel.getSelectedItem().getLabel();
		int idParent = (Integer) lstBoxClassesModel.getSelectedItem().getAttribute("valueID");
		record.setNamecl(txtBNameClass.getValue());
		record.setIdperent(idParent);
		clsTrPerJDBC.save(record);
		wdClassesTreeModel_Detail.onClose();
		clsTrModelCtr.refresh();
	}

}
