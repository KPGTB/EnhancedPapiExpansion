package eu.kpgtb.util.placeholder;

import eu.kpgtb.util.placeholder.parameter.PlaceholderParameter;

import java.lang.reflect.Method;
import java.util.List;

public class PlaceholderInfo {
    private final String prefix;
    private final String description;
    private final boolean relational;
    private final Method method;
    private final List<PlaceholderParameter> params;

    private int size;

    public PlaceholderInfo(String prefix, String description, boolean relational, Method method, List<PlaceholderParameter> params) {
        this.prefix = prefix;
        this.description = description;
        this.relational = relational;
        this.method = method;
        this.params = params;
        this.size = 0;

        for (PlaceholderParameter param : this.params) {
            size += param.getSize();
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRelational() {
        return relational;
    }

    public List<PlaceholderParameter> getParams() {
        return params;
    }

    public int getSize() {
        return size;
    }

    public Method getMethod() {
        return method;
    }
}
