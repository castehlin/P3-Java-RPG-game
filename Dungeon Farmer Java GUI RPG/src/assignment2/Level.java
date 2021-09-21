/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 * Class that allocates stats and unlocks skills for hero.
 * @author chris
 */
public class Level
{
    private String learnSkill;
    private String description;
    
    public Level(String learnSkill)
    {
        this.learnSkill = learnSkill;
    }
       
    public Hero LevelUp(Hero hero)
    {
        switch (hero.getLevel())
        {
            case 0: //level up from 0 to 1
                hero.setLevel(1);
                hero.setMaxHealth(hero.getMaxHealth() + 20);
                hero.setHealth(hero.getMaxHealth()); //heals hero to full
                hero.setMaxAttack(hero.getMaxAttack() + 10);
                hero.setAttack(hero.getMaxAttack());
                hero.setMaxDefense(hero.getMaxDefense() + 5);
                hero.setDefense(hero.getMaxDefense());
                for(Skill skill: hero.getSkillSet())
                {
                    if (skill.getName().equals("Pitchfork Piston Poke"))
                    {
                        skill.setIsLearned(true);
                        learnSkill = "Pitchfork Piston Poke";
                        break;
                    }
                }
                break;
            case 1:
                hero.setLevel(2);
                hero.setMaxHealth(hero.getMaxHealth() + 20);
                hero.setHealth(hero.getMaxHealth()); //heals hero to full
                hero.setMaxAttack(hero.getMaxAttack() + 10);
                hero.setAttack(hero.getMaxAttack());
                hero.setMaxDefense(hero.getMaxDefense() + 5);
                hero.setDefense(hero.getMaxDefense());
                for(Skill skill: hero.getSkillSet())
                {
                    if (skill.getName().equals("Haystorm"))
                    {
                        skill.setIsLearned(true);
                        learnSkill = "Haystorm";
                        break;
                    }
                }
                break;
            case 2:
                hero.setLevel(3);
                hero.setMaxHealth(hero.getMaxHealth() + 20);
                hero.setHealth(hero.getMaxHealth()); //heals hero to full
                hero.setMaxAttack(hero.getMaxAttack() + 10);
                hero.setAttack(hero.getMaxAttack());
                hero.setMaxDefense(hero.getMaxDefense() + 5);
                hero.setDefense(hero.getMaxDefense());
                break;
        }
        description = ("\nLevel up! "+hero.getName()+" is now level "+hero.getLevel()+"!\n"
                +"Attack: "+hero.getMaxAttack()+"\nDefense: "+hero.getMaxDefense()+"\n"
                        +"Health: "+hero.getMaxHealth());
        if (!learnSkill.equals(""))
        {
            description = description.concat("\nLearned "+learnSkill+"!");
        }
        return hero;
    }  
    
    public String getDescription()
    {
        return this.description;
    }
    
    public void setDescription(String description)
    {
        this.description = this.description;
    }
}
