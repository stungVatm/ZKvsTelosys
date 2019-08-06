package com.sopen.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.demo.data.record.ClassesRecord;
import org.demo.data.record.StudentsRecord;
import org.demo.persistence.impl.jdbc.ClassesPersistenceJdbc;
import org.demo.persistence.impl.jdbc.StudentsPersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.google.protobuf.DescriptorProtos.SourceCodeInfo.Location;

public class StudentsDetailController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

// Declare 
	private StudentsRecord stRec;
	private StudentsListController StListController;
	private StudentsPersistenceJdbc StuJDBC = new StudentsPersistenceJdbc();
	List<StudentsRecord> lstStudents = StuJDBC.findAll();
	private ClassesPersistenceJdbc clsJDBC = new ClassesPersistenceJdbc();

	List<ClassesRecord> lstClasses = clsJDBC.findAll("", true, false);
	java.util.Date dateNow1 = new java.util.Date();
	Date dateNow = new Date(dateNow1.getTime());

//	@Wire
//	Intbox Age;
	@Wire
	Listbox lstboxClass;
	@Wire
	Label errorName, errorCode, errorEmail, errorAge, Age;
	@Wire
	Datebox dbBBirthday;
	@Wire
	Window wdStudentsDetail;
	@Wire
	Textbox txtName, txtCodest, txtAddress, txtEmail, txtNote;
	@Wire
	Button btnRandom;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		wdStudentsDetail.setAttribute("abc", this);

		// load list Class
		for (ClassesRecord clsRc : lstClasses) {
			Listitem newListitem = new Listitem();
			newListitem.setLabel(clsRc.getNamecl());
			newListitem.setValue(clsRc.getId());
			newListitem.setParent(lstboxClass);
		}

		lstboxClass.setSelectedIndex(0);

	}

	public StudentsRecord getStRec() {
		return stRec;
	}

	public void setStRec(StudentsRecord stRec) {
		this.stRec = stRec;
		if (stRec != null) {
			if (stRec.getId() == null) {
				wdStudentsDetail.setTitle("New Student");
			} else {
				wdStudentsDetail.setTitle("Detail Student");
			}
			errorName.setValue("");
			errorCode.setValue("");
			errorEmail.setValue("");
			txtName.setValue(stRec.getNamest());
			txtCodest.setValue(stRec.getCodest());
			if (stRec.getClassid() != null) {
				for (Listitem item : lstboxClass.getItems()) {
					if (item.getValue() == stRec.getClassid()) {
						item.setSelected(true);
						break;
					}
				}
			}

			//
			Age.setValue(stRec.getAge() + "");
			txtAddress.setValue(stRec.getAddress());
			txtEmail.setValue(stRec.getEmail());
			txtNote.setValue(stRec.getNote());
			if (stRec.getBirthday() == null) {
				dbBBirthday.setValue(dateNow);
			} else {
				dbBBirthday.setValue(stRec.getBirthday());
			}
			Date dateBorn = new Date(dbBBirthday.getValue().getTime());
			Age.setValue((dateNow.getYear() - dateBorn.getYear()) + "");
		}
	}

	@Listen("onClick = #btnSave")
	public void save() {
		if (!validateEmpty()) {
			Clients.evalJavaScript("$( \".z-window\" ).effect( \"shake\" );");
			return;
		}

		if (!validate()) {
			alert("Sr, you neet check the condition plz!!");
			Clients.evalJavaScript("$( \".z-window\" ).effect( \"shake\" );");
			return;
		}
		stRec.setNamest(txtName.getValue());
		stRec.setCodest(txtCodest.getValue());
		stRec.setClassid((Integer) lstboxClass.getSelectedItem().getValue());
		stRec.setAge(Integer.parseInt(Age.getValue()));
		stRec.setAddress(txtAddress.getValue());
		stRec.setEmail(txtEmail.getValue());
		stRec.setNote(txtNote.getValue());
		stRec.setBirthday(new Date(dbBBirthday.getValue().getTime()));
		try {
			StuJDBC.save(stRec);
		} catch (Exception e) {
			alert("Vui long xem lai");
		}
		wdStudentsDetail.onClose();
		StListController.refresh();

	}

	private boolean validate() {
		if (!errorName.getValue().isEmpty()) {
			return false;
		}

		if (!errorCode.getValue().isEmpty()) {
			return false;
		}

		if (!errorEmail.getValue().isEmpty()) {
			return false;
		}

		if (!errorAge.getValue().isEmpty()) {
			return false;
		}
		return true;
	}

	private boolean validateEmpty() {
		boolean result = true;
		if (txtName.getValue().isEmpty()) {
			errorName.setValue("This cant be empty!!");
			result = false;
		}

		if (txtCodest.getValue().isEmpty()) {
			errorCode.setValue("This cant be empty!!");
			result = false;
		}

		if (txtEmail.getValue().isEmpty()) {
			errorEmail.setValue("This cant be empty!!");
			result = false;
		}

		return result;
	}

	public StudentsListController getStListController() {
		return StListController;
	}

	public void setStListController(StudentsListController stListController) {
		this.StListController = stListController;
	}

	// vaindate
	@Listen("onChange = #txtName")
	public void checkName() {
		if (txtName.getValue().length() > 20 || txtName.getValue().length() < 6) {
			errorName.setValue("The length String isnt fine !!");
			return;
		}
		errorName.setValue("");
	}

	@Listen("onChange = #txtCodest")
	public void checkCode() {
		if (txtCodest.getValue().length() < 2 || !txtCodest.getValue().substring(0, 2).equals("st")) {
			errorCode.setValue("The beginning isn't 'st'");
			return;
		}

		try {
			Integer.parseInt(txtCodest.getValue().substring(2));
		} catch (Exception e) {
			errorCode.setValue("after 'st' is not a number");
			return;
		}

		if (txtCodest.getValue().equals(stRec.getCodest())) {
			errorCode.setValue("");
			return;
		}

		for (StudentsRecord stRc : lstStudents) {

			if (stRc.getCodest().equals(txtCodest.getValue())) {
				errorCode.setValue("This code is exist");
				return;
			}
		}
		errorCode.setValue("");
	}

	@Listen("onChange = #txtEmail")
	public void checkGmail() {

		if (txtEmail.getValue().equals(stRec.getEmail())) {
			errorEmail.setValue("");
			return;
		}

		for (StudentsRecord stRc : lstStudents) {

			if (stRc.getEmail().equals(txtEmail.getValue())) {
				errorEmail.setValue("This Email is exist");
				return;
			}
		}

		if (!isValid(txtEmail.getValue())) {
			errorEmail.setValue("This is not a gmail!!");
			return;
		}

		for (StudentsRecord stRc : lstStudents) {
			if (stRc.getEmail().equals(txtEmail.getValue())) {
				errorEmail.setValue("This Email is exist");
				return;
			}
		}

		errorEmail.setValue("");

	}

	public static boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	@Listen("onChange = #dbBBirthday")
	public void checkAge() {
//		Date dateBorn = new Date(dbBBirthday.getValue().getTime());
//		Age.setValue((dateNow.getYear() - dateBorn.getYear()) + "");
//		if ((dateNow.getYear() - dateBorn.getYear()) < 15) {
//			errorAge.setValue("You are so young for Class, you need older than 15 year old !!");
//			return;
//		}
//		errorAge.setValue("");

	}

	@Listen("onClick = #btnRefresh")
	public void refresh() {
		setStRec(stRec);
	}

	@Listen("onClick = #btnRandom")
	public void getRandomCode() {
		String codeRan;
		do {
			int random = (int) (Math.random() * 10000 + 1);
			codeRan = "st" + random;
		} while (existCode(codeRan));
		txtCodest.setValue(codeRan);
	}

	private boolean existCode(String codeRan) {
		for (StudentsRecord stRc : lstStudents) {
			if (stRc.getCodest().equals(codeRan)) {
				errorEmail.setValue("This Email is exist");
				return true;
			}
		}
		return false;
	}

}
