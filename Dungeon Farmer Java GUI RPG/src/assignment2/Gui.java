/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 * The main class of the project, and probably a close tie for the messiest (with Combat). This class contains
 * all the button and action listener instantiations. The same 9 buttons and listeners for each button
 * are reused time a different gui needs to be accessed.
 * @author chris
 */
public class Gui
{
    private JButton[] buttonsList;
    private JTextPane textPane;
    private int tileID;
    private String description = "";
    JPanel panel = new JPanel(); 
    //JPanel panel2 = new JPanel();
    JFrame frame = new JFrame("Dungeon Farmer");
    TileActionListener tileActionListener;
    MainActionListener mainActionListener;
    LoadActionListener loadActionListener;
    InventoryActionListener inventoryActionListener;
    SaveActionListener saveActionListener;
    CombatActionListener combatActionListener;
    
    Load load = new Load();             
    OutOfCombatAction outOfCombatAction = new OutOfCombatAction();        
    Save save = new Save();
    Inventory inventory = new Inventory();
    Combat combat = new Combat(-1, -1, 1); //Class that contains methods dedicated to fights between mobs/bosses and the hero.    
    ArrayList<Object> heroMonsterArrayList;
    NewGame newGame;    
    
    public Gui(NewGame newGame)
    {
        this.newGame = newGame;
        
        try
        {
            heroMonsterArrayList = newGame.GameBegin(outOfCombatAction);      
        } 
        catch (Exception e)
        {
        }
        
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null); //puts frame in centre of screen, provided frame has had size set first
        
        textPane = new JTextPane(); //information before buttons/options in GUI from method that calls this class
        textPane.setText("");
        textPane.setPreferredSize(new Dimension(371, 180));
        textPane.setEditable(false);
        panel.add(textPane);
        
        buttonsList = new JButton[9];

