package com.sopen.controller;

import java.util.List;

import org.demo.data.record.ClassestreeRecord;
import org.demo.persistence.impl.jdbc.ClassestreePersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ClassTreeListController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Wire
	@Wire
	Listbox lstBox;
	
 
	//Declare
	int keyParent = 0;
	ClassestreePersistenceJdbc clsTrPerJDBC = new ClassestreePersistenceJdbc();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		lstBox.setItemRenderer(new ClassesTreeListItemRender());
		loadData();
	}


	private void loadData() {
		ListModelList<ClassestreeRecord> model = new ListModelList<ClassestreeRecord>(clsTrPerJDBC.findAllByIdParent(keyParent));
		lstBox.setModel(model);
		
	}
	
	class ClassesTreeListItemRender implements ListitemRenderer<ClassestreeRecord> {

		public void render(Listitem item, final ClassestreeRecord data, int index) throws Exception {
		
			Listcell cell = new Listcell();
			Button btnBack = new Button("Back");
			btnBack.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event arg0) throws Exception {
					if (clsTrPerJDBC.findById(data.getIdperent()).getIdperent()==null) {
						keyParent=0;
					} else {
						keyParent = clsTrPerJDBC.findById(data.getIdperent()).getIdperent() ;
					}					
					loadData();
				}
			});
			btnBack.setParent(cell);
			cell.setParent(item);
			
			cell = new Listcell(data.getId()+"");
			cell.setParent(item);
			
			cell = new Listcell(data.getNamecl());
			cell.setParent(item);
			
			cell = new Listcell(data.getIdperent()+"");
			cell.setParent(item);
			
			
			//button next
			cell = new Listcell();
			Button btnNext = new Button("Next");
			btnNext.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				public void onEvent(Event arg0) throws Exception {
					keyParent = data.getId();
					loadData();
				}
			});
			btnNext.setParent(cell);
			cell.setParent(item);
			
		}
		
	}
}
