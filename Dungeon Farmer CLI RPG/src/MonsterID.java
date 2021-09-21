/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;

/**
 * Used to differentiate monsters when referring to them in a mostly generic way
 * @author chris
 */
public enum MonsterID
{
   NONE(0), MOB_ONE(1), MOB_TWO(2), MOB_THREE(3), CHEST(4), BOSS_ONE(5);
    
    private final int monsterIDno;
    
    MonsterID (int monsterIDno)
    {
        this.monsterIDno = monsterIDno;
    }

    public int getMonsterIDNo()
    {
        return monsterIDno;
    }
}
