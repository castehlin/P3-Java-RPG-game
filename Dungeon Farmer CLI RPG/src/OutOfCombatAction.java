/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

/**
 * Class that controls movement between tiles and the events they lead to. 
 * Going back to old tiles not fully implemented yet. Boss and chest tile not implemented yet.
 * @author chris
 */
public class OutOfCombatAction 
{
    ItemDrops itemDrops; //Class used to randomly pick items from monsters after killing them 
    Inventory inventory;
    private Scanner userDecision; //used anytime users input is needed alongside scanner
    private int savedInput; //used so scanner input can be referenced after scanning
    private int loopNumber; //used for input validation and while loop coverage, might be tidier to make this local instead
    private char checkMovementChoice;
    private String tileChoiceMessage; //used to control message printed by TileChoices method
    private String movementChoice;
    private int tileID; //tracks what tile the hero is on - important for monster distinction for combat
    /*used to check if north, south, west, or east are options in direction via comparison of value of movementChoice against
    checkMovementChoice. If char 0 of movementChoice is n, case 1 of tileChoices is used to go north, if char 1 of movementChoice is
    s, tileChoices is south, if char 2 == w, west and char 3 == e, east.
    */
    
    public OutOfCombatAction()
    {
       this.movementChoice = ""; 
       this.tileChoiceMessage = ""; 
       this.checkMovementChoice = ' ';
       this.userDecision =  new Scanner(System.in);
       this.loopNumber = 0;
       this.tileID = 0;
       inventory = new Inventory();
       itemDrops = new ItemDrops();
    }

