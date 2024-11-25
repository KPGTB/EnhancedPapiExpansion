package eu.kpgtb.util.placeholder.parameter.java;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.plugin.java.JavaPlugin;

public class StringConverter implements IParameterConverter<String> {
    @Override
    public String convert(String s, JavaPlugin plugin) {
        return s;
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        return true;
    }
}
