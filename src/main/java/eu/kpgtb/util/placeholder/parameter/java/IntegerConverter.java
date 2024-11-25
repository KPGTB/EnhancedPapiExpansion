package eu.kpgtb.util.placeholder.parameter.java;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.plugin.java.JavaPlugin;

public class IntegerConverter implements IParameterConverter<Integer> {
    @Override
    public Integer convert(String s, JavaPlugin plugin) {
        return Integer.parseInt(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
