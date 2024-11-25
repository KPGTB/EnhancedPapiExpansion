package eu.kpgtb.util.placeholder.parameter.java;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.plugin.java.JavaPlugin;

public class BooleanConverter implements IParameterConverter<Boolean> {
    @Override
    public Boolean convert(String s, JavaPlugin plugin) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes");
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        return s != null && (
                s.equalsIgnoreCase("true") ||
                        s.equalsIgnoreCase("yes") ||
                        s.equalsIgnoreCase("false") ||
                        s.equalsIgnoreCase("no")
        );
    }
}
