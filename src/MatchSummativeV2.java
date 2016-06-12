/**
 * @author Vivian
 *MatchSummative V3
 *January 16th, 2015
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class MatchSummativeV2 extends JFrame implements ActionListener
{	
	//Initialize the double array 4x4 called MatchCard that stores all the MatchCards
	private MatchCard[][] cards = new MatchCard[4][4];

	//Declare a private array called MatchCard to keeps track of the two cards that have been flipped
	private MatchCard[] flippedCards = new MatchCard[2];
	
	//Initialize the private string called color to hold the card contents
	private ArrayList <String> color = new ArrayList();
	
	private ArrayList <Image> farmAnimals = new ArrayList<Image>();

			
	//Declare a private integer variable called numCardsFlipped to ensure that two non-matching cards cannot be faceup for a long period of time
	private int numCardsFlipped;
	
    // Creates counter to count the amount of moves made
    private int counter;
	
	//Declare a timer called delayer for the delay so that the user can see the second card
	private Timer delayer;
	
	//Declare a private long called startTime to determine the duration of the game
	private long startTime;
		
	//Declare a private boolean called gameOver to determine whether the game is over
	private boolean gameOver;
	
    // Start and moves buttons
	private JButton startButton, movesButton;
	
	// Create panels to add to JFRAME
	private JPanel gameboardPanel, buttonPanel; 
	
	// Create drop down menu with different level options
	private JComboBox levelList;
	
	// Create String array to store different level options
	private String[] levels = {"EASY", "MEDIUM", "HARD"};
	

	public MatchSummativeV2()
	{
		super("Memory Game");
	
		//At the beginning, the game is starting so it is yet to be game over
		gameOver = false;
		
		//Initialize the timer that will delay the game so that the user can see the two cards while
		//the computer determines whether they have been matched
		delayer = new Timer(1000, this);
		
		//At the beginning, the number of the cards flipped is 0
		numCardsFlipped = 0;
		
		counter=0;
		
		setSize(500,500);  //Overall size of grid
		setResizable(false);
		
	    // Create new JPanel to store game buttons
		gameboardPanel = new JPanel();
		add(gameboardPanel);
		
	    // Create new JPanel to store administrative buttons
		buttonPanel = new JPanel();
	    buttonPanel.setPreferredSize(new Dimension(400, 40));
		buttonPanel.setBackground(Color.cyan); 
		add(buttonPanel, BorderLayout.SOUTH);

		// Creates drop down menu for different levels
		levelList = new JComboBox(levels);
		levelList.setSelectedIndex(0);
		levelList.addActionListener(this);
		buttonPanel.add(levelList);
		
        // Creates Start/Reset button
		startButton = new JButton("START GAME");
	    startButton.addActionListener(this);
	    buttonPanel.add(startButton);
	    
        // Creates moves button to check the amount of moves made by the player in the game
	    movesButton = new JButton("CHECK MOVES");
	    movesButton.addActionListener(this);
	    buttonPanel.add(movesButton);
	    
		init();
	}
	/**
	 * Initializes the Game
	 */
	public void init()
	{	
		color.add("red");
		color.add("red");
		color.add("purple");
		color.add("purple");
		color.add("blue");
		color.add("blue");
		color.add("pink");
		color.add("pink");
		color.add("grey");
		color.add("grey");
		color.add("green");
		color.add("green");
		color.add("white");
		color.add("white");
		color.add("black");
		color.add("black");

	    Collections.shuffle(color);

		gameboardPanel.removeAll();
		gameboardPanel.setLayout(new GridLayout(4,4,10,10)); // grid with 10 pixel vert/horz dividers
		gameboardPanel.setBackground(Color.cyan); 
				
		for(int i = 0; i <cards.length; i++)
		{
			for(int j = 0; j <cards[0].length; j++)
			{				

				cards[i][j] = new MatchCard("?", color.get(i*4+j));
				cards[i][j].setFont(new java.awt.Font("?", 1, 30));  
				gameboardPanel.add(cards[i][j]);			
				
				System.out.println(cards[i][j].getMatchingWord());
			}

		}	
		
		setVisible(true);     //Turn on JFrame
		repaint();

	} // end init method
		
	public void actionPerformed(ActionEvent e) {
		
		//If the cause of the ActionEvent was a MatchCard which is a click
		if(e.getSource() instanceof MatchCard)
		{
			//Make a temporary variable for the MatchButton since you know it is a MatchButton
			//Once a variable points to the object, you can call its methods and determine 
			//whether it is a match
			
			MatchCard temp = (MatchCard)(e.getSource());
			
			//flip the card
			temp.flip();
			
			//keep track of the flip card
			flippedCards[numCardsFlipped] = temp;
			
			//Make sure the user does not click on the same card twice
			temp.removeActionListener(this);
			
			//The number of cards flipped has increased
			numCardsFlipped++;
						
			//If two cards are currently flipped, check if a match was made
			if(numCardsFlipped == 2)
			{
				//Make sure the user cannot click any other cards when the computer is 
				//determining whether a match is made
				disableAllMatchCards();
				
				//Do a delay so the user can see both cards as the computer computes
				delay();

				//The cards have now been turned over, so no cards have been flipped up
				numCardsFlipped = 0;
				
				counter++;

			}
		}
	
		//If the timer has been started and it has been 1000 ms <- time is set at the beginning
		if(e.getSource() == delayer)
		{
			//tell the timer to stop
			delayer.stop();
			
			//Check if the two flipped cards match each other
			if(flippedCards[0].getMatchingWord().equals(flippedCards[1].getMatchingWord()))
			{								
				//Let both cards know that they have been matched
				flippedCards[0].isMatched();
				flippedCards[1].isMatched();
				
				//check to see if all cards have been matched
				checkAllMatchCards();
			}
			else
			{
				//If the two cards do not match other
				//Flip both cards facedown
				flippedCards[0].flip();
				flippedCards[1].flip();
			}
			
			//Allow the user to select two new cards
			enableAllMatchCards();
		}
		
		if(e.getSource() == startButton)
		{
			//When the user decides to start the game, take the current time stamp in seconds
			startTime = System.currentTimeMillis()/1000;
			
			//Enable all cards to be clickable (add their ActionListeners)
			enableAllMatchCards();
			
			startButton.removeActionListener(this);
		}
		
        // When the 'moves' button is clicked, pops a window telling the player how many moves they've made.
		if (e.getSource()==movesButton)  
		{
			JOptionPane.showMessageDialog(null, "You have made " + counter + " move(s).", "Move Counter", JOptionPane.INFORMATION_MESSAGE);
		}
	}	
		
	/**
	 * This method causes a delay after the user has selected two cards
	 * It also causes the timer to launch an ActionEvent
	 */
	private void delay()
	{
		//Initializing the timer
		delayer.start();
		
		//Only goes when it calls
		delayer.setRepeats(false);
	}
	
	/**
	 * This method allows all cards to be clickable
	 */
	private void enableAllMatchCards()
	{
		for(int i = 0; i <cards.length; i++)
		{
			for(int j = 0; j <cards[0].length; j++)
			{
				cards[i][j].addActionListener(this);;
			}
		}
	}
	
	/**
	 * This methods disables all cards such that when they are clicked
	 * nothing happens.  This is to ensure that only two cards can be
	 * flipped at one time and so that cards cannot be flipped before
	 * the game has started.
	 */
	private void disableAllMatchCards()
	{
		for(int i = 0; i <cards.length; i++)
		{
			for(int j = 0; j <cards[0].length; j++)
			{
				cards[i][j].removeActionListener(this);;
			}
		}
	}
	
	/**
	 * Check to see if all cards have been matched
	 */
	private void checkAllMatchCards()
	{
		for(int i = 0; i <cards.length; i++)
		{
			for(int j = 0; j <cards[0].length; j++)
			{
				if (cards[i][j].getMatchStatus() == false)
				{
					// at least one card is still not matched
					// return so the game can continues
					return;
				}
			}
		}
		
		// All cards are matched
		gameOver = true;
		
		// Get the current end time stamp in seconds
		long endTime = System.currentTimeMillis()/1000;
		
		// Calculate the total time taken
		JOptionPane.showMessageDialog(null, "Congratulations! It took you " + (endTime - startTime) + " seconds and " + counter + " moves to match all the cards!", "You win!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {
		
		new MatchSummativeV2();
	}
}

