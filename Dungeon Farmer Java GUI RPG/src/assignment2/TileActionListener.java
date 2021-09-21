    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Listener to the tile GUI buttons. Serves as the middleman of all the other listeners
 * @author chris
 */
public class TileActionListener implements ActionListener
{
    Load load;
    Gui gui;
    Save save;
    ArrayList<Object> heroMonsterArrayList;
    Combat combat;
    OutOfCombatAction outOfCombatAction;
    NewGame newGame;
    private int userInput;
    
    public TileActionListener(NewGame newGame, Gui gui, Load load, ArrayList<Object> heroMonsterArrayList, Save save, OutOfCombatAction outOfCombatAction, Combat combat)
    {
        this.combat = combat;
        this.outOfCombatAction = outOfCombatAction;
        this.heroMonsterArrayList = heroMonsterArrayList;
        this.newGame = newGame; 
        this.load = load;
        this.gui = gui;
        this.userInput = 0;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "Move":                 
                try
                {
                    userInput = 1;
                    //get hero, change choiceID, readd to arraylist
                    Hero hero = NewGame.getHero(heroMonsterArrayList);  
                    if (hero.getchoiceID() == 10)
                    {
                        System.exit(0);
                    }
                    hero.setchoiceID(1);
                    NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                    
                    heroMonsterArrayList = outOfCombatAction.Tile(heroMonsterArrayList, combat, userInput);
                    gui.TileGUI(heroMonsterArrayList, userInput, combat);                                    

                } 
                catch (Exception ex)
                {
                }
                break;
            case "View Stats": 
                try
                {
                    userInput = 2; 
                    //get hero, change choiceID, readd to arraylist
                    Hero hero = NewGame.getHero(heroMonsterArrayList);                                         
                    NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                    
                    hero.setchoiceID(2);
                    NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                    heroMonsterArrayList = outOfCombatAction.Tile(heroMonsterArrayList, combat, userInput);                     
                    gui.TileGUI(heroMonsterArrayList, userInput, combat);                                    
                } 
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                break;
            case "View inventory":
                try
                {
                    int index = 0;
                    gui.InventoryGUI(heroMonsterArrayList, index);                                    
                } 
                catch (Exception ex)
                {
                }
                break;
            case "Save":               
                gui.SaveGUI(heroMonsterArrayList); 
                break;
            case "Fight!": //transition to combat GUI
            {
                userInput = -3;
                //get hero, change choiceID, readd to arraylist
                Hero hero = NewGame.getHero(heroMonsterArrayList);                                         
                hero.setchoiceID(1);
                NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                
                heroMonsterArrayList = outOfCombatAction.Tile(heroMonsterArrayList, combat, userInput);
                hero.setchoiceID(0);
                hero = NewGame.getHero(heroMonsterArrayList);
                NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                gui.CombatGUI(heroMonsterArrayList, userInput, combat); 
                break;
            }
            case "OK":
            {
                try
                {
                    userInput = 1;
                    Hero hero = NewGame.getHero(heroMonsterArrayList);
                    hero.setchoiceID(0);
                    NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                    gui.TileGUI(heroMonsterArrayList, userInput, combat); 
                    break;
                }
                catch (Exception ex) 
                {
                }       
            }
            case "Exit game":
            {                
                System.exit(0);
            }
        }      
    }  
}
