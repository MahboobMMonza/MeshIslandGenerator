package ca.mcmaster.cas.se2aa4.a3.island.creator;

import ca.mcmaster.cas.se2aa4.a3.island.biomes.Biome;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.Elevation;
import ca.mcmaster.cas.se2aa4.a3.island.moisture.Moisture;
import ca.mcmaster.cas.se2aa4.a3.island.shaper.Shaper;
import ca.mcmaster.cas.se2aa4.a3.island.water.aquifer.Aquifer;
import ca.mcmaster.cas.se2aa4.a3.island.water.lake.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.water.river.River;

public class IslandCreatorBuilder {

    Lake lake;
    Elevation elev;
    Aquifer aqua;
    Biome boime;
    Moisture moist;
    Shaper shape;
    River river;
    int height;
    int width;

    public IslandCreatorBuilder lake(Lake lake) {
        this.lake = lake;
        return this;
    }

    public IslandCreatorBuilder elevation(Elevation elev) {
        this.elev = elev;
        return this;
    }

    public IslandCreatorBuilder aquifer(Aquifer aqua) {
        this.aqua = aqua;
        return this;
    }

    public IslandCreatorBuilder biome(Biome biome) {
        this.boime = biome;
        return this;
    }

    public IslandCreatorBuilder moisture(Moisture moist) {
        this.moist = moist;
        return this;
    }

    public IslandCreatorBuilder shape(Shaper shape) {
        this.shape = shape;
        return this;
    }

    public IslandCreatorBuilder height(int height) {
        this.height = height;
        return this;
    }

    public IslandCreatorBuilder width(int width) {
        this.width = width;
        return this;
    }

    public IslandCreatorBuilder river(River river) {
        this.river = river;
        return this;
    }

    public IslandCreator build() {
        return new IslandCreator(lake, aqua, elev, moist, boime, shape, river, height, width);
    }

}
