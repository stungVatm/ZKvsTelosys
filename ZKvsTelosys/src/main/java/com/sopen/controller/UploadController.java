package com.sopen.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.demo.persistence.impl.jdbc.ClassesPersistenceJdbc;
import org.demo.persistence.impl.jdbc.StudentsPersistenceJdbc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

public class UploadController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	Image imgLogoSchool;

	@Wire
	Label lblTotalStudents, lblTotalClasses;

	// Declare
	StudentsPersistenceJdbc stdPerJDBC = new StudentsPersistenceJdbc();
	ClassesPersistenceJdbc clsPerJDBC = new ClassesPersistenceJdbc();

	@Override
	public void doAfterCompose(Component cmp) throws Exception {
		super.doAfterCompose(cmp);
//		imgLogoSchool.setSrc("");
//		FileOutputStream fileout = new FileOutputStream(
//				"C:\\Users\\Sopen\\eclipse-workspace\\ZKvsTelosys\\src\\main\\webapp\\Image\\LogoSchool\\sopen-logo.png");
		File file = new File(
				"C:\\Users\\Sopen\\eclipse-workspace\\ZKvsTelosys\\src\\main\\webapp\\Image\\LogoSchool\\sopen-logo.png");

		String urlImage = "data:image/png;base64," + encodeFileToBase64Binary(file);
		imgLogoSchool.setSrc(urlImage);
//		File file = new File("C:\\Users\\Sopen\\eclipse-workspace\\ZKvsTelosys\\src\\main\\webapp\\Image\\LogoSchool\\sopen-logo.png");

//		file.get
//		String srcImgeLogo = encodeFileToBase64Binary(file);
		lblTotalStudents.setValue(stdPerJDBC.countAll() + "");
		lblTotalClasses.setValue(clsPerJDBC.countAll() + "");
	}

	@Listen("onUpload = #btnupload")
	public void upload(UploadEvent e) throws IOException {

		byte[] bytes = e.getMedia().getByteData();

		FileOutputStream fileout = new FileOutputStream(
				"C:\\Users\\Sopen\\eclipse-workspace\\ZKvsTelosys\\src\\main\\webapp\\Image\\LogoSchool\\sopen-logo.png");
		fileout.write(bytes);
		System.out.println();
		File file = new File(
				"C:\\Users\\Sopen\\eclipse-workspace\\ZKvsTelosys\\src\\main\\webapp\\Image\\LogoSchool\\sopen-logo.png");

		String urlImage = "data:image/png;base64," + encodeFileToBase64Binary(file);
		imgLogoSchool.setSrc(urlImage);
	}

	private static String encodeFileToBase64Binary(File file) {
		String encodedfile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = Base64.getEncoder().encodeToString(bytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encodedfile;
	}
}
