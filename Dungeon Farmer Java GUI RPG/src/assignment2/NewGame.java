/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class called by the main GUI. Instantiate monsters/hero/items/skills etc as well as
 *  providing static methods for easy access of hero/monster/arraylist additions and removals.
 * @author chris
 */
public class NewGame
{
    public ArrayList<Object> GameBegin(OutOfCombatAction outOfCombatAction) throws IOException
    {
        ArrayList<Object> heroMonsterArrayList = new ArrayList<>();       
        Hero hero = new Hero("Bob the Farmer", 100, 100, null, 15, 15, 5, 5, 0, 0, new <Item> ArrayList(10), 0, 0);
        heroMonsterArrayList.add(hero); 
        
        ItemList itemList = new ItemList(hero);
        /*
        Class that contains all the loot tables that monster's save to an array, requires hero object to provide item effect
        argument directly to hero object.   
       */        
        SkillSets skillsets = new SkillSets(); //class that assigns monsters and hero their skills 
         
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
            Monster mob1TileB = new Monster("Imp", 115, 115, null, 0, 0, itemList.getFloorOneMobItemList(), MonsterID.MOB_ONE, 100, 0, 0); 
            Monster mob2TileC = new Monster("Giant Rat", 225, 225, null, 0, 0, itemList.getFloorOneMobItemList(), MonsterID.MOB_TWO, 125, 0, 0);
            Monster mob3TileD = new Monster("Skeleton Archer", 275, 275, null, 0, 0, itemList.getFloorOneMobItemList(), MonsterID.MOB_THREE, 100, 0, 0); 
           // Monster chest1TileE = new Monster("Chest", 0, 0, null, 0, 0, itemList.getFloorChestItemList(), MonsterID.CHEST, 0, 0, 0);
            //Monster boss1TileF = new Monster("Damaged Stone Golem", 350, 700, null, 0, 0, itemList.getFloorOneBossItemList(), MonsterID.BOSS_ONE, 0, 0, 0); //not implemented yet
                                
           PutObjectsIntoArrayList(hero, heroMonsterArrayList);
           heroMonsterArrayList.add(mob1TileB); 
           heroMonsterArrayList.add(mob2TileC);
           heroMonsterArrayList.add(mob3TileD);
           heroMonsterArrayList = skillsets.assignSkillsToHeroAndMonsters(heroMonsterArrayList); 
           //arraylist passed to skillsets to assign skills to hero and mobs                     
        }
        
        return heroMonsterArrayList;
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
    { //only adds hero
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
        Hero hero = new Hero("", 0, 0, null, 0, 0, 0, 0, 0, 0, null, 0, 0);
        
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
                else if (monster.getMonsterStatus() == 0 && monster.getMonsterID().getMonsterIDNo() == hero.getTileID())
                {
                    monster.setMonsterStatus(1); //set to fighting 
                    return monster;
                }           
            }          
        }
        return null;       
    }
}
