/**
 *  This class represents the single field on the board
 *  it only has one property, status, which can be . or *
 */

public class Field
{

//Instance Variables

    //status of the field (. or * or number of adjacent mined fields)
    private String status; 
    
//Methods

    //Constructor
    public Field(String status)
    {
        this.status=status;    
    }
  
//getter and setter

    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String newStatus)
    {
        status=newStatus;
    }

}//end of class