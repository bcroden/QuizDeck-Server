package com.quizdeck.analysis;

/**
 * Represents an analysis algorithm which can only process the data set initially given to it.
 *
 * @author Alex
 */
public interface StaticAnalysis extends Analysis {
    /**
     * Takes the resuts of the algorithm's analysis and places them in an Excel file.
     * The Excel file is placed in the directory the indicated directory.
     * @param pathToDir Path to the directory where the Excel file should be placed
     * @return True if the Excel file was created successfully, false otherwise
     */
    public boolean toExcel(String pathToDir);
}
