/**
 *  This class represents the board with collection of fields objects.
 *  each board has 2 properties: lines (board height) and columns(board width)
 */

public class Board
{

//Instance Variables
    //mine fields
    private Field[][] fields;

    //board size .i.e. lines(board height) and columns(board width)
    private int lines;
    private int columns;

//Methods

     /**
     * Initialises the board fields 
     *
     * @param xPosition x position of the field 
     * @param yPosition x position of the field 
     * @return            N/A 
     * @exception         throws MyException for invald inputs
     */   
    private void initialise() 
    {
        //loop through the fields and set the status to default value (-1)
        for (int row =0 ; row < lines; row++) 
        {
            for (int col =0 ; col < columns; col++) 
            {
                fields[row][col] = new Field(String.valueOf("-1"));                
            }
        }

    }
    
    //Constructor
    public Board (int lines, int columns)
	{
       fields = new Field[lines][columns];
       this.lines = lines;
       this.columns = columns;
       initialise();        
    }
    
    /**
     * Calculates the number of mines adjacent to given position
     *
     * @param xPosition x position of the field 
     * @param yPosition x position of the field 
     * @return            number of mined field adjacent to the given position
     * @exception         throws MyException for negative numbers
     */
    public int neighbours(int xPosition, int yPosition) throws MyException
    {
        if (xPosition <0 || yPosition <0 ) 
        {
           throw new MyException("Positions must be positive numbers.");
        }
        // Number of mines
        int minesCount = 0;

        //Loop through the fields array of the board to calculate number of mines for the given position 
        for(int row=xPosition-1; row <=xPosition+1; row++)
        {
            for(int col=yPosition-1; col<=yPosition+1; col++)
            {
                //check to see if the given positions are in bound of the board size
                if(!(xPosition==row && yPosition==col) && row>=0 && col >=0 && row<lines && col<columns )
                {
                    //if we hit the mine field increase the mine count, else move on to next field
                    if ((fields[row][col]).getStatus().equals("*")) 
                    {
                        minesCount++;
                    }
                }
            }
        }
        return minesCount;
    }

    /**
     * Gets the field at a given position
     *
     * @param xPosition x position of the field 
     * @param yPosition x position of the field 
     * @return            the field at the given position, otherwise returns null 
     * @exception         throws MyException for negative numbers
     */
    public Field getFieldAt(int xPosition, int yPosition) throws MyException
    {   if (xPosition < 0 || yPosition <0) 
        {
            throw new MyException("Positions must be positive numbers.");
        }
        return fields[xPosition][yPosition];
    }

    /**
     * Sets the field at a given position
     *
     * @param xPosition x position of the field 
     * @param yPosition x position of the field 
     * @return            N/A 
     * @exception         throws MyException for invald inputs
     */   
    public void setFieldAt(int xPosition, int yPosition, String value) throws MyException
    {   
        if (value.length() < 0 || value== null || xPosition < 0 || yPosition <0) 
        {
           throw new MyException("Invalid Inputs. Check your inputs and try again.");
        }
        fields[xPosition][yPosition].setStatus(String.valueOf(value));
    }
  
    /**
     * Returns the baord fields as a String 
     *
     * @param       N/A
     * @return      N/A 
     */
    public String toString() 
    {
        //to hold the board fields data
        StringBuilder result =new StringBuilder();

        // loop through and append the fields data to result string
        for (int i=0;i<lines;i++) 
        {
            for(int j=0;j<columns;j++) 
            {
                //get the field staus and append it to the result
                result.append((fields[i][j]).getStatus());
            }

            //go to next line at the end of each line
            result.append("\n");
        }
        return result.toString();
    }

}//End of class
  