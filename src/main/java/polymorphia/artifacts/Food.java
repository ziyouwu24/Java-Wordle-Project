package polymorphia.artifacts;

public class Food extends Artifact {
    private final double healthValue;

    public Food(String name, double healthValue) {
        super(name);
        this.healthValue = healthValue;
    }

    public double getHealthValue() {
        return healthValue;
    }

    @Override
    public String toString() {
        String formattedHealth = String.format("%.2f", healthValue);
        return name + "(" + formattedHealth + ")";
    }

}
