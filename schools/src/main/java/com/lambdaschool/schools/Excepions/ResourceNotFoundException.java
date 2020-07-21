package com.lambdaschool.schools.Excepions;

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String message)
    {
        super("Error from Gracee's Application: " + message);
    }
}
