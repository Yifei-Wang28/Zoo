package areas;

import java.util.List;

/**
 * This file must remain exactly as it is.
 */
public interface IArea
{
	/**
	 * @return Returns the IDs of the areas adjacent to this one.
	 */
	public List<Integer> getAdjacentAreas();
}
