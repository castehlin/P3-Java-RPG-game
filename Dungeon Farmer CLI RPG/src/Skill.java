/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;
import java.io.Serializable; //needed to save the hero object and thus skill objects that lurk within it
/**
 * Template for all skills, both for monsters, bosses and players. 
 * Will be saved into arrays in SkillSets to be then accessed by aforementioned class objects in relevant methods.
 * @author chris
 */
public class Skill implements Serializable
{
    private final String nameAndDescription;
    private final String name; //to reference easier without walls of text
    private final int effect; //could be damage done, healing done, stat increase done...depends on the ability
    private int cooldown; //starts at 0, every turn (refer to combat class) it iterates up until it reaches maxCooldown value
    private final int maxCooldown;
    private boolean isLearned = false; //used to control whether player has access to level locked skills or not
    
    public Skill(String nameAndDescription, int effect, int cooldown, int maxCooldown, boolean isLearned, String name)
    {
        this.nameAndDescription = nameAndDescription;
        this.effect = effect;
        this.cooldown = cooldown;
        this.maxCooldown = maxCooldown;
        this.isLearned = isLearned;
        this.name = name;       
    }
    
    public String getNameAndDescription()
    {
        return this.nameAndDescription;
    }
    
    public int getEffect()
    {
        return this.effect;
    }
    
    public int getCooldown()
    {
        return this.cooldown;
    }
    
    public int getMaxCooldown()
    {
        return this.maxCooldown;
    }
    
    public void setCooldown(int cooldown)
    {
        this.cooldown = cooldown;
    }
    
   public boolean getIsLearned()
   {
       return this.isLearned;
   }
   
   public void setIsLearned(boolean isLearned)
   {
       this.isLearned = isLearned;
   }
   //used when player reaches a level that a skill should be available at, so it is set to true allowing its use
   
   public String getName()
   {
       return this.name;
   }
}
