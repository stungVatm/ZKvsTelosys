package com.sopen.controller;

import java.awt.Window;

import org.demo.data.record.AdminRecord;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Toolbarbutton;

public class Main extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	Toolbarbutton TbAdmin;
	@Wire
	org.zkoss.zul.Div mainZul;

	private org.zkoss.zul.Window detail;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Session ssn = Sessions.getCurrent();
		AdminRecord admin = null;
		// check exist Admin
		if (ssn.getAttribute("AdminLogin") == null) {
			Executions.sendRedirect("login.zul");
		} else {
			admin = (AdminRecord) ssn.getAttribute("AdminLogin");
			TbAdmin.setLabel(admin.getNamead());
		}

		// setup for admin

	}

	@Listen("onClick = #TbAdmin")
	public void logoutAdmin() {
		detail = (org.zkoss.zul.Window) Executions.createComponents("Zul/Admin/LogoutAdmin.zul", null, null);
		detail.doModal();
		detail.setParent(mainZul);
	}
}
