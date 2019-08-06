package com.sopen.controller;

import org.demo.data.record.AdminRecord;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;

public class index extends SelectorComposer<Component>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public void doAfterCompose(Component cpm) throws Exception {
		super.doAfterCompose(cpm);
		Session ssn = Sessions.getCurrent();
		if (ssn.getAttribute("AdminLogin")==null) {
			Executions.sendRedirect("login.zul");
		}
		
	}
	
	
}
