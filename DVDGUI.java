package assignPackage5;


import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */

public class DVDGUI implements DVDUserInterface {
	 
	 private DVDCollection dvdlist;
	 private DefaultListModel<String> list;
	 
	 public DVDGUI(DVDCollection dl)
	 {
		 dvdlist = dl;
		 displayDVDList();
	 }
	 
	 public void processCommands()
	 {
		
	 }

	private void doAddOrModifyDVD() {

		// Request the title
		String title = JOptionPane.showInputDialog("Enter title");
		if (title == null) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();
		
		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating for " + title);
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		
		// Request the running time
		String time = JOptionPane.showInputDialog("Enter running time for " + title);
		if (time == null) {
		}
		
                // Add or modify the DVD (assuming the rating and time are valid
        dvdlist.addOrModifyDVD(title, rating, time);
        // Display current collection to the console for debugging      
        
        // refresh list after adding
        refreshList(); 
		
	}
	
	private void doRemoveDVD() {

		// Request the title
		String title = JOptionPane.showInputDialog("Enter title");
		if (title == null) {
			return;		// dialog was cancelled
		}
		title = title.toUpperCase();
		
        // Remove the matching DVD if found
        dvdlist.removeDVD(title);
                 
        // refresh list after deleting
        refreshList();
               
          		
	}
	
	private void doGetDVDsByRating() {

		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating");
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		
        String results = dvdlist.getDVDsByRating(rating);
        MyFrame f1 = new MyFrame();
        f1.setLayout(new BoxLayout(f1.getContentPane(), BoxLayout.Y_AXIS)); // displays contents vertically
        JPanel p1 = new JPanel();
 
        f1.setTitle("DVD by Rating");
        JLabel l1 = new JLabel("DVDs with " + rating + " Rating: " + results + "\n");
        p1.add(l1);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
  		l1.setForeground(new Color(0xd981ff)); // set label  color, purple
   		l1.setFont(new Font("Ariel", Font.PLAIN,20)); // set font	
  		f1.add(l1); // add to frame
  	
            

	}

    private void doGetTotalRunningTime() {
    	
  		MyFrame f1 = new MyFrame();
        f1.setTitle("Total Running Time");
        		
        int total = dvdlist.getTotalRunningTime();
                
      	String s = Integer.toString(total);
        JLabel l1 = new JLabel("Total Running Time: " + s);
        // center label text
        l1.setHorizontalAlignment(SwingConstants.CENTER);
  		l1.setForeground(new Color(0xd981ff)); // set label  color, purple
   		l1.setFont(new Font("Ariel", Font.PLAIN,20)); // set font	
  		f1.add(l1); // add to frame
  		
                
    }
    
    // helper function to populate list
    private void createDListModel(){
    	 //list = new DefaultListModel<String>();
         DVD[] dvd = dvdlist.getArray(); // create a an array of DVD object to use to access info, title, rating and time
         
         // adding the DVD titles to the list
         for(int i = 0; i < dvdlist.getArrayLength(); i++) {
         	list.addElement(dvd[i].getTitle());
         }
         //return list; 
    }
    
    private void refreshList() {
    	/* Note to self: 
    	 * figure out why you cannot just call createDListModel() 
    	 * after clearing the list and why looping again in this function properly updates the list*/
    	/* Reason:
    	 *  createDListModel created a new DLM every time it was called,
    	 * therefore when the list was cleared, and createDLM was called again,
    	 * a new list was created but JList is still pointing to the old one, which was cleared
    	 * TLDR: createDLM is called but, cannot reflect the changes because JList is pointing to the previous cleared list */
    	/* Fix: 
    	 * Create the new DefaultListModel ONCE in the displayDVDList function to ensure that it only
    	 * created once and referenced throughout the program. This way refreshList works as intended and
    	 * createDListModel has one simple task*/
    	list.clear(); // clear out the list ...	
    	createDListModel(); // ... and re add the updated collection
    	
    }
    
