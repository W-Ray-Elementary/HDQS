package bin.io.smartIO2;

public class Info {
    private final String name;
    private String value;
    private final boolean isInteger;
    private static int width;

    public Info(String name, String value, boolean isInteger) {
        this.name = name;
        this.value = value;
        this.isInteger = isInteger;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isInteger() {
        return isInteger;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
