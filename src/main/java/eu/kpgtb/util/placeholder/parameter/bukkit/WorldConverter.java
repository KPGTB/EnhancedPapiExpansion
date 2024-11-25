package eu.kpgtb.util.placeholder.parameter.bukkit;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldConverter implements IParameterConverter<World> {
    @Override
    public World convert(String s, JavaPlugin plugin) {
        return Bukkit.getWorld(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        return convert(s,plugin) != null;
    }
}
