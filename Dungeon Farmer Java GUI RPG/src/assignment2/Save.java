/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Using Serializable, FileOutputStream and ObjectOutputStream, the entire heroMonsterArrayList object is stored as a save.
 * .txt file. Only the hero object needs to be saved to keep adventure progress, as tile/advancement progress is tied to 
 * tileStatus in the hero class.
 * @author chris
 */
public class Save
{
    private final String playerHomePath; //will save it to the user home folder regardless of OS
    private int fileNumber;
    private final int maxSaveFiles; //only allows 10 saves, makes things easier to cycle through load saves, also seems more appropriate for a RPG
    private Scanner scanner;
    private File myFile;
    private String description;
    private int userInput;
    
    
    public Save()
    {
        this.playerHomePath = System.getProperty("user.home");
        this.fileNumber = 0;
        this.maxSaveFiles = 10;
        this.scanner = new Scanner(System.in);    
        this.myFile = new File(playerHomePath+"/Farmer_Dungeon_Save"+fileNumber+".txt"); 
    }  

    public String getDescription()
    {
        switch(userInput)
        {
            case 1: //yes
            {
                description = ("Saved successfully to "+myFile.getAbsolutePath()+"!");
            }
                break;  
            case 2: // no, fileNumber should be iterated when this is called
            {
                description = ("SAVE SLOT:("+fileNumber+"/"+(maxSaveFiles - 1)+")\n"
                    + "Save already exists at: "+myFile.getAbsolutePath()+"."
                    + "\nOverwrite existing save? NOTE, I left this in, but I didn't find out"
                        + "that we weren't being marked on file input/output till I finished this"
                        + "class So figured I'd leave it in even if I hadn't finished load.");
            }
                break;
            default:
            {
                //is there a file in the current slot?
                if(myFile.exists()) //yes
                {
                    description = ("SAVE SLOT:("+fileNumber+"/"+(maxSaveFiles - 1)+")\n"
                        + "Save already exists at: "+myFile.getAbsolutePath()+"."
                        + "\nOverwrite existing save?");
                }
                else if (!myFile.exists()) //no
                {
                    description = ("SAVE SLOT:("+fileNumber+"/"+(maxSaveFiles - 1)+")\n"
                        + "Saved successfully to "+myFile.getAbsolutePath()+"!"); 
                } 
            }
                break;
        }
       return description;
    }
    
    public void saveHeroMonsterArrayListToFile(ArrayList<Object> heromonsterArrayList, int userInput) throws FileNotFoundException, IOException
    {
        //update save value
        this.userInput = userInput;
        
       //is there a file in the current slot?
       if(myFile.exists()) //yes 
       {           
           switch (userInput)
           {
               case 1: //overwrite save
               {
                    FileOutputStream fos = new FileOutputStream(myFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(heromonsterArrayList);
                    oos.close();  
               }
               break;
                case 2: //don't overwrite existing save, save in next spot available
                if (fileNumber == 0)
                {
                    fileNumber++;
                    
                }    
                myFile = new File(playerHomePath+"/Farmer_Dungeon_Save"+(fileNumber++)+".txt");                 
                break;
           }
       }
        if (!myFile.exists()) //if there is no save in the current save slot (generally only triggered first time around using save)
        {
            FileOutputStream fos = new FileOutputStream(myFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);  
            oos.writeObject(heromonsterArrayList);
            oos.close(); 
        }
        if(maxSaveFiles == fileNumber) 
        {
            fileNumber = 1; //cycles save slots back to first slot
        }
       userInput = 0; //reset userInput value
    } 
}
