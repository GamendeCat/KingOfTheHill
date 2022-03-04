package me.gamendecat.kingofthehill.data;

import org.bukkit.Location;

public class Map {

    private Location bottomCorner;
    private Location topCorner;

    private Location mapSpawnLocation;

    private String name;

    public Map(Location bottomCorner, Location topCorner, String name, Location mapSpawnLocation) {
        this.bottomCorner = bottomCorner;
        this.topCorner = topCorner;
        this.name = name;
        this.mapSpawnLocation = mapSpawnLocation;
    }

    public String getName() {
        return name;
    }

    public Location getBottomCorner() {
        return bottomCorner;
    }

    public Location getTopCorner() {
        return topCorner;
    }

    public Location getMapSpawnLocation() {
        return mapSpawnLocation;
    }

    public void setBottomCorner(Location bottomCorner) {
        this.bottomCorner = bottomCorner;
    }

    public void setMapSpawnLocation(Location mapSpawnLocation) {
        this.mapSpawnLocation = mapSpawnLocation;
    }

    public void setTopCorner(Location topCorner) {
        this.topCorner = topCorner;
    }
}
