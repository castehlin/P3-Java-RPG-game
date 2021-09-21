/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * ActionListener that serves to manage the buttons pressed for the inventory GUI. 
 * @author chris
 */
public class InventoryActionListener implements ActionListener
{
    Inventory inventory;
    Gui gui;
    ArrayList<Object> heroMonsterArrayList;
    int index;
    Combat combat;
    
    public InventoryActionListener(Inventory inventory, Gui gui, ArrayList<Object> heroMonsterArrayList, int index, Combat combat)
    {
        this.combat = combat;
        this.index = index;
        this.heroMonsterArrayList = heroMonsterArrayList;
        this.inventory = inventory;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        
      switch(e.getActionCommand())
      {
            case "Use item": //use item, cycle to next item
            case "Equip item":
            case "Unequip item":
            {
                try
                {
                    Hero hero = NewGame.getHero(heroMonsterArrayList); //get hero                                     
                        
                   inventory.setUserInput(1); //set inventory variable to use item
                   inventory.useItemInInventory(hero, index); //call inventory method to use item
                   NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList); //readd hero to arrayList
                   inventory.setUserInput(0); //reset userInput variable  
                   index++;
                   gui.InventoryGUI(heroMonsterArrayList, index); //return arrayList 
                } 
                catch (Exception ex)
                {
                }
                break;
            }
            case "Cycle to next item": //cycle to next item, don't use current item
            {
                try
                {
                   inventory.setUserInput(2); //set inventory variable to cycle to next item
                   Hero hero = NewGame.getHero(heroMonsterArrayList); //get hero                                    
                   inventory.useItemInInventory(hero, index); //call inventory method to use item
                   NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList); //readd hero to arrayList 
                   inventory.setUserInput(0); //examine next item
                   index++;
                   gui.InventoryGUI(heroMonsterArrayList, index); //return arrayList 
                } 
                catch (Exception ex)
                {
                }
                break;
            }
            case "Stop viewing inventory": //go back to TileGUI
            {
                try
                {
                   inventory.setUserInput(3); //set inventory variable to cycle to next item
                   Hero hero = NewGame.getHero(heroMonsterArrayList); //get hero                                    
                   inventory.useItemInInventory(hero, index); //call inventory method to use item
                   NewGame.PutObjectsIntoArrayList(hero, heroMonsterArrayList); //readd hero to arrayList
                   inventory.setUserInput(0); //examine next item  
                   int userInput = 0;
                   gui.TileGUI(heroMonsterArrayList, userInput, combat); //return arrayList 
                } 
                catch (Exception ex)
                {
                }
                break;
            }
     }
    }
    
}
