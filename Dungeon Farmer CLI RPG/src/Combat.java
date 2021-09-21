/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class with methods that deal with monster/boss fights against hero. I know this class is a mess!!!
 * I found it hard to complete this before the deadline. I've come to the realization that this class probably needs
 * to be split into a few classes, with a lot less specific if/else/switch statements. 
 * @author chris
 */
public class Combat
{
    private int heroAction; //used to track what action and ability hero used
    private int mobAction; //used to check a number of conditions based on hero and monster actions, refer to CombatDetails() for more detail
    private int turn; //used to control who's turn it is, and also controls if CombatDetails() processes heroAction or mobAction
    
    public Combat(int heroAction, int mobAction, int turn)
    {
        this.heroAction = heroAction;
        this.mobAction = mobAction; 
        this.turn = turn;
    }
    public ArrayList<Object> MonsterFight(ArrayList<Object> heroMonsterArrayList, ItemDrops itemdrops)
    {
        Random rng = new Random(); //used for loot tables and some monster abilities
        Level level = new Level(""); //used if hero's exp reaches a point that they gain a level.
        int savedInput = 0; //used so scanner input can be referenced after scanning
        int loopNumber = 0; //used for input validation and while loop coverage                
        int skillInput = 0; //changes switch state to use skills rather than picking a general action by hero. 0 = off, 1 = on
        boolean enableInspect = false; //used to control whether monster has had hero skill "Inspect Creature" used against it.                        

        Hero hero;
        hero = Main.getHero(heroMonsterArrayList); //gets hero from ArrayList and assigns it to local object
        
        Monster monster; 
        monster = Main.getMonster(heroMonsterArrayList, hero, 0); //gets monster from ArrayList and assigns it to local object
        
        System.out.println("\n****BATTLE START!****");       
        
        while(loopNumber != 3) 
        /*
          Loop that represents the fight - this loop will never be exited because as soon as Bob's or the monster he's fighting
          dies, either the monsterDeathAndDrops(monster) or heroDeath() methods will be triggered, 
        */
        {
            while (turn == 1) //represents player's turn
            {
                while(loopNumber != 2) //used for input validation purposes
                {
                    if (loopNumber == 3) //escape middle loop, and then outer loop, only triggered if monster died
                    {
                        break;
                    }                   
                    
                    if (skillInput != 1) //if user didn't iterate earlier and select 3) to use skills
                    {
                       System.out.println("\n****Bob the Farmer's turn!****"); 
                       System.out.println("Bob the Farmer's health: "+hero.getHealth()+"/"+hero.getMaxHealth());
                       System.out.println("1) Attack, 2) Defend, 3) Skill, 4) Item, 5) Escape");
                       savedInput = InputValidator(loopNumber, savedInput, hero); //get user input
                    }                                                                                          
                    
                    switch (savedInput)
                    {
                        case 1: 
                            switch (skillInput) //0 if user didn't select 3) Skill, 1 if they did
                            {
                                case 0: //regular attack
                                    heroAction = 1;
                                    System.out.print(hero.getName()+" attacks "+monster.getName()+".");
        heroMonsterArrayList = CombatDetails(heroMonsterArrayList, monster, hero, null);                                                                                                                                       
                                    break;
                                case 1: //Use inspect creature  skill                                   
                                      for(Skill skill: hero.getSkillSet()) 
                                      {
                                          if (skill.getName().equals("Inspect creature") && skill.getIsLearned() == true)
                                          {
                                              heroAction = 6;
                                              CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                              enableInspect = true; //enables health display  
                                              skillInput = 0; //skill switch state disabled
                                              break;
                                          }
                                          else //if hero uses skill again
                                          {
                                              System.out.println(hero.getName()+" attempts to inspect the condition of "+monster.getName()+""
                                                      + " again. It has no effect!");
                                              skillInput = 0; //skill switch state disabled
                                              break;                                           
                                          }
                                      }                                   
                            break;
                            }                                                                                                                  
                            hero = Main.getHero(heroMonsterArrayList);
                            monster = Main.getMonster(heroMonsterArrayList, hero, 0); 
                            if (skillInput == 0)
                            {
                              loopNumber = 2; 
                              turn = 2;
                            }                                                     
                            break;
                        case 2: //defend increases defense stat by 50% for one turn
                            switch (skillInput) //0 if user didn't select 3) Skill, 1 if they did
                            {
                                case 0:
                                    heroAction = 2;
                                    System.out.println(hero.getName()+" braces for an incoming attack...");   
                                    CombatDetails(heroMonsterArrayList, monster, hero, null);
                                    break;
                                case 1: //use Pitchfork Piston Poke                                                                       
                                    for(Skill skill: hero.getSkillSet())
                                    {
                                        if (skill.getName().equals("Pitchfork Piston Poke") && skill.getIsLearned() == true) 
                                        {
                                            if (skill.getCooldown() == 0)
                                            {
                                                heroAction = 7;
                                                CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                                skillInput = 0; //skill switch state disabled
                                            }
                                            else
                                            {
                                                System.out.println("That skill is on cooldown.");                                                
                                                break;
                                            }                                                                                                                                                                                                                                                                               
                                            break;
                                        }
                                        else if (skill.getName().equals("Pitchfork Piston Poke") && skill.getIsLearned() == false)
                                        {
                                            System.out.println("Skill not learned yet.");
                                            break;
                                        }                        
                                    }                                          
                            break;
                            }   
                            hero = Main.getHero(heroMonsterArrayList);
                            monster = Main.getMonster(heroMonsterArrayList, hero, 0);
                            if (skillInput == 0)
                            {
                              loopNumber = 2; 
                              turn = 2;
                            }
                            else if (skillInput == 1) //will trigger if skill is on cooldown
                            {
                                skillInput = 0; //skill switch state disabled)
                            }
                            break;
                        case 3: //print skills and then get input to use one of the skills
                            switch (skillInput) //0 if user selected 3) Skill, 1 if they chose 3) Haystorm with skillInput enabled
                            {
                                case 0: 
                                    skillInput = 1; //skill switch state enabled
                                    int skillNumber = 1; //used for skill iteration display below
                                    System.out.println("Skills:");
                                    for(Skill skill: hero.getSkillSet()) //print skill names to user
                                    {                                                                               
                                        System.out.print(skillNumber+") "+skill.getName()+"\n");
                                        ++skillNumber;
                                        
                                    } 
                                    savedInput = InputValidator(loopNumber, savedInput, hero); //get user input for skills
                                    break;
                                case 1:                                                                        
                                    for(Skill skill: hero.getSkillSet())
                                    {
                                        if (skill.getName().equals("Haystorm") && skill.getIsLearned() == true)
                                        {
                                            if (skill.getCooldown() == 0)                                                                                                    
                                            {   
                                                heroAction = 8; //triggers alternative 0 damage text if monster attempts to attack                                           
                                                CombatDetails(heroMonsterArrayList, monster, hero, skill);                                                  
                                                skillInput = 0; //skill switch state disabled
                                                break;                                                          
                                            }
                                            else
                                            {
                                                System.out.println("That skill is on cooldown."); 
                                                skillInput = 0; //skill switch state disabled
                                                break;
                                            }
                                        }
                                        else if (skill.getName().equals("Haystorm") && skill.getIsLearned() == false)
                                        {
                                            System.out.println("Skill not learned yet.");
                                            skillInput = 0; //skill switch state disabled
                                            break;
                                        }
                                    }                                                                                                            
                            break;                                    
                            }
                            break;
                        case 4:
                            System.out.println("In combat item use not implemented yet!");
                            //I couldn't get this done before the deadline, will work on it later
                            break;
                        case 5: //maybe I'll make a real escape function at some point, but I wanted to add it like this either way 
                            System.out.println("Bob refuses to run!");
                            loopNumber = 2;
                            turn = 2;
                            break;
                        default:
                            System.out.println("Invalid input, try again.");
                    }
                    
                    if(monster.getHealth() <= 0) //monster death
                    {
                        System.out.println("\n"+monster.getName()+" slain!");
                        monster.setMonsterStatus(2); //monster set to dead
                        turn = 0; //will exit outer loop and avoid monster turn when middle loop is exited
                        loopNumber = 3; //will exit middle while loop when inner loop is exited
                        break; //exits inner loop
                    }
                    
                    if(turn == 2)
                    //if the hero has made its action and for loop is exited, this will trigger the following iteration
                    {
                        //***iterates skills and reduces cooldown on all skills by 1 if they aren't already 0
                        for(Skill skillCooldownCheck: hero.getSkillSet())
                        {
                            if (skillCooldownCheck.getCooldown() != 0) 
                            {
                                skillCooldownCheck.setCooldown(skillCooldownCheck.getCooldown() -1);
                            }                                                             
                       }                                          
                    }                    
                }  
            }
            
            while (turn == 2) //represents monster's turn
            {
                System.out.println("\n****"+monster.getName()+"'s turn!****");
                if (enableInspect == true) //Inspect Creature was used
                {
                  System.out.println(monster.getName()+"'s health: "+monster.getHealth()+"/"+monster.getMaxHealth());  
                }
                else if (enableInspect == false) //Inspect Creature wasn't used
                {
                    System.out.println(monster.getName()+"'s health: ???/???");
                }
                
                for(Skill skill: monster.getSkillSet()) 
                    //loop to iterate through skills of mob and conditions used to find appropriate skill for mob actions in switch
                {
                    if (turn == 1) //breaks out of for loop if monster performed an action
                    {
                        break;
                    }
                    switch(monster.getMonsterID())
                    {
                        case MOB_ONE:                        
                            if(skill.getCooldown() == 0 && skill.getName().equals("Fiery power")) //attack +10
                            {
                                mobAction = 2;
                                System.out.println(monster.getName()+" empowers itself! Its attack increases!"); 
                                CombatDetails(heroMonsterArrayList, monster, hero, skill); 
                                turn = 1;
                            }
                            else if (skill.getName().equals("Flameball")) //mob's regular attack
                            {
                                mobAction = 1; //monster attacking hero 
                                System.out.print(monster.getName()+" hurls a ball of flame!");                            
                                CombatDetails(heroMonsterArrayList, monster, hero, skill); 
                                turn = 1;
                            }                                
                            hero = Main.getHero(heroMonsterArrayList);
                            monster = Main.getMonster(heroMonsterArrayList, hero, 0);
                            break; 
                        case MOB_TWO:
                            if(skill.getCooldown() == 1 && skill.getName().equals("Frenzied counterattack")) 
                            //does nothing, first turn, does heavy damage is attacked the turn after, nothing if not
                            {
                                mobAction = 3; //set specialAction value to current health
                                monster.setSpecialAction(monster.getHealth()); //records monster's current health
                                System.out.println(monster.getName()+" has a crazed look in its eyes, as if daring "+hero.getName()+""
                                        + " to attack...");
                                CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                turn = 1;
                            }
                            else if(skill.getCooldown() == 0 && skill.getName().equals("Frenzied counterattack"))  
                            {
                                if ((monster.getSpecialAction() != monster.getHealth()))
                                 //if the monster's health isn't equal to what it was last turn, which means hero attacked
                                {             
                                     mobAction = 1; //monster attacks hero                                                                             
                                    System.out.print(monster.getName()+" screeches madly, jumping upon "+hero.getName()+","
                                            + " biting and clawing him in a rage!");
                                    CombatDetails(heroMonsterArrayList, monster, hero, skill); 
                                    skill.setCooldown(skill.getMaxCooldown());
                                    turn = 1;
                                }
                                else //hero did not attatck
                                {
                                    mobAction = 4; //monster does nothing, puts skill on cooldown
                                    System.out.println(monster.getName()+" looks almost disappointed that "+hero.getName()+""
                                            + " didn't attack. "+hero.getName()+" takes advantage of its lack of action!");
                                    CombatDetails(heroMonsterArrayList, monster, hero, skill); 
                                    turn = 1;
                                }                               
                            }
                            else if (skill.getName().equals("Bite")) //mob's regular attack
                            {
                                mobAction = 1; //monster attacking hero  
                                System.out.print(monster.getName()+" leaps forward and bites "+hero.getName()+"!");                                                            
                                CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                turn = 1;
                            }                            
                            monster = Main.getMonster(heroMonsterArrayList, hero, 0);
                            hero = Main.getHero(heroMonsterArrayList); 
                            break;
                        case MOB_THREE:                      
                            switch (monster.getSpecialAction()) 
                            {
                                case 0:
                                {
                                    System.out.print(monster.getName()+" lets loose a flaming arrow at "+hero.getName()+"!");
                                    
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
                                        monster = Main.getMonster(heroMonsterArrayList, hero, 0);
                                        hero = Main.getHero(heroMonsterArrayList);
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
                                                System.out.print(monster.getName()+" fires a perfectly aimed shot at "+hero.getName()+"!");
                                                mobAction = 1; //monster attacking hero
                                                CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                                turn = 1;
                                                break;                                            
                                            } 
                                            default: //hero did damage
                                            {
                                                mobAction = 4; //monster does nothing, puts skill on cooldown (no cd on this ability, but put here for consistancy)
                                                System.out.print(hero.getName()+" disrupts "+monster.getName()+"'s "
                                                    + "focus with his attack! ");
                                                CombatDetails(heroMonsterArrayList, monster, hero, skill);
                                                turn = 1;
                                            }
                                        }                                
                                        monster = Main.getMonster(heroMonsterArrayList, hero, 0);
                                        hero = Main.getHero(heroMonsterArrayList);
                                    }
                                }
                            }    
                            if (turn == 1)
                            {
                                //get hint of mobs action for next turn
                                int randomAbility = rng.nextInt(2);
                                switch (randomAbility)
                                {
                                    case 0: //flaming arrow tell
                                    {
                                        System.out.print(monster.getName()+" is staring at the unarmored areas of "+hero.getName()+"'s outfit...\n");
                                        monster.setSpecialAction(0);
                                        break;
                                    }
                                    case 1: //aimed shot tell
                                    {
                                        System.out.print(monster.getName()+" carefully lines its next shot up with great focus...\n");
                                        monster.setSpecialAction(1);  
                                        break;
                                    }
                                }
                            }
                            break;                                                                                       
                        case BOSS_ONE: //not implemented yet
                            break;
                    }
                }
                //****Triggers after iteration through skill for loop above complete****
                if(turn == 1)
                //if the monster has made its action and for loop is exited, this will trigger the following iteration
                {
                    //***iterates skills and reduces cooldown on all skills by 1 if they aren't already 0
                    for(Skill skillCooldownCheck: monster.getSkillSet())
                    {
                        if (skillCooldownCheck.getCooldown() != 0) //if skill is on cooldown 
                        {
                            skillCooldownCheck.setCooldown(skillCooldownCheck.getCooldown() -1); //reduce cooldown value by 1
                        }                                                             
                    }                                          
                }
                loopNumber = 0; //allows turn 1 to run all over again
            }
            monster.setAttack(monster.getMaxAttack()); //resets monster's attack to normal values if it was lowered by hero
                
            if (hero.getHealth() <= 0) //if hero dies, triggered after end of monster's turn if condition true
            {
                System.out.println("\nBob the Farmer joins the fate of many other villagers, sacrified to the denizens of the dungeon"
                        + " for purposes unknown...");
                System.out.println("||||||GAME OVER||||||");
                System.exit(0); //closes program, maybe if I want to keep working on this I will add in the ability to load save here          
            }
        }
        
        //****AFTER BATTLE IF HERO WIN****
        
        //normalize hero's stats and set class values to default after battle
        hero.setDefense(hero.getMaxDefense()); 
        hero.setAttack(hero.getMaxAttack()); 
        heroAction = 0; 
        mobAction = 0;
        turn = 1;
        
        //loot gained by hero
        Item item = itemdrops.getItemDrop(monster, hero); //gets random item from the monster killed
        hero.addItem(item); //adds item from monster to hero's inventory
        
        //exp gained by hero
        hero.setExp(hero.getExp() + monster.getExp()); //sets hero exp to current exp + exp value from mob  
        System.out.println(monster.getExp()+" EXP gained!");
        
        //level up check
        switch(hero.getLevel())
        {
            case 0: //hero is level 1
                if(hero.getExp() >= 100)
                {
                    hero = level.LevelUp(hero);
                    hero.setExp(hero.getExp() - 100); //reset hero's exp to whatever was left over from exp gained, if any
                }   
                break;
            case 1: //hero is level 2
                if(hero.getExp() >= 200)
                {
                    hero = level.LevelUp(hero);
                    hero.setExp(hero.getExp() - 200);
                }
            case 2: //hero is level 3
                if(hero.getExp() >= 300)
                {
                    hero = level.LevelUp(hero); 
                    hero.setExp(hero.getExp() - 300);
                }
        }
        
        //update ArrayList with new hero values and remove old     
        heroMonsterArrayList = Main.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList);                     
        return heroMonsterArrayList; //returns monster updated as dead and hero with
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
                        System.out.print(" He does "+hero.getAttack()+" damage!\n");
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
                    case 6: //hero uses Inspect Creature skill
                    {
                        System.out.println(hero.getName()+" inspects the condition of "+monster.getName()+""
                                + " closely, as if it were one of his sheep back on the farm. Its health is "
                                + "revealed!");                  
                        skill.setIsLearned(false); //only needs to be used once per combat
                        break;
                    }
                    case 7: //hero uses Pitchfork Piston Poke
                    {
                        System.out.println(hero.getName()+" rapidly stabs "+monster.getName()+""
                                + " with his weapon! He deals "+(hero.getAttack() * 2)+" damage!");
                        monster.setHealth(monster.getHealth() - (hero.getAttack() * 2));
                        skill.setCooldown(skill.getMaxCooldown());
                        break;
                    }
                    case 8: //hero uses Haystorm
                    {
                        System.out.println(hero.getName()+", pulls hay out of his pockets and throws"
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
                    System.out.print(" "+monster.getName()+"'s attack ignores "+hero.getName()+"'s defenses!");
                    hero.setDefense(0);
                    //fallthrough to case 1
                    
                }
                case 1: //monster attacks hero
                {
                    monster.setAttack(monster.getAttack() + skill.getEffect()); 
                               
                    if (heroAction == 8) //hero used Haystorm on turn just passed
                    {
                        System.out.print(monster.getName()+" is blind! It completely misses its mark,"
                                + "dealing no damage!\n");  
                    }
                    else if (hero.getDefense() >= monster.getAttack()) //monster attack lower than hero defense
                    {
                            System.out.print(" It fails to penetrate "+hero.getName()+"'s defenses!\n");
                    }                                          
                    else //monster attack higher than hero defense
                    {
                        System.out.print(" It deals "+(monster.getAttack() - hero.getDefense())+" damage!\n");
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
        Main.PutObjectsIntoArrayList(hero, monster, heroMonsterArrayList); //put hero and monster objects back into ArrayList
        return heroMonsterArrayList;
    }

    private int InputValidator(int loopNumber, int savedInput, Hero hero)
    {
        Scanner userDecision = new Scanner(System.in); //used anytime users input is needed alongside scanner
        userDecision.useDelimiter("\n"); //ignores newline entry when user enters something in scanned input below                                               
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
                if (savedInput == 1 || savedInput == 2 || savedInput == 3 || savedInput == 4 || savedInput == 5)
                { //if savedInput has one of the values that fit a case in the switch below, purge an extra line before printing invalid input
                    userDecision.nextLine();                              
                }
                userDecision.nextLine(); //advances scanner to next line, effectively clearing input
                System.out.println("Invalid input, try again."); 
            }
        }       
    return savedInput;
    }
}
