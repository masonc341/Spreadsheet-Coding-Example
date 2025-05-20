public class PercentCell extends RealCell {

    public PercentCell(String n) {
        super(n);
    }
    
    public String abbreviatedCellText() {
        String text;
        if (super.fullCellText().contains(".")) {
            text = super.fullCellText().substring(0, super.fullCellText().indexOf(".")) + "%";
        } else {
            text = super.fullCellText() + "%";
        }
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
        return getDoubleValue() + "";
    }
    
    public double getDoubleValue() {
        return super.getDoubleValue() / 100;
    }
}
