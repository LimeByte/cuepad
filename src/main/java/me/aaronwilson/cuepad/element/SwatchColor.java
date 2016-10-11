package me.aaronwilson.cuepad.element;

public enum SwatchColor {

    YELLOW("yellow"),
    ORANGE("orange"),
    GREEN("green"),
    BLUE("blue"),
    PURPLE("purple"),
    PINK("pink"),
    RED("red");

    private String styleClass;


    SwatchColor(String styleClass) {
        this.styleClass = styleClass;
    }


    public String getStyleClass() {
        return styleClass;
    }


    public SwatchColor next() {
        SwatchColor[] colors = values();

        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(this)) {
                int nextIndex = (i + 1) % colors.length;
                return colors[nextIndex];
            }
        }

        // Should never reach
        return null;
    }


    public static SwatchColor getByStyleClass(String styleClass) {
        for (SwatchColor color : values()) {
            if (color.getStyleClass().equalsIgnoreCase(styleClass)) {
                return color;
            }
        }

        return null;
    }

}
