/**
 * 
 */
package com.marwilc.botIndexer;
import java.awt.EventQueue;

import javax.swing.UIManager;

import com.marwilc.botIndexer.controller.Manager;

/**
 * @author marwilc
 *
 */
public class App
{
	private Manager myManager;
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					App myPrincipal= new App();
					myPrincipal.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	
	private void init()
	{
		/*se instancian las clases
		 */
		myManager = new Manager();
		
	}

}