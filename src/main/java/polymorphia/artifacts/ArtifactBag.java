package polymorphia.artifacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ArtifactBag {
    private final List<Artifact> artifacts = new ArrayList<>();

    public List<Food> foodItems() {
        return itemsOf(Food.class);
    }

    public List<Treasure> treasureItems() {
        return itemsOf(Treasure.class);
    }

    // Yes, this cast is a bit of code smell, which is why this method is private and
    // this odor is contained within this class. The alternative would be to have a separate list for each type of
    // artifact, but that would be more code and more complexity.
    private <T extends Artifact> List<T> itemsOf(Class<T> artifactClass) {
        return artifacts.stream()
                .filter(artifactClass::isInstance)
                .map(artifactClass::cast)
                .toList();
    }


    public Artifact[] allItems() {
        return artifacts.toArray(new Artifact[0]);
    }

    public boolean contains(Artifact artifact) {
        return artifacts.contains(artifact);
    }

    public void add(Artifact artifact) {
        artifacts.add(artifact);
    }

    public Optional<Food> removeFoodItem() {
        Optional<Artifact> artifact = Optional.ofNullable(removeOneItemOfType(Food.class));
        return artifact.map(Food.class::cast);
    }

    public Optional<DieArtifact> removeDie() {
        return Optional.ofNullable(removeOneItemOfType(DieArtifact.class))
                .map(DieArtifact.class::cast);
    }

    private Artifact removeOneItemOfType(Class<? extends Artifact> artifactClass) {
        Optional<Artifact> artifact = artifacts.stream()
                .filter(artifactClass::isInstance)
                .findFirst();
        artifact.ifPresent(artifacts::remove);
        return artifact.orElse(null);
    }

    public boolean hasFood() {
        return hasArtifact(Food.class);
    }

    public boolean hasTreasure() {
        return hasArtifact(Treasure.class);
    }

    public boolean hasArtifact(Class<? extends Artifact> artifactClass) {
        return !itemsOf(artifactClass).isEmpty();
    }

    public Optional<Treasure> removeTreasureItem() {
        Optional<Artifact> artifact = Optional.ofNullable(removeOneItemOfType(Treasure.class));
        return artifact.map(Treasure.class::cast);
    }

    @Override
    public String toString() {
        return artifacts.stream()
                .map(Artifact::toString)
                .collect(Collectors.joining(", "));
    }

    public Optional<TreasureMap> removeTreasureMap() {
        Optional<Artifact> artifact = Optional.ofNullable(removeOneItemOfType(TreasureMap.class));
        return artifact.map(TreasureMap.class::cast);
    }
}
