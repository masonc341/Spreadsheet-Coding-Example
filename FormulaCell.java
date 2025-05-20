import java.util.*;

public class FormulaCell extends RealCell {
    Spreadsheet s;

    public FormulaCell(String n, Spreadsheet s) {
        super(n);
        this.s = s;
    }

    public String fullCellText() {
        return "( " + super.fullCellText() + " )";
    }

    public double getDoubleValue() {
        ArrayList<String> f = new ArrayList<>(Arrays.asList(super.fullCellText().split(" ")));
        double v = 0;
        if (f.get(0).toUpperCase().equals("AVG")) {
            SpreadsheetLocation start = new SpreadsheetLocation(f.get(1).substring(0, f.get(1).indexOf("-")));
            SpreadsheetLocation fin = new SpreadsheetLocation(f.get(1).substring(f.get(1).indexOf("-") + 1));
            int t = 0;
            for (int i = start.getRow(); i <= fin.getRow(); i++) {
                for (int j = start.getCol(); j <= fin.getCol(); j++) {
                    v += ((RealCell) s.getCell(new SpreadsheetLocation(i, j))).getDoubleValue();
                    t++;
                }
            }
            v /= t;
        } else if (f.get(0).toUpperCase().equals("SUM")) {
            SpreadsheetLocation start = new SpreadsheetLocation(f.get(1).substring(0, f.get(1).indexOf("-")));
            SpreadsheetLocation fin = new SpreadsheetLocation(f.get(1).substring(f.get(1).indexOf("-") + 1));
            for (int i = start.getRow(); i <= fin.getRow(); i++) {
                for (int j = start.getCol(); j <= fin.getCol(); j++) {
                    v += ((RealCell) s.getCell(new SpreadsheetLocation(i, j))).getDoubleValue();
                }
            }
        } else {
            try {
                v = Double.parseDouble(f.get(0));
            } catch (NumberFormatException e) {
                v = ((RealCell) s.getCell(new SpreadsheetLocation(f.get(0)))).getDoubleValue();
            }
            for (int i = 1; i < f.size() - 1; i += 2) {
                switch(f.get(i)) {
                    case "+":
                        try {
                            v += Double.parseDouble(f.get(i + 1));
                        } catch (NumberFormatException e) {
                            v += ((RealCell) s.getCell(new SpreadsheetLocation(f.get(i + 1)))).getDoubleValue();
                        }
                        break;
                    case "-":
                        try {
                            v -= Double.parseDouble(f.get(i + 1));
                        } catch (NumberFormatException e) {
                            v -= ((RealCell) s.getCell(new SpreadsheetLocation(f.get(i + 1)))).getDoubleValue();
                        }
                        break;
                    case "*":
                        try {
                            v *= Double.parseDouble(f.get(i + 1));
                        } catch (NumberFormatException e) {
                            v *= ((RealCell) s.getCell(new SpreadsheetLocation(f.get(i + 1)))).getDoubleValue();
                        }
                        break;
                    case "/":
                        try {
                            v /= Double.parseDouble(f.get(i + 1));
                        } catch (NumberFormatException e) {
                            v /= ((RealCell) s.getCell(new SpreadsheetLocation(f.get(i + 1)))).getDoubleValue();
                        }
                        break;
                }
            }
        }
        return v;
    }
}
