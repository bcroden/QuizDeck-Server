package com.quizdeck.analysis;

import com.quizdeck.analysis.inputs.Member;

/**
 * Mock Member class for testing
 *
 * @author Alex
 */
public class MockMember extends Member
{
    public MockMember(String name) {
        USERNAME = name;
    }
    @Override
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;

        if(!(o instanceof MockMember))
            return false;

        MockMember that = (MockMember) o;

        return getUsername().equals(that.getUsername());
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    private final String USERNAME;
}
