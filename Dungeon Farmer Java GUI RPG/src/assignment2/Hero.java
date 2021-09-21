/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Template for the hero of our game, Bob the farmer.
 * @author chris
 */
public class Hero extends Entity
{
    private int choiceID; //used for easy way to track choice across any gui
    private int tileID;
    private int defense;
    private int level;
    private int exp;
    private int maxDefense; //used to keep original defense referenced when temporary increases/decreases to defense occur
    private ArrayList <Item> itemSlots;
    private ArrayList <Integer> tileStatus;
    /*
     Used to represent if the tile has been visited yet. 0 = hero hasn't visited tile yet, 1 = hero has visited tile in past
     2 = hero is currently at this tile, 3 = tile chosen to move to.
    */
    private ArrayList <Integer> revisitExploredTile;
    //represents whether hero is trying to visit a tile already explored. 0 = trying to, 1 = not trying to
    
    public Hero(String name, int health, int maxHealth, ArrayList<Skill> skillSet, int attack, int maxAttack, int defense, int maxDefense, int level, int exp, ArrayList<Item> itemSlots, int tileID, int choiceID)
    {
        this.tileID = tileID;
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.skillSet = skillSet;
        this.attack = attack;
        this.maxAttack = maxAttack;
        this.defense = defense;
        this.maxDefense = maxDefense;
        this.level = level;
        this.exp = exp;
        this.itemSlots = itemSlots;
        ArrayList<Integer> tileArrayToArrayList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
        this.tileStatus = tileArrayToArrayList;       
        ArrayList<Integer> revisitTileArrayToArrayList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
        this.revisitExploredTile = revisitTileArrayToArrayList;
        this.choiceID = 0;
    }  
    
    public int getchoiceID()
    {
        return this.choiceID;
    }
    
    public void setchoiceID(int choiceID)
    {
        this.choiceID = choiceID;
    }
    
    @Override
    public String getName()
    {
        return this.name;
    }
    
    @Override
    public int getHealth()
    {
        return this.health;
    }
    
    @Override
    public int getMaxHealth()
    {
        return this.maxHealth;
    }
    
    @Override
    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth; 
    }
    
    @Override
    public ArrayList<Skill> getSkillSet()
    {
        return skillSet;
    }    
    
    @Override
    public void setSkillSet(ArrayList<Skill> skillSet)
    {
       this.skillSet = skillSet; 
    }
    
    @Override
    public void setHealth(int health)
    {
        this.health = health;
    }
    //used to update monster health after being attacked
    
    public int getDefense()
    {
        return this.defense;
    }
    
    public void setDefense(int defense)
    {
        this.defense = defense;
    }
    //used in calculations to determine how hard monster/boss will attack, defense is subtracted from the initial attack
    
    public int getMaxDefense()
    {
        return this.maxDefense;
    }
    
    public void setMaxDefense(int maxDefense)
    {
        this.maxDefense = maxDefense;
    }
    
     public int getLevel()
    {
        return this.level;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public int getExp()
    {
        return this.exp;
    }
    
    public void setExp(int exp)
    {
        this.exp = exp;
    }
    //used to add exp to player after killing monster/boss
    
    @Override
    public int getAttack()
    {
        return this.attack;
    }
    
    @Override
    public void setAttack(int attack)
    {
        this.attack = attack;
    }
    //used to update monster's attack damage to hero when it switches between using skills and regular attacks 
    
    @Override
    public int getMaxAttack()
    {
        return this.maxAttack;
    }
    
    @Override
    public void setMaxAttack(int maxAttack)
    {
        this.maxAttack = maxAttack;
    }
    //used 

    public ArrayList<Integer> getTileStatus()
    {
        return this.tileStatus; 
    }

    public ArrayList<Integer> getRevisitExploredTile()
    {
        return this.revisitExploredTile;
    }
    
    public int getTileID()
    {
        return this.tileID;
    }
    
    public void setTileID(int tileID)
    {
        this.tileID = tileID;
    }
    
    public void addItem(Item item)
    {   //provides method to add to itemSlots 
        this.itemSlots.add(item);
    }
    
    public ArrayList<Item> getItemSlots() //might be redunant, we will see
    {   //used for anything that needs to check inventory for something
        return itemSlots; 
    }
}