        for(int i = 0; i < buttonsList.length; i++)
        {
            buttonsList[i] = new JButton("");
            panel.add(buttonsList[i]);
            panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        }
        frame.setVisible(true); 
    }           

    
    //Controls what GUI buttons display and calls the appropriate actionListener class to cover events when they're pressed    
    public void MainGUI(Gui gui)
    {
        description = "Welcome to 'Dungeon Farmer'. Please click a button below."; 
        textPane.setText(description);
        String[] mainButtonNames = {"New Game", "Load Game", "Exit Game"};
        
        buttonsList = UpdateButtonNameOrVisibility(buttonsList, mainButtonNames);
        RemoveActionListeners(buttonsList);
        
        mainActionListener = new MainActionListener(heroMonsterArrayList, gui, load, 0, save, outOfCombatAction, combat);
        for(int i = 0; i < buttonsList.length; i++)
        {
            buttonsList[i].addActionListener(mainActionListener);
        }
    }
    
    public void TileGUI(ArrayList<Object> heroMonsterArrayList, int userInput, Combat combat) throws IOException
    {
       Hero hero = NewGame.getHero(heroMonsterArrayList); //get hero
       String[] tileButtonNames = {};
       tileActionListener = new TileActionListener(newGame, this, load, heroMonsterArrayList, save, outOfCombatAction, combat);    
            
        switch(hero.getchoiceID())
        {
            case 1: 
            {
                tileButtonNames = new String[] {"Fight!"};
            }
                break;
            case 2: //if user viewed stats
            {
                tileButtonNames = new String[] {"OK"};
            }
                break;
            case 10: //end of game
            {
                tileButtonNames = new String[] {"Exit game"};  
                break;
            }
              
            default: //default
            {
                tileButtonNames = new String[] {"Move", "View Stats", "View inventory",
                    "Save"};
            }
                break;       
        }   
 
       // newGame.TileCheck(heroMonsterArrayList, save, outOfCombatAction, combat);
        if(hero.getchoiceID() != 10)
        {
          description = outOfCombatAction.getDescription(heroMonsterArrayList, userInput);   
        }
        else
        {
           description = "End of current version";
        }       
        textPane.setText(description); 
        buttonsList = UpdateButtonNameOrVisibility(buttonsList, tileButtonNames);
        RemoveActionListeners(buttonsList);
       
        for(int i = 0; i < buttonsList.length; i++)
        {
            buttonsList[i].addActionListener(tileActionListener);
        }     
    }
    
    public void InventoryGUI(ArrayList<Object> heroMonsterArrayList, int index) throws IOException
    {       
       Hero hero = newGame.getHero(heroMonsterArrayList);
       String[] inventoryButtonNames = {};
       
        if (index  == hero.getItemSlots().size())
        {
            index = 0;
        }
        
       Item itemSlot = hero.getItemSlots().get(index);      
 
        switch (itemSlot.getItemType())
        {
            case NO_COMBAT_ITEM:
            case ANYTIME_ITEM:
            {
                inventoryButtonNames = new String[] {"Use item", "Cycle to next item", "Stop viewing inventory"};  
                break;
            }
            case UNEQUIPPED_ARMOR:
            case UNEQUIPPED_WEAPON:
            {
                inventoryButtonNames = new String[] {"Equip item", "Cycle to next item", "Stop viewing inventory"}; 
                break;
            }
            case EQUIPPED_ARMOR:  
            case EQUIPPED_WEAPON:    
            {
                inventoryButtonNames = new String[] {"Unequip item", "Cycle to next item", "Stop viewing inventory"}; 
                break;
            }
            default: //inventory empty
            {
                inventoryButtonNames = new String[] {"Stop viewing inventory"};  
                break;
            }
        }
                      
        inventoryActionListener = new InventoryActionListener(inventory, this, heroMonsterArrayList, index, combat);
        
        if (inventory.getUserInput() == 0) //examine item, gets description
        {
            inventory.useItemInInventory(hero, index); 
        }  
        
        description = inventory.getDescription(); 
        textPane.setText(description); 
        buttonsList = UpdateButtonNameOrVisibility(buttonsList, inventoryButtonNames);
        RemoveActionListeners(buttonsList);
       
        for(int i = 0; i < buttonsList.length; i++)
        {
            buttonsList[i].addActionListener(inventoryActionListener);
        }     
    }
        
    //Controls what GUI buttons display and calls the appropriate actionListener class to cover events when they're pressed  
    public void CombatGUI(ArrayList<Object> heroMonsterArrayList, int userInput, Combat combat)
    {     
        Hero hero = NewGame.getHero(heroMonsterArrayList); //get hero
        String[] combatButtonNames = {}; 
            
        switch(hero.getchoiceID())
        {
            case 0: //fight
            {
              combatButtonNames = new String[] {"Attack", "Defend", "Skill", "Item", "Technically Multithreading"};
              break;
            }
            case 1: //skill submenu
            {
               combatButtonNames = new String[] {"Pitchfork Piston Poke", "Haystorm", "Back"};
               break;
            }
            case 2: //item submenu
            {
               //not implemented 
               break;
            }
            case 3: //dead hero
            {
               combatButtonNames = new String[] {"Game over..."};
               break;
            }
            case 4: //confirmation of turn actions, switch to hero/player action
            {
                 combatButtonNames = new String[] {"OK"};
                 break;
            }
            case 5: //dead monster
            {
               combatButtonNames = new String[] {"Victory!"};
               break;
            }               
        }
                
                  
        combatActionListener = new CombatActionListener(heroMonsterArrayList, this, combat, userInput, save);
        if(userInput != -4)
        {
            description = combat.getDescription(heroMonsterArrayList, userInput);   
        }
        
        textPane.setText(description); 
        buttonsList = UpdateButtonNameOrVisibility(buttonsList, combatButtonNames);
        RemoveActionListeners(buttonsList);
        
        for(int i = 0; i < buttonsList.length; i++)
        {
            buttonsList[i].addActionListener(combatActionListener);
        }  
    }
    
    public void SaveGUI(ArrayList<Object> heroMonsterArrayList)
    {
        Hero hero = NewGame.getHero(heroMonsterArrayList); //get hero
        String[] saveButtonNames = {};
        
        switch(hero.getchoiceID())
        {
            case 0: //default
            {
                saveButtonNames = new String[] {"Yes", "Cycle to next save", "Return to previous menu"};
            }
            break;
            case 1: //If user saved
            {
                saveButtonNames = new String[] {"Cycle to next save", "Return to previous menu"};
                hero.setchoiceID(0); //reset to no choice
            }
            break;       
        }
        NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList); //readd hero to arrayList
        
        //update buttons name and which are visible or not
        buttonsList = UpdateButtonNameOrVisibility(buttonsList, saveButtonNames);
        
        //remove old actionListeners from buttons
        buttonsList = RemoveActionListeners(buttonsList);
        
        //add actionListeners to buttons
        saveActionListener = new SaveActionListener(heroMonsterArrayList, save, this, combat);
        
        //get description and add to textPane
        description = save.getDescription();
        textPane.setText(description);
        
        for(int i = 0; i < buttonsList.length; i++)
        {
            buttonsList[i].addActionListener(saveActionListener);
        }  
    }
    
    public void LoadGUI(Load load)
    {
        description = load.getDescription();
        textPane.setText(description);
        String[] loadButtonNames = {"Load this File", "Cycle to next save slot", "Exit Game"};
        
        
        //update buttons name and which are visible or not
        buttonsList = UpdateButtonNameOrVisibility(buttonsList, loadButtonNames);
        
        //remove old actionListeners from buttons
        buttonsList = RemoveActionListeners(buttonsList);
        
        //add actionListeners to buttons
        loadActionListener = new LoadActionListener(this, buttonsList, description, load);
        for(int i = 0; i < buttonsList.length; i++)
        {
            buttonsList[i].addActionListener(loadActionListener);
        }  
    }
    
    public JButton[] UpdateButtonNameOrVisibility(JButton[] bList, String[] buttonNames)
    {       
        //remove existing button names from previous iteration
        for (int k = 0; k < bList.length; ++k)
        {
            bList[k].setText("");
        }
        
        //add appropriate button name to buttons
        for (int j = 0; j < buttonNames.length; ++j) 
        {
            for(int i = 0; i < bList.length; ++i)
            {
                if(bList[i].getText().equals("")) //if button text is ""
                {
                    bList[i].setVisible(true); //if button is invisible, make it visible
                    bList[i].setText(buttonNames[i]); 
                    break;              
                }
            }           
        }
        
        //hides any buttons that aren't in use for the moment
        for (int i = 0; i < bList.length; ++i)
        {
           if(bList[i].getText().equals(""))
           {
              bList[i].setVisible(false);
           }               
        }      

        return bList;
    }
 
    public JButton[] RemoveActionListeners(JButton[] buttonsList)
    {
        for (JButton currentButton : buttonsList)
        {
            for (ActionListener al : currentButton.getActionListeners())
            {
                currentButton.removeActionListener(al);
            }          
        }        
        return buttonsList;
    }  
    
    //used to combine multiple descriptions into one description to be sent as one message in gui 
    public static String AddStringToString(String stringToBeAddedTo, String stringToAddToOtherString)
    {
        String newCombinedString = stringToBeAddedTo.concat(" "+stringToAddToOtherString);
        return newCombinedString; 
    }
    
    public void ChangePanelColour() //random!!
    {
        Random rand = new Random();          
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        
        Color randomColor = new Color(r,g,b);
       panel.setBackground(randomColor);

    }
   
}               

