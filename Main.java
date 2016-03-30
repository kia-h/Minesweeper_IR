import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;

public class Main {

    public static void main(String args[]) throws IOException, FileNotFoundException
    {
        Scanner input       = new Scanner(System.in);
        Minesweeper game    = new Minesweeper();

        //holds the revealed board
        String outputFile   = new String("result.txt");
        
        boolean flag = true;
        try
        {
            while (flag) 
            {
                //let the user know bout the options
                System.out.println("1- Enter input in the console.\n2- Enter input file name.\n3- Exit.");

                int choice = 0;
            
                choice = input.nextInt();
                switch (choice) 
                {
                    case 1: 
                //read from console
                        game.readInputFromConsole();
                        break;
                    case 2:
                //read from file
                        System.out.println("Reading from file");
                        System.out.println("Enter the input file name. Must be in the same directory as the program.");
                        String fileName = input.next();

                        game.readFromFileAndUpdate(fileName);
                        game.writeToFile(outputFile);
                        break;
                    case 3: 
                //exit
                        System.out.println("exiting minesweper");
                        flag=false;
                        break;
                    default: 
                        System.out.println("bad input or invalid choice!.");
                        break;
                }//end of switch
            }//end of while loop
        }//end of try block
 
        catch(FileNotFoundException ex) 
        {
            System.out.println("main");
            System.out.println("File not found.");                
        }
        catch(IOException ex) 
        {
            System.out.println("Error in writing/reading");                  
        }
        catch (NullPointerException ex)
        {
            System.out.println("Error in opening file.");    
        }
        
        catch (Exception ex) 
        {
            System.out.println("Error! highly experienced ninjas are working on the issue!");
        }    
      
    }//end of main method  
}