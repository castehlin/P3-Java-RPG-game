/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;
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
    
    public Save()
    {
        this.playerHomePath = System.getProperty("user.home");
        this.fileNumber = 0;
        this.maxSaveFiles = 10;
        this.scanner = new Scanner(System.in);    
        this.myFile = new File(playerHomePath+"/Farmer_Dungeon_Save"+fileNumber+".txt"); 
    }
    
    public void saveHeroMonsterArrayListToFile(ArrayList<Object> heromonsterArrayList) throws FileNotFoundException, IOException
    {
        int userInput = 0;   
        OUTER:
        while (myFile.exists()) 
        {
            System.out.println("\nSAVE SLOT:("+fileNumber+"/"+(maxSaveFiles - 1)+")");
            System.out.println("Save already exists at: "+myFile.getAbsolutePath()+".\nOverwrite existing save? 1) Yes, 2) No, 3) Return to previous menu");
            userInput = scanner.nextInt();
            switch (userInput)
            {
            //overwrite existing save
                case 1:
                    FileOutputStream fos = new FileOutputStream(myFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(heromonsterArrayList);
                    oos.close();
                    System.out.println("Saved successfully to "+myFile.getAbsolutePath()+"!");
                    break OUTER; 
            //don't overwrite existing save, save in next spot available
                case 2:
                    if (fileNumber == 0)
                    {
                        fileNumber++;
                    }    
                    myFile = new File(playerHomePath+"/Farmer_Dungeon_Save"+(fileNumber++)+".txt");                 
                    break;
            //exit to previous menu/return to method section that called this method
                case 3:
                    break OUTER;
            //invalid input
                default:
                    System.out.println("Invalid input, try again.");
                    break;              
            }
            if(maxSaveFiles == fileNumber) 
            {
                fileNumber = 1; //cycles save slots back to first slot
            }
        }
        if (!myFile.exists()) //if there is no save in the current save slot (generally only triggered first time around using save)
        {
            System.out.println("\nSAVE SLOT:("+fileNumber+"/"+(maxSaveFiles - 1)+")");
            FileOutputStream fos = new FileOutputStream(myFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);  
            oos.writeObject(heromonsterArrayList);
            oos.close();
            System.out.println("Saved successfully to "+myFile.getAbsolutePath()+"!");  
        }
    }    
}
