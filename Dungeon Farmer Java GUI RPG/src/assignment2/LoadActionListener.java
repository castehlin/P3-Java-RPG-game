/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Load action listener that is used to load saves...though I didn't finish it (Didn't see till later that
 * there is no file I/O requirement for this one, which is why the save gui is up and running).
 * @author chris
 */
public class LoadActionListener implements ActionListener
{
    Gui gui;
    JButton[] buttonsList;
    String description;
    Load load;
    
    public LoadActionListener(Gui gui, JButton[] buttonsList, String description, Load load)
    {
        this.load = load;
        this.gui = gui;
        this.buttonsList = buttonsList;
        this.description = description;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "Load this File":
            {
                try
                {
                    System.out.println("Load section not complete, exiting.");
                    System.exit(0);
                    load.ReadHeroMonsterArrayListFromFile(1);
                    description = load.getDescription();
                    break;
                    //need to take save file to new game start from here...
                } 
                catch (Exception ex)
                {
                }               
            }
            case "Cycle to next save slot":
            {
                try
                {
                    System.out.println("Load section not complete, exiting.");
                    System.exit(0);
                    load.ReadHeroMonsterArrayListFromFile(2);
                    description = load.getDescription();
                    gui.LoadGUI(load);
                } 
                catch (Exception ex)
                {
                }   
            }
            case "Exit Game":
            {
                System.exit(0);
            }
        }           
    }
}
