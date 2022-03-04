package me.gamendecat.kingofthehill.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class LocationUtility {
    public void write(Location location, ConfigurationSection section) {
        section.set("worldName", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
    }

    public Location read(ConfigurationSection section) {
        World world = Bukkit.getWorld(section.getString("worldName"));
        return new Location(world,
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z"));
    }

    public List<Location> blockFromTwoPoints(Location loc1, Location loc2) {
        List<Location> blocks = new ArrayList<>();

        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));

        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));

        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    blocks.add(loc1.getWorld().getBlockAt(x,y,z).getLocation());
                }
            }
        }

        return blocks;
    }

    public boolean coordinatesMatch(Location one, Location two) {
        if(one == null ||two == null) return false;

        return one.getBlockX() == two.getBlockX() &&
                one.getBlockY() == two.getBlockY() &&
                one.getBlockZ() == two.getBlockZ();
    }

    public boolean playerIsBetween(Location player, Location topCorner, Location bottomCorner) {
        if(player == null || topCorner == null ||bottomCorner == null) return false;

        if(player.getWorld() != topCorner.getWorld()) return false;

        double playerX = player.getX();
        double playerY = player.getY();
        double playerZ = player.getZ();

        int topX = (Math.max(topCorner.getBlockX(), bottomCorner.getBlockX()));
        int bottomX = (Math.min(topCorner.getBlockX(), bottomCorner.getBlockX()));

        int topY = (Math.max(topCorner.getBlockY(), bottomCorner.getBlockY()));
        int bottomY = (Math.min(topCorner.getBlockY(), bottomCorner.getBlockY()));

        int topZ = (Math.max(topCorner.getBlockZ(), bottomCorner.getBlockZ()));
        int bottomZ = (Math.min(topCorner.getBlockZ(), bottomCorner.getBlockZ()));

        if(bottomX <= playerX && playerX <= topX) {
            if(bottomY <= playerY && playerY <= topY) {
                if(bottomZ <= playerZ && playerZ <= topZ) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean coordinatesMatchWithoutY(Location one, Location two) {
        if(one == null || two == null) return false;

        return one.getBlockX() == two.getBlockX() &&
                one.getBlockZ() == two.getBlockZ();
    }
}
