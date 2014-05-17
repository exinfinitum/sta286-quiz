
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.*;
import java.io.*;

 
/* FrameDemo.java requires no other files. */
public class statsQuiz implements ActionListener{
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

	//Define our constants HERE.
	public static ArrayList<Integer> dimensions = new ArrayList(Arrays.asList(500, 500));
	// General components.

	public int timeLimit = 120000; // time limit.
	
	public static statsQuiz myApp;
	public static JFrame frame;
	public JMenu fileMenu;
	public static JPanel cardPanel, omniPanel, buttonPanel;
	public JTextField answerEntry;
	public double randomnum;
	public javax.swing.Timer mytimer = new javax.swing.Timer(1, this);
	//Question info.
	
	public int timeLeft = 0;
	
	public static ArrayList<String> questions_strings = new ArrayList<String>();
	public static ArrayList<String> questions_images = new ArrayList<String>();
	public static int currentQuestion = 0;
	
	
	//Intro pane stuff.
	public JMenuItem about, exit;
	public JButton next, toinfo, btnBeginQuiz, nextQuestion, btnExit;
	
	//Questionnaire.
	public JTextField calc1, calc2, calc3, davis, highSchoolMath;
	public JRadioButton rdbtnYes, rdbtnNo;

	//Collected data.
	private String calculatorUse;
	private String calc1grade, calc2grade, calc3grade, davisgrade;
	private String highSchoolLevel;
	private String highSchoolCalc;
	private ArrayList<String> responses = new ArrayList<String>();
	private ArrayList<String> times = new ArrayList<String>();

