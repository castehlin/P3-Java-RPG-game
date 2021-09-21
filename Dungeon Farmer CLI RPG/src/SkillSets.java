/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;

import java.util.ArrayList;

/**
 * Class that creates an ArrayList of skill objects and assigns them to hero/monster objects.
 * @author chris
 */
public class SkillSets
{          
    /*
      Left out a constructor because outside of putting assignSkillsToHeroAndMonsters() into it (which I can't because
      I can't return anything in a constructor) there didn't seem to be a point. Maybe this is bad design, I don't know. 
    */
    
    private ArrayList<Skill> createHeroSkillSet(Hero hero)
    {
        //Bob the Farmer's skills
       ArrayList<Skill> heroSkills = new ArrayList<>(4);
       heroSkills.add(new Skill("Inspect creature - the farmer examines the enemy closely, using his years of experience of performing livestock health checks to determine the state of the enemy.\nReveals the enemy's max health, and how much health it has remaining. Persists until end of combat.", 0, 0, 0, true, "Inspect creature")); 
       heroSkills.add(new Skill("Pitchfork Piston Poke - A battle-farmer classic.\n Pokes the target enemy with the sharp end of the pitchfork repeatedly. Does 2x regular attack damage. 3 turn cooldown.", hero.getAttack() * 2, 0, 3, false, "Pitchfork Piston Poke"));
       heroSkills.add(new Skill("Haystorm - an unorthodox technique used in desperation by farmers in a pinch.\n Throws handfuls of hay at the target enemy, blinding them. 100% chance to avoid the next attack of affected enemy. 4 turn cooldown.", 0, 0, 4, false, "Haystorm"));
       //complex skill that will need interaction with each monster object to reduce their attack...come back to it
       
       return heroSkills;
    }
    
    private ArrayList<Skill> createMonsterSkillSet(Monster monster)
    {
        switch (monster.getMonsterID())
        {
            case MOB_ONE:
                ArrayList<Skill> mobOneTileBSkillSet = new ArrayList<>(2);
                mobOneTileBSkillSet.add(new Skill("Fiery power - Imp powers itself up, increasing attack for this combat by 10. 1 turn cooldown.", 10,  0, 2, true, "Fiery power"));
                //need to make sure that this skill increases attack + maxAttack of the monster unit that uses it by 10, not just attack. Also doesn't attack hero when used.
                mobOneTileBSkillSet.add(new Skill("Flameball - Imp throws small ball of flame at hero, dealing 15 damage.", 15, 0, 0, true, "Flameball"));             
                return mobOneTileBSkillSet;
            case MOB_TWO:
                ArrayList<Skill> mobTwoTileCSkillSet = new ArrayList<>(2);
                mobTwoTileCSkillSet.add(new Skill("Frenzied counterattack - Giant rat retaliates with flurry of its claws, dealing 50 damage. 4 turn cooldown, requires hero to have attacked the previous turn.", 50,  2, 4, true, "Frenzied counterattack"));
                mobTwoTileCSkillSet.add(new Skill("Bite - The giant rat bites at any body part of the hero that it can reach, dealing 20 damage. ", 20,  0, 0, true, "Bite"));             
                return mobTwoTileCSkillSet;
            case MOB_THREE:
                ArrayList<Skill> mobThreeTileDSkillSet = new ArrayList<>(2);
                mobThreeTileDSkillSet.add(new Skill("Flaming arrow - The skeleton archer shoots a flaming arrow, which is super effective against non-defending enemies. Deals 30 damage, ignores defense\n"
                        + ", unless hero is defending, in which case it doesn't ignore defense. No cooldown. It is random which ability is used.", 30,  0, 0, true, "Flaming arrow"));
                mobThreeTileDSkillSet.add(new Skill("Aimed shot - The skeleton archer lines up a carefully placed arrow, which is super effective against stationery (defending) enemies. The skeleton archer is interrupted however,"
                        + " if it is attacked the turn it plans on using it. Deals 80 damage. No cooldown. It is random which ability is used.", 80,  0, 0, true, "Aimed shot"));
                return mobThreeTileDSkillSet;
            default:
                break;
        } 
        return null; 
    }    
    
    public ArrayList<Object> assignSkillsToHeroAndMonsters(ArrayList<Object> heroMonsterArrayList) 
    {   
        ArrayList <Skill> heroSkillSet; 
        ArrayList <Skill> monsterSkillSet;
        
        for(int i = 0; i < heroMonsterArrayList.size(); ++i)
        {
            if(heroMonsterArrayList.get(i) instanceof Hero)
            {     
                Hero hero = (Hero) heroMonsterArrayList.get(i); //local hero variable that is set to values that the hero in the arraylist has
                heroSkillSet = createHeroSkillSet(hero); 
                /*
                  Sets the local skillSet ArrayList heroSkillSet to the skills found in createHeroSkillSet(Hero hero). This step is 
                  needed because without hero being called by createHeroSkillSet, references to hero getters/setters isn't possible.
                */

                hero.setSkillSet(heroSkillSet); //sets hero's skillset to the sklls in heroSkillSet
                heroMonsterArrayList.set(i, hero); 
                /*
                  Sets the element in the ArrayList that contains the hero object with no skills to the local one that now has
                  the skills from createHeroSkillSet(Hero hero).
                */
            } 
            else if(heroMonsterArrayList.get(i) instanceof Monster) //exactly the same as above, but for all monster objects instead
            {
               Monster monster = (Monster) heroMonsterArrayList.get(i); 
               monsterSkillSet = (createMonsterSkillSet(monster)); 
               monster.setSkillSet(monsterSkillSet);
               heroMonsterArrayList.set(i, monster);
            }
        }
        return heroMonsterArrayList;
    }
}


 
