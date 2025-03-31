package assignPackage5;

import java.util.*;
import javax.swing.*;
/**
 * 	Program to display and modify a simple DVD collection
 */

public class DVDManager {

	public static void main(String[] args) {
		
		DVDUserInterface dlInterface;
		DVDCollection dl = new DVDCollection();

		String filename = JOptionPane.showInputDialog("Enter name of data file to load: ");
		//  filename error checking 
		if (filename == null || filename.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "No file selected. Program will exit.");
	        System.exit(0); // Exit if filename is not provided
	    }
		
		if(!filename.equals("dvddata.txt")) {
			JOptionPane.showMessageDialog(null, "The filename: " + filename + " does not exist." );
			System.exit(0);
		}

		dl.loadData(filename);
		dlInterface = new DVDGUI(dl);
		dlInterface.processCommands();
		/*
		
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter name of data file to load:");
		String filename = scan.nextLine();			
		dl.loadData(filename);

		System.out.println("Input interface type: C=Console, G=GUI");
		String interfaceType = scan.nextLine();
		if (interfaceType.equals("C")) {
			dlInterface = new DVDConsoleUI(dl);
			dlInterface.processCommands();
		} else if (interfaceType.equals("G")) {
			dlInterface = new DVDGUI(dl);
			dlInterface.processCommands();
		} else {
			System.out.println("Unrecognized interface type. Program exiting.");
			System.exit(0);
		}*/
		
		
	}

}
