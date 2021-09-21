/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;
import java.io.IOException;

/**
 * Class that controls what tile, which controls which monster, is being fought. Also has a little text for
 * transition between tiles.
 * @author chris
 */
public class OutOfCombatAction 
{
    private String description; //used to control message printed by TileChoices method
    private int userInput;
    
    public OutOfCombatAction()
    {
       this.description = "Game intro dialogue, there's a passage north, or you could try exiting dungeon."; 
    }
    
    public String getDescription(ArrayList<Object> heroMonsterArrayList, int userInput)
    {
        Hero hero = NewGame.getHero(heroMonsterArrayList); //local hero variable is now value of hero in ArrayList       
        Monster monster = NewGame.getMonster(heroMonsterArrayList, hero, hero.getTileID());
        switch(userInput)
        {
            case -1: //zone transition
            {
                switch(hero.getTileID())
                {
                    case 1: //after first monster fight
                    description = "Imp is dead. Background info about room its in";
                        break;                    
                    case 2: //after second mosnter fight
                     description = "Giant rat is dead. Background info about room its in";   
                        break;
                     case 3: //after second mosnter fight
                      description = "Skeleton archer is dead. Background info about room its in"; 
                      break;
                }                  
            }
            case 1: //move
            {
                switch(hero.getTileID()) 
                {
                    case 0: //dungeon entrance
                    {
                       description = "Game intro dialogue, there's a passage north, or you could try exiting dungeon."; 
                    }  
                    break;
                    case 1: //first monster fight
                    {
                        description = "Imp monster room";                                    
                    }
                    break;
                    case 2: //second monster fight
                    {
                        description = "Giant rat monster room";
                        break;
                    }
                    case 3:
                    {
                        description = "Skeleton monster room";
                    }
                    break;
                }        
            }
                break;
            case 2: //view stats
            {
                description = (hero.getName()+" stats:\nAttack: "+hero.getAttack()+
                "\nDefense: "+hero.getDefense()+"\nHealth: "+hero.getHealth()+"\nLevel: "+hero.getLevel()+
                "\nEXP: "+hero.getExp());

            }
                break;
        }        
        return description;
    }
    
    
    public ArrayList<Object> Tile(ArrayList<Object> heroMonsterArrayList, Combat combat, int userInput)
    {
        Hero hero = NewGame.getHero(heroMonsterArrayList); //local hero variable is now value of hero in ArrayList      
        Monster monster;
        switch(userInput)
        {
            case 1: //if move was chosen
            {            
                switch (hero.getTileID()) 
                {
                    case 0:
                        hero.setTileID(1);
                        break;
                    case 1:
                        hero.setTileID(2);
                        break;
                    case 2:
                        hero.setTileID(3);
                        break;
                    case 3:
                        hero.setchoiceID(10);
                        break;
                    default:
                        break;
                }
            }
        }          
        NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
        return heroMonsterArrayList;
    }

}
    

