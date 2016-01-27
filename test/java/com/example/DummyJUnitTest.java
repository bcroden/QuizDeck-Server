package com.example;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Added to check JUnit integration
 */
public class DummyJUnitTest {
    @Test
    public void dummyTest() {
        assertThat("Dummy Error message", new Object(), not(is(new Object())));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void exceptionTest() throws ArrayIndexOutOfBoundsException {
        throw new ArrayIndexOutOfBoundsException();
    }

    @Test
    public void accessTest() {
        assertThat("Bad Data object", new Data().getData().get("first"), is("1st"));
    }
}
