package errors;
import java.io.IOException;

public class FlagError extends IOException
{
    public FlagError () // constructor without parameter
    {
        super("error in Hostel application");
    }
    public FlagError (String message)// constructor with parameter
    {
        super (message);
    }
}