    private void displayDVDList() {
        //String title = dvdlist.getArray()[2].getTitle();
    	
        // Set up frame
        JFrame f = new MyFrame();
        f.setTitle("DVD List");
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS)); // displays contents vertically
        JLabel header = new JLabel("DVD Collection", JLabel.CENTER);
        
        // Panels
        JPanel headerPanel = new JPanel(); // panel for the header
        JPanel buttonsPanel = new JPanel(); // panel for buttons
        JPanel detailsPanel = new JPanel(); // panel for the DVD details, title, rating, time
        JPanel centerDetailPanel = new JPanel(); // center the detailsPanel
        JPanel imgPanel = new JPanel(); // panel for image
        
        // Buttons
        JButton editButton = new JButton("Edit Selected");
        JButton timeButton = new JButton("Get Running Time");
        JButton removeButton = new JButton("Remove DVDs");
        JButton ratingsButton = new JButton("Get DVD by Ratings");
        JButton addButton = new JButton("Add DVD");
        JButton saveButton = new JButton("Save and Exit");
        
        // Labels
        JLabel imgLabel = new JLabel();
        JLabel titleLabel = new JLabel("Title: ");
        JLabel ratingLabel = new JLabel("Rating: ");
        JLabel timeLabel = new JLabel("Running Time: ");
        
        // Add labels to details panel to organize them 
        detailsPanel.add(titleLabel);
        detailsPanel.add(ratingLabel);
        detailsPanel.add(timeLabel);
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS)); // display details vertically
        centerDetailPanel.add(detailsPanel); // center detail panel
        
        imgPanel.add(imgLabel); // add imgLabel to panel
     
        headerPanel.add(header); // add header to headerPanel
        
        // Main GUI logic
        
        
        // Populate list with our DVD Collection
        list = new DefaultListModel<String>();  // default list model - to manage items in the list
        DVD[] dvd = dvdlist.getArray(); // get our DVD collection array, reference of DVDCollection object
        createDListModel(); // Function to add our DVD collection to the list, private variable 
        
        // Initializing the list
        JList<String> dvdJList = new JList<String>(list); // adding our list to JList to display DVDs
        
        JScrollPane listScroller = new JScrollPane(dvdJList); // Scroller
        listScroller.setPreferredSize(new Dimension(150, 80));
        dvdJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION); //  specifies how many items the user can select
        //dvdJList.setLayoutOrientation(dvdJList.VERTICAL); //  lets the list display its data in multiple columns, not needed because of Jframe boxlayout
        dvdJList.setVisibleRowCount(-1); // makes the list display the maximum number of items possible in the available space on screen
        
        // Selection and Action Listeners
        
        // Displaying our list, when user clicks on a DVD, its information will be displayed
        dvdJList.addListSelectionListener(e -> {
        	if(!e.getValueIsAdjusting()) {
        		int selectedChoice  = dvdJList.getSelectedIndex(); // get index when user clicks
        		if(selectedChoice >= 0) {
        			 DVD selectedDVD = dvd[selectedChoice]; // DVD is an array of DVD objects
                     titleLabel.setText("Title: " + selectedDVD.getTitle());
                     ratingLabel.setText("Rating: " + selectedDVD.getRating());
                     timeLabel.setText("Running Time: " + selectedDVD.getRunningTime() + " minutes");
                     displayDVDImg(selectedDVD.getTitle(), imgLabel);
        		}
        	}
        });
       
        // edit a DVD when clicked
        editButton.addActionListener(e -> {
            int selectedIndex = dvdJList.getSelectedIndex();
            if (selectedIndex >= 0) {
                // Call edit method for the selected DVD
                editDVD(dvd[selectedIndex]);
            } else {
                JOptionPane.showMessageDialog(f, "Please select a DVD to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Additional GUI features
        
        // Display total running time 
        timeButton.addActionListener(e-> {
        	doGetTotalRunningTime();
        });
        
        // removes a DVD and updates a list
        removeButton.addActionListener(e-> {
        	doRemoveDVD(); // after removing refresh the list
        });
        
        // Displays the ratings
        ratingsButton.addActionListener(e-> {
        	doGetDVDsByRating();
        });
        
        // Adds a DVD and updates the list
        addButton.addActionListener(e-> {
        	doAddOrModifyDVD();
        });
        
        // Saves and closes
        saveButton.addActionListener(e-> {
        	doSave();
        });
        
        // add all the buttons to the panel
        buttonsPanel.add(editButton);
        buttonsPanel.add(timeButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(ratingsButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(saveButton);
        // Add components to frame
        f.add(headerPanel);
        f.add(imgPanel);
        f.add(listScroller);
        f.add(dvdJList);
        f.add(centerDetailPanel);
        f.add(buttonsPanel);
       
    }
    
    private void displayDVDImg(String title, JLabel imgLabel ) {
    	String dvdPath = title + ".jpg"; 
    	ImageIcon img1;
    	
    	try {
    		img1 = new ImageIcon(getClass().getResource(dvdPath)); // get our image
    		
    	}catch (Exception e) {
    		// if not found, load default image
    		img1 = new ImageIcon(getClass().getResource("default.png"));
    	}
    	// resize image
    	Image scaledImage = img1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imgLabel.setIcon(scaledIcon);
    }
    
    private void editDVD(DVD dvd) {
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // arrange component vertically
    	
    	// display the dvd infomation so the user knows the details of what they're editing
    	JTextField ratingField = new JTextField(dvd.getRating());
    	JTextField timeField = new JTextField(String.valueOf(dvd.getRunningTime()));
    	// add our labels
    	JLabel l1 = new JLabel("Rating:");
    	JLabel l2= new JLabel("Running Time:");
    	
    	// add components to panel
        panel.add(l1);
   	    panel.add(ratingField); // displays the selected dvd rating
   	    panel.add(l2);
    	panel.add(timeField);
    	
    	// result will be OK or CANCEL
    	int result = JOptionPane.showConfirmDialog(
    			null,
    			panel, 
    			"Edit " + dvd.getTitle(),
    			JOptionPane.OK_CANCEL_OPTION
    			);
    	
    	if(result == JOptionPane.OK_OPTION) {
    		// pass same title and update our rating and running time from the user
    		dvdlist.addOrModifyDVD(dvd.getTitle(), ratingField.getText(), timeField.getText());
    	}
    }

	private void doSave() {
		
		dvdlist.save();
		System.exit(0); // save and close program
		
	}
		
}
