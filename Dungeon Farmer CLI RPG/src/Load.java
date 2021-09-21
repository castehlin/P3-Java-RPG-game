/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.part1;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
;
import java.util.Scanner;
/**
 * Load class, loads saved object ArrayList, containing hero/monster objects and all their related objects.
 * @author chris
 */
public class Load
{
  private final String playerHomePath = System.getProperty("user.home"); 
  private int fileNumber = 0; 
  private final int maxSaveFiles = 10; 
  private Scanner scanner = new Scanner(System.in);
  private int userInput = 0;
  private File myFile = new File(playerHomePath+"/Farmer_Dungeon_Save"+fileNumber+".txt");
  
  public ArrayList<Object> ReadHeroMonsterArrayListFromFile()throws FileNotFoundException, IOException, ClassNotFoundException
  {     
      ArrayList<Object> heroMonsterArrayList = new ArrayList<>();
           
      OUTER: //label to allow breaks to escape out of both the switch and the loop if they are labelled break OUTER;
      while (true) 
      {
          if (myFile.isFile()) 
          {
              System.out.println("\nSAVE SLOT:("+fileNumber+"/"+(maxSaveFiles - 1)+")");
              System.out.println("Load "+myFile.getAbsolutePath()+"? 1) Yes, load this file, 2) No, cycle to next save slot, 3) Return to main menu");
              userInput = scanner.nextInt();
              switch (userInput)
              {
              //if user wants to load this file
                  case 1:
                      FileInputStream fis = new FileInputStream(myFile);
                      ObjectInputStream ois = new ObjectInputStream(fis);
                      Object obj = ois.readObject(); //reads object (which is the Hero object that is saved) from text file in filepath and saves it to local Object object.
                      ois.close(); //stop reading file
                      heroMonsterArrayList = (ArrayList<Object>) obj; //obj read from file is a saved Hero object, so using casting to assign it to a local Hero object.                      
                      System.out.println("Loaded successfully from "+myFile.getAbsolutePath()+"!");
                      break OUTER;
              //if the above file was not chosen to be loaded, cycle to next save slot by incrementing fileNumber
                  case 2:
                      if (fileNumber == 0)
                      {
                         fileNumber++;
                      }                    
                      myFile = new File(playerHomePath+"/Farmer_Dungeon_Save"+(fileNumber++)+".txt"); //increments the number in the save name by 1 
                      break;
              //exit to main menu
                  case 3:
                      break OUTER;
                  default:
                      System.out.println("Invalid input, try again."); 
              }
          } 
          else //file doesn't exist....forgot to sort this out before the deadline, so just exiting program once we've iterated through all the loads
          {
              System.out.println("No loads selected. Closing game.");
             System.exit(0);
          } 
          
          if(maxSaveFiles == fileNumber) //if user iterated to save slot 10
          {
              fileNumber = 0; //cycles save slots back to first slot
          }
      }
      
      //can only get here if they loaded an existing save by entering 1 or entered 3 to exit loading a file
      if (userInput == 1) //if they loaded save
      {
          return heroMonsterArrayList; //returns hero with loaded save's values.
      }
      else //if they wanted to exit to main menu and entered 3
      {
          return null; 
          /*
            Back to main menu with hero equaling null. It will be overwritten by the hero object instantiation if they choose to 
            load a save file by coming here again, or by starting a new game anyway.
          */
      }
  }
}
