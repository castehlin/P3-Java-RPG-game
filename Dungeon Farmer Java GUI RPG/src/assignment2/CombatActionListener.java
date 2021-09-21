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
 * Class that handles all events caused by buttons pressed in the CombatGUI. Messy class.
 * @author chris
 */
public class CombatActionListener implements ActionListener
{
    ArrayList<Object> heroMonsterArrayList;
    private int userInput;
    Gui gui;
    Combat combat;
    Save save;
    
    public CombatActionListener(ArrayList<Object> heroMonsterArrayList, Gui gui, Combat combat, int userInput, Save save)
    {
        this.combat = combat;
        this.gui = gui;
        this.heroMonsterArrayList = heroMonsterArrayList;
        this.userInput = userInput;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Hero hero = NewGame.getHero(heroMonsterArrayList);
        
        Monster monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);
        //monster marked with attacking status taken out of ArrayList
        
        switch(e.getActionCommand())
        {
            case "Attack":
            {
                userInput = 0;
                heroMonsterArrayList = combat.Fight(heroMonsterArrayList, userInput);
                monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);
                               
                if(monster.getHealth() <= 0) //monster death
                {                
                   hero.setchoiceID(5);
                   heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
                   gui.CombatGUI(heroMonsterArrayList, userInput, combat);        
                }
                else
                {
                    hero.setchoiceID(4);  //swap to monster turn
                    heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
                    gui.CombatGUI(heroMonsterArrayList, userInput, combat); 
                }
                break;                
            }
            case "Defend":
            {
                userInput = 1;
                heroMonsterArrayList = combat.Fight(heroMonsterArrayList, userInput);
                monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);
                
                if(monster.getHealth() <= 0) //monster death
                {                
                   hero.setchoiceID(5);
                   heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
                   gui.CombatGUI(heroMonsterArrayList, userInput, combat);        
                }
                else
                {
                    hero.setchoiceID(4);  //swap to monster turn
                    heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
                    gui.CombatGUI(heroMonsterArrayList, userInput, combat); 
                } 
                break;
            } 
            case "Skill":
            {
                userInput = -3;
                hero.setchoiceID(1);        
                gui.CombatGUI(heroMonsterArrayList, userInput, combat); 
                break;
            }
            case "Pitchfork Piston Poke":
            {
                userInput = 3;
                heroMonsterArrayList = combat.Fight(heroMonsterArrayList, userInput);
                monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);
                
                if(monster.getHealth() <= 0) //monster death
                {     
                   userInput = 0;
                   hero.setchoiceID(5);
                   heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
                   gui.CombatGUI(heroMonsterArrayList, userInput, combat);        
                }
                else
                {
                    hero.setchoiceID(4);  //swap to monster turn
                    heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
                    gui.CombatGUI(heroMonsterArrayList, userInput, combat); 
                    break;
                }               
                
                for(Skill skill: hero.getSkillSet())
                {
                   if (skill.getName().equals("Pitchfork Piston Poke") && skill.getIsLearned() == true)
                   {
                       if (monster.getHealth() > 0)
                       {
                         hero.setchoiceID(4);  //swap to monster turn  
                       }                       
                   }
                   else
                   {
                       if (monster.getHealth() > 0)
                       {
                           hero.setchoiceID(0);
                       }

                       
                   }
                }
                gui.CombatGUI(heroMonsterArrayList, userInput, combat);               
                break;
            }
            case "Haystorm":
            {
                userInput = 4;
                heroMonsterArrayList = combat.Fight(heroMonsterArrayList, userInput);
                
                for(Skill skill: hero.getSkillSet())
                {
                   if (skill.getName().equals("Haystorm") && skill.getIsLearned() == true)
                   {
                       hero.setchoiceID(4);  //swap to monster turn
                   }
                   else
                   {
                      hero.setchoiceID(0);  
                   }
                }
                gui.CombatGUI(heroMonsterArrayList, userInput, combat);               
                break; 
            }
            case "Back":
            {
                userInput = -4;
              hero.setchoiceID(0);  
              gui.CombatGUI(heroMonsterArrayList, userInput, combat);
              break;
            }
            case "Item":
            {
                userInput = 11;
                hero.setchoiceID(4);  //swap to monster turn
                combat.getDescription(heroMonsterArrayList, userInput);
                gui.CombatGUI(heroMonsterArrayList, userInput, combat); 
                break;
            }
            case "Run":
            {  
                userInput = 12;
                combat.getDescription(heroMonsterArrayList, userInput);
                hero.setchoiceID(4);  //swap to monster turn
                gui.CombatGUI(heroMonsterArrayList, userInput, combat); 
                break;
            }
            case "OK":
            {       
                
                if (userInput == -2) //after monster death, get loot/exp and transition to next zone
                { 
                    userInput = -1;
                    NewGame.getHero(heroMonsterArrayList);
                    hero.setchoiceID(0);                   
                    monster.setMonsterStatus(2); //monster set to dead    
                    heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
                    try
                    {                        
                        gui.TileGUI(heroMonsterArrayList, userInput, combat);
                    } 
                    catch (Exception ex)
                    {                       
                    }
                    break;
                }
                
                userInput = 5;
                heroMonsterArrayList = combat.Fight(heroMonsterArrayList, userInput);
                if (hero.getHealth() <= 0) //hero death check
                {
                    userInput = -1;
                    hero.setchoiceID(3); //hero is dead
                    heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
                    gui.CombatGUI(heroMonsterArrayList, userInput, combat); //game over screen
                    break;
                }                   
                hero = NewGame.getHero(heroMonsterArrayList);
                hero.setchoiceID(0);  
                heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);                 
                gui.CombatGUI(heroMonsterArrayList, userInput, combat); //return to combat gui
            }
                break;           
            case "Victory!":
            {                
                userInput = -2;
                hero.setchoiceID(4);  //calls OK button that will transition to tileGUI at new location               
                heroMonsterArrayList = MonsterDeath(heroMonsterArrayList, userInput);
                gui.CombatGUI(heroMonsterArrayList, userInput, combat); //return to combat gui     
                break;
            }
            case "Game over...":
            {
                System.exit(0);
                break;
            }
            case "Technically Multithreading":
            {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() 
                {
                    gui.ChangePanelColour();
                }
                    }); 
            }
        }  
    }
    
    private ArrayList<Object> MonsterDeath(ArrayList<Object> heroMonsterArrayList, int userInput)
    {       
        combat.getDescription(heroMonsterArrayList, userInput);
        heroMonsterArrayList = combat.Fight(heroMonsterArrayList, userInput); 
  
        return heroMonsterArrayList;
    }
    
}
