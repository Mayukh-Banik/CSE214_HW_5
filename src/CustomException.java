// Mayukh Banik CSE 214 mayukh.banik@stonybrook.edu R03 Homework 5
public class CustomException extends Exception
{
    /**
     * custom exception
     */
    public CustomException()
    {
        super();
    }

    /**
     * custom exception with string output
     * @param string output
     */
    public CustomException(String string)
    {
        super(string);
    }

}
