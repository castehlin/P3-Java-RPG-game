package assignment1.part1;

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
    Scanner itemDecision;
    ArrayList<Item> itemRemoved;
    Hero hero;
    
    public Inventory()
    {      
        this.itemDecision = new Scanner(System.in);
        this.itemRemoved = new ArrayList<>();
        this.hero = null;
    }
    
   public Hero useItemInInventory(Hero oldHero) //should only be accessed within this class, so private access for better encapsulation
    { //all out of combat interactions with the inventory happen here. Equipping armor/weapons and using consumables.
       
        hero = oldHero; //used to make it easier to keep hero in the loop when changing items in other methods in this class       
        itemDecision.useDelimiter("\n"); //ignores newline entry when user enters something in scanned input below
        int loopNumber = 0;
        int savedInput = 0;
                       
        while (loopNumber != 4) //outer loop that controls repeating entire chain of loops and switches below
                    {
                        for(Item itemSlot: hero.getItemSlots()) //middle loop that iterates over each item in inventory
                        {
                            loopNumber = -1; //set back to -1 for new iterations
                            if (itemSlot.getItemType() != ItemType.USED_ITEM) 
                            { //does not print item out if it was marked for removal in previous iteration
                                System.out.println("\n"+itemSlot); 
                            }                                      
                            if ((itemSlot.getItemType() != ItemType.USED_ITEM && itemSlot.getItemType() != ItemType.COMBAT_ITEM))
                            //items only usable out of combat for now
                            {
                                switch (itemSlot.getItemType()) 
                                {
                                    case ANYTIME_ITEM:
                                    case NO_COMBAT_ITEM:
                                        System.out.println("Would you like to use this item? 1) Yes, 2) No");
                                        break;
                                    case UNEQUIPPED_ARMOR:
                                    case UNEQUIPPED_WEAPON:
                                        System.out.println("Would you like to equip this piece of equipment? 1) Yes 2) No");
                                        break;
                                    case EQUIPPED_ARMOR:  
                                    case EQUIPPED_WEAPON:
                                        System.out.println("You currently have this equipped. Would you like to remove it? 1) Yes 2) No");
                                        break;
                                        default:
                                            break;
                                }
                                                      
                                while (loopNumber != 1) 
                                {
                                    savedInput = InputValidator(savedInput);
                                       
                                    switch (savedInput)
                                    {
                                        case 1: 
                                            switch (itemSlot.getItemType())
                                            {
                                                case ANYTIME_ITEM:
                                                case NO_COMBAT_ITEM: 
                                                    itemSlot.setItemType(ItemType.USED_ITEM); 
                                                    System.out.println(itemSlot);
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
                                                            EquipOrUnequipItem(checkItemSlotforEquippedItems);
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
                                                            EquipOrUnequipItem(checkItemSlotforEquippedItems);
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
                                            loopNumber = 1; //breaks out of inner while loop
                                            break;                                                                                      
                                        case 2: //does not use item, will exit inner while loop and iterate to next item, if there is one
                                            System.out.println("Item put back in inventory.");
                                            loopNumber = 1;
                                            break;                                           
                                        default:
                                            savedInput = -1;
                                            System.out.println("Invalid input, try again.");    
                                            itemDecision.nextLine(); //advances scanner to next line, effectively clearing input                                                                        
                                    }              
                                }                                       
                            }                             
                        }
                        if (hero.getItemSlots().isEmpty()) //is run if user calls this method with nothing in inventory
                        { //exits all loops and returns to method that called this one
                           System.out.println("Bob's inventory is empty.");
                           loopNumber = 3;
                           savedInput = 10;
                        }  
                        else
                        {
                           System.out.println("\nStop viewing inventory? 1) Yes, 2) No");
                        }                                                                           
                        while (loopNumber != 3) 
                        {
                            savedInput = InputValidator(savedInput);
                            
                            switch (savedInput)
                            {
                                case 1: //stop viewing inventory, return to while loop in relevant tile method that called this method and remove items from inventory that were marked to be removed
                                    loopNumber = 3;
                                    savedInput = 10; //used to exit outer while loop
                                    hero.getItemSlots().removeAll(itemRemoved);
                                    break;
                                case 2: //repeat for loop that iterates through inventory ArrayList
                                    loopNumber = 3;
                                    break;
                                default:    
                                    System.out.println("Invalid input, try again.");
                                    savedInput = -1;
                                    itemDecision.nextLine(); //advances scanner to next line, effectively clearing input
                            }       
                        }
                        if (savedInput  == 10) 
                        /*
                           if it equals 10, that means the user wants to exit inventory, thus also meeting condition to break out
                           of outer while loop as well, by setting loopNumberB to 4. This section is included to keep loop exit
                            control to loopNumberB rather than savedInputB as I feel it'd be more confusing if so
                        */
                        {
                            loopNumber = 4; //exit outer while loop
                        }
                    }
        return hero;
    } 
   
   private Item EquipOrUnequipItem(Item item) 
   //pretty straightfoward method, make stats negative/positive of currrent, assign to hero and update item to unequipped/equipped
   {
       int inverseStat = item.getDamageOrAttackOrDefenseOrStatBonusOrHeal();
       int inverseBonusStat = item.getBonusStatModifier();
       
       if(inverseStat > 0)
       {
            inverseStat = -inverseStat;
            System.out.println(item.getSimpleName()+" unequipped.");
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
            System.out.println(item.getSimpleName()+" equipped.");
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
   
   private int InputValidator(int savedInput)
   {
        int loopNumber = 1;
       
        while (loopNumber != 0)
        {
            if (itemDecision.hasNextInt()) 
            {
                savedInput = itemDecision.nextInt(); //sets user input to savedInput 
                loopNumber = 0; //escapes this loop, allowing scanned input via savedInputB to be applied below
            }
            else //user input is invalid, print options again and loop back through if statements again
            {
                if (savedInput != -1) //if previous iteration was valid case 
                {
                    itemDecision.nextLine(); //advances scanner to next line, effectively clearing input 
                }
                savedInput = -1;
                System.out.println("Invalid input, try again.");
                itemDecision.nextLine(); //advances scanner to next line, effectively clearing input                                                  
            } 
       }  
       return savedInput;
   }
}
