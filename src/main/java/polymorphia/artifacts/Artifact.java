package polymorphia.artifacts;

public abstract class Artifact {
    protected final String name;

    public Artifact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
