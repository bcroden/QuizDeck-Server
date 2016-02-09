package com.quizdeck.analysis;

import com.quizdeck.analysis.inputs.Member;

/**
 * Mock Member class for testing
 *
 * @author Alex
 */
public class MockMember implements Member
{
    public MockMember(String name) {
        USERNAME = name;
    }
    @Override
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public int compareTo(Member that) {
        if(equals(that))
            return 0;
        return getUsername().compareTo(that.getUsername());
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

    private final String USERNAME;
}
