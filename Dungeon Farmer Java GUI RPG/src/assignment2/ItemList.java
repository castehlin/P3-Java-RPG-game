/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 * Location of where all item objects are created and stored in an array. 
 * Note, -1 on the bonusStatModifier variable indicates the item does not have a secondary stat and won't be used
 * @author chris
 */
public class ItemList
{
    private Item[] floorOneMobItemList;
    private Item[] floorOneBossItemList;
    private Item[] floorOneChestItemList;
    
    public ItemList(Hero hero)
    {
        this.floorOneMobItemList = createFloorOneMobItemList(hero);
        this.floorOneBossItemList = createFloorOneBossItemList(hero);
        this.floorOneBossItemList = createFloorOneChestItemList(hero);
    }
    
    private Item[] createFloorOneMobItemList(Hero hero)
    {
       Item[] floorOneMobItems = new Item[3]; //3 possible items in pool, 3 excluded until combat items enabled
       floorOneMobItems[0] = (new Item(ItemType.ANYTIME_ITEM, "Healing potion - heals 25% HP.", hero.getMaxHealth() / 4, 0, "Bob drinks the healing potion and recovers some health...or would, if it were programmed to. Instead, it does nothing...neat!", ItemType.NONE, "Healing potion"));
       //floorOneMobItems[x] = (new Item(ItemType.COMBAT_ITEM, "Fire potion - deals 50 damage to target enemy.", 50, 0, "Bob throws the fire potion at the enemy...", ItemType.NONE, "Fire potion"));
       //floorOneMobItems[x] = (new Item(ItemType.COMBAT_ITEM, "Attack boost potion - increases your attack by 10 for the duration of this combat.", 10, 0, "temp", ItemType.NONE, "Attack boost potion"));
       //floorOneMobItems[x] = (new Item(ItemType.COMBAT_ITEM, "Defense boost potion - increases your defense by 10 for the duration of this combat.", 10, 0, "temp", ItemType.NONE, "Defense boost potion"));
       floorOneMobItems[1] = (new Item(ItemType.UNEQUIPPED_WEAPON, "Common Battle Pitchfork - Standard iron pitchfork used by many battle-farmers throughout the world.\nIncreases attack by 15.", -15, 0, "temp", ItemType.ATK, "Common Battle Pitchfork"));
       floorOneMobItems[2] = (new Item(ItemType.UNEQUIPPED_ARMOR, "Lightly Reinforced Farmer's Outfit - Farmer's tunic and pants made of thick wool, with a thin layer of iron on the inside coating of both.\nIncreases defense by 10.", -10, 0, "temp", ItemType.DEF, "Lightly Reinforced Farmer's Outfit"));
       
       return floorOneMobItems;
    }
    
    private Item[] createFloorOneBossItemList(Hero hero)
    {
       Item[] floorOneBossItems = new Item[4]; //4 possible items in pool
       floorOneBossItems[0] = (new Item(ItemType.ANYTIME_ITEM, "Elixir - Fully heals health.", hero.getMaxHealth(), 0, "temp", ItemType.NONE, "Elixir")); 
       floorOneBossItems[1] = (new Item(ItemType.UNEQUIPPED_WEAPON, "Fiery Pitchfork - A steel pitchfork with a fiery enchantment imbued upon it. Increases attack by 30.", -30, 0, "temp", ItemType.ATK, "Fiery Pitchfork")); 
       floorOneBossItems[2] = (new Item(ItemType.UNEQUIPPED_ARMOR, "Blackrock Farmer's Armor - Farmer's tunic and pants that is more rock than clothing.\nThis armor is made of a thin inner lining of wool and a thick outer layer of sturdy but inflexible blackrock.\nIncreases defense by 30, but decreases attack by 15.", -30, 15, "atkminus", ItemType.DEF, "Blackrock Farmer's Armor"));
       floorOneBossItems[3] = (new Item(ItemType.NO_COMBAT_ITEM, "Combat Serum - Permanently increases attack and defense by 5.", 5, 5, "temp", ItemType.DEF, "Combat Serum"));
       
       return floorOneBossItems;
    }
    
    private Item[] createFloorOneChestItemList(Hero hero)
    {
       Item[] floorOneChestItems = new Item[2]; //2 possible items in pool
       floorOneChestItems[0] = (new Item(ItemType.ANYTIME_ITEM, "Elixir - Fully heals health.", hero.getMaxHealth(), 0, "temp", ItemType.NONE, "Elixir"));
       floorOneChestItems[1] = (new Item(ItemType.ANYTIME_ITEM, "Healing potion - heals 25% HP.", hero.getMaxHealth() / 4, 0, "temp", ItemType.NONE, "Healing potion"));
       
       return floorOneChestItems;
    } 
    
    public Item[] getFloorOneMobItemList()
    {
        return floorOneMobItemList;
    }
    
    public Item[] getFloorOneBossItemList()
    {
        return floorOneBossItemList;
    }
    
    public Item[] getFloorChestItemList()
    {
        return floorOneChestItemList;
    }
}
