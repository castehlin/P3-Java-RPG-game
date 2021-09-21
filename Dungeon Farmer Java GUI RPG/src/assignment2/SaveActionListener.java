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
 * ActionListener that serves as the response to buttons pressed on the save GUI. Connected to tile GUI.
 * Works, but it has no real purpose until loading is implemented at some point.
 * @author chris
 */
public class SaveActionListener implements ActionListener
{
    ArrayList<Object> heroMonsterArrayList;
    Save save;
    private int userInput;
    Gui gui;
    Combat combat;
    
    public SaveActionListener(ArrayList<Object> heroMonsterArrayList, Save save, Gui gui, Combat combat)
    {
        this.combat = combat;
        this.gui = gui;
        this.heroMonsterArrayList = heroMonsterArrayList;
        this.save = save;
        this.userInput = 0;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "Yes":
            {   
                try
                {                    
                    userInput = 1; 
                    
                    //get hero, change choiceID, readd to arraylist
                    Hero hero = NewGame.getHero(heroMonsterArrayList);                                         
                    hero.setchoiceID(1);
                    NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList); 
                    
                    //overwite file
                    save.saveHeroMonsterArrayListToFile(heroMonsterArrayList, userInput);
                    gui.SaveGUI(heroMonsterArrayList); 
                    
                }
                catch (Exception ex)
                {
                }                
            }
                break;
            case "Cycle to next save":
            {
                try
                {
                     userInput = 2; 
                
                    //get hero, change choiceID, readd to arraylist
                    Hero hero = NewGame.getHero(heroMonsterArrayList);                                         
                    hero.setchoiceID(0);
                    NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                    
                    //overwite file
                    save.saveHeroMonsterArrayListToFile(heroMonsterArrayList, userInput);
                    gui.SaveGUI(heroMonsterArrayList);   
                } 
                catch (Exception ex)
                {
                }    
            }
                break;
            case "Return to previous menu":
            {
                try
                {
                    //get hero, change choiceID, readd to arraylist
                    Hero hero = NewGame.getHero(heroMonsterArrayList);                                         
                    hero.setchoiceID(0);
                    NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                    
                    int userInput = 0;
                    gui.TileGUI(heroMonsterArrayList, userInput, combat); //return arrayList  
                }
                catch (Exception ex)
                {
                }
            }
                break;
        }
    }    
}
