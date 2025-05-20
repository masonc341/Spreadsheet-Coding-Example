public class RealCell extends Cell {
    private String n;
    
    public RealCell(String n) {
        this.n = n;
    }

    public String abbreviatedCellText() {
        String text = getDoubleValue() + "";
        String atext = text;
        if (text.length() < 11) {
            for (int i = 0; i < 10 - text.length(); i++) {
                atext += " ";
            }
        } else {
            atext = text.substring(0, 10);
        }
        return atext;
    }

    public String fullCellText() {
        return n;
    }
    
    public double getDoubleValue() {
        return Double.parseDouble(n);
    }
}
