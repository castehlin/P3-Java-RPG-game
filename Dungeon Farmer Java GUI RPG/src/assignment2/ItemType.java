/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 * Used to differentiate items, weapons and armor, which will have some differing variables 
 * as well as applications.
 * @author chris
 */
public enum ItemType 
{
    COMBAT_ITEM, ANYTIME_ITEM, NO_COMBAT_ITEM, USED_ITEM, EQUIPPED_WEAPON, EQUIPPED_ARMOR, UNEQUIPPED_ARMOR, UNEQUIPPED_WEAPON, 
    ATK, DEF, NONE;
    /*
      From start to end: Item that is combat only, item that can be used in or out of combat, item that can only be used
      out of combat, item has been used, weapon that is equipped to hero, armor that is equipped to hero, armor that is not equipped to hero (default
      for all armor not currently worn by hero), weapon that is not equipped to hero (default for all weapons not currently worn by hero), 
      item effects atk, item effects def, or it has no effect (default).
    */
}
