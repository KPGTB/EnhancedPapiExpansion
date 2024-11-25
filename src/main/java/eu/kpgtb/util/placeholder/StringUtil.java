package eu.kpgtb.util.placeholder;

public class StringUtil {
    public static String camelToSnake(String str) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        str = str
                .replaceAll(regex, replacement)
                .toLowerCase();
        return str;
    }
}
