package eu.kpgtb.util.placeholder.parameter;

import org.bukkit.plugin.java.JavaPlugin;

public interface IParameterConverter<T> {
    T convert(String s, JavaPlugin plugin);
    boolean canConvert(String s, JavaPlugin plugin);
}
