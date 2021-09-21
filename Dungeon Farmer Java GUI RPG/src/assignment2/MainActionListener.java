/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 * ActionListener Used only at the start of the game to start a new game, load(not implemented) or quit. 
 * Connects to tile GUI
 * @author chris
 */
public class MainActionListener implements ActionListener
{
    ArrayList<Object> heroMonsterArrayList;
    Gui gui;
    Load load;
    int tileID;
    Save save;
    OutOfCombatAction outOfCombatAction;
    NewGame newGame;
    Combat combat;
    
    public MainActionListener(ArrayList<Object> heroMonsterArrayList,Gui gui, Load load, int tileID, Save save, OutOfCombatAction outOfCombatAction, Combat combat)
    {
        this.combat = combat;
        this.heroMonsterArrayList = heroMonsterArrayList;
        this.newGame = new NewGame();
        this.tileID = tileID;
        this.load = load;
        this.gui = gui;
        this.save = save;
        this.outOfCombatAction = outOfCombatAction;
    }
       
    @Override
    public void actionPerformed(ActionEvent e) 
    {             
        switch(e.getActionCommand())
        {
            case "New Game": 
            {
                try
                { 
                   int userInput = 0;
                   gui.TileGUI(heroMonsterArrayList, userInput, combat);                   
                   break;
                } 
                catch (IOException exception)
                {                   
                }                
                break; //needed?
            }
            case "Load Game":
            {                                
                gui.LoadGUI(load);
                
                try
                {                   
                   load.ReadHeroMonsterArrayListFromFile(0);                       
                } 
                catch (Exception exception)
                {
                }
                
                break;
            }
            case "Exit Game":
            {
               System.exit(0);
            }
        }
    }

    public ArrayList<Object> getHeroMonsterArrayList()
    {
        return heroMonsterArrayList;
    }
    
    

}
