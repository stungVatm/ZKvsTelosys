package com.sopen.controller;

import java.text.DecimalFormat;

import org.demo.data.record.ScoresRecord;
import org.demo.data.record.StudentsRecord;
import org.demo.data.record.SubjectsRecord;
import org.demo.persistence.impl.jdbc.ScoresPersistenceJdbc;
import org.demo.persistence.impl.jdbc.SubjectsPersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

public class StudentsScoreController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentsRecord getStRec() {
		return stRec;
	}

	public void setStRec(StudentsRecord stRec) {
		this.stRec = stRec;
		btnCompare.setDisabled(false);
		if (stRec != null) {
			LoadData();
		}
	}

	// Para
	private StudentsRecord stRec;
	private StudentsListController StListController;

	public StudentsListController getStListController() {
		return StListController;
	}

	public void setStListController(StudentsListController stListController) {
		StListController = stListController;
	}

	private ScoresPersistenceJdbc scPerJDPC = new ScoresPersistenceJdbc();
	private SubjectsPersistenceJdbc sbPerJDPC = new SubjectsPersistenceJdbc();

	// Wire
	@Wire
	Window wdScoreStudents;
	@Wire
	Label testRc, erroSelectSub;
	@Wire
	Listbox lstBox;
	@Wire
	Label avgScore;
	@Wire
	Label lblRank;
	@Wire
	Button btnCompare;
	@Wire
	Listbox lstBSub;
	@Wire
	Doublebox txtBScore;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		wdScoreStudents.setAttribute("Controller", this);
		lstBox.setItemRenderer(new ScoresListItemRenderer());
	}

	public void LoadData() {
		ListModelList<ScoresRecord> model = new ListModelList<ScoresRecord>(scPerJDPC.findByStudentID(stRec.getId()));
		lstBox.setModel(model);
		testRc.setValue(stRec.getNamest());
		// convert Avg
		DecimalFormat df = new DecimalFormat("#.00");
		avgScore.setValue(df.format(avgScore(model)));
		lblRank.setValue(getRank(avgScore(model)));
		txtBScore.setValue(null);
		//
		lstBSub.getChildren().clear();
		for (SubjectsRecord subRc : sbPerJDPC.findAll()) {
			String nameSub = subRc.getNamesu();
			Listitem lsitem = new Listitem(nameSub);
			ScoresRecord newscoRc = new ScoresRecord();
			newscoRc.setStudentid(stRec.getId());
			newscoRc.setSubjectid(subRc.getId());
			lsitem.setValue(newscoRc);
			for (ScoresRecord scoRc : model) {
				if (scoRc.getSubjectid() == subRc.getId()) {
					lsitem.setLabel(nameSub + "-SCORE");
					lsitem.setValue(scoRc);
					break;
				}
			}

			lsitem.setParent(lstBSub);
		}

	}

	// return Average
	public double avgScore(ListModelList<ScoresRecord> model) {
		double totalScore = 0.0;
		int totalFactor = 0;
		for (ScoresRecord t : model) {
			double Score = t.getScore();
			int Factor = sbPerJDPC.findById(t.getSubjectid()).getFactor();
			totalScore += Score * Factor;
			totalFactor += Factor;
		}
		return totalScore / totalFactor;
	}

	// return Rank
	public String getRank(double avgScore) {
		if (avgScore > 8.5) {
			return "top";
		} else if (avgScore > 6.5) {
			return "Medium";
		} else {
			return "Buttom";
		}
	}

	@Listen("onClick = #btnCompare")
	public void getRcCompe() {
		btnCompare.setDisabled(true);
		StListController.st1 = StListController.st2;
		StListController.st2 = stRec;
		StListController.renderCompare();
	}

	class ScoresListItemRenderer implements ListitemRenderer<ScoresRecord> {

		public void render(Listitem item, ScoresRecord data, int index) throws Exception {
			Listcell cell = new Listcell((index + 1) + "");
			cell.setParent(item);
			cell = new Listcell(sbPerJDPC.findById(data.getSubjectid()).getNamesu());
			cell.setParent(item);
			cell = new Listcell(sbPerJDPC.findById(data.getSubjectid()).getFactor() + "");
			cell.setParent(item);
			cell = new Listcell(data.getScore() + "");
			cell.setParent(item);

		}

	}

	@Listen("onSelect = #lstBSub")
	public void selectSub() {
		txtBScore.setValue(((ScoresRecord) lstBSub.getSelectedItem().getValue()).getScore());
	}

	@Listen("onClick = #btnSubmit")
	public void submitScore() {
		try {
			ScoresRecord scoRcSubmit = (ScoresRecord) lstBSub.getSelectedItem().getValue();
			if (txtBScore.getValue() == null || txtBScore.getValue() < 0 || txtBScore.getValue() > 10) {
				Clients.evalJavaScript("$( \".z-window\" ).effect( \"shake\" );");
				alert("incorrect score");
				return;
			}
			scoRcSubmit.setScore(txtBScore.getValue());
			scPerJDPC.save(scoRcSubmit);
			LoadData();
		} catch (Exception e) {
			Clients.evalJavaScript("$( \".z-window\" ).effect( \"shake\" );");
			alert("You need select a subject");
			return;

		}

	}
}
