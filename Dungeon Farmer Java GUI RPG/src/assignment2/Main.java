/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Main class that instantiates the gui class that is the main class of this project, as well as the NewGame 
 * class, which instantiates most objects used.
 * @author chris
 */
public class Main 
{    
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        NewGame newGame = new NewGame();
        Gui gui = new Gui(newGame);       
        gui.MainGUI(gui);
    } 
}
