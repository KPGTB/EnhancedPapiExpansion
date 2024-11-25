package eu.kpgtb.util.placeholder.parameter.java;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleConverter implements IParameterConverter<Double> {
    @Override
    public Double convert(String s, JavaPlugin plugin) {
        return Double.parseDouble(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        try {
            Double.parseDouble(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
