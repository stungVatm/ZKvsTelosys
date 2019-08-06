package com.sopen.controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.demo.data.record.ClassesRecord;
import org.demo.persistence.impl.jdbc.ClassesPersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

public class ClassDetailController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Declare wire
	@Wire
	Window wdClassesDetail;
	@Wire
	Button btnSave;
	@Wire
	Textbox txtbClassName, txtNote;
	@Wire
	Timebox tBOpen, tBClose;
	@Wire
	Radiogroup rdoGStt;
	@Wire
	Label lbErroTime, lbErroName;
	// Declare Variable

	ClassesPersistenceJdbc clsPerJDBC = new ClassesPersistenceJdbc();
	private ClassesRecord clsRc;
	private ClassesListController clsListController;

	public ClassesListController getClsListController() {
		return clsListController;
	}

	public void setClsListController(ClassesListController clsListController) {
		this.clsListController = clsListController;
	}

	public ClassesRecord getClsRc() {
		return clsRc;
	}

	public void setClsRc(ClassesRecord clsRc) throws WrongValueException, ParseException {
		this.clsRc = clsRc;
		lbErroTime.setValue("");
		lbErroName.setValue("");
		if (clsRc != null) {
			txtbClassName.setValue(clsRc.getNamecl());
			if (clsRc.getTimeopencl() == null) {
				tBOpen.setValue(new Time(3600000));
			} else {
				tBOpen.setValue(clsRc.getTimeopencl());
			}

			if (clsRc.getTimeopencl() == null) {
				tBClose.setValue(new Time(37800000));
			} else {
				tBClose.setValue(clsRc.getTimeclosecl());
			}

			txtNote.setValue(clsRc.getNote());
		}
	}

	// Method
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		wdClassesDetail.setAttribute("controller", this);
	}

	@Listen("onClick = #btnSave")
	public void saveRecord() {
//		validate();
		lbErroName.setValue("");
		if (txtbClassName.getValue().isEmpty()) {
			lbErroName.setValue("This name cant be empty");
			Clients.evalJavaScript("$( \".z-window\" ).effect( \"shake\" );");
			return;
		}

		if (validate() == false) {
			alert("Sr, You need check the condition plz!!");
			Clients.evalJavaScript("$( \".z-window\" ).effect( \"shake\" );");
			return;
		}
		clsRc.setNamecl(txtbClassName.getValue());
		clsRc.setTimeopencl(new Time(tBOpen.getValue().getTime()));
		clsRc.setTimeclosecl(new Time(tBClose.getValue().getTime()));
		clsRc.setNote(txtNote.getValue());
		if (rdoGStt.getSelectedItem().getLabel().equals("Open")) {
			clsRc.setStt(1);
		} else {
			clsRc.setStt(0);
		}
		clsPerJDBC.save(clsRc);
		wdClassesDetail.onClose();
		clsListController.loadData();
	}

	private boolean validate() {
		if (!lbErroName.getValue().isEmpty()) {
			return false;
		}

		if (!lbErroTime.getValue().isEmpty()) {
			return false;
		}
		return true;
	}

	@Listen("onChange = #tBClose,#tBOpen")
	public void setrank() {
		if (tBOpen.getValue() == null) {
			tBOpen.setValue(new Time(3600000));
		}

		if (tBClose.getValue() == null) {
			tBClose.setValue(new Time(37800000));
		}

		if (tBOpen.getValue().compareTo(tBClose.getValue()) > 0) {
			lbErroTime.setValue("opening hours and closing hours are not feasible");
			return;
		}

		lbErroTime.setValue("");
	}

	@Listen("onChange = #txtbClassName")
	public void validateName() {
		if (clsRc.getNamecl() != null
				&& txtbClassName.getValue().toUpperCase().equals(clsRc.getNamecl().toUpperCase())) {
			lbErroName.setValue("");
			return;
		}
		for (ClassesRecord clsRc : clsPerJDBC.findAll()) {
			if (clsRc.getNamecl().toUpperCase().equals(txtbClassName.getValue().toUpperCase())) {
				lbErroName.setValue("This name is exist");
				return;
			}
		}

		lbErroName.setValue("");

	}
}
