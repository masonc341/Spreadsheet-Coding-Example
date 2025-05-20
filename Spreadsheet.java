import java.util.*;

/**
 * Spreadsheet
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Spreadsheet
{
    private Cell[][] iLoveCells;

    public Spreadsheet() {
        iLoveCells = new Cell[20][12];
        for (int i = 0; i < iLoveCells.length; i++) {
            for (int j = 0; j < iLoveCells[0].length; j++) {
                iLoveCells[i][j] = new EmptyCell();
            }
        }
    }

    public String processCommand(String command)
    {
        ArrayList<String> commanda = new ArrayList<>(Arrays.asList(command.split(" ", 3)));
        boolean cellname = false;
        if (commanda.get(0).length() == 2) {
            boolean l = false;
            boolean n = false;
            for (int i = 0; i < commanda.get(0).length(); i++) {
                if (commanda.get(0).toUpperCase().codePointAt(i) > 64 && commanda.get(0).toUpperCase().codePointAt(i) < 77) {
                    l = true;
                }
                if (commanda.get(0).codePointAt(i) > 48 && commanda.get(0).codePointAt(i) < 58 && l) {
                    n = true;
                }
            }
            if (l && n) {
                cellname = true;
            }
        }
        if (cellname) {
            SpreadsheetLocation l = new SpreadsheetLocation(commanda.get(0));
            if (commanda.size() == 1) {
                return getCell(new SpreadsheetLocation(commanda.get(0))).fullCellText();
            } else if (commanda.contains("=")) {
                String v = commanda.get(2);
                if (v.contains("\"")) iLoveCells[l.getRow()][l.getCol()] = new TextCell(v.substring(v.indexOf("\"") + 1, v.lastIndexOf("\"")));
                else if (v.contains("(")) {
                    if (commanda.get(3).toUpperCase().codePointAt(0) > 64 && commanda.get(3).toUpperCase().codePointAt(0) < 77) {
                        if (commanda.get(3).toUpperCase().equals("AVG") || commanda.get(3).toUpperCase().equals("SUM")) {
                            iLoveCells[l.getRow()][l.getCol()] = new FormulaCell(v.substring(v.indexOf("(") + 2, v.lastIndexOf(")") - 1), this);
                        }
                    } else {
                        iLoveCells[l.getRow()][l.getCol()] = new FormulaCell(v.substring(v.indexOf("(") + 2, v.lastIndexOf(")") - 1), this);
                    }
                    
                }
                else if (v.contains("%")) iLoveCells[l.getRow()][l.getCol()] = new PercentCell(v.substring(0, v.indexOf("%")));
                else {
                    
                    try {
                        Double.parseDouble(v);
                        iLoveCells[l.getRow()][l.getCol()] = new ValueCell(v);
                    } catch (NumberFormatException e) {
                        
                    }
                }
            }
        } else if (commanda.get(0).toLowerCase().contains("clear")) {
            if (commanda.size() == 1) {
                for (int i = 0; i < iLoveCells.length; i++) {
                    for (int j = 0; j < iLoveCells[0].length; j++) {
                        iLoveCells[i][j] = new EmptyCell();
                    }
                }
            } else if (commanda.get(1).length() >= 2 && commanda.get(1).length() <= 4) {
                SpreadsheetLocation l = new SpreadsheetLocation(commanda.get(1));
                iLoveCells[l.getRow()][l.getCol()] = new EmptyCell();
            }
        } else if (commanda.get(0).toLowerCase().contains("sort")) {
            SpreadsheetLocation start = new SpreadsheetLocation(commanda.get(1).substring(0, commanda.get(1).indexOf("-")));
            SpreadsheetLocation fin = new SpreadsheetLocation(commanda.get(1).substring(commanda.get(1).indexOf("-") + 1));
            int h = (fin.getRow() - start.getRow() + 1);
            int w = (fin.getCol() - start.getCol() + 1);
            int order = commanda.get(0).toLowerCase().substring(4).contains("a") ? 1 : -1;
            if (getCell(start) instanceof TextCell) {
                for (int i = 1; i < h * w; i++) {
                    Cell k = iLoveCells[i / w + start.getRow()][i % w + start.getCol()];
                    int j = i - 1;
                    while (j >= 0 && order * iLoveCells[j / w + start.getRow()][j % w + start.getCol()].fullCellText().compareTo(k.fullCellText()) > 0) {
                        iLoveCells[(j + 1) / w + start.getRow()][(j + 1) % w + start.getCol()] = iLoveCells[j / w + start.getRow()][j % w + start.getCol()];
                        j = j - 1;
                    }
                    iLoveCells[(j + 1) / w + start.getRow()][(j + 1) % w + start.getCol()] = k;
                }
            } else if (getCell(start) instanceof RealCell) {
                for (int i = 1; i < h * w; i++) {
                    Cell k = iLoveCells[i / w + start.getRow()][i % w + start.getCol()];
                    int j = i - 1;
                    System.out.println(i);
                    while (j >= 0 && order * ((RealCell) iLoveCells[j / w + start.getRow()][j % w + start.getCol()]).getDoubleValue() > order * ((RealCell) k).getDoubleValue()) {
                        System.out.println("j: " + j);
                        iLoveCells[(j + 1) / w + start.getRow()][(j + 1) % w + start.getCol()] = iLoveCells[j / w + start.getRow()][j % w + start.getCol()];
                        j = j - 1;
                    }
                    iLoveCells[(j + 1) / w + start.getRow()][(j + 1) % w + start.getCol()] = k;
                    System.out.println();
                }
            }
        } else {
            return "ERROR: Invalid command.";
        }
        if (command.equals("")) {
            return "";
        }
        return getGridText();
    }

    public int getRows()
    {
        return iLoveCells.length;
    }

    public int getCols()
    {
        return iLoveCells.length == 0 ? 0 : iLoveCells[0].length;
    }

    public Cell getCell(Location loc)
    {
        return iLoveCells[loc.getRow()][loc.getCol()];
    }

    public String getGridText()
    {
        String grid = "   ";
        for (int i = 1; i <= getCols(); i++) {
            String l = Character.toString((char) (i + 48 + '0'));
            l = l.toUpperCase();
            grid += "|" + l + "         ";
        }
        grid += "|\n";
        for (int i = 1; i <= getRows(); i++) {
            String n = "" + i;
            grid += n;
            for (int s = 0; s < 3 - n.length(); s++) {
                grid += " ";
            }
            for (int c = 0; c < getCols(); c++) {
                grid += "|" + iLoveCells[i - 1][c].abbreviatedCellText();
            }
            grid += "|\n";
        }
        return grid;
    }

}
