/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;
import java.io.Serializable; //needed to save the hero object and thus item objects that lurk within it
/**
 * Template for all items, from items to be used in the inventory to weapons and armor
 * equipped by the player.
 * @author chris
 */
public class Item implements Serializable
{
    private ItemType itemType; //used to differentiate items, weapons, and armor
    private final String nameAndDescription; 
    private final String simpleName; //for when an entire description of item is unnecessary
    private int damageOrAttackOrDefenseOrStatBonusOrHeal; 
    /* used to represent damage dealt by an item, attack granted by a weapon,
     * defense granted by armor, stat bonus granted by weapon or armor, or 
     * health recovered from an item. 
    */
    private int bonusStatModifier; //is -1 under normal circumstances, but if an item has a secondary stat, it will have a different value
    private final String usedDescriptionAndApplication; //when hero uses item or equips armor, this variable will be printed to user and relevant item effects triggered
    private final ItemType bonusItemEffect;
    
    public Item(ItemType itemType, String nameAndDescription, int damageOrAttackOrDefenseOrStatBonusOrHeal, int bonusStatModifier, String usedDescriptionAndApplication, ItemType bonusItemEffect, String simpleName)
    {
        this.itemType = itemType;
        this.nameAndDescription = nameAndDescription;
        this.damageOrAttackOrDefenseOrStatBonusOrHeal = damageOrAttackOrDefenseOrStatBonusOrHeal;
        this.bonusStatModifier = bonusStatModifier;
        this.usedDescriptionAndApplication = usedDescriptionAndApplication;
        this.bonusItemEffect = bonusItemEffect;
        this.simpleName = simpleName;   
    }

    
    //might not need any of the below? Don't know if I will be needing to refer to specific variable of an item or not. Maybe getNameAndDescription() useful for player to examine item though.
    public ItemType getItemType()
    {
        return itemType;
    }

    public String getNameAndDescription()
    {
        return nameAndDescription;
    }
    
    public String getSimpleName()
    {
        return simpleName;
    }
    
    public int getDamageOrAttackOrDefenseOrStatBonusOrHeal()
    {
        return damageOrAttackOrDefenseOrStatBonusOrHeal;
    }
    
    public void setDamageOrAttackOrDefenseOrStatBonusOrHeal(int damageOrAttackOrDefenseOrStatBonusOrHeal)
    {
        this.damageOrAttackOrDefenseOrStatBonusOrHeal = damageOrAttackOrDefenseOrStatBonusOrHeal;
    }
    
    public int getBonusStatModifier()
    {
        return bonusStatModifier;
    }
    
    public void setBonusStatModifier(int bonusStatModifier)
    {
        this.bonusStatModifier = bonusStatModifier;
    }
   
    public ItemType getBonusItemEffect()
    {
        return bonusItemEffect;
    }
    
    public void setItemType(ItemType itemType)
    {
        this.itemType = itemType;
    }

    @Override
    public String toString()
    {
        if (itemType == ItemType.USED_ITEM)
        {
            return ""+this.usedDescriptionAndApplication;
        }
           return "Item class: "+this.getItemType()+"\n"+this.getNameAndDescription()+"\n";
           //used so player can view relevant info regarding items they get
    } 
}
