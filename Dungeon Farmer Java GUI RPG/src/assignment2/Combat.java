/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;
import java.util.Random;

/**
 * A repurposed class from assignment 1, which was originally also very messy. There's bound to be plenty
 * of redundant or just dead code in this class...but it works as intended and I didn't give myself enough 
 * time, so here we are. Class used in conjunction with the combat GUI and combatActionListener to have fights
 * between the hero and monsters. Also contains connections to the level up and itemdrops classes.
 * @author chris
 */
public class Combat
{
    Random rand = new Random();
    private int heroAction; //used to track what action and ability hero used
    private int mobAction; //used to check a number of conditions based on hero and monster actions, refer to CombatDetails() for more detail
    private int turn; //used to control who's turn it is, and also controls if CombatDetails() processes heroAction or mobAction
    private String description = "";
    ItemDrops itemDrops = new ItemDrops(); //Class used to randomly pick items from monsters after killing them 
    Level level = new Level(""); //used if hero's exp reaches a point that they gain a level.
    private boolean levelCheck;
    
    public Combat(int heroAction, int mobAction, int turn)
    {
        this.heroAction = heroAction;
        this.mobAction = mobAction; 
        this.turn = turn;
    }
    
    public String getDescription(ArrayList<Object> heroMonsterArrayList,int userInput)
    {
        Hero hero = NewGame.getHero(heroMonsterArrayList);
        Monster monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);
        
        switch(userInput)
        {
            case -2: //monster dead
            {
                description = (monster.getName()+" slain!\n"+monster.getExp()+" EXP gained");
                description = Gui.AddStringToString(description, itemDrops.getDescription());  
                if (levelCheck == true)
                {
                    description = Gui.AddStringToString(description, level.getDescription());
                    levelCheck = false;
                }
                break;
            }
            case -1: //hero dead
            {
                description = ("Bob the Farmer joins the fate of many other villagers, sacrified to the denizens of the dungeon"
                        + " for purposes unknown...\n");                
                break;
            }
            case -3: //start of fight
            {
               description = hero.getName()+"'s health: "+hero.getHealth()+"/"+hero.getMaxHealth()+"\n"+monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth(); 
               break;
            }  
            case 0: //player attacks, lower monster hp by hero atk dmg
            {   
               description = hero.getName()+" attacks "+monster.getName()+". He does "+hero.getAttack()+" damage!";
               description = Gui.AddStringToString(description, "\n"+hero.getName()+"'s health: "+hero.getHealth()+"/"+hero.getMaxHealth()+"\n"+monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth()); 
               break;
            }
            case 1: //defend increases defense stat by 50% for one turn 
            {
                description = (hero.getName()+" braces for an incoming attack...");
                description = Gui.AddStringToString(description, "\n"+hero.getName()+"'s health: "+hero.getHealth()+"/"+hero.getMaxHealth()+"\n"+monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth()); 
                break;
            }
            case 3: //pitchfork piston poke
            {
                description = Gui.AddStringToString(description, "\n"+hero.getName()+"'s health: "+hero.getHealth()+"/"+hero.getMaxHealth()+"\n"+monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth()); 
                break;
            }
            case 4: //haystorm
            {
                description = Gui.AddStringToString(description, "\n"+hero.getName()+"'s health: "+hero.getHealth()+"/"+hero.getMaxHealth()+"\n"+monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth()); 
               break;
            }
            case 5: //monster attacks, lower hero hp by monster atk dmg
            {
                //handled in the calculation section...was just too much of a pain to try and seperate it from how I did it in assignment 1
                description = Gui.AddStringToString(description, "\n"+hero.getName()+"'s health: "+hero.getHealth()+"/"+hero.getMaxHealth()+"\n"+monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth()); 
                break;
            }
            case 11: //item use
            {
                userInput = 20;
                description = ("In-combat item use not implemented!");
                description = Gui.AddStringToString(description, "\n"+hero.getName()+"'s health: "+hero.getHealth()+"/"+hero.getMaxHealth()+"\n"+monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth()); 
                break;
            }
            case 12: //run
            {              
               description = ("Bob refuses to run!");
               description = Gui.AddStringToString(description, "\n"+hero.getName()+"'s health: "+hero.getHealth()+"/"+hero.getMaxHealth()+"\n"+monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth()); 
               break;
            }
                
        }

