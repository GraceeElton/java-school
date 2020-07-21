package com.lambdaschool.schools.Excepions;

public class ResourceFoundException extends RuntimeException
{
    public ResourceFoundException(String message)
    {
        super("Error from Gracee's Application: " + message);
    }
}
