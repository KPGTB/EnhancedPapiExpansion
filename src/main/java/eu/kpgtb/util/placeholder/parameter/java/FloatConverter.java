package eu.kpgtb.util.placeholder.parameter.java;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.plugin.java.JavaPlugin;

public class FloatConverter implements IParameterConverter<Float> {
    @Override
    public Float convert(String s, JavaPlugin plugin) {
        return Float.parseFloat(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        try {
            Float.parseFloat(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
