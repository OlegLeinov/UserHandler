package com.leinov;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UserHandlerTest
    extends TestCase
{
    public UserHandlerTest(String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( UserHandlerTest.class );
    }

    public void testUserHandler()
    {
        assertTrue( true );
    }
}
