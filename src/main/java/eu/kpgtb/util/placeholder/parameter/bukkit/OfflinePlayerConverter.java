package eu.kpgtb.util.placeholder.parameter.bukkit;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public class OfflinePlayerConverter implements IParameterConverter<OfflinePlayer> {
    @Override
    public OfflinePlayer convert(String s, JavaPlugin plugin) {
        return Bukkit.getOfflinePlayer(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        return convert(s,plugin) != null;
    }
}
