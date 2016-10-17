package com.appdirect;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple DummyClass.
 */
public class DummyClassTest extends TestCase {
    
    private DummyClass testedClass = new DummyClass();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DummyClassTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DummyClassTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( testedClass.sayHello().equals("Hello World!!!") );
    }
}
