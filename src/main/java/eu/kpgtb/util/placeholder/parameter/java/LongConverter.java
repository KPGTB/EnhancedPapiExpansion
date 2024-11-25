package eu.kpgtb.util.placeholder.parameter.java;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.plugin.java.JavaPlugin;

public class LongConverter implements IParameterConverter<Long> {
    @Override
    public Long convert(String s, JavaPlugin plugin) {
        return Long.parseLong(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        try {
            Long.parseLong(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
