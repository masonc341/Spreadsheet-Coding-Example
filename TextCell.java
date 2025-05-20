public class TextCell extends Cell {
    private String text;
    
    public TextCell(String text) {
        this.text = text;
    }
    
    public String abbreviatedCellText() {
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
        return "\"" + text + "\"";
    }
}
