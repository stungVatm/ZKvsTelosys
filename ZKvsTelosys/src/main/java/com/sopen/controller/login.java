package com.sopen.controller;

import java.util.List;

import org.demo.data.record.AdminRecord;
import org.demo.persistence.impl.jdbc.AdminPersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

public class login extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	Div loginWin;

	@Wire
	Textbox name;
	@Wire
	Textbox pwd;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.init();
	}

	private void init() {
		// TODO Auto-generated method stub

	}

	@Listen("onClick = #Submit; onOK = #loginWin")
	public void Submit() {
		List<AdminRecord> lstAdm = new AdminPersistenceJdbc().findAll();
		for (AdminRecord a : lstAdm) {
			if (a.getUserad().equalsIgnoreCase(name.getValue()) && a.getPassad().equals(pwd.getValue())) {
				Session ss = Sessions.getCurrent();
				ss.setAttribute("AdminLogin", a);
				Executions.sendRedirect("main.zul");
				return;
			}
		}
		Clients.evalJavaScript("$( \".z-window\" ).effect( \"shake\" );");
		alert("Loi");

	}

}
