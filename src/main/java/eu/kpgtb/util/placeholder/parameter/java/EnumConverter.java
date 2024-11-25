package eu.kpgtb.util.placeholder.parameter.java;

public class EnumConverter<T extends Enum<T>>{
    private final Class<T> enumClass;

    public EnumConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    public T convert(String param) {
        return Enum.valueOf(enumClass, param.toUpperCase());
    }

    public boolean canConvert(String param) {
        try {
            convert(param);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
