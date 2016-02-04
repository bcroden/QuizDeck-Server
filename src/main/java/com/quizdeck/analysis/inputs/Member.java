package com.quizdeck.analysis.inputs;

/**
 * Represents a user who is playing a role in a quiz as either a participant or the owner.
 *
 * // TODO: Depreciate isSameAs() and implement Comparable<>
 *
 * @author Alex
 */
public interface Member {
    /**
     * Returns a string representation of the member's username.
     * This string will be used to if needed to identify the member's scores
     * in any reports.
     * All members within a given data set should have unique usernames.
     * @return The member's username as a string
     */
    public String getUsername();

    /**
     * Indicates if the Member passed into the function is equivalent
     * to this member.
     * @param that The member to which this member is being compared
     * @return True if the members are the same, false otherwise
     */
    public boolean isSameAs(Member that);
}
