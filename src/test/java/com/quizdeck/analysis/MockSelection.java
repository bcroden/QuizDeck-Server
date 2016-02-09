package com.quizdeck.analysis;

import com.quizdeck.analysis.inputs.Selection;

/**
 * Mock Selection class for testing
 *
 * @author Alex
 */
class MockSelection implements Selection
{
    public MockSelection(char selection)
    {
        SELECTION = selection;
    }

    @Override
    public String toDisplayString() {
        return Character.toString(SELECTION);
    }
    @Override
    public boolean isSameAs(Selection that) {
        return toDisplayString().equals(that.toDisplayString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockSelection that = (MockSelection) o;

        return SELECTION == that.SELECTION;
    }

    @Override
    public int hashCode() {
        return (int) SELECTION;
    }

    private final char SELECTION;
}
