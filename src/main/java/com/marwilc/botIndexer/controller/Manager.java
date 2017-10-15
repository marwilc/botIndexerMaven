/**
 * 
 */
package com.marwilc.botIndexer.controller;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.marwilc.botIndexer.view.ViewPrincipal;

/**
 * @author marwilc
 *
 */
public class Manager
{

	private ViewPrincipal myViewPrincipal;
	private Robot myRobot;
	private Desktop dk = null;
	private ArrayList<String> accounts;
	
	public Manager()
	{
		super();
		myViewPrincipal = new ViewPrincipal(this);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method Open the browser web
	 * default this pc
	 * @param url
	 */
	public void openBrowser(String url)
	{
		// TODO Auto-generated method stub
		if(java.awt.Desktop.isDesktopSupported()){
			 try{
				 if(dk==null)
					 dk = Desktop.getDesktop();
			      
				 dk.browse(new URI(url));
			 }catch(Exception error){
			     System.out.println("Error al abrir URL: "+ error.getMessage());
			 }
		}
	}

	/**
	 * Este metodo lanza el evento del boton run 
	 */
	public void initRunEvent()
	{
		 
		// TODO Auto-generated method stub
		if(!myViewPrincipal.getTextFieldApi().getText().isEmpty()){
			// open browser google search 
			openBrowser("https://www.google.com/webmasters/tools/home?hl=en");
			
			// construye un arrayList con las cuentas de email
			String emailAccounts = myViewPrincipal.getTextAreaEmail().getText();
			accounts = buildArrayAccounts(emailAccounts);
			
			
			// iniciar robot escritor
			try
			{
				robotWriteEmail();
			}
			catch (AWTException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("Api Key no configurada");
		}
	}

	/**
	 * construye un array list de las cuentas de gmail
	 * con su password
	 * @return
	 */
	private ArrayList<String> buildArrayAccounts(String gmailAccounts)
	{
		// TODO Auto-generated method stub
		ArrayList<String> accounts =  new ArrayList<>();
		
		for (int i = 0; i < gmailAccounts.length(); i++)
		{
			
			String gmail = "";
			char c = gmailAccounts.charAt(i);
			
			/*
			 *  compara si es un caracter separador ,
			 *  si es , agrega la cadena a la lista de array
			 */
			
			if(c != ',')
				gmail = gmail + c;
			else
				accounts.add(gmail);
		} 
		return accounts;
	}

	private void robotWriteEmail() throws AWTException
	{
		// TODO Auto-generated method stub
		// cadena sencilla soy un robot
		
	
		final int keys[] = {
				KeyEvent.VK_S, KeyEvent.VK_O,
				KeyEvent.VK_Y, KeyEvent.VK_SPACE,
				KeyEvent.VK_U, KeyEvent.VK_N,
				KeyEvent.VK_SPACE, KeyEvent.VK_R,
				KeyEvent.VK_O,KeyEvent.VK_B,
				KeyEvent.VK_O, KeyEvent.VK_T
		};
		
		
				myRobot = new Robot();
				 
				// esperamos 2 segundos para escribir
				myRobot.delay(15000);
		
				for (int i = 0; i < keys.length; i++)
				{
					myRobot.keyPress(keys[i]);
					myRobot.keyRelease(keys[i]);
					
					//dormimos el robot por 250 mili segundos luego de usar cada tecla
		            myRobot.delay(250);
				}
	}

	/**
	 * este metodo carga en la interfaz el archivo excel con las 
	 * cuentas de google del archivo seleccionado 
	 */
	public void initEventAddFileAccounts()
	{
		// TODO Auto-generated method stub
		openExcelFile();
		

	}

	/**
	 * abre una ventana nueva con la interfaz para abrir un archivo excel
	 */
	private void openExcelFile()
	{
		// TODO Auto-generated method stub
		FileDialog dialogoArchivo; 
		File excelFile;
		InputStream excelStream = null;
		String urlFileSelected = "";
		dialogoArchivo = new FileDialog(myViewPrincipal,"Abrir Archivo excel ", FileDialog.LOAD); 
		dialogoArchivo.setVisible(true);
		urlFileSelected =  dialogoArchivo.getDirectory() + dialogoArchivo.getFile();
		
		excelFile = new File(urlFileSelected);
		
		try{
			excelStream = new FileInputStream(excelFile);
			// representacion del mas alto nivel de la hoja de excel
			XSSFWorkbook workbook = new XSSFWorkbook(excelStream);
			// elegimoas la hoja que pasa por parametros
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			   
			while (rowIterator.hasNext()) {
			   Row row = rowIterator.next();
			   Iterator<Cell> cellIterator = row.cellIterator();
			   while (cellIterator.hasNext()) {
				   Cell cell = cellIterator.next();
				   switch (cell.getCellTypeEnum()) {
					   case Cell.CELL_TYPE_STRING:
						   System.out.print(cell.getStringCellValue() + "(String)\t");
						   break;   
				   }  
			   }
			   System.out.println("");
			   }
			excelStream.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
}
