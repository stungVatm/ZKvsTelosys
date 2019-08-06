package com.sopen.controller;

import java.util.LinkedList;
import java.util.List;

import org.demo.data.record.ClassestreeRecord;
import org.demo.persistence.impl.jdbc.ClassestreePersistenceJdbc;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.TreeDataListener;

import com.sopen.controller.ClassTreeListController.ClassesTreeListItemRender;

import bsh.Console;

public class ClassTreeModelController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Wire
	@Wire
	Div showLists;
	@Wire
	Tree showTree;
	@Wire
	Button btnNewRecord;
	@Wire
	Window wdClassesTreeModel_Detail;
	@Wire
	Listbox lstBox;
	// Declare
	ClassestreePersistenceJdbc clsTrPerJDBC = new ClassestreePersistenceJdbc();
	List<ClassestreeRecord> list = clsTrPerJDBC.findAll();
	int lever = 0;
	private Window detail;
	ClassestreeRecord parentBoot = new ClassestreeRecord();
	int renderID = 0;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		lstBox.setItemRenderer(new ClassesTreeListItemRender());
		parentBoot.setId(0);
		init();
	}

	private void init() {
		showLists.getChildren().clear();
		loadData(lever, renderID, 0);
		loadDataPlus();

	}

	private void loadDataPlus() {

		ListModelList<ClassestreeRecord> model = new ListModelList<ClassestreeRecord>(
				clsTrPerJDBC.findAllByIdParent(parentBoot.getId()));
		lstBox.setModel(model);

	}

	class ClassesTreeListItemRender implements ListitemRenderer<ClassestreeRecord> {

		public void render(Listitem item, final ClassestreeRecord data, int index) throws Exception {

			Listcell cell = new Listcell();
			Button btnBack = new Button("Back");
			if (data.getIdperent() == null) {
				btnBack.setDisabled(true);
			}
			btnBack.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event arg0) throws Exception {
					if (clsTrPerJDBC.findById(data.getIdperent()).getIdperent() == null) {
						parentBoot = new ClassestreeRecord();
						parentBoot.setId(0);
					} else {
						parentBoot = clsTrPerJDBC.findById(clsTrPerJDBC.findById(data.getIdperent()).getIdperent());
					}
					init();
				}
			});
			btnBack.setParent(cell);
			cell.setParent(item);

			cell = new Listcell(data.getId() + "");
			cell.setParent(item);

			cell = new Listcell(data.getNamecl());
			cell.setParent(item);

			cell = new Listcell(data.getIdperent() + "");
			cell.setParent(item);

			// button next
			cell = new Listcell();
			Button btnNext = new Button("Next");
			for (ClassestreeRecord variableResolver : clsTrPerJDBC.findAllChildren()) {
				if (data.getId() == variableResolver.getId()) {
					btnNext.setDisabled(true);
					break;
				}
			}
			btnNext.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event arg0) throws Exception {
					parentBoot = data;
					init();
				}
			});
			btnNext.setParent(cell);
			cell.setParent(item);

		}

	}

	private void loadData(int lever, int renderID, int child2) {
		int child = child2;
		for (ClassestreeRecord record : searchChildren(renderID)) {
			String space = "";
			for (int i = 0; i < lever; i++) {
				space += "...............................";
			}
			Label lb = new Label(space + record.getNamecl() + "(ID:" + record.getId() + ")");
			lb.setParent(showLists);
			lb.setStyle("color:black");
			for (ClassestreeRecord clsTrRc : parentBoot.listParent()) {
				if (record.getId() == clsTrRc.getId()) {
					lb.setStyle("color:red");
					break;
				}
			}

			if (record.getId() == parentBoot.getId()) {
				lb.setStyle("color:red");
			}

			for (ClassestreeRecord clsTrRc : searchChildren(parentBoot.getId())) {
				if (record.getId() == clsTrRc.getId()) {
					lb.setStyle("color:blue");
					child = 1;
					break;
				}
			}

			if (!lb.getStyle().equals("color:blue")) {
				if (child == 1) {
					lb.setStyle("color:green");
				}
			}

			// xuong dong
			org.zkoss.zul.Separator enter = new org.zkoss.zul.Separator();
			enter.setParent(showLists);
			loadData(lever + 1, record.getId(), child);
		}

	}

	private List<ClassestreeRecord> searchChildren(int idParent) {

		List<ClassestreeRecord> listChildren = new LinkedList<ClassestreeRecord>();
		for (int i = 0; i < list.size(); i++) {
			ClassestreeRecord record = list.get(i);
			if (record.getIdperent() == null) {
				record.setIdperent(0);
			}
			if (record.getIdperent() == idParent) {
				listChildren.add(record);
			}
		}
		return listChildren;
	}

	@Listen("onClick = #btnNewRecord")
	public void createNew() {
		detail = (Window) Executions.createComponents("/Zul/ClassesTree/ClassesTreeModel_Detail.zul", null, null);
		((ClassesTreeModelDetailController) detail.getAttribute("Controller")).setRecord(new ClassestreeRecord());
		((ClassesTreeModelDetailController) detail.getAttribute("Controller")).setClsTrModelCtr(this);
		detail.doModal();
		detail.setParent(wdClassesTreeModel_Detail);
	}

	public void refresh() {
		showLists.getChildren().clear();
		lever = 0;
		parentBoot = new ClassestreeRecord();
		parentBoot.setId(0);
		list = clsTrPerJDBC.findAll();
		loadData(lever, 0, 0);
	}
}
