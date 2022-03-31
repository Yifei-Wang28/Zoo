package animals;

/**
 * You can modify the contents of this class, but you cannot:
 * - change the name, parameters or return types of provided methods
 * - remove it entirely
 */
public abstract class Animal
{
	protected final String nickName;
	// This is protected so that subclass can call it directly but other classes can't.

	public Animal (String nickName) {
		this.nickName = nickName;
	}
	// Put the constructor here to make it general for all animals.

	/**
	 * @return Returns this animal's given name.
	 */
	public abstract String getNickname();
	
	/**
	 * Check whether two animals can live together.
	 * @param animal The animal for which to check compatibility with this animal.
	 * @return Returns true for compatible animals and false otherwise.
	 */
	public abstract boolean isCompatibleWith(Animal animal);
}