	public void nextQuestion(){
		responses.add(answerEntry.getText());
		times.add(Integer.toString(timeLeft));
		
		if (currentQuestion < (questions_strings.size() - 1)){
		currentQuestion ++;
		cardPanel.add(myApp.makeQuestion(questions_strings.get(currentQuestion), questions_images.get(currentQuestion)));
		CardLayout cl = (CardLayout)(cardPanel.getLayout());
		cl.next(cardPanel);
		
		
		//mytimer.restart();
		//timeLeft = timeLimit;
		//timeLabel.setText(("<html><body style='width: 100px'> <div align='center'>Time left: " + Integer.toString(timeLeft) + "</div>"));
		}
		else{
			//Export stuff to a csv.
			csvexport();
			cardPanel.add(myApp.endOfTest());
			CardLayout cl = (CardLayout)(cardPanel.getLayout());
			cl.next(cardPanel);
			mytimer.stop();
	
		}
	}
	public void csvexport(){
		//Export in this order: calculatorUse, calc1, calc2, calc3, davis, highSchoolLevel, highSchoolCalc, responses, times.
		File oldfile = new File("output.csv");
		//Set up
		String outputstring = "";
		if(oldfile.exists()) {
			FileReader readme = null;
			try {
				readme = new FileReader("output.csv");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader buffer = new BufferedReader (readme);
			String currLine;
			try {
				while ((currLine = buffer.readLine()) != null){
					outputstring += currLine;
					outputstring += "\n";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			outputstring = "";
		}
		
		
		//Output our data
		
		ArrayList<String> strings = new ArrayList(Arrays.asList(calculatorUse, calc2grade, highSchoolLevel, highSchoolCalc));
		for (String iter : strings){
			outputstring += iter;
			outputstring += "\t";
		}
		for (int i = 0; i < responses.size(); i++)
		{
			outputstring += responses.get(i);
			outputstring += "\t";
			outputstring += times.get(i);
			outputstring += "\t";
			
		}
			
			
		Writer outfile = null;
		try {
			outfile = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("output.csv"), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			outfile.write(outputstring);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
				try {
					outfile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
		//if(!outfile.exists()){ 
		//	outfile.createNewFile();
		//}
		
	
	public void timerEvent(){

			//Each second we decrease the count by one.
			//Once the count reaches zero, we advance to next question.
			if (timeLeft <= 0){
			myApp.nextQuestion();
			}
			else{
				timeLeft--;
				timeLabel.setText(("<html><body style='width: 100px'> <div align='center'>Time left: " + Integer.toString(timeLeft) + "</div>"));
			}
			
		
	}
	
	public JPanel endOfTest(){
	      // We create a bottom JPanel to place everything on.
	        JPanel totalGUI = new JPanel();
	        
	        // We set the Layout Manager to null so we can manually place
	        // the Panels.
	        totalGUI.setLayout(null);
	        
	        lblNewLabel_4 = new JLabel("<html><body style='width: 340px'>Thank you for your time. Your answers have been recorded.<div align='center'></div>");
	        lblNewLabel_4.setVerticalAlignment(SwingConstants.TOP);
	        lblNewLabel_4.setBounds(25, 32, 450, 15);
	        totalGUI.add(lblNewLabel_4);
	        
	        btnExit = new JButton("Exit");
	        btnExit.setBounds(193, 383, 117, 25);
	        totalGUI.add(btnExit);
	        btnExit.addActionListener(this);


	        return totalGUI;
		

		}
	
	public JPanel makeQuestion(String questionText, String imageFile){
	      // We create a bottom JPanel to place everything on.
	        JPanel totalGUI = new JPanel();
	        
	        // We set the Layout Manager to null so we can manually place
	        // the Panels.
	        totalGUI.setLayout(null);
	        
	        BufferedImage img = null;
	        if (imageFile != null && imageFile.compareTo("") != 0){
			try {
	    		img = ImageIO.read(new File(imageFile));
			} catch (IOException e) {
	    		e.printStackTrace();
			}
			Image resized = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			JLabel theimage = new JLabel();
	        theimage.setIcon(new ImageIcon(resized));
	        theimage.setBounds(150, 12, 200, 200);
	        totalGUI.add(theimage);
	        }
	        
			 
			
			
	        
	        
	        
	        
	        JLabel lblNewLabel_3 = new JLabel("<html><body style='width: 340px'>" + questionText + "<div align='center'>\n\n\n\n</div>");
	        lblNewLabel_3.setVerticalAlignment(SwingConstants.TOP);
	        lblNewLabel_3.setBounds(20, 237, 460, 100);
	        totalGUI.add(lblNewLabel_3);
	        
	        answerEntry = new JTextField();
	        answerEntry.setBounds(20, 360, 460, 34);
	        totalGUI.add(answerEntry);
	        
	        nextQuestion = new JButton("Submit answer");
	        nextQuestion.setBounds(175, 426, 150, 25);
	        totalGUI.add(nextQuestion);
	        
	        timeLabel = new JLabel("");
	        timeLabel.setVerticalAlignment(SwingConstants.TOP);
	        timeLabel.setBounds(175, 406, 150, 15);
	        totalGUI.add(timeLabel);
	        nextQuestion.addActionListener(this);

	        return totalGUI;
		

		}
	
	
	public JPanel makeQuestionnaire(){
      // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel();
        
        // We set the Layout Manager to null so we can manually place
        // the Panels.
        totalGUI.setLayout(null);

	//Now, we add stuff.
	
	//JPanel questionOne = new JPanel();
	//JPanel questionTwo = new JPanel();
	//JPanel questionThree = new JPanel();
        JLabel maintext = new JLabel("<html><body style='width: 370px'> <div align='center'>"
					+questionnaire 
					+"</div>");

        maintext.setSize(480, 15);
        totalGUI.add(maintext);



	totalGUI.add(addJLabel("1. What was your grade (out of 100) in the following courses?", 10, 30, 480, 17, "370", "center"));

	//calc1 = new JTextField(2);
    //    calc1.setLocation(20, 50);
    //    calc1.setSize(56, 20);
    //    totalGUI.add(calc1);	
	//totalGUI.add(addJLabel("Calc I", 7, 75, 50, 17, "50", "left"));

	calc2 = new JTextField(2);
        calc2.setLocation(120, 50);
        calc2.setSize(212, 20);
	totalGUI.add(calc2);
	//totalGUI.add(addJLabel("Calc II", 104, 75, 60, 17, "60", "left"));

	//calc3 = new JTextField(2);
    //    calc3.setLocation(220, 50);
    //   calc3.setSize(56, 20);
    //    totalGUI.add(calc3);
	//totalGUI.add(addJLabel("Calc III", 200, 75, 80, 17, "80", "left"));

	//davis = new JTextField(2);
    //    davis.setLocation(350, 50);
    //    davis.setSize(56, 20);
    //    totalGUI.add(davis);
	//totalGUI.add(addJLabel("AER210 Vector Calculus", 295, 75, 180, 17, "180", "left"));






	//totalGUI.add(addJLabel("2. What level of mathematics did you cover up to in high school (e.g. IB, AP, Gifted)?", 10, 100, 480, 27, "370", "center"));
	
	//totalGUI.add(addJLabel("3. Were you allowed to use a calculator in high school mathematics?", 10, 150, 480, 24, "370", "center"));
	//totalGUI.add(questionOne);
	//totalGUI.add(questionTwo);
	//totalGUI.add(questionThree);
        
        // Finally we return the JPanel.

        totalGUI.setOpaque(true);
        
        lblWhatLevel = new JLabel("<html><body style='width: 325px'> <div align='center'>2. What level of calculus did you cover in high school (e.g. AP, IB, Gaokao, Gifted program)?</div>");
        lblWhatLevel.setBounds(49, 82, 415, 62);
        totalGUI.add(lblWhatLevel);
        
        highSchoolMath = new JTextField();
        highSchoolMath.setBounds(150, 138, 220, 19);
        totalGUI.add(highSchoolMath);
        highSchoolMath.setColumns(10);
        
        lblNewLabel = new JLabel("<html><body style='width: 450'> <div align='center'>3. Did you use a calculator when doing calculus in high school?</div>");
        lblNewLabel.setBounds(12, 175, 468, 36);
        totalGUI.add(lblNewLabel);
        ButtonGroup radButtonGroup = new ButtonGroup();
        rdbtnYes = new JRadioButton("Yes");
        rdbtnYes.setBounds(91, 203, 149, 23);
        totalGUI.add(rdbtnYes);
        radButtonGroup.add(rdbtnYes);
        rdbtnYes.addActionListener(this);
        
        rdbtnNo = new JRadioButton("No");
        rdbtnNo.setBounds(244, 203, 149, 23);
        totalGUI.add(rdbtnNo);
        radButtonGroup.add(rdbtnNo);
        rdbtnNo.addActionListener(this);
        
        
        toinfo = new JButton("Next");
        toinfo.setBounds(182, 393, 117, 25);
        totalGUI.add(toinfo);
        
        lblPleaseFillOut = new JLabel("");
        lblPleaseFillOut.setBounds(110, 366, 279, 15);
        totalGUI.add(lblPleaseFillOut);
        toinfo.addActionListener(this);
        return totalGUI;
	

	}
	
	public JPanel makeInfoPane(){
	      // We create a bottom JPanel to place everything on.
	        JPanel totalGUI = new JPanel();
	        totalGUI.setLayout(null);
	        totalGUI.setOpaque(true);
	        
	        JLabel lblNewLabel_1 = new JLabel("<html><body style='width: 370px'> <div align='center'>The quiz will consist of two (2) questions. You must finish all questions in order - there is no skipping ahead, nor is there redoing questions that you have already answered. You will receive two minutes for the entire quiz. If you feel that you have already answered the question to the best of your ability and would like to conclude it early, press the \"Next question\" button at the bottom.</div>");
	        lblNewLabel_1.setBounds(12, 12, 476, 174);
	        totalGUI.add(lblNewLabel_1);
	        
	        JLabel lblNewLabel_2;
	        if (randomnum >= 0.5){
	        lblNewLabel_2 = new JLabel("You will NOT be using a calculator for this quiz.");}
	        else{
	        lblNewLabel_2 = new JLabel("You will be using a calculator for this quiz.");}
	        lblNewLabel_2.setBounds(100, 255, 400, 15);
	        totalGUI.add(lblNewLabel_2);
	        
	        btnBeginQuiz = new JButton("Begin quiz");
	        btnBeginQuiz.setBounds(189, 412, 117, 25);
	        btnBeginQuiz.addActionListener(this);
	        totalGUI.add(btnBeginQuiz);
	        
	        JLabel lblNoteThatAll = new JLabel("NOTE THAT ALL ANSWERS WILL BE GRADED BY A HUMAN.");
	        lblNoteThatAll.setBounds(49, 198, 422, 15);
	        totalGUI.add(lblNoteThatAll);
	        return totalGUI;
		

		}

	public void initialize_questionlist(){
		//First, load all of the questions in.
		/*The format is as follows:
		 Question string%%imagefilename****next question string%%next image filename... 
		 
		
		*/
		mytimer.stop();
		randomnum = Math.random();
		File oldfile = new File("questions.txt");
		//Set up
		String questionstring = "";
		if(oldfile.exists()) {
			FileReader readme = null;
			try {
				readme = new FileReader("questions.txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader buffer = new BufferedReader (readme);
			String currLine;
			try {
				while ((currLine = buffer.readLine()) != null){
					questionstring += currLine;
					questionstring += "\n";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			questionstring = "";
		}
		
		String[] questions = questionstring.split("\\*\\*\\*\\*");
		
		//System.out.println(questions.length);
		for (int i= 0; i < ((questions.length - 1)); i++){
			//System.out.println(questions[i]);
			String[] sub = questions[i].split("\t");
			//System.out.println(sub[0]);
			//System.out.println(sub[1]);
			
			questions_strings.add(sub[0]);
			questions_images.add(sub[1]);
			
			
		}
		
		
	}
	
	public JLabel addJLabel(String text, int xloc, int yloc, int xsize, int ysize, String htmlsize, String alignment)
	{
		        JLabel mylabel = new JLabel("<html><body style='width: 370px'> <div align='center'>1. What do you believe is your aptitude in calculus on a scale of 0 (clueless) to 10 (calculus genius)?</div>");
        		mylabel.setLocation(10, 12);
        		mylabel.setSize(480, 35);
		        return mylabel;
	}
	
	

	public JPanel introPane(){
      // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel();
        
        // We set the Layout Manager to null so we can manually place
        // the Panels.
        totalGUI.setLayout(null);

	//Now, we add stuff.

        JLabel maintext = new JLabel("<html><body style='width: 370px'> <div align='center'>"
					+introtext 
					+"</div>");
        maintext.setLocation(10, 200);
        maintext.setSize(480, 200);
        totalGUI.add(maintext);





        next = new JButton("Next");
        next.setLocation(200, 400);
        next.setSize(100, 30);
	next.addActionListener(this);
        totalGUI.add(next);

        
        // Finally we return the JPanel.

        totalGUI.setOpaque(true);
        


        BufferedImage img = null;
		try {
    		img = ImageIO.read(new File("snake_long.jpg"));
		} catch (IOException e) {
    		e.printStackTrace();
		}
        
		Image resized = img.getScaledInstance(435, 207, Image.SCALE_SMOOTH); 
		
		
        lblNewLabel_3 = new JLabel();
        lblNewLabel_3.setIcon(new ImageIcon(resized));
        lblNewLabel_3.setBounds(34, 12, 435, 207);
        totalGUI.add(lblNewLabel_3);
        
        
        return totalGUI;
	

	}

	public JLabel generateScaledImageLabel(String filename, int xdim, int ydim, int xloc, int yloc){
		BufferedImage img = null;
		try {
    		img = ImageIO.read(new File(filename));
		} catch (IOException e) {
    		e.printStackTrace();
		}
        
		Image resized = img.getScaledInstance(xdim, ydim, Image.SCALE_SMOOTH); 
		
		
        JLabel Imagelabel = new JLabel();
        Imagelabel.setIcon(new ImageIcon(resized));
        Imagelabel.setBounds(yloc, xloc, ydim, xdim);
        return Imagelabel;
	}
	
	
	public JMenuBar createMenuBar()
	{
		//Create a menu bar with the "About" and "Exit"
		//functions.
		JMenuBar menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		about = new JMenuItem ("About");
		exit = new JMenuItem ("Exit");
		about.addActionListener(this);
		exit.addActionListener(this);

		menuBar.add(fileMenu);
		fileMenu.add(about);
		fileMenu.add(exit);


		return menuBar;

		
	}


	public void actionPerformed (ActionEvent e) {
		CardLayout cl = (CardLayout)(cardPanel.getLayout());
		if (e.getSource() == about){
			
			JOptionPane.showMessageDialog(frame, ("<html><body style='width: 150px'> <div align='center'>"
					+abouttext 
					//+"</div>"
					));
			
		}
		if (e.getSource() == mytimer){
			myApp.timerEvent();
		}
		if (e.getSource() == exit){
			
			System.exit(1);
			
		}
		if (e.getSource() == rdbtnNo){
			highSchoolCalc = "NO";
		}
		if (e.getSource() == rdbtnYes){
			highSchoolCalc = "YES";
		}
		if (e.getSource() == btnExit){
			System.exit(0);
		}
		if (e.getSource() == toinfo){
			
			if (
					//	(calc1.getText().compareTo("") != 0)
					 (calc2.getText().compareTo("") != 0)
					//&& (calc3.getText().compareTo("") != 0)
					//&& (davis.getText().compareTo("") != 0)
					&& (highSchoolMath.getText().compareTo("") != 0)
					&& highSchoolCalc != null){
			//calc1grade = calc1.getText();
			calc2grade = calc2.getText();
			//calc3grade = calc3.getText();
			//davisgrade = davis.getText();
			highSchoolLevel = highSchoolMath.getText();
			if (randomnum >= 0.5){
		    calculatorUse = "NO";}
		    else{
		    calculatorUse = "YES";}
			cl.next(cardPanel);
			}
			else
			{
				lblPleaseFillOut.setText("Please fill out all items to continue.");
				/*if (calc1.getText().compareTo("") == 0){
					lblPleaseFillOut.setText("STANGEBY");
				}
				if (calc2.getText().compareTo("") == 0){
					lblPleaseFillOut.setText("PRASHANT");
				}
				if (calc3.getText().compareTo("") == 0){
					lblPleaseFillOut.setText("DIFEQ");
				}
				if (davis.getText().compareTo("") == 0){
					lblPleaseFillOut.setText("DAVIS");
				}
				if ((highSchoolMath.getText().compareTo("") == 0)){
					lblPleaseFillOut.setText("MATH");
				}
				if (highSchoolCalc == null){
					lblPleaseFillOut.setText("CALC");*/
					
			}
			}
			
			
		
		if (e.getSource() == next){
			cl.next(cardPanel);
		}
		if (e.getSource() == btnBeginQuiz){
			
			mytimer.start();
			timeLeft = timeLimit;
			timeLabel.setText(("<html><body style='width: 100px'> <div align='center'>Time left: " + Integer.toString(timeLeft) + "</div>"));
			cl.next(cardPanel);
		}
		if (e.getSource() == nextQuestion){
			
			//Record the data HERE!!!
			myApp.nextQuestion();
			
		}
		//if (e.getSource() == finalQuestion){
		//	
		//	System.exit(1);
		//	
		//}
		
		
	}

	

	public JPanel makeCardPanel(){
		JPanel cardPanel = new JPanel(new CardLayout(0, 0));
		return cardPanel;
		
	}

    private static void initialize_intro_and_other_stuff() {
        //Create and set up the window.
        frame = new JFrame("Math Quiz!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        frame.setSize(525, 600);

        //JLabel emptyLabel = new JLabel("");
        //emptyLabel.setPreferredSize(new Dimension(dimensions.get(0), dimensions.get(1)));
        //frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
 
        //Display the window.
        //frame.pack();
        frame.setVisible(true);

	//Pane stuff.
	myApp = new statsQuiz();
	myApp.initialize_questionlist();
	cardPanel = myApp.makeCardPanel();
	cardPanel.add(myApp.introPane());
	cardPanel.add(myApp.makeQuestionnaire());
	cardPanel.add(myApp.makeInfoPane());
	
	cardPanel.add(myApp.makeQuestion(questions_strings.get(currentQuestion), questions_images.get(currentQuestion)));
	//cardPanel.add(myApp.endOfTest());
	
	omniPanel = new JPanel();
    omniPanel.setLayout(new BorderLayout());
	omniPanel.add(cardPanel, BorderLayout.CENTER);
	frame.setContentPane(omniPanel);
	
	
	frame.setJMenuBar(myApp.createMenuBar());
	
    }
 
   
    
    public static void main (String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initialize_intro_and_other_stuff();
            }
        });
    }
    
    public class ScheduledTask extends TimerTask{
    	public void run(){
    		
    	}
    }

private static String abouttext = "This very nice little quiz was written by TEAM SSSNNNNAAAAAAKKKKKE!!!!\n\nAlso known as: \nDaniel 'Primordial Snake' Eftekhari\nPaul 'Liquid Snake' Xu\nBryon 'Most likely not a Snake' Leung\nWith special thanks to: \nEclipse 'Adder Snake' IDE\nAnd greets to:\nGianluca 'Chameleon Snake' Roberts\n\nFor a course taught by:\nProf. Keith Knight";
private static String introtext = "Welcome to Team SNAKE's Math Quiz statistics evaluation project.\nYou may remember in Calculus class that you were "+
				"not allowed to use calculators for any quiz, test or exam, based on the fact that you do not need them. The purpose " +
				"of this quiz is to ascertain the courses' claims that calculators are unnecessary.\n\n This quiz will take about 3-5 minutes of your "
				+"time, and will focus on basic first-year calculus (Calc I) content.";
private static String questionnaire = "Please answer the following questions.";
private JLabel lblWhatLevel;

private JLabel lblNewLabel;
private JLabel lblNewLabel_3;
private JLabel timeLabel;
private JLabel lblPleaseFillOut = new JLabel("");
private JLabel lblNewLabel_4;

}
