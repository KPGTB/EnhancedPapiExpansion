package eu.kpgtb.util.placeholder.parameter.java;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.plugin.java.JavaPlugin;

public class ByteConverter implements IParameterConverter<Byte> {
    @Override
    public Byte convert(String s, JavaPlugin plugin) {
        return Byte.parseByte(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        try {
            convert(s, plugin);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
