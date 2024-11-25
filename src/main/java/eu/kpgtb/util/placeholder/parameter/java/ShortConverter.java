package eu.kpgtb.util.placeholder.parameter.java;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.plugin.java.JavaPlugin;

public class ShortConverter implements IParameterConverter<Short> {
    @Override
    public Short convert(String s, JavaPlugin plugin) {
        return Short.parseShort(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        try {
            Short.parseShort(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
