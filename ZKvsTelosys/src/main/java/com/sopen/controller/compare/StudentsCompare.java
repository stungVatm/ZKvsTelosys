package com.sopen.controller.compare;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.demo.data.record.ScoresRecord;
import org.demo.data.record.StudentsRecord;
import org.demo.data.record.SubjectsRecord;
import org.demo.persistence.impl.jdbc.ClassesPersistenceJdbc;
import org.demo.persistence.impl.jdbc.ScoresPersistenceJdbc;
import org.demo.persistence.impl.jdbc.SubjectsPersistenceJdbc;
import org.zkoss.chart.Charts;
import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

public class StudentsCompare extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Wire
	@Wire
	Window wdStudentsCompare;
	@Wire
	Div divCheckBox, divTableCompare;
	@Wire
	Label lbNameSt1, lbNameSt2, lbClassSt1, lbClassSt2, lbRankSt1, lbRankSt2, lbAvgSt1, lbAvgSt2, lbBestSubSt1,
			lbBestSubSt2;
	@Wire
	Tabpanel tpTable, tpColumn;
	@Wire
	Button btnApply;

	// Declare
	private StudentsRecord st1;
	private StudentsRecord st2;
	private ClassesPersistenceJdbc clsPerJDBC = new ClassesPersistenceJdbc();
	private ScoresPersistenceJdbc scoPerJDBC = new ScoresPersistenceJdbc();
	private SubjectsPersistenceJdbc subPerJDBC = new SubjectsPersistenceJdbc();

	public StudentsRecord getSt1() {
		return st1;
	}

	public void setSt1(StudentsRecord st1) {
		this.st1 = st1;
	}

	public StudentsRecord getSt2() {
		return st2;
	}

	public void setSt2(StudentsRecord st2) {
		this.st2 = st2;
		loadData();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		wdStudentsCompare.setAttribute("controller", this);

	}

	public void loadData() {
		lbNameSt1.setValue(st1.getNamest());
		lbNameSt2.setValue(st2.getNamest());
		lbClassSt1.setValue(clsPerJDBC.findById(st1.getClassid()).getNamecl() + "");
		lbClassSt2.setValue(clsPerJDBC.findById(st2.getClassid()).getNamecl() + "");
		lbRankSt1.setValue(st1.getRank());
		lbRankSt2.setValue(st2.getRank());

		// Covert
		DecimalFormat df = new DecimalFormat("#.00");
		lbAvgSt1.setValue(df.format(st1.getAvgScore()));
		lbAvgSt2.setValue(df.format(st2.getAvgScore()));
		String bestSub1 = "";
		for (SubjectsRecord subRc : st1.getBestSub()) {
			bestSub1 += subRc.getNamesu();
		}

		bestSub1 = bestSub1 + "-" + st1.getBestScore();
		lbBestSubSt1.setValue(bestSub1);

		String bestSub2 = "";
		for (SubjectsRecord subRc : st2.getBestSub()) {
			bestSub2 += subRc.getNamesu();
		}

		bestSub2 = bestSub2 + "-" + st2.getBestScore();
		lbBestSubSt2.setValue(bestSub2);

		//
		loadCheckBox();
	}

	private void loadCheckBox() {
		divCheckBox.getChildren().clear();
		List<ScoresRecord> listSubRcSt1 = scoPerJDBC.findByStudentID(st1.getId());
		List<ScoresRecord> listSubRcSt2 = scoPerJDBC.findByStudentID(st2.getId());
		// render checkBox
		for (ScoresRecord scoRcSt1 : listSubRcSt1) {
			for (ScoresRecord scoRcSt2 : listSubRcSt2) {
				if (scoRcSt1.getSubjectid() == scoRcSt2.getSubjectid()) {
					SubjectsRecord subjectCompare = subPerJDBC.findById(scoRcSt1.getSubjectid());
					Checkbox checkbox = new Checkbox(subjectCompare.getNamesu());
					checkbox.setValue(subjectCompare.getId());
					checkbox.setChecked(true);
					checkbox.setParent(divCheckBox);
					divCheckBox.appendChild(new Separator());
				}
			}
		}
		divTableCompare.getChildren().clear();
		tpColumn.getChildren().clear();
	}

	@Listen("onClick = #btnApply")
	public void loadTable() {
		divTableCompare.getChildren().clear();
		tpColumn.getChildren().clear();
		// create char
		Charts chart = new Charts();
		chart.setType("line");
		chart.setTitle("Compare two students");
		chart.getYAxis().setAllowDecimals(true);
		chart.getYAxis().getTitle().setText("Score");

		List<ScoresRecord> listSubRcSt1 = scoPerJDBC.findByStudentID(st1.getId());
		List<ScoresRecord> listSubRcSt2 = scoPerJDBC.findByStudentID(st2.getId());
		List<Integer> listIntCheckBox = new LinkedList<Integer>();
		for (int i = 0; i < divCheckBox.getChildren().size(); i++) {
			if (divCheckBox.getChildren().get(i).getClass().getName().equals("org.zkoss.zul.Checkbox")) {
				if (((Checkbox) divCheckBox.getChildren().get(i)).isChecked()) {
					listIntCheckBox.add((Integer) ((Checkbox) divCheckBox.getChildren().get(i)).getValue());
				}
			}
		}

		CategoryModel catemodel = new DefaultCategoryModel();
		for (ScoresRecord scoRcSt1 : listSubRcSt1) {
			for (ScoresRecord scoRcSt2 : listSubRcSt2) {
				if (scoRcSt1.getSubjectid() == scoRcSt2.getSubjectid()) {
					SubjectsRecord subjectCompare = subPerJDBC.findById(scoRcSt1.getSubjectid());

					for (Integer integer : listIntCheckBox) {
						if (integer.intValue() == subjectCompare.getId()) {

							// set tab 1
							Hlayout hlayout = new Hlayout();
							hlayout.setStyle("border-bottom: 3px solid black");

							Label label = new Label();
							// Compare1
							label.setValue((scoRcSt1.getScore() - scoRcSt2.getScore()) + "");
							if (scoRcSt1.getScore().compareTo(scoRcSt2.getScore()) > 0) {
								label.setStyle("color : green");
							} else {
								label.setStyle("color : red");
							}
							label.setHflex("1");
							label.setParent(hlayout);
							// Score st1
							label = new Label(scoRcSt1.getScore() + "");
							label.setHflex("1");
							label.setParent(hlayout);
							// Name Subject
							label = new Label(subjectCompare.getNamesu());
							label.setHflex("1");
							label.setParent(hlayout);
							// Score st2
							label = new Label(scoRcSt2.getScore() + "");
							label.setHflex("1");
							label.setParent(hlayout);

							// Compare2
							label = new Label();
							label.setValue((scoRcSt2.getScore() - scoRcSt1.getScore()) + "");
							if (scoRcSt2.getScore().compareTo(scoRcSt1.getScore()) > 0) {
								label.setStyle("color : green");
							} else {
								label.setStyle("color : red");
							}
							label.setHflex("1");
							label.setParent(hlayout);
							// out code
							hlayout.setParent(divTableCompare);

							// set tab 2
							catemodel.setValue(st1.getNamest(), subjectCompare.getNamesu(), scoRcSt1.getScore());
							catemodel.setValue(st2.getNamest(), subjectCompare.getNamesu(), scoRcSt2.getScore());
							break;
						}
					}

				}
			}
		}
		chart.setModel(catemodel);
		chart.setParent(tpColumn);
	}

}
