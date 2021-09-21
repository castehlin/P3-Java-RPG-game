/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Class that executes program and instantiates most classes. 
 * @author chris
 */
public class Main
{
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        String userInput; 
        boolean inputValidation = false;
        Scanner scanner = new Scanner(System.in);
        //ArrayList <Object> heroMonsterArrayList = new ArrayList<>(); 
        //used to store monsters + hero so they can be passed as a parameter to a single method      
        Load load = new Load(); //Load Class instantiated 
        
        //Game start menu, also offers ability to load or close program
        while(inputValidation != true) 
        /*
           Runs forever and never breaks unless user types 3 for exit. If hero dies 
            or game ends in some other fashion, they should return to this loop, 
            which serves as the main menu.         
        */

        {
            System.out.println("Welcome to 'Dungeon Farmer'.\nEnter '1' to start a new game,"
                + "'2' to load an existing saved game, or '3' to exit.");
            userInput = scanner.next(); //get user input for if statements below
            try
            {                 
                switch (userInput)
                {
                //new game
                    case "1":
                          ArrayList <Object> heroMonsterArrayList = new ArrayList<>(); 
                         //array used to store hero and monster objects. To be passed through as an argument to multiple methods                                             

                        //****HERO/MONSTER CLASS INSTANTIATION SECTION****                       
                            Hero hero = new Hero("Bob the Farmer", 100, 100, null, 15, 15, 5, 5, 0, 0, new <Item> ArrayList(10));                                                     
                        heroMonsterArrayList.add(hero); //hero added to arraylist
                          
                        GameBegin(heroMonsterArrayList);
                        break;
                //load save
                    case "2":                                                                     
                        heroMonsterArrayList = load.ReadHeroMonsterArrayListFromFile(); //local object hero set to loaded hero object                          
                        GameBegin(heroMonsterArrayList);                       
                        break;
                //exit
                    case "3":
                        inputValidation = true; //exits loop and ends program
                        break;
                    default:
                        throw new InputMismatchException();
                }
            }
            catch(InputMismatchException exception) //coming back to this hours later, throwing exceptions for wrong input is probably stupid
            {
               System.out.println("Invalid input.\n");
            }          
        }
    }
    
    private static void GameBegin(ArrayList<Object> heroMonsterArrayList) throws IOException
    {
       Hero hero = getHero(heroMonsterArrayList); 
       
       Save save = new Save(); //instantiates the Save class allowing saving at key locations found in methods in OutOfCombatAction
       
       OutOfCombatAction outOfCombatAction = new OutOfCombatAction();
       //Class that contains methods dedicated to map/tile navigation by calling methods in the OutOfCombatAction class
        
       Combat combat = new Combat(-1, -1, 1); //Class that contains methods dedicated to fights between mobs/bosses and the hero.
               
       ItemList itemList = new ItemList(hero); 
        /*
         Class that contains all the loot tables that monster's save to an array, requires hero object to provide item effect
         argument directly to hero object.   
       */
        
        SkillSets skillsets = new SkillSets();
        //class that assigns monsters and hero their skills                   
      
       //****ASSIGN <NEW GAME> HERO STARTER GEAR AND <NEW GAME> HERO AND MOBS ADDED TO ARRAYLIST AND THEN GIVEN THEIR SKILLS****
        if(hero.getTileStatus().get(0) == 0)
        //if hero hasn't explored very first tile, only possible on new game. Prevents loaded saves triggering this
        {          
           //Starting equipment/items for Bob the Farmer
           hero.addItem(new Item(ItemType.ANYTIME_ITEM, "Healing potion - heals 25% HP.", hero.getMaxHealth() / 4, 0, "Bob drinks the"
                + " health potion and recovers some health.", ItemType.NONE, "Healing potion"));
           hero.addItem(new Item(ItemType.EQUIPPED_ARMOR, "Simple Farmers's Clothing - a tattered pair of pants and a ragged long "
                + "sleeved shirt, both of which have seen better days.\nIncreases defense by 5.", 5, 0, "", ItemType.DEF, "Simple Farmers's Clothing"));           
           hero.addItem(new Item(ItemType.EQUIPPED_WEAPON, "Blunt Pitchfork - A pitchfork that has a sharpness closer to a spoon than a fork."
                + "\nIncreases attack by 5.", 5, 0, "", ItemType.ATK, "Blunt Pitchfork"));
           hero.setMaxAttack(hero.getAttack() + 5);
           hero.setAttack(hero.getMaxAttack());
           hero.setMaxDefense(hero.getDefense() + 5); 
           hero.setDefense(hero.getMaxDefense());
           /*
             Set attack and defense to match armor benefits...this is done automatically in the class that handles equipping 
             items after game starts, just isn't done before we get there, so this is needed. 
           */
           
            //****MONSTER OBJECT CREATION****
            Monster mob1TileB = new Monster("Imp", 75, 75, null, 0, 0, itemList.getFloorOneMobItemList(), MonsterID.MOB_ONE, 50, 0, 0); 
            Monster mob2TileC = new Monster("Giant Rat", 150, 150, null, 0, 0, itemList.getFloorOneMobItemList(), MonsterID.MOB_TWO, 65, 0, 0);
            Monster mob3TileD = new Monster("Skeleton Archer", 175, 175, null, 0, 0, itemList.getFloorOneMobItemList(), MonsterID.MOB_THREE, 100, 0, 0); 
           // Monster chest1TileE = new Monster("Chest", 0, 0, null, 0, 0, itemList.getFloorChestItemList(), MonsterID.CHEST, 0, 0, 0);
            //Monster boss1TileF = new Monster("Damaged Stone Golem", 350, 700, null, 0, 0, itemList.getFloorOneBossItemList(), MonsterID.BOSS_ONE, 0, 0, 0); //not implemented yet
                                
           PutObjectsIntoArrayList(hero, heroMonsterArrayList);
           heroMonsterArrayList.add(mob1TileB); 
           heroMonsterArrayList.add(mob2TileC);
           heroMonsterArrayList.add(mob3TileD);
           heroMonsterArrayList = skillsets.assignSkillsToHeroAndMonsters(heroMonsterArrayList); 
           //arraylist passed to skillsets to assign skills to hero and mobs                     
       }
        
        //****<NEW GAME> HERO AND MOBS OR <LOAD GAME> HERO AND MOBS ARE NOW IN ARRAYLIST, READY TO BEGIN GAME                         
           while (true)
           {
               hero = getHero(heroMonsterArrayList);
               /*
                 After one instance of outOfCombatAction.Tile running, this is updated with the arrayList's new hero definition
                 to keep hero.getHasExploredTile() accurate down there.
               */              
               
               /*
                ***ITERATE THROUGH TILES EXPLORED. IF TILE 0 == 0, START AT FIRST TILE AS IT IS NEW GAME. IF TILE i == 2, 
                THAT IS <LOAD GAME> HERO LOCATION, START FROM THERE.
               */
               for (int i = 0; i < hero.getTileStatus().size(); ++i)
               {                  
                   
                   if (hero.getTileStatus().get(i) == 1 && hero.getRevisitExploredTile().get(i) == 1) 
                   //will be true if hero revists tile they have been to before
                   {
                       int index = hero.getRevisitExploredTile().indexOf(1); 
                       //only one element in getRevisitExploredTile should be 1 if this if statement triggers 
                       
                        heroMonsterArrayList = outOfCombatAction.Tile(combat, heroMonsterArrayList, save, index, hero.getTileStatus().get(i),
                        hero.getRevisitExploredTile().get(i)); 
                        break;
                   }
                   else if(hero.getTileStatus().get(0) == 0) //will trigger on new game
                   {                      
                        heroMonsterArrayList = outOfCombatAction.Tile(combat, heroMonsterArrayList, save, 0, hero.getTileStatus().get(i),
                        hero.getRevisitExploredTile().get(i));
                        break;
                   }
                   else if (hero.getTileStatus().get(i) == 3) //will be true if user selected to move to unexplored tile at their last/current tile
                   {
                        int index = hero.getTileStatus().indexOf(3); //only one element should be 3, giving us the index
                        hero.getTileStatus().set(i, 0); //sets element to 0 to allow first time event at chosen tile
                        heroMonsterArrayList = outOfCombatAction.Tile(combat, heroMonsterArrayList, save, index, hero.getTileStatus().get(i),
                        hero.getRevisitExploredTile().get(i));
                        break;
                   }
                   else if (hero.getTileStatus().get(i) == 2) //will only run on load of save game, takes you to tile where game was saved                 
                   {
                        int index = hero.getTileStatus().indexOf(2); //only one element should be 2, giving us the index
                        heroMonsterArrayList = outOfCombatAction.Tile(combat, heroMonsterArrayList, save, index, hero.getTileStatus().get(i),
                        hero.getRevisitExploredTile().get(i));
                        break;
                   }           
               }              
           }              
    }
    
    public static ArrayList<Object> PutObjectsIntoArrayList(Hero hero, Monster monster, ArrayList<Object> heroMonsterArrayList)
    { //currently always adds hero and only adds monster if its fighting and has died.
        for (int i = 0; i < heroMonsterArrayList.size(); ++i) 
        {
            if (heroMonsterArrayList.get(i) instanceof Hero)
            {
                heroMonsterArrayList.set(i, hero); //replace hero in ArrayList with local hero data
            }
            if (heroMonsterArrayList.get(i) instanceof Monster)
            {
                Monster tempMonster = (Monster) heroMonsterArrayList.get(i);
                if (tempMonster.getMonsterStatus() == 1) 
                //if monster is fighting, update old version in ArrayList
                {
                    monster = tempMonster;                   
                    heroMonsterArrayList.set(i, monster); //replace monster in ArrayList with local monster data
                }
                else if (tempMonster.getMonsterStatus() == 2 && tempMonster.getHealth() <= 0)
                {
                //if monster has died, been marked as dead, but hasn't replaced its old version in ArrayList yet
                   monster = tempMonster; 
                   monster.setHealth(1); //so it doesn't trigger this again
                   heroMonsterArrayList.set(i, monster); //replace monster in ArrayList with local monster data                  
                }
            }
        }
        return heroMonsterArrayList;
    }
    
    public static ArrayList<Object> PutObjectsIntoArrayList(Hero hero, ArrayList<Object> heroMonsterArrayList)
    { //currently always adds hero and only adds monster if its fighting and has died.
        for (int i = 0; i < heroMonsterArrayList.size(); ++i) 
        {
            if (heroMonsterArrayList.get(i) instanceof Hero)
            {
                heroMonsterArrayList.set(i, hero); //replace hero in ArrayList with local hero data
            }
        }
        return heroMonsterArrayList;
    }
    
    public static Hero getHero(ArrayList<Object> heroMonsterArrayList)
    {
        Hero hero = new Hero("", 0, 0, null, 0, 0, 0, 0, 0, 0, null);
        
        for (int i = 0; i < heroMonsterArrayList.size(); ++i)
        {
            if (heroMonsterArrayList.get(i) instanceof Hero)
            {
                hero = (Hero) heroMonsterArrayList.get(i);
            }
        }
        return hero;
    }

    public static Monster getMonster(ArrayList<Object> heroMonsterArrayList, Hero hero, int tileID) 
    { //removes monster from ArrayList if it's to be set to fighting, or if its already set to fighting
     
        Monster monster;
        
        for (int i = 0; i < heroMonsterArrayList.size(); ++i) 
        {  
            if (heroMonsterArrayList.get(i) instanceof Monster)
            {
                monster = (Monster) heroMonsterArrayList.get(i);
                
                if (monster.getMonsterStatus() == 1) //is it fighting?
                {
                    return monster; //return it in its current state
                }
                else if (monster.getMonsterStatus() == 0 && monster.getMonsterID().getMonsterIDNo() == tileID)
                {
                    monster.setMonsterStatus(1); //set to fighting 
                    return monster;
                }           
            }          
        }
        return null;       
    }
}