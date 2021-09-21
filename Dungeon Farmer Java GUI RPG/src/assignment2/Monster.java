/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;

/**
 * Template for all monsters. 
 * @author chris
 */
public class Monster extends Entity
{
    private int monsterStatus; //0 is not encountered, 1 is currently fighting, 2 is dead
    private final Item[] itemDrops;
    private final MonsterID monsterID;
    private int exp;
    private int specialAction; //use of this varies dependent on monster
    
    public Monster(String name, int health, int maxHealth, ArrayList<Skill> skillSet, int attack, int monsterStatus, Item[] itemDrops, MonsterID monsterID, int exp, int maxAttack, int specialAction)
    {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.skillSet = skillSet; 
        this.attack = attack;
        this.monsterStatus = monsterStatus;
        this.itemDrops = itemDrops;
        this.monsterID = monsterID;
        this.exp = exp;
        this.maxAttack = maxAttack;
        this.specialAction = specialAction;
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
    public void setHealth(int health)
    {
        this.health = health;
    }
    //used to update monster health after being attacked
    
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
    //don't know if will need to use this method for monsters, but will leave in just in case
    
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
    
    public int getMonsterStatus()
    {
        return this.monsterStatus;
    }
    //used in combat class to generically refer to the monster that is fighting hero
    
    public void setMonsterStatus(int monsterStatus)
    {
        this.monsterStatus = monsterStatus;
    }
    
    public Item[] getItemDrops()
    {
        return itemDrops;
    }
    
    public MonsterID getMonsterID()
    {
        return monsterID;
    }
    
    public int getExp()
    {
        return exp;
    }
    
    public void setExp(int exp)
    {
        this.exp = exp;
    }
    
    public int getSpecialAction()
    {
        return specialAction;
    }
    
    public void setSpecialAction(int specialAction)
    {
        this.specialAction = specialAction;
    }
}   

