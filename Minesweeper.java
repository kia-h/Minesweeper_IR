/**
 *  This class is responsible for reading inputs, initialising the board and playing the game.
 *  It interacts with Board object, and manipulate the fields through Board 
 */

import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

public class Minesweeper 
{

//Instance Variables

    //initial board
	private Board initialBoard;
	
    //holds the board after mined revealed
    private Board resultBoard; 

    //bord height
    private int rows;

    //borad width
    private int columns;

//Methods

    /**
     * Sets up the board, initial and result boards 
     *
     * @param rows     height off the board 
     * @param columns   width of the board 
     * @return            N/A 
     * @exception         throws MyException for invald inputs
     */   
	public void setupBoards(int rows, int columns) 
    {
		
       initialBoard = new Board(rows,columns);
       resultBoard  = new Board(rows,columns);       
	}
    
    /**
     * Gets the initial Board 
     *
     * @param N/A
     * @return            initialBoard
     */   
	public Board getInitialBoard() 
    {
        return initialBoard;
    }

    /**
     * Gets the result Board 
     *
     * @param N/A
     * @return            resultBoard
     */ 
	public Board getResultBoard() 
    {
        return resultBoard;
    }

    /**
     * Read board data from given file name 
     *
     * @param       fileName, passed input file name 
     * @return      String Array containing fields data of the board
     * @exception   throws FileNotFoundException, IOException
     */ 
	public String[] readFromFile(String fileName) throws FileNotFoundException, IOException
    {
        
		//holds the size of the board
        String[] tokenizedLine =null;

        //to save the line read from file
		String line = null;

		//holds fields data line by line
        String[] fieldsData;
		
		try 
        {
			//to read the data file
            FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

            //read the board size
			line=bufferedReader.readLine();

            //first line is board size, 2 integer seperated by space
			tokenizedLine = line.split(" ");

            // first one is the Board height(number of rows)
            rows    = Integer.parseInt(tokenizedLine[tokenizedLine.length - 2]);

            //second one is the Board width (number of columns)
			columns = Integer.parseInt(tokenizedLine[tokenizedLine.length - 1]);

            //set up our boards,i.e. initialBoard and resultBoard
            this.setupBoards(rows,columns);

            //read the rest of file and save the fields data
            int i=0;	
            fieldsData = new String[rows];

            //as long as there is data in input file
            while((line=bufferedReader.readLine())!= null) 
            {
                if(line.trim().length() > 0) 
                {
                    fieldsData[i] = line;
                    i++;
                }
            }

            bufferedReader.close(); 
            return fieldsData;
		}//end of try block

		catch(FileNotFoundException ex) 
        {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) 
        {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
        //we shouldn't reach here
        return null;
	}//end of readFromFile

    /**
     * Tokeinze the fields data and initialise the board fields with data read from input file. it shows the result board
     *
     * @param       values, holds the fields data 
     * @return      N/A
     */ 
     public void updateBoardFromFile(String[] values) 
     {

        //to hold the whole line
        String line;

        //to hold each character 
        char[] tokenized;

        try 
        {
            for (int i=0;i<rows;i++) 
            {
                //get the characters as far as the board width(columns) from the read data
                line = values[i].substring(0,columns);

                //convert it into an array of characters
                tokenized = line.toCharArray();

                //set the board fields data
                for (int j=0;j<columns;j++) 
                {
                    initialBoard.setFieldAt(i,j,String.valueOf(tokenized[j]));
                }
            }

            //show revealed borad
            this.showMines();           

        }//end of try block
        catch (MyException e) 
        {
            System.out.println("Updating board failed!");
        }
    }//end of method

    /**
     * it reads data from input file and prepare the result board
     * it calls 2 other methods: updateBoardFromFile() and readFromFile()
     * first method uses the outpput of second method as its input
     *
     * @param       fileName, input file name 
     * @return      N/A
     * @exception   FileNotFoundException, IOException
     */ 
    public void readFromFileAndUpdate(String fileName) throws FileNotFoundException, IOException
    {
            this.updateBoardFromFile(this.readFromFile(fileName));
    }

    /**
     * Writes the result board with  number of adjacent mines to a file
     *
     * @param       outputFileName, output file name 
     * @return      N/A
     * @exception   FileNotFoundException, IOException, NullPointerException, IllegalArgumentException
     */ 
    public void writeToFile(String outputFileName) throws FileNotFoundException, IOException, NullPointerException,IllegalArgumentException
    {
        File file = new File(outputFileName);
        file.createNewFile();
        if (file == null) 
        {            
            throw new NullPointerException("File must not be null.");
        }
        if (!file.isFile()) 
        {
          throw new IllegalArgumentException("Must not be a directory: " + file);
        }

        BufferedWriter writer = null;

        //holds the whole result board
        String temp = getResultBoard().toString();
        try 
        {
            writer = new BufferedWriter((new OutputStreamWriter( new FileOutputStream(outputFileName), "utf-8")));
            if (temp.equals("\n"))
                writer.write("\n");
            writer.write(temp);
        }
        catch(FileNotFoundException ex) 
        {
            System.out.println("Unable to open file '" + file.getName() + "'");                
        }
        catch(IOException ex) 
        {
            System.out.println("Error in writing file '" + file.getName() + "'");                  
        }
        finally 
        {
            if(writer != null) 
            {
                try 
                {
                    writer.close();
                } 
                catch (IOException e) 
                {
                    System.out.println("Error in closing file '" + file.getName() + "'");                  
                }
            }
        }
    }

    /**
     * Reads input from console if user choose to do so, and it shows the result afterward
     *
     * @param       N/A 
     * @return      N/A
     * @exception   throws MyException if input was out of bound(0-100)
     *              throws IOException 
     */ 
	public void readInputFromConsole() throws MyException, IOException
    {
        //to read the user input
        Scanner input = new Scanner(System.in);
        
        System.out.println ("Enter size of the board <x y> :");
        
        //number of rows and columns of the board
        rows    = input.nextInt();
        columns = input.nextInt();

        //check if inputs are in range (0<x<100)
        if (rows<0 || columns<0 || rows>100 || columns>100) 
        {
            throw new MyException("Invalid Input!. numbers must be between 0-100.\ne.g. 4 5 - wih space between 2 numbers.");
        }

        //inputs are good, set up the boards
        this.setupBoards(rows,columns);
        
        //prepare to read the rest of data from console
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println ("Enter each field status(. for safe, * for mined) ");
        System.out.println ("Other entered characters will be interpreted as .");
        System.out.println ("At end of each line press <Enter>");

        int linesNumber=0;

        //holds the read line from console
        String line;
        String temp;
        char[] fieldsData;
        
        //read user input 
        while((line = br.readLine()) != null && linesNumber<rows) 
        {
            if(line.trim().length() > 0) 
            {
                //only get as much data we need i.e. board size, and ignore the rest
                temp = line.substring(0,columns);
                fieldsData = temp.toCharArray();
                     
                //update the board based on the given inputs
                for(int j=0;j<columns;j++) 
                {
                    initialBoard.setFieldAt(linesNumber,j,String.valueOf(fieldsData[j]));
                }
                linesNumber++;                   
            }
        }

        //show revealed borad
        this.showMines();
	}

    /**
     * Shows the revealed board
     *
     * @param       N/A 
     * @return      N/A
     */ 
    public void showMines() 
    {
        try 
        {
            for (int i=0;i<rows;i++) 
            {
                for(int j=0;j<columns;j++) 
                {
                    if(initialBoard.getFieldAt(i,j).getStatus().equals("*")) 
                    {
                        resultBoard.setFieldAt(i,j,"*");
                    }
                    else 
                    {
                        resultBoard.setFieldAt(i,j,String.valueOf(initialBoard.neighbours(i,j)));
                    }
                }
            }

            System.out.println(resultBoard.toString());
        }
        catch (MyException e) 
        {
            System.out.println("Error in calculating adjacent mined fields!");
        }
    }
    
}