package areas;

import animals.Animal;
import java.util.ArrayList;
import java.util.List;

public class HabitatArea implements IArea {
    //This class is the intermediate class between IArea and habitats(Cage, Aquarium, and Enclosure)

    private final int maxCapacity;
    private final ArrayList<Integer> adjacentAreasId = new ArrayList<>();
    private final ArrayList<Animal> animals = new ArrayList<>();

    public HabitatArea(int mc) {
        // Habitats should at least be able to hold one animal.
        if (mc <= 0) {
            throw new IllegalArgumentException("The max capacity of an area should be bigger than 0!");
        }
        else {
            this.maxCapacity = mc;
        }
    }


    /**
     *
     * @return the max capacity of the area
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     *
     * @return the animal lists of this area
     */
    public ArrayList<Animal> getAnimals () {
        return animals;
    }

    @Override
    public List<Integer> getAdjacentAreas () {
        return adjacentAreasId;
    }
}
