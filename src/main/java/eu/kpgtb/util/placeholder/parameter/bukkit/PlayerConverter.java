package eu.kpgtb.util.placeholder.parameter.bukkit;

import eu.kpgtb.util.placeholder.parameter.IParameterConverter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerConverter implements IParameterConverter<Player> {
    @Override
    public Player convert(String s, JavaPlugin plugin) {
        return Bukkit.getPlayer(s);
    }

    @Override
    public boolean canConvert(String s, JavaPlugin plugin) {
        return convert(s,plugin) != null;
    }
}
