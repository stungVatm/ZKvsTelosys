package com.sopen.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.demo.data.record.ClassesRecord;
import org.demo.data.record.StudentsRecord;
import org.demo.persistence.impl.jdbc.ClassesPersistenceJdbc;
import org.demo.persistence.impl.jdbc.StudentsPersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.sopen.controller.compare.StudentsCompare;

public class StudentsListController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	Listbox lstBox;
	@Wire
	Hbox hboxSearch;
	@Wire
	Hlayout hlayoutNaviPage;
	@Wire
	Intbox numberPage;
	@Wire
	Intbox totalpage;
	@Wire
	Textbox txbSearch;
	@Wire
	Listbox lstClassName;
	@Wire
	Listbox numberOfItem;

	// Navigation buttom Page
	@Wire
	Button firstPage;
	@Wire
	Button previousPage;
	@Wire
	Button nextPage;
	@Wire
	Button lastPage;

	@Wire
	Div divStudents;
	@Wire
	Button btnNew;

	// Connection
	StudentsPersistenceJdbc stuPer = new StudentsPersistenceJdbc();
	List<StudentsRecord> lstStudents = stuPer.findAll();

	ClassesPersistenceJdbc clsJDBC = new ClassesPersistenceJdbc();
	List<ClassesRecord> lstClasses = clsJDBC.findAll();

	Window newCompo, newCompo2, newCompo3;
	// button search
	@Wire
	Button btnSearch;
	@Wire
	Button btnCleanSearch;
	// Wire infomation Search
	@Wire
	Textbox txtSearchName, txtSearchCodeST, txtSearchAddress, txtSearchEmail, txtSearchNote, txtIdst1, txtNamest1,
			txtIdst2, txtNamest2;
	@Wire
	Intbox txtSearchClassID, txtSearchAge;
	// Wire order infor
	@Wire
	Listheader lstSortName, lstSortCodest, lstSortClassId, lstSortAge, lstSortAddress, lstSortEmail, lstSortNote;

	// Khai bao bien dau vao
	private String strSearchName, strSearchCodeST, strSearchAddress, strSearchEmail, strSearchNote;
	private int intSearchClassID, intSearchAge;

	private int orderBy = 20;

	private int currentPage = 1;

	private int intNumberOfItems = 4;

	public StudentsRecord st1 = lstStudents.get(0);
	public StudentsRecord st2 = lstStudents.get(1);

	// Load data
	@Listen("onChange = #numberPage;onOK = #hlayoutNaviPage")
	public void loadDataPlus() {

		// get search
		strSearchName = txtSearchName.getValue() != null ? txtSearchName.getValue() : null;
		strSearchCodeST = txtSearchCodeST.getValue() != null ? txtSearchCodeST.getValue() : null;
		for (ClassesRecord cls : lstClasses) {
			if (lstClassName.getSelectedItem().getLabel().equals("ALL")) {
				intSearchClassID = -1;
			}
			if (cls.getNamecl().equals(lstClassName.getSelectedItem().getLabel())) {
				intSearchClassID = cls.getId();
				break;
			}
		}
		intSearchAge = txtSearchAge.getValue() != null ? txtSearchAge.getValue() : -1;
		strSearchAddress = txtSearchAddress.getValue() != null ? txtSearchAddress.getValue() : null;
		strSearchEmail = txtSearchEmail.getValue() != null ? txtSearchEmail.getValue() : null;
		strSearchNote = txtSearchNote.getValue() != null ? txtSearchNote.getValue() : null;

		// set value for numberofItem
		intNumberOfItems = Integer.parseInt(numberOfItem.getSelectedItem().getLabel());

		// count all
		String MYSQLcountALl = "select count(*) from students where 1=1" + queryMYSQLCondition();

		int resultCount = (int) stuPer.countAll(MYSQLcountALl);

		// set totapage

		int totalPage;
		if (resultCount % intNumberOfItems == 0) {
			totalPage = resultCount / intNumberOfItems;
		} else {
			totalPage = (resultCount + intNumberOfItems - resultCount % intNumberOfItems) / intNumberOfItems;
		}
		totalpage.setValue(totalPage);

		// set value of currentPage
		firstPage.setDisabled(false);
		previousPage.setDisabled(false);
		nextPage.setDisabled(false);
		lastPage.setDisabled(false);

		int currentPage;

		if (numberPage.getValue() == null || numberPage.getValue() <= 1) {
			currentPage = 1;
			firstPage.setDisabled(true);
			previousPage.setDisabled(true);
		} else if (numberPage.getValue() >= totalPage) {
			currentPage = totalPage;
			nextPage.setDisabled(true);
			lastPage.setDisabled(true);
		} else {
			currentPage = numberPage.getValue();
		}

		numberPage.setValue(currentPage);
		lstBox.setHeight(intNumberOfItems * 90 + "px");
		// getData
		String MYSQLcondition = "select * from students where 1 = 1" + queryMYSQLCondition() + strSortBy() + " limit "
				+ (currentPage - 1) * intNumberOfItems + "," + intNumberOfItems + "";
		ListModelList<StudentsRecord> model = new ListModelList<StudentsRecord>(stuPer.findAll(MYSQLcondition));
		lstBox.setModel(model);

		// render compare
		renderCompare();
	}

	public void renderCompare() {
		txtIdst1.setValue(st1.getCodest());
		txtNamest1.setValue(st1.getNamest());
		txtIdst2.setValue(st2.getCodest());
		txtNamest2.setValue(st2.getNamest());
	}

	// Create MYSQL query

	private String queryMYSQLCondition() {
		String MYSQLcondition = "";
		if (!strSearchName.isEmpty()) {

			MYSQLcondition += " and nameSt LIKE '%" + strSearchName + "%'";
		}

		if (!strSearchCodeST.isEmpty()) {
			MYSQLcondition += " and codeSt LIKE '%" + strSearchCodeST + "%'";
		}

		if (intSearchClassID != -1) {
			MYSQLcondition += " and classId = " + intSearchClassID + " ";
		}

		if (intSearchAge != -1) {
			MYSQLcondition += " and age = " + intSearchAge + " ";
		}

		if (!strSearchAddress.isEmpty()) {
			MYSQLcondition += " and address LIKE '%" + strSearchAddress + "%'";
		}

		if (!strSearchEmail.isEmpty()) {
			MYSQLcondition += " and email LIKE '%" + strSearchEmail + "%'";
		}

		if (!strSearchNote.isEmpty()) {
			MYSQLcondition += " and note LIKE '%" + strSearchNote + "%'";
		}

		return MYSQLcondition;
	}

	private String strSortBy() {

		switch (orderBy) {
		case 10:
			return " order by nameSt ASC";
		case 11:
			return " order by nameSt DESC";
		case 20:
			return " order by codeSt ASC";
		case 21:
			return " order by codeSt DESC";
		case 30:
			return " order by classId ASC";
		case 31:
			return " order by classId DESC";
		case 40:
			return " order by age ASC";
		case 41:
			return " order by age DESC";
		case 50:
			return " order by address ASC";
		case 51:
			return " order by address DESC";
		case 60:
			return " order by email ASC";
		case 61:
			return " order by email DESC";
		case 70:
			return " order by note ASC";
		case 71:
			return " order by note DESC";
		default:
			return " order by nameSt ASC";
		}

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.init();
		for (ClassesRecord clsRc : lstClasses) {
			Listitem newListitem = new Listitem();
			newListitem.setLabel(clsRc.getNamecl());
			newListitem.setValue(clsRc.getId());
			newListitem.setParent(lstClassName);
		}
	}

	private void init() {

		newCompo = (Window) Executions.createComponents("Zul/Students/Students_detail.zul", null, null);
		((StudentsDetailController) newCompo.getAttribute("abc")).setStListController(this);
		newCompo.onClose();

		newCompo2 = (Window) Executions.createComponents("Zul/Students/Students_Score.zul", null, null);
		((StudentsScoreController) newCompo2.getAttribute("Controller")).setStListController(this);
		newCompo2.onClose();

		newCompo3 = (Window) Executions.createComponents("Zul/Students/Students_compare.zul", null, null);
//		((StudentsScoreController) newCompo2.getAttribute("Controller")).setStListController(this);
		newCompo3.onClose();

		lstBox.setItemRenderer(new StudentsListItemRenderer());
		loadDataPlus();

	}

	@Listen("onChange = #txbSearch ; onSelect=#numberOfItem")
	public void loadDataPage1() {
		numberPage.setValue(1);
		loadDataPlus();
	}

	@Listen("onClick= #btnSearch;onOK = #hboxSearch")
	public void search() {
		numberPage.setValue(1);
		loadDataPlus();
	}

	@Listen("onClick= #btnCleanSearch")
	public void cleanSearch() {
		txtSearchName.setValue(null);
		txtSearchCodeST.setValue(null);
		lstClassName.setSelectedIndex(0);
		txtSearchAge.setValue(null);
		txtSearchAddress.setValue(null);
		txtSearchEmail.setValue(null);
		txtSearchNote.setValue(null);
	}

	@Listen("onClick= #btnComper")
	public void compare() {
		((StudentsCompare) newCompo3.getAttribute("controller")).setSt1(st1);
		((StudentsCompare) newCompo3.getAttribute("controller")).setSt2(st2);
		newCompo3.setParent(divStudents);
		newCompo3.doModal();

	}

	// Button Sort By 1-7
	@Listen("onClick = #lstSortName")
	public void orderByName() {
		if (orderBy == 10) {
			orderBy = 11;
		} else if (orderBy == 11) {
			orderBy = 10;
		} else {
			orderBy = 10;
		}
		loadDataPlus();
	}

	@Listen("onClick = #lstSortCodest")
	public void orderByCodeSt() {
		if (orderBy == 20) {
			orderBy = 21;
		} else if (orderBy == 21) {
			orderBy = 20;
		} else {
			orderBy = 20;
		}
		loadDataPlus();
	}

	@Listen("onClick = #lstSortClassId")
	public void orderByClassId() {
		if (orderBy == 30) {
			orderBy = 31;
		} else if (orderBy == 31) {
			orderBy = 30;
		} else {
			orderBy = 30;
		}
		loadDataPlus();
	}

	@Listen("onClick = #lstSortAge")
	public void orderByAge() {
		if (orderBy == 40) {
			orderBy = 41;
		} else if (orderBy == 41) {
			orderBy = 40;
		} else {
			orderBy = 40;
		}
		loadDataPlus();
	}

	@Listen("onClick = #lstSortAddress")
	public void orderByAddress() {
		if (orderBy == 50) {
			orderBy = 51;
		} else if (orderBy == 51) {
			orderBy = 50;
		} else {
			orderBy = 50;
		}
		loadDataPlus();
	}

	@Listen("onClick = #lstSortEmail")
	public void orderByEmail() {
		if (orderBy == 60) {
			orderBy = 61;
		} else if (orderBy == 61) {
			orderBy = 60;
		} else {
			orderBy = 60;
		}
		loadDataPlus();
	}

	@Listen("onClick = #lstSortNote")
	public void orderByNote() {
		if (orderBy == 70) {
			orderBy = 71;
		} else if (orderBy == 71) {
			orderBy = 70;
		} else {
			orderBy = 70;
		}
		loadDataPlus();
	}

	// paging phan trang
	@Listen("onClick = #firstPage")
	public void firstPage() {
		numberPage.setValue(1);
		loadDataPlus();
	}

	@Listen("onClick = #previousPage")
	public void previousPage() {
		numberPage.setValue(numberPage.getValue() - 1);
		loadDataPlus();
	}

	@Listen("onClick = #nextPage")
	public void nextPage() {
		numberPage.setValue(numberPage.getValue() + 1);
		loadDataPlus();
	}

	@Listen("onClick = #lastPage")
	public void lastPage() {
		numberPage.setValue(totalpage.getValue());
		loadDataPlus();
	}

	// tao moi
	@Listen("onClick = #btnNew")
	public void newStudent() {
		StudentsRecord stRecos = new StudentsRecord();
		((StudentsDetailController) newCompo.getAttribute("abc")).setStRec(stRecos);
		newCompo.setParent(divStudents);
		newCompo.doModal();
	}

	class StudentsListItemRenderer implements ListitemRenderer<StudentsRecord> {
		ClassesPersistenceJdbc clps = new ClassesPersistenceJdbc();

		public void render(Listitem item, final StudentsRecord data, int index) throws Exception {
			Listcell cell = new Listcell(data.getNamest());
			cell.setParent(item);

			cell = new Listcell(data.getCodest());
			cell.setParent(item);

			cell = new Listcell(clps.findById(data.getClassid()).getNamecl());
			cell.setParent(item);

			cell = new Listcell(data.getAge() + "");
			cell.setParent(item);

			cell = new Listcell(data.getAddress());
			cell.setStyle("white-space: nowrap; overflow: hidden; text-overflow: ellipsis");
			cell.setParent(item);

			cell = new Listcell(data.getEmail());
			cell.setParent(item);

			cell = new Listcell(data.getNote());
			cell.setStyle("white-space: nowrap; overflow: hidden; text-overflow: ellipsis");
			cell.setParent(item);

			cell = new Listcell(new SimpleDateFormat("dd/MM/yyyy").format(data.getBirthday()));
			cell.setParent(item);

			cell = new Listcell();
			cell.setParent(item);

			Button btEdit = new Button();
			btEdit.setTooltiptext("Edit student's info");
			btEdit.setIconSclass("z-icon-edit");
			btEdit.setParent(cell);

			// Edit
			btEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event arg0) throws Exception {
					((StudentsDetailController) newCompo.getAttribute("abc")).setStRec(data);
					newCompo.setParent(divStudents);
					newCompo.doModal();
				}
			});

			// delete
			Button btDelete = new Button();
			btDelete.setTooltiptext("Delete a student");
			btDelete.setIconSclass("z-icon-trash-o");
			btDelete.setParent(cell);

			final int idData = data.getId();
			btDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event arg0) throws Exception {
					Messagebox.show("Delete?", "Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt) throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										stuPer.deleteById(idData);
										loadDataPlus();
									}
								}
							});
				}
			});

			item.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {

				public void onEvent(Event arg0) throws Exception {
					((StudentsScoreController) newCompo2.getAttribute("Controller")).setStRec(data);
					newCompo2.setParent(divStudents);
					newCompo2.doModal();

				}
			});
		}

	}

	public void refresh() {
		loadDataPlus();

	}

}
