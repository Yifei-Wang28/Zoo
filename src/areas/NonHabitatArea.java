package areas;

import java.util.ArrayList;
import java.util.List;

public class NonHabitatArea implements IArea {

    private final ArrayList<Integer> adjacentAreasId = new ArrayList<>();

    @Override
    public List<Integer> getAdjacentAreas () {
        return adjacentAreasId;
    }
}
