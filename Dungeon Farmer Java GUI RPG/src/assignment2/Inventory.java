package assignment2;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

/**
 * Class that manages adding and removing items from hero's inventory. Consumable item and combat item functionality not implemented
 * yet (You can use consumable items, but they don't do anything, ran out of time to do it). The plan is to seperate items from equipment,
 * once I get around to implementing item effects I will look into that.
 * @author chris
 */
public class Inventory
{
    private ArrayList<Item> itemRemoved;
    Hero hero;
    private String description;
    private int userInput;
    
    public Inventory()
    {
        this.description = "";
        this.itemRemoved = new ArrayList<>();
        this.hero = null;
    }    
    
   public Hero useItemInInventory(Hero oldHero, int index) //should only be accessed within this class, so private access for better encapsulation
    { //all out of combat interactions with the inventory happen here. Equipping armor/weapons and using consumables.
       
        hero = oldHero; //used to make it easier to keep hero in the loop when changing items in other methods in this class             

            Item itemSlot = hero.getItemSlots().get(index);
                                     
            if ((itemSlot.getItemType() != ItemType.USED_ITEM && itemSlot.getItemType() != ItemType.COMBAT_ITEM))
            //items only usable out of combat for now
            {
                if (userInput == 0) //if we are inquiring about item
                {
                    switch (itemSlot.getItemType()) 
                    {
                        case ANYTIME_ITEM:
                        case NO_COMBAT_ITEM:
                            description = (itemSlot+"\nWould you like to use this item?");
                            break;
                        case UNEQUIPPED_ARMOR:
                        case UNEQUIPPED_WEAPON:
                            description = (itemSlot+"\nWould you like to equip this piece of equipment?");
                            break;
                        case EQUIPPED_ARMOR:  
                        case EQUIPPED_WEAPON:
                            description = (itemSlot+"\nYou currently have this equipped. Would you like to remove it?");
                            break;
                    }
                }  
                
                switch (userInput)
                {
                    case 1:     
                    switch (itemSlot.getItemType())
                    {
                        case ANYTIME_ITEM:
                        case NO_COMBAT_ITEM: 
                            itemSlot.setItemType(ItemType.USED_ITEM); 
                            description = (itemSlot.toString());
                            if(itemSlot.getItemType() == ItemType.USED_ITEM)
                            {
                                itemRemoved.add(itemSlot); //added to this ArrayList so a reference can remain to remove the same element from itemSlots later
                            }
                            break;
                        case UNEQUIPPED_ARMOR:
                            for(Item checkItemSlotforEquippedItems: hero.getItemSlots()) 
                            {
                                if (checkItemSlotforEquippedItems.getItemType() == ItemType.EQUIPPED_ARMOR) //unequip existing equipped weapon first
                                {
                                    itemSlot = EquipOrUnequipItem(checkItemSlotforEquippedItems);
                                    break;
                                }
                            }
                            itemSlot = EquipOrUnequipItem(itemSlot);
                            break;
                        case UNEQUIPPED_WEAPON:
                            for(Item checkItemSlotforEquippedItems: hero.getItemSlots()) //unequip existing equipped armor first
                            {                   
                                if (checkItemSlotforEquippedItems.getItemType() == ItemType.EQUIPPED_WEAPON)
                                {                                                            
                                    itemSlot = EquipOrUnequipItem(checkItemSlotforEquippedItems);
                                    break;
                                }                                                                                                      
                            }
                            itemSlot = EquipOrUnequipItem(itemSlot);
                            break;
                        case EQUIPPED_ARMOR:
                        case EQUIPPED_WEAPON: 
                            itemSlot = EquipOrUnequipItem(itemSlot); 
                            break;                                                   
                    }   
                    break;
                    case 2: //does not use item, will exit inner while loop and iterate to next item, if there is one
                        description = ("Item put back in inventory.");
                        break; 
                    default: 
                    {
                        break;
                    }
                }              
                                                                       
            }
        
        if (hero.getItemSlots().isEmpty()) //is run if user calls this method with nothing in inventory
        { //exits all loops and returns to method that called this one
            description = ("Bob's inventory is empty.");
        }                                                                            

        hero.getItemSlots().removeAll(itemRemoved);
        return hero;
    } 
   
   public String getDescription()
   {
       return this.description;
   }
   
   public int getUserInput()
   {
       return this.userInput;
   }
   
   public void setUserInput(int userInput)
   {
       this.userInput = userInput;
   }
   
   private Item EquipOrUnequipItem(Item item) 
   //pretty straightfoward method, make stats negative/positive of currrent, assign to hero and update item to unequipped/equipped
   {
       int inverseStat = item.getDamageOrAttackOrDefenseOrStatBonusOrHeal();
       int inverseBonusStat = item.getBonusStatModifier();
       
       if(inverseStat > 0)
       {
            inverseStat = -inverseStat;
            description = Gui.AddStringToString(description, item.getSimpleName()+" unequipped.");
            switch (item.getItemType())
            {
                case EQUIPPED_ARMOR:
                   item.setItemType(ItemType.UNEQUIPPED_ARMOR);
                   break;
                case EQUIPPED_WEAPON:
                    item.setItemType(ItemType.UNEQUIPPED_WEAPON);                              
            }
        }
        else if (inverseStat < 0)
        {
            inverseStat = Math.abs(inverseStat); 
            description = Gui.AddStringToString(description, item.getSimpleName()+" equipped.");;
            switch (item.getItemType())
            {
                case UNEQUIPPED_ARMOR:
                   item.setItemType(ItemType.EQUIPPED_ARMOR);
                   break;
                case UNEQUIPPED_WEAPON:
                    item.setItemType(ItemType.EQUIPPED_WEAPON);                              
            }            
        }   
       
        if(inverseBonusStat > 0)
        {
            inverseBonusStat = -inverseBonusStat;
        }
        else if (inverseBonusStat < 0)
        {
            inverseBonusStat = Math.abs(inverseBonusStat); 
        }    
       
        item.setDamageOrAttackOrDefenseOrStatBonusOrHeal(inverseStat);
        item.setBonusStatModifier(inverseBonusStat); 
        
        switch(item.getBonusItemEffect())
        {
            case ATK:
                hero.setMaxAttack(hero.getMaxAttack() + item.getDamageOrAttackOrDefenseOrStatBonusOrHeal());
                hero.setAttack(hero.getMaxAttack());
                hero.setAttack(hero.getAttack() + item.getBonusStatModifier());
                break;
             case DEF:
                hero.setMaxDefense(hero.getMaxDefense() + item.getDamageOrAttackOrDefenseOrStatBonusOrHeal());
                hero.setDefense(hero.getMaxDefense());
                break;                           
        }      
        return item;
   }  
}
