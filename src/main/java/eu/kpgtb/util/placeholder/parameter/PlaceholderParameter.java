package eu.kpgtb.util.placeholder.parameter;

public class PlaceholderParameter {
    private final String name;
    private final int size;
    private final Class<?> expected;

    public PlaceholderParameter(String name, int size, Class<?> expected) {
        this.name = name;
        this.size = size;
        this.expected = expected;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Class<?> getExpected() {
        return expected;
    }
}
