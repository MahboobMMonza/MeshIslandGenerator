package ca.mcmaster.cas.se2aa4.a3.island.moisture;

public class BasicMoistureFactory extends AbstractMoistureFactory {

    @Override
    public Moisture createMoisture(SoilTypes soilType) {
        return new BasicMoisture();
    }

}
