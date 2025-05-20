
//Update this file with your own code.
/**
 * Represents a location in the spreadsheet
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SpreadsheetLocation extends Location
{
    private int r;
    private int c;
    @Override
    public int getRow()
    {
        return r;
    }

    @Override
    public int getCol()
    {
        return c;
    }

    /**
     * Constructor
     * 
     * @param cellName Name of cell, like "A1"
     */
    public SpreadsheetLocation(String cellName)
    {
        r = Integer.valueOf(cellName.substring(1)) - 1;
        c = cellName.substring(0, 1).toUpperCase().codePointAt(0) - 65;
    }

    public SpreadsheetLocation(int r, int c)
    {
        this.r = r;
        this.c = c;
    }
}
