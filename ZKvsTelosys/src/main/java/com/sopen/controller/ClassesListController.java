package com.sopen.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.demo.data.record.ClassesRecord;
import org.demo.data.record.StudentsRecord;
import org.demo.persistence.StudentsPersistence;
import org.demo.persistence.impl.jdbc.ClassesPersistenceJdbc;
import org.demo.persistence.impl.jdbc.StudentsPersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ClassesListController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Declare Wire

	@Wire
	Listbox lstBoxRender;

	@Wire
	Button btnNew, btSearch;

	@Wire
	Div divClasses;

	@Wire
	Textbox tbSearch;
	@Wire
	Checkbox cBOpen, cBClose;
	@Wire
	Hlayout hloSearch;
	// Declare variable

	private Window detail;
	ClassesPersistenceJdbc clsPerJDBC = new ClassesPersistenceJdbc();
	StudentsPersistenceJdbc stdPerJDBC = new StudentsPersistenceJdbc();
	// Declare Method

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		init();
	}

	private void init() {
		detail = (Window) Executions.createComponents("Zul/Classes/Classes_Detail.zul", null, null);
		((ClassDetailController) detail.getAttribute("controller")).setClsListController(this);
		detail.doModal();
		detail.onClose();
		lstBoxRender.setItemRenderer(new ClassesListItemRenderer());
		loadData();
	}

	@Listen("onClick = #cBOpen,#cBClose,#btSearch; onOK = #divClasses")
	public void loadData() {
		String search = tbSearch.getValue();
		Boolean blOpen = cBOpen.isChecked();
		Boolean blClose = cBClose.isChecked();
		ListModelList<ClassesRecord> model = new ListModelList<ClassesRecord>(
				clsPerJDBC.findAll(search, blOpen, blClose));
		lstBoxRender.setModel(model);
	}

	class ClassesListItemRenderer implements ListitemRenderer<ClassesRecord> {

		public void render(Listitem item, final ClassesRecord data, int index) throws Exception {

			if (data.getStt() == 0) {
				item.setDisabled(true);
			}
			Listcell cell = new Listcell(data.getId() + "");
			cell.setParent(item);

			cell = new Listcell(data.getNamecl());
			cell.setParent(item);

			cell = new Listcell(new SimpleDateFormat("HH:mm").format(data.getTimeopencl()));
			cell.setParent(item);

			cell = new Listcell(new SimpleDateFormat("HH:mm").format(data.getTimeclosecl()));
			cell.setParent(item);

			cell = new Listcell(data.getNote());
			cell.setParent(item);

			cell = new Listcell();
			cell.setParent(item);

			Button btEdit = new Button("Edit");
			btEdit.setParent(cell);
			btEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event arg0) throws Exception {
					((ClassDetailController) detail.getAttribute("controller")).setClsRc(data);
					detail.setParent(divClasses);

				}
			});

			if (data.getStt() == 1) {
				Button btDelete = new Button("Delete");
				btDelete.setParent(cell);
				final int idData = data.getId();
				btDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					public void onEvent(Event arg0) throws Exception {
						Messagebox.show("Delete?", "Confirm Dialog", Messagebox.OK | Messagebox.CANCEL,
								Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener<Event>() {
									public void onEvent(Event evt) throws InterruptedException {
										if (evt.getName().equals("onOK")) {
											for (StudentsRecord stdRc : stdPerJDBC.findAll()) {
												if (stdRc.getClassid() == data.getId()) {
													data.setStt(0);
													clsPerJDBC.save(data);
													alert("There are students in this class so this cant been delete!!, so this class will be close ");
													Clients.evalJavaScript("$( \".z-window\" ).effect( \"shake\" );");
													loadData();
													return;
												}
											}
											alert("Successes!!");

											clsPerJDBC.deleteById(idData);

											loadData();
										}
									}
								});
					}
				});
			} else if (data.getStt() == 0) {

				Button btOpenClass = new Button("Open");
				btOpenClass.setParent(cell);
				btOpenClass.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						data.setStt(1);
						clsPerJDBC.save(data);
						loadData();
					}
				});

			}

			item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event arg0) throws Exception {
					alert("Luoi viet qua vao danh sach tim di ban");
				}
			});
		}

	}

	@Listen("onClick = #btnNew")
	public void createNewRecord() throws WrongValueException, ParseException {
		ClassesRecord newClsRc = new ClassesRecord();
		((ClassDetailController) detail.getAttribute("controller")).setClsRc(newClsRc);
		detail.setParent(divClasses);
	}

}
