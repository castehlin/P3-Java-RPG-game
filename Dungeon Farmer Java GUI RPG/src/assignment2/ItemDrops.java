/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.Random;

/**
 * Class that serves as a random number generator for returning an item to the hero from the loot table array each monster has.
 * @author chris
 */
public class ItemDrops
{
    private Random rng;
    private String description;
    
    public ItemDrops()
    {
        this.rng = new Random();
    }
    
    public Item getItemDrop(Monster monster, Hero hero) 
    //Uses random to take copy of item out of itemArray of monster and returns it
    {
        int lootDrop = rng.nextInt(monster.getItemDrops().length); 
            
        for(int i = 0; i < monster.getItemDrops().length; ++i)
        {
            if(i == lootDrop) 
            {
                    Item item = monster.getItemDrops()[i];  //this is the item that will be returned and given to hero
                    description = ("\n"+monster.getName()+" dropped "+item.getSimpleName()+"! \n"+item.getSimpleName()+" is added to "+hero.getName()+"'s inventory.");
                    return item; 
            }
        }  
        return null; 
    }
    
    public String getDescription()
    {
        return this.description;
    }
}