package assignment1.part1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *Superclass for monster/boss/hero related classes and their objects. 
 * @author chris
 */
public abstract class Entity implements Serializable
{
   protected String name;
   protected int health;
   protected int attack;
   protected ArrayList <Skill> skillSet;
   protected int maxHealth; //used to keep original health referenced when temporary increases/decreases to health occur
   protected int maxAttack;

    protected abstract int getHealth(); 
    protected abstract void setHealth(int health);
    protected abstract int getMaxHealth(); 
    protected abstract void setMaxHealth(int maxHealth);
    protected abstract int getMaxAttack(); 
    protected abstract void setMaxAttack(int maxAttack);
    protected abstract int getAttack();
    protected abstract void setAttack(int attack);;
    protected abstract String getName();
    protected abstract ArrayList<Skill> getSkillSet();
    protected abstract void setSkillSet(ArrayList<Skill> skillSet);
}
