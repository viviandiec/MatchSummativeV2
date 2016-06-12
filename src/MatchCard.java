/**
 * @author Vivian
 *MatchSummative V3
 *January 16th, 2015
 */

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MatchCard extends JButton{

	//The matching word (assuming that matching process is done using words)
	private String matchingWord = "";
		
	//Whether a button was pressed and now showing its contents
	private boolean faceUp;
	
	//Whether this button/card has been matched with another one in the game
	private boolean matched;
		
	/**
	 * Constructor of the MatchButton object
	 * @param label This is what it will display.  Same as JButton
	 * @param hiddenWord This is the string that will determine whether it matches with another card
	 */
	public MatchCard(String label, String matchingWord)
	{
		//Given the string label to the parent constructor
		super(label);

		//Assign the given string to the MatchButton instance so it knows it's matching word
		this.matchingWord = matchingWord;
				
		faceUp = false;
		matched = false;		
	}
	
	/**
	 * Constructor of the MatchButton object
	 * @param label This is what it will display.  Same as JButton
	 * @param hiddenWord This is the string that will determine whether it matches with another card
	 * @param image The image for the card
	 */
	public MatchCard(String label, String matchingWord, ImageIcon image)
	{
		//Given the string label to the parent constructor
		super();
//		super(label);

		//Assign the given string to the MatchButton instance so it knows it's matching word
		this.matchingWord = matchingWord;
				
		faceUp = false;
		matched = false;		
	}
	
	public String getMatchingWord()
	{
		return matchingWord;
	}
	
	public void setMatchingWord(String newWord)
	{
		matchingWord = newWord;
	}
	
	public boolean isFaceUp()
	{
		return faceUp;
	}
	
	public void flip()
	{
		if(!matched) //Don’t want to change anything if the card has already been matched
		{
			//If it was face up
			if(faceUp)
			{
				//Change the text back to a ?
				super.setText("?");
			}
			else	//If it was face down
			{
				//Show the word
				super.setText(matchingWord);
			}
			
			//Reverse it's status
			//true -> false, false -> true
			faceUp = !faceUp;
		}
	}
	
	public boolean getMatchStatus()
	{
		return matched;
	}
	
	public void isMatched()
	{
		matched = true;
	}
}

