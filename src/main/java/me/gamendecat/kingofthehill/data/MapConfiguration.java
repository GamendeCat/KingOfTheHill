package me.gamendecat.kingofthehill.data;

import me.gamendecat.kingofthehill.KingOfTheHill;
import me.gamendecat.kingofthehill.util.ConfigurationFile;
import me.gamendecat.kingofthehill.util.LocationUtility;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MapConfiguration {

    private FileConfiguration config;
    private ConfigurationFile file;
    private List<Map> maps;

    public MapConfiguration(KingOfTheHill plugin) {
        file = new ConfigurationFile(plugin, "maps.yml");
        config = file.getCustomConfig();
        maps = loadMaps();
    }

    public List<Map> loadMaps() {
        List<Map> maps = new ArrayList<>();
        for(String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);
            Location bottomLocation = LocationUtility.read(section.getConfigurationSection("bottomCorner"));
            Location spawnLocation = LocationUtility.read(section.getConfigurationSection("spawnLocation"));
            Location topLocation = LocationUtility.read(section.getConfigurationSection("topCorner"));
            maps.add(new Map(bottomLocation, topLocation, key, spawnLocation.clone().add(0,1,0)));
        }
        return maps;
    }

    public void addMap(Map map) {
        ConfigurationSection section = config.createSection(map.getName());
        LocationUtility.write(map.getBottomCorner(), section.createSection("bottomCorner"));
        LocationUtility.write(map.getTopCorner(), section.createSection("topCorner"));
        LocationUtility.write(map.getMapSpawnLocation(), section.createSection("spawnLocation"));
        file.saveConfig();
        maps.add(map);
    }

    public List<Map> getMaps() {
        return maps;
    }
}