    public ArrayList<Object> Tile(Combat combat, ArrayList<Object> heroMonsterArrayList, Save save, int tileIndex, int tileElement, int RevisitedExploredTile) throws IOException 
    {     
        userDecision.useDelimiter("\n"); //ignores newline entry when user enters something in scanned input below
        tileChoiceMessage = "\n1) Move to new location, 2) View stats, 3) View inventory, 4) View equipment, 5) Save, 6) Leave Dungeon";
        
        Hero hero;
        hero = Main.getHero(heroMonsterArrayList); //local hero variable is now value of hero in ArrayList      
        Monster monster;
        //assigned value from ArrayList dependent on switch        
                
        switch (tileIndex) //current index of hero.getTileStatus() or hero.getRevisitExploredTile()
        {
            case 0: //entrance, Tile A0, go north to access Tile B1
                tileID = 0;
                switch (tileElement) //current value of element within current index of hero.getTileStatus()
                {
                    case 0: //if unexplored
                        
                        hero.getTileStatus().set(tileIndex, 2); //mark hero as currently at this tile
                        System.out.println("\nIntro to game, background about how Bob gets to dung...");                                             
                        TileChoices(heroMonsterArrayList, hero, save, tileIndex);                                              
                        //if there were an option on which way to go, it would be implemented here. Only one option in this case though.
                        System.out.println("Realizing he has little choice, Bob goes north, moving deeper into"
                                + " the dungeon...\n");                       
                        hero.getTileStatus().set(0, 1); //marks entrance, Tile A0 as explored and left                                           
                        hero.getTileStatus().set(1, 3); 
                        //sets tileElement 1 to 3, marking the tile user chose to go to as the next destination                         
                        heroMonsterArrayList.set(0, hero); //replace hero in ArrayList with local variable hero
                        
                        return heroMonsterArrayList;
                    case 1: //if Tile explored already
                        
                        hero.getTileStatus().set(tileIndex, 2); 
                        System.out.println("Returned to tile AO, entrance of dungeon...");
                        hero.getTileStatus().set(1, 3);
                        hero.getTileStatus().set(0, 1); //marks entrance, Tile A0 as explored and left
                        TileChoices(heroMonsterArrayList, hero, save, tileIndex);                       
                        System.exit(0);                        
                        System.out.println("Return to tile B1, from tile A0...");
                        heroMonsterArrayList.set(0, hero); 
                        
                        return heroMonsterArrayList;
                    case 2: //if explored, but still here, only triggers on load of save game 
                        //****CALL TILECHOICES, TRIGGER GENERIC PLAYER CHOICE****
                        TileChoices(heroMonsterArrayList, hero, save, tileIndex);
                        hero.getTileStatus().set(0, 1); //marks entrance, Tile A0 as explored and left  
                        hero.getTileStatus().set(1, 3); //marks mob1, Tile B1 as next destination
                        return heroMonsterArrayList;
                }                                               
                break;
            case 1: //mob1, Tile B1, go north to access Tile C2, or south to return to tile A0
                tileID = 1;
                switch (tileElement) 
                {
                    case 0: //if unexplored
                         hero.getTileStatus().set(tileIndex, 2); //mark hero as currently at this tile
                         System.out.println("Bob warily enters the next room of the dungeon, a narrow, but long, dim room only lit by a small "
                                    + "flame burning in front\nof the doorway at the far end of the room.\nLooking around, Bob "
                                    + "doesn't see much else of note. Bob curiously approaches the fire, when suddenly, a figure emerges"
                                    + " from the flame... its a monster!\nThe flaming imp snarls and then lunges at Bob!");
                        
                        monster = Main.getMonster(heroMonsterArrayList, hero, tileID); //assigns appropriate monster to fighting status
                        Main.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList); //adds monster to ArrayList
                        heroMonsterArrayList = combat.MonsterFight(heroMonsterArrayList, itemDrops); //triggers fight between monster and hero in Combat class                                                
                        //goes to case 2
                        break;
                    case 1: //if already explored and returned to
                        System.out.println("Returned to Tile B1...");
                        hero.getTileStatus().set(tileIndex, 2);                         
                        hero.getTileStatus().set(1, 3);
                        hero.getTileStatus().set(0, 1); //marks entrance, Tile A0 as explored and left
                        TileChoices(heroMonsterArrayList, hero, save, tileIndex);
                        System.out.println("Return to tile AO, entrance of dungeon, from Tile B1...");
                        
                        return heroMonsterArrayList;
                    case 2: //if explored, but still here - triggered on load games or going to next location after fight
     
                        TileChoices(heroMonsterArrayList, hero, save, tileIndex);                      
                        //****HERO MOVE OPTIONS: SOUTH (BACK) OR NORTH****                       
                           tileChoiceMessage = "Past the imp's corpse and to the north lies a door leading deeper into the dungeon"
                                   + ". Alternatively, Bob considers turning back and trying to open the dungeon door...\n"
                                   + "1) Go north, 2) Go south (Will let you do this, but game will end if you try and move elsehwere after that)";
                           movementChoice = "ns  "; 
                           TileChoices(heroMonsterArrayList,hero, save, tileIndex); //calls method to display movement options and get input
                           
                           switch(savedInput)
                           {
                                case 10: //north trigger
                                    hero.getTileStatus().set(1, 1); //marks Mob1, Tile B1 as explored and left 
                                    hero.getTileStatus().set(2, 3); //marks Mob2, Tile C2 as the next destination of hero   
                                    break;
                                case 11: //south trigger
                                    hero.getTileStatus().set(1, 1); //marks Mob1, Tile B1 as explored and left
                                    hero.getRevisitExploredTile().set(0, 1); //marks Tile A0 as being revisited
                                   break;
                           } 
                           movementChoice = ""; 
                           heroMonsterArrayList = Main.PutObjectsIntoArrayList(hero, heroMonsterArrayList); 
                                                   
                        return heroMonsterArrayList;
                }              
                break;
            case 2: //Mob2, Tile C2, go left to access Tile E4, go right to access Tile D3, go north to access Tile F5, or south to return to Tile 1B
                tileID = 2;
                switch (tileElement)
                {
                    case 0: //if unexplored
                        hero.getTileStatus().set(tileIndex, 2); //mark hero as currently at this tile
                        System.out.println("Bob opens the door past the imp's corpse, and immediately the smell of sewage\n"
                                + "and rotting flesh engulfs his senses. Grimancing, he walks through the door and takes in his\n"
                                + "surroundings. Bones. Bones everywhere. Human skeletons lay strewn throughout the large room,\n"
                                + " most picked clean from the bone, while others still had badly decaying flesh being nibbled\n"
                                + "upon by the many rats that occupied the room. The rats didn't seem bothered by Bob's presence.\n"
                                + "In the middle of the room, a pile of bones lay, haphazardly stacked as high as Bob is tall\n"
                                + "\"Are these the other chosen sacrifices from \"my village? Others who were in my position?\"\n"
                                + "Bob thought to himself.While thinking upon this matter, suddenly, a variety of growling \n"
                                + "and hissing sounds came from the pile of bones. Preparing himself, Bob readies his pitchfork,\n"
                                + "as the pile of bones suddenly launch in all directions. A rat, larger than any Bob had ever \n"
                                + "seen, lay where the bones once were.At least 3 feet tall, and closer to double than in length,\n"
                                + "the creature is a shocking sight. The rat's red, bloodshot eyes lock onto his own, anger\n"
                                + "visibly radiating from them...the creature starts bounding towards him!"); //formatting needs to be fixed
                        
                        monster = Main.getMonster(heroMonsterArrayList, hero, tileID); //assigns appropriate monster to fighting status
                        Main.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList); //adds monster to ArrayList
                        heroMonsterArrayList = combat.MonsterFight(heroMonsterArrayList, itemDrops); //triggers fight between mob1 and hero in Combat class
                    //****AFTER FIGHT WIN/RETURN FROM COMBAT CLASS****
                        //go to case 2
                        return heroMonsterArrayList;
                    case 1: //if already explored and returned to
                            System.exit(0);
                        break;
                    case 2: //if explored, but still here
                        TileChoices(heroMonsterArrayList, hero, save, tileIndex);
                        
                        //****HERO MOVE OPTIONS: SOUTH (BACK), WEST, EAST OR NORTH*** 
                        tileChoiceMessage = "Multiple options. Where to next?, 1) North (Game ends if you go this way),\n"
                                + " 2) South (back, will let you do this, but game will end after that), 3) West (Game ends if you go this way), 4) East(fight)";
                        movementChoice = "nswe"; //north, south, west and east are all options
                        TileChoices(heroMonsterArrayList, hero, save, tileIndex); 
                        
                        switch(savedInput)
                        {
                            case 10: //north, go fight boss at TileF5, last tile of current version
                                hero.getTileStatus().set(2, 1); //marks Mob2, Tile C2 as explored and left
                                hero.getTileStatus().set(5, 3); //marks Boss1, Tile F5 as the next destination of hero
                                break;
                            case 11: //south, go back to TileB1
                                hero.getTileStatus().set(2, 1); //marks Mob2, Tile C2 as explored and left
                                hero.getRevisitExploredTile().set(1, 1); //marks Tile B1 as being revisited
                                break;
                            case 12: //west, go get chest at TileE4
                                hero.getTileStatus().set(2, 1); //marks Mob2, Tile C2 as explored and left
                                hero.getTileStatus().set(4, 3); //marks Chest1, Tile E4 as the next destination of hero
                                break;
                            case 13: //east, go fight mob at TileD3
                                hero.getTileStatus().set(2, 1); //marks Mob2, Tile C2 as explored and left
                                hero.getTileStatus().set(3, 3); //marks Mob3, Tile D3 as next destination of hero
                                break;                                
                        }
                        movementChoice = ""; //resets value for future iterations
                        heroMonsterArrayList = Main.PutObjectsIntoArrayList(hero, null, heroMonsterArrayList);
                        return heroMonsterArrayList;
                }          
                break;
            case 3: //Mob3, Tile D3, dead end. Go west to return to Tile C2
                tileID = 3;
                switch (tileElement)
                {
                    case 0: //if unexplored
                    {
                        hero.getTileStatus().set(tileIndex, 2); //mark hero as currently at this tile
                        System.out.println("Tile D3, east from C2, Mob3");
                
                        monster = Main.getMonster(heroMonsterArrayList, hero, tileID); //assigns appropriate monster to fighting status
                        Main.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList); //adds monster to ArrayList
                        heroMonsterArrayList = combat.MonsterFight(heroMonsterArrayList, itemDrops); //triggers fight between mob1 and hero in Combat class

                 
                         //****AFTER FIGHT WIN/RETURN FROM COMBAT CLASS****
                         return heroMonsterArrayList; //go to case 2
                    }
                    case 1: //revisit
                    {
                        break;
                    }
                    case 2: //load game or continue from case 1
                    { 
                        TileChoices(heroMonsterArrayList, hero, save, tileIndex);
                        //****HERO MOVE OPTIONS: WEST(BACK)***     
                        System.out.println("Game ends here for now."); 
                        //tileChoices won't be used if there is no choice, so best to print this out than leave as string                      
                        hero.getTileStatus().set(3, 1); //marks Mob3, Tile D3 as explored and left
                        hero.getRevisitExploredTile().set(2, 1); //marks Tile Mob2, Tile C2 as being revisited           
                        return heroMonsterArrayList;
                    }
                }
            case 4: //Chest, Tile E4, dead end. Go east to return to Tile C2
                tileID = 4;
                switch (tileElement)
                {
                    case 0: //if unexplored
                        System.out.println("Not implemented yet!");
                        System.exit(0);
                        break;
                    case 1: //if already explored and returned to
                        break;
                    case 2: //if explored, but still here
                        break;

                }          
                return heroMonsterArrayList;
            case 5: //Boss1, Tile F5, Game ends here.
                tileID = 5;
                System.out.println("Game end, boss and further content not implemented yet.");
                System.exit(0);
                break;
        }                        
        return heroMonsterArrayList;       
    }
    
    private Hero TileChoices(ArrayList<Object> heroMonsterArrayList, Hero hero, Save save, int tileIndex) throws IOException
    {       
        userDecision.useDelimiter("\n"); //ignores newline entry when user enters something in scanned input below
        
        while (loopNumber != 2)
        {
            System.out.println(tileChoiceMessage);              
            loopNumber = 0;
                        
            while (loopNumber != 1)
            {               
                if(userDecision.hasNextInt()) //checks if scanner has int stored or not
                {
                    savedInput = userDecision.nextInt(); 
                    loopNumber = 1; //exits inner loop
                }
                else //user input is invalid, print options again and loop back through if statements again
                {
                    if (savedInput == 1 || savedInput == 2 || savedInput == 3 || savedInput == 4 || savedInput == 5 || savedInput == 6)
                    //if last iteration matched a switch case, meaning it is still in the scanner on top of invalid input
                    {
                        userDecision.nextLine(); //advances scanner to next line, effectively clearing input
                    }
                    userDecision.nextLine(); //advances scanner to next line, effectively clearing input
                    System.out.println("Invalid input, try again."); 
                }   
            }
            
            switch (savedInput)
            {
                case 1:
                    if (!movementChoice.equals("")) //if movementChoice is not an empty string
                    {
                      checkMovementChoice = movementChoice.charAt(0); 
                      if(checkMovementChoice == 'n')
                      {
                          savedInput = 10; //10 == north trigger for movement in tileElement switch above
                      } 
                    }
                    heroMonsterArrayList.set(0, hero); 
                    loopNumber = 2;
                    break;
                case 2:
                    if (!movementChoice.equals("")) //if movementChoice is not an empty string
                    {
                        checkMovementChoice = movementChoice.charAt(1);
                        if (checkMovementChoice == 's') 
                        {
                            savedInput = 11; //11 == south trigger for movement in tileElement switch above
                            loopNumber = 2;
                        }
                    }
                    else
                    {
                        hero.displayAllStatsToPlayer(); //prints out Bob's stat info to player
                    }                  
                    break; 
                case 3:
                    if (!movementChoice.equals("")) //if movementChoice is not an empty string
                    {
                       checkMovementChoice = movementChoice.charAt(2); 
                       if (checkMovementChoice == 'w')
                       {
                          savedInput = 12; //12 == west trigger for movement in tileElement switch above 
                          loopNumber = 2;
                       }
                    }
                    else
                    {
                      inventory.useItemInInventory(hero); //calls method in this class that deals with using items in inventory out of combat  
                    }                         
                    break;
                case 4: 
                    if (!movementChoice.equals("")) //if movementChoice is not an empty string
                    {
                        checkMovementChoice = movementChoice.charAt(3);
                        if (checkMovementChoice == 'e')
                        {
                            savedInput = 13; //13 == east trigger for movement in tileElement switch above 
                            loopNumber = 2;
                        }
                    }
                    else
                    {
                        System.out.println("Equipment seperation from items in inventory not implemented yet. Use item menu.");
                    }
                    break;
                case 5:  
                    heroMonsterArrayList = Main.PutObjectsIntoArrayList(hero, heroMonsterArrayList);
                    save.saveHeroMonsterArrayListToFile(heroMonsterArrayList);                                 
                    break;
                case 6:
                    System.out.println("This will just be flavour text, and should only function on entrance...but not implemented yet.");
                    break;
                default:
                    System.out.println("Invalid input, try again."); 
            }  
        } 
        loopNumber = 0; //reset loopNumber to 0 for future use of this method
        return hero;
    }
}
    

