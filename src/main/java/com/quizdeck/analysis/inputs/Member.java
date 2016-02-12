package com.quizdeck.analysis.inputs;

/**
 * Represents a user who is playing a role in a quiz as either a participant or the owner.
 *
 * @author Alex
 */
public abstract class Member {
    /**
     * Returns a string representation of the member's username.
     * This string will be used to if needed to identify the member's scores
     * in any reports.
     * All members within a given data set should have unique usernames.
     * @return The member's username as a string
     */
    public abstract String getUsername();

    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
