package com.quizdeck.analysis;

import com.quizdeck.analysis.inputs.Member;

/**
 * Mock Member class for testing
 *
 * @author Alex
 */
class MockMember implements Member
{
    @Override
    public String getUsername() {
        return "Steve";
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
}
