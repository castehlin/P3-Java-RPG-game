/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chris
 */
public class GameTest {
    
    public GameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

                
    /**
     * Test of AddStringToString method, of class Gui.
     * Test 1, tests if button names are updated
     */
    @Test
    public void testUpdateButtonText()
    {
        //instantiation of 3 buttons with text, represents buttons from a previous event
        JButton[] buttonsList = new JButton[3];
        buttonsList[0] = new JButton("OldButton 1");
        buttonsList[1] = new JButton("OldButton 2");
        buttonsList[2] = new JButton("OldButton 3");
       
        //buttons to replace the 5 old buttons with the 3 below strings in the array
        String[] newButtonNamesTest = {"NewButton1", "NewButton2", "NewButton3"}; 
        
        //remove existing button names from previous iteration
        for (int k = 0; k < buttonsList.length; ++k)
        {
            buttonsList[k].setText("");
        }
        
        //add appropriate button name to buttons
        for (int j = 0; j < newButtonNamesTest.length; ++j) 
        {
            for(int i = 0; i < newButtonNamesTest.length; ++i)
            {
                if(buttonsList[i].getText().equals("")) //if button text is ""
                {
                    buttonsList[i].setText(newButtonNamesTest[i]); 
                    break;              
                }
            }           
        }
        
        for(int i = 0; i < newButtonNamesTest.length; ++i)
        {
            for(int j = 0; j < buttonsList.length; ++j)
            {
               if (!buttonsList[i].getText().equals(newButtonNamesTest[i]))
               {
                   fail();
               }
            }
        }

        System.out.println("Test 1, testUpdateButtonText() passed\n");
    }
    
     /**
     * Test of AddStringToString method, of class NewGame.
     * Test 2, tests if actionListener was removed successfully
     */
    @Test
    public void testRemoveActionListener()
    {
        JButton button = new JButton("Test");
        button.addActionListener((ActionEvent e) -> 
                {
                    switch(e.getActionCommand())
                    {
                        case "Test":
                        {
                            System.out.println("Old actionListener execution...");                           
                        }
                    }                    
                });
        
        button.doClick(); 
        
        //create button array, add button above to array
        JButton[] buttonsList = new JButton[1];
        for(int i = 0; i < buttonsList.length; i++)
        {
            buttonsList[i] = button;           
        }      
        
  
        //remove the actionListener of the button using this, requires it to be an array/list
        for (JButton currentButton : buttonsList)
        {
            for (ActionListener al : currentButton.getActionListeners())
            {
                currentButton.removeActionListener(al);
            }          
        }
        
        button.addActionListener((ActionEvent e) ->
                {
                    switch(e.getActionCommand())
                    {
                        case "Test":
                        {
                            System.out.println("Same button, new actionListener...");
                            System.out.println("Test 2, TestRemoveActionListener() passed\n"); 
                        }
                    }                    
                });
        
        button.doClick(); 
        
        
    }  
    
    /**
     * Test of AddStringToString method, of class NewGame.
     * Test 3, tests if actionListener was added and executed successfully
     */
    @Test
    public void testAddActionListener()
    {
        JButton button = new JButton("Test");
        button.addActionListener((ActionEvent e) -> 
                {
                    switch(e.getActionCommand())
                    {
                        case "Test":
                        {
                            System.out.println("Test 3, TestAddActionListener() passed\n");                           
                        }
                    }                    
                });
        
        button.doClick();      
    }            

    /**
     * Test of AddStringToString method, of class Gui.
     * Test 4, tests if button name/visibility is updated   
     */
    @Test
    public void testUpdateButtonVisiblity()
    {
        JButton[] buttonsList = new JButton[3];
        buttonsList[0] = new JButton("Test1");  
        buttonsList[1] = new JButton("OldButton 2"); 
        buttonsList[2] = new JButton(""); 
        
        //hides any buttons that aren't in use for the moment
        for (int i = 0; i < buttonsList.length; ++i)
        {
           if(buttonsList[i].getText().equals(""))
           {
              buttonsList[i].setVisible(false);
           }               
        }        

        //is the button invisible?
        if (!buttonsList[0].isVisible()) //isn't empty string, so should be visible
        {
            fail();
        }
        //is the button invisible?
        if (!buttonsList[1].isVisible())  //isn't empty string, so shouldn't be invisible
        {
            fail();
        }
        //is the button invisible?
        if (!buttonsList[2].isVisible())
        {
            System.out.println("Test 4, testupdateButtonVisiblity() passed\n");                     
        }       
    }
    
    /**
     * Test of AddStringToString method, of class Gui.
     * Test 5, tests if concat works for combining two strings, to be used for
     * combining multiple descriptions to be set as the Jtextpane
     */
    @Test
    public void testAddStringToString()
    {
        String stringToBeAddedTo = "Original string";
        System.out.println(stringToBeAddedTo);
        String stringToAddToOtherString = "Additional string";
        System.out.println(stringToAddToOtherString);
        String newString = stringToBeAddedTo.concat(" "+stringToAddToOtherString);
        assertTrue(newString.equals("Original string Additional string"));
        System.out.println(newString);
        System.out.println("Test 5, testAddStringToString() passed\n");       
    }
    
}
