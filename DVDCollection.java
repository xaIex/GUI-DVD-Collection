package assignPackage5;

import java.awt.Color;
import java.awt.Font;
import java.io.*;

import java.util.Scanner; 
import java.io.FileWriter; 
import java.io.IOException;
import javax.swing.*;
public class DVDCollection {
	

	// Data fields
	
	/** The current number of DVDs in the array */
	private int numdvds;
	
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	
	/** The name of the data file that contains dvd data */
	private String sourceName;
	
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;
	
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}
	
	public String toString() {
		// Return a string containing all the DVDs in the
		// order they are stored in the array along with
		// the values for numdvds and the length of the array.
		// See homework instructions for proper format.
		String dvdCollections = "";

		dvdCollections += "numdvds = " + numdvds + "\n" + "dvdArray.length = " +  dvdArray.length + "\n";
		
				
		if(numdvds == 0) return "No dvds in the collection\n";
		
		for(int i = 0; i < numdvds; i++) {
			dvdCollections += "dvdArray["+ i +"] = " + dvdArray[i].toString() + "\n";
			
		}
		
		return dvdCollections;	
	}

	public void addOrModifyDVD(String title, String rating, String runningTime) {
		// NOTE: Be careful. Running time is a string here
		// since the user might enter non-digits when prompted.
		// If the array is full and a new DVD needs to be added,
		// double the size of the array first.
		
		//validate rating
		if(!validateRating(rating)) {
			System.out.print("Not a valid rating entered! DVD was not added. " + rating + "\n");
			return;
		}
		
		if(!validateRunningTime(runningTime)) {
			System.out.print("Not a valid runnning time! DVD was not added. " + runningTime + "\n");
			return;
		}
		
		
		// checks if the title exists in the collection, if it does modify it and return
		for(int i = 0; i < numdvds; i++) {
			if(dvdArray[i].getTitle().equals(title)) { // note to self: use equals instead of '==', equals() compares the actual contents unlike == 
				dvdArray[i].setRating(rating);
				dvdArray[i].setRunningTime(Integer.parseInt(runningTime)); // convert to integer 
				modified = true;
				return;
			}
		} 
		
		// if dvd is not found, add it to the collection 
		if(numdvds < dvdArray.length) {
			dvdArray[numdvds] = new DVD(title, rating, Integer.parseInt(runningTime));
			numdvds++;
			
		} else { 
			// else case where numdvds == dvdArray length, double its size 
			DVD[] newDVDArray = new DVD[dvdArray.length * 2]; // create another DVD array with double the size
			System.arraycopy(dvdArray, 0, newDVDArray, 0, dvdArray.length); // copy dvdArray elements to new array
			dvdArray = newDVDArray;
			
			dvdArray[numdvds] = new DVD(title, rating, Integer.parseInt(runningTime));
		    numdvds++;
		}
		
		
		modified = true; 

	
	}
	
	public void removeDVD(String title) {
		
		title = title.toUpperCase();
		
		if(numdvds == 0) {
			System.out.print("Collection is empty, nothing to remove");
			return;
		} 
		
		for(int i = 0; i < numdvds; i++) {
			if(dvdArray[i].getTitle().equals(title)) {
				
				/* shifting dvds to replace the removed DVD to ensure no null spaces in the middle of the collection
				 * otherwise an error occurs while trying to access a null element:
				 * Cannot invoke "assign2Package.DVD.toString()" because "this.dvdArray[i]" is null
				 * this happens if we replace the index where the dvd is found and just replace it with null
				 * dvdArray[i] = null;
				*/
				dvdArray[i] = dvdArray[numdvds - 1]; 
				dvdArray[numdvds - 1] = null;
				
				
				numdvds--;
				modified = true;
			
				return;
			}
		}
		
		//System.out.print("No dvd found with the title: " + title + "\n");
		JOptionPane.showMessageDialog(null, "No DVD found with the title: " + title, "removeDVD Error", JOptionPane.ERROR_MESSAGE);

	}
	
	public String getDVDsByRating(String rating) {


		if(numdvds == 0) return "No DVDs in the collection\n";
		
		String dvdRatings = "";
		Boolean flag = false;
		for(int i = 0; i < numdvds; i++) {
			if(dvdArray[i].getRating().equals(rating)) { // loop through collection and compare with selected rating
				dvdRatings += dvdArray[i].getTitle() + "\n"; // new line after each dvd that matches
				flag = true;
			}
		}
		
		if(flag) {
			return dvdRatings;
		} else 
			return "No DVDs found with matching rating\n";
		

	}

	public int getTotalRunningTime() {
		
		if(numdvds == 0) return 0;
	
		int totalRunningTime = 0;
		for(int i = 0; i < numdvds; i++) {
			totalRunningTime += dvdArray[i].getRunningTime();
		}
		
	
		return totalRunningTime;

	}

	public void loadData(String filename) {
		File myObj = new File(filename);
		Scanner scanner = null;
		try {
	
			scanner = new Scanner(myObj);
			//readinh each line from the file
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] dvdLine = line.split(","); 
				// trim any white space
				String title = dvdLine[0].trim();
				String rating = dvdLine[1].trim();
				String runningTime = dvdLine[2].trim();
				
			
				addOrModifyDVD(title, rating, runningTime);
				modified = false;
				sourceName = filename;
				
			}
			System.out.print("Data received from file: " + filename + "\n");
			scanner.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + filename + ". Starting with an empty collection.");

	        dvdArray = new DVD[7];
	        numdvds = 0;
			e.printStackTrace();
		}




		
	}
	
	public void save() {
		

		try {
			 FileWriter myWriter = new FileWriter(sourceName);
			 
			 if(numdvds > 0) {
				 for(int i = 0; i < numdvds; i++) {
					 myWriter.write(dvdArray[i].getTitle() + "," + 
							 		dvdArray[i].getRating() + "," + 
							 		dvdArray[i].getRunningTime() + "\n");
					 
				 }
				 //System.out.print("Changes saved to file: " + sourceName + "\n" + "Exiting...\n");
				 JOptionPane.showMessageDialog(null, "Your DVD Collection has been saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
			 }
			 
			 myWriter.close();
			 
			 modified = false;
			 
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}
	
	public DVD[] getArray(){
		return dvdArray;
	}
	
	public int getArrayLength() {
		return numdvds;
	}

	// Additional private helper methods go here:
	private boolean validateRating(String rate) {
		
		if(rate.equals("R") || rate.equals("PG") || rate.equals("G") || rate.equals("PG-13") || 
				rate.equals("PG-17") || rate.equals("PG-14")) {
			return true;
		} else 
			return false;
		
	}
	
	private boolean validateRunningTime(String time) {
		
		  try {  
			    Double.parseDouble(time);  
			    return true;
			  } catch(NumberFormatException e){  
			    return false;  
			  }  
			
		
	}



	
}