        return this.description; 
    }
    
    public ArrayList<Object> Fight(ArrayList<Object> heroMonsterArrayList, int userInput)
    {
        Hero hero = NewGame.getHero(heroMonsterArrayList);
        Monster monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);
        
        switch(userInput)
        {
            case -2: //monster dead
            {
                //set cooldown to 0
                for(Skill skillCooldownCheck: hero.getSkillSet())
                {
                        if (skillCooldownCheck.getCooldown() != 0) //if skill is on cooldown 
                        {
                        skillCooldownCheck.setCooldown(0); 
                        }                                                             
                }
                //reset stat lowering to normal
                hero.setDefense(hero.getMaxDefense()); 
                hero.setAttack(hero.getMaxAttack()); 
                
                //reset instance variable values to normal
                turn = 1;
                heroAction = 0; 
                mobAction = 0;               
                
                //loot gained by hero
                Item item = itemDrops.getItemDrop(monster, hero); //gets random item from the monster killed
                hero.addItem(item); //adds item from monster to hero's inventory
                
                //exp gained by hero
                hero.setExp(hero.getExp() + monster.getExp()); //sets hero exp to current exp + exp value from mob  
                
                switch(hero.getLevel())
                {
                    case 0: //hero is level 1
                        if(hero.getExp() >= 100)
                        {                                
                            hero = level.LevelUp(hero);
                            hero.setExp(hero.getExp() - 100); //reset hero's exp to whatever was left over from exp gained, if any
                            levelCheck = true;
                        }   
                        break;
                    case 1: //hero is level 2
                        if(hero.getExp() >= 200)
                        {
                            hero = level.LevelUp(hero);
                            hero.setExp(hero.getExp() - 200);
                            levelCheck = true;
                        }
                        break;
                    case 2: //hero is level 3
                        if(hero.getExp() >= 300)
                        {
                            hero = level.LevelUp(hero); 
                            hero.setExp(hero.getExp() - 300);
                            levelCheck = true;
                        }
                }
                break;
            }
            //**PLAYER ACTION**
            case 0:  //hero attack monster
            {
                heroAction = 1;
                monster.setHealth(monster.getHealth() - hero.getAttack());
                break; 
            }
            //**PLAYER ACTION**
            case 1: 
            {
                heroAction = 2;
                CombatDetails(heroMonsterArrayList, monster, hero, null);
                break;
            }
            case 3: //Pitchfork Piston Poke
            {
                for(Skill skill: hero.getSkillSet())
                {
                    if (skill.getName().equals("Pitchfork Piston Poke") && skill.getIsLearned() == true) 
                    {
                        if (skill.getCooldown() == 0)
                        {
                            heroAction = 7;
                            CombatDetails(heroMonsterArrayList, monster, hero, skill);
                            description = (hero.getName()+" attacks rapidly with his pitchfork!");
                            break;
                        }
                        else
                        {
                            description = ("That skill is on cooldown.");                                                
                            break;
                        }                                                                                                                                                                                                                                                                               
                    }
                    else if (skill.getName().equals("Pitchfork Piston Poke") && skill.getIsLearned() == false)
                    {
                        description = ("Skill not learned yet.");
                        break;
                    }                        
                } 
                break;
            }
            case 4: //Haystorm
            {
                //not implemented yet
                description = ("Skill not learned yet.");
                break;
            }
            //**MONSTER ACTION**
            case 5: //monster attacks, lower hero hp by monster atk damage 
            {
                turn = 2;
                
                for(Skill skill: monster.getSkillSet())
                {
                    //****IMP, MOB ONE SKILLS****                    
                    if(skill.getCooldown() == 0 && skill.getName().equals("Fiery power")) //attack +10
                    {                       
                        mobAction = 2;
                        description = (monster.getName()+" empowers itself! Its attack increases!"); 
                        heroMonsterArrayList = CombatDetails(heroMonsterArrayList, monster, hero, skill); 
                        turn = 1;
                        break;
                    }
                    else if (skill.getName().equals("Flameball")) //mob's regular attack
                    {
                        mobAction = 1; //monster attacking hero 
                        description = (monster.getName()+" hurls a ball of flame!");                            
                        heroMonsterArrayList = CombatDetails(heroMonsterArrayList, monster, hero, skill); 
                        turn = 1;
                        break;
                    } 
                    
                    //****GIANT RAT, MOB TWO SKILLS****  
                    if(skill.getCooldown() == 1 && skill.getName().equals("Frenzied counterattack")) 
                    //does nothing, first turn, does heavy damage is attacked the turn after, nothing if not
                    {
                        mobAction = 3; //set specialAction value to current health
                        monster.setSpecialAction(monster.getHealth()); //records monster's current health
                        description = (monster.getName()+" has a crazed look in its eyes, as if daring "+hero.getName()+""
                            + " to attack...");
                        CombatDetails(heroMonsterArrayList, monster, hero, skill);
                        turn = 1;
                        break;
                    }
                    else if(skill.getCooldown() == 0 && skill.getName().equals("Frenzied counterattack"))  
                    {
                            if ((monster.getSpecialAction() != monster.getHealth()))
                            //if the monster's health isn't equal to what it was last turn, which means hero attacked
                        {             
                            mobAction = 1; //monster attacks hero                                                                             
                            description = (monster.getName()+" screeches madly, jumping upon "+hero.getName()+","
                                + " biting and clawing him in a rage!");
                            CombatDetails(heroMonsterArrayList, monster, hero, skill); 
                            skill.setCooldown(skill.getMaxCooldown());
                            turn = 1;
                            break;
                        }
                        else //hero did not attatck
                        {
                            mobAction = 4; //monster does nothing, puts skill on cooldown
                            description = (monster.getName()+" looks almost disappointed that "+hero.getName()+""
                                + " didn't attack. "+hero.getName()+" takes advantage of its lack of action!");
                            CombatDetails(heroMonsterArrayList, monster, hero, skill); 
                            turn = 1;
                            break;
                        }                               
                    }
                    else if (skill.getName().equals("Bite")) //mob's regular attack
                    {
                        mobAction = 1; //monster attacking hero  
                        description = (monster.getName()+" leaps forward and bites "+hero.getName()+"!");                                                            
                        CombatDetails(heroMonsterArrayList, monster, hero, skill);
                        turn = 1;
                        break;
                    }                                                        
                    hero = NewGame.getHero(heroMonsterArrayList);
                    monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);                   
                
                    if(monster.getMonsterID() == MonsterID.MOB_THREE)
                    {
                        switch (monster.getSpecialAction()) 
                            {
                                case 0:
                                {
                                    description = (monster.getName()+" lets loose a flaming arrow at "+hero.getName()+"!");
                                    
                                    switch(heroAction)
                                    {
                                        case 2: //if hero defended
                                        {    
                                            mobAction = 1; //monster attacking hero
                                            CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                            break;
                                        }               
                                        default: //if hero did not defend
                                        {    
                                            mobAction = 0; //armor pierce, fall through to monster attacking hero
                                            CombatDetails(heroMonsterArrayList, monster, hero, skill);  
                                        }
                                        monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);
                                        hero = NewGame.getHero(heroMonsterArrayList);
                                    }
                                    turn = 1;
                                    break;
                                }                                
                                case 1:
                                {  
                                    if(skill.getName().equals("Aimed shot"))
                                    {
                                        switch(heroAction)
                                        {
                                            //Includes all heroAction values that don't deal damage
                                            case 2:
                                            case 4:
                                            case 5:      
                                            case 6:
                                            {           
                                                description =  monster.getName()+" fires a perfectly aimed shot at "+hero.getName()+"!";
                                                mobAction = 1; //monster attacking hero
                                                CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                                turn = 1;
                                                break;                                            
                                            } 
                                            default: //hero did damage
                                            {
                                                mobAction = 4; //monster does nothing, puts skill on cooldown (no cd on this ability, but put here for consistancy)
                                                description =  hero.getName()+" disrupts "+monster.getName()+"'s "
                                                    + "focus with his attack! ";
                                       CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                                turn = 1;
                                            }
                                        }                                         
                                        monster = NewGame.getMonster(heroMonsterArrayList, hero, 0);
                                        hero = NewGame.getHero(heroMonsterArrayList);
                                    }
                                }
                            }    
                            if (turn == 1)
                            {
                                //get hint of mobs action for next turn
                                int randomAbility = rand.nextInt(2);
                                switch (randomAbility)
                                {
                                    case 0: //flaming arrow tell
                                    {
                                        description = Gui.AddStringToString(description, monster.getName()+" is staring at the unarmored areas of "+hero.getName()+"'s outfit...\n");
                                        monster.setSpecialAction(0);
                                        break;
                                    }
                                    case 1: //aimed shot tell
                                    {
                                        description = Gui.AddStringToString(description, monster.getName()+" carefully lines its next shot up with great focus...\n");
                                        monster.setSpecialAction(1);  
                                        break;
                                    }
                                }
                                break;
                 }            }
                }
                //after monster's action, reduce cooldown on abilities by 1
                for(Skill skillCooldownCheck: monster.getSkillSet())
                {
                    if (skillCooldownCheck.getCooldown() != 0) //if skill is on cooldown 
                    {
                        skillCooldownCheck.setCooldown(skillCooldownCheck.getCooldown() -1); //reduce cooldown value by 1
                    }                                                             
                } 
                break;
            }
            case 6: //use Pitchfork Piston Poke                                                                       
            for(Skill skill: hero.getSkillSet())
            {
                if (skill.getName().equals("Pitchfork Piston Poke") && skill.getIsLearned() == true) 
                {
                    if (skill.getCooldown() == 0)
                    {
                        description = (hero.getName()+" attacks rapidly with his pitchfork!");
                        heroAction = 7;
                        CombatDetails(heroMonsterArrayList, monster, hero, skill);
                        break;
                    }
                    else
                    {
                        description = ("That skill is on cooldown.");                                                
                        break;
                    }                                                                                                                                                                                                                                                                               
                }
                else if (skill.getName().equals("Pitchfork Piston Poke") && skill.getIsLearned() == false)
                {
                    description = ("Skill not learned yet.");
                    break;
                }                        
            }  
        }
        heroMonsterArrayList = NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);
        return heroMonsterArrayList;
    }
    
    private ArrayList<Object> CombatDetails(ArrayList <Object> heroMonsterArrayList, Monster monster, Hero hero, Skill skill) 
    //used to keep a lot of the fluff out of the turns above
    {       
        if(turn == 1)
        {
                switch(heroAction)
                {
                    case 1: //hero normal attacks enemy
                    {
                        Gui.AddStringToString(description," He does "+hero.getAttack()+" damage!\n");
                        monster.setHealth(monster.getHealth() - hero.getAttack());
                        break;
                    }
                    case 2: //hero defends
                    {
                    hero.setDefense((int) (hero.getDefense() * 1.5)); 
                    break;
                    }
                    case 3: //hero uses Skill menu, no use implemented yet...
                    case 4: //hero uses item, not needed yet until implemented properly
                    case 5: //hero tries to escape, not needed yet until implemented properly
                    {
                        Gui.AddStringToString(description,hero.getName()+" inspects the condition of "+monster.getName()+""
                                + " closely, as if it were one of his sheep back on the farm. Its health is "
                                + "revealed!");                  
                        skill.setIsLearned(false); //only needs to be used once per combat
                        break;
                    }
                    case 7: //hero uses Pitchfork Piston Poke
                    {
                        Gui.AddStringToString(description, hero.getName()+" rapidly stabs "+monster.getName()+""
                                + " with his weapon! He deals "+(hero.getAttack() * 2)+" damage!");
                        monster.setHealth(monster.getHealth() - (hero.getAttack() * 2));
                        skill.setCooldown(skill.getMaxCooldown());
                        break;
                    }
                    case 8: //hero uses Haystorm
                    {
                        Gui.AddStringToString(description, hero.getName()+", pulls hay out of his pockets and throws"
                                + "it accurately in the face of "+monster.getName()+", blinding it!");
                        skill.setCooldown(skill.getMaxCooldown());
                        break;
                    }
                } 
            }
        if(turn == 2)
        {
            switch(mobAction)
            {
                case 0: //disable hero's armor before attacking him
                {
                    description = monster.getName()+"'s attack ignores "+hero.getName()+"'s defenses!";
                    hero.setDefense(0);
                    //fallthrough to case 1
                    
                }
                case 1: //monster attacks hero
                {
                    monster.setAttack(monster.getAttack() + skill.getEffect()); 
                               
                    if (heroAction == 8) //hero used Haystorm on turn just passed
                    {
                        Gui.AddStringToString(description, monster.getName()+" is blind! It completely misses its mark,"
                                + "dealing no damage!\n");  
                    }
                    else if (hero.getDefense() >= monster.getAttack()) //monster attack lower than hero defense
                    {
                            Gui.AddStringToString(description, " It fails to penetrate "+hero.getName()+"'s defenses!\n");
                    }                                          
                    else //monster attack higher than hero defense
                    {
                        Gui.AddStringToString(description, " It deals "+(monster.getAttack() - hero.getDefense())+" damage!\n");
                        hero.setHealth(hero.getHealth() - (monster.getAttack() - hero.getDefense()));
                        monster.setAttack(monster.getMaxAttack());
                    }                                   
                    break;
                }
                case 2: //monster permanently buffs its attack
                {
                    monster.setAttack(monster.getAttack() + skill.getEffect()); 
                    monster.setMaxAttack(monster.getAttack()); //keeps maxAttack the same as Attack
                    skill.setCooldown(skill.getMaxCooldown()); 
                    break;
                }
                case 3: //sets specialAction value to value of monster's current health
                {
                  monster.setSpecialAction(monster.getHealth());; //records monster's current health  
                  break;
                }
                case 4: //monster does nothing, skill put on cooldown
                {
                    skill.setCooldown(skill.getMaxCooldown());
                }
            }       
            //****END OF ROUND CALCULATIONS, TRIGGERED AT BOTTOM OF MOBACTION SWITCH****
            if (heroAction == 2 || mobAction == 0) //if hero defended on their last turn, or if their defense was reduced by monster
            {
                hero.setDefense(hero.getMaxDefense()); //sets hero's defense back to normal values
            }
            mobAction = -1; //default
            heroAction = -1; //default
        }
        
        for(Skill skillCooldownCheck: hero.getSkillSet())
        {
            if (skillCooldownCheck.getCooldown() != 0) //if skill is on cooldown 
            {
                skillCooldownCheck.setCooldown(skillCooldownCheck.getCooldown() -1); //reduce cooldown value by 1
            }                                                             
        } 
        NewGame.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList); //put hero and monster objects back into ArrayList
        return heroMonsterArrayList;
    }
}
