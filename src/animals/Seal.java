package animals;

public class Seal extends Animal implements Aqua {
    public Seal(String n) {
        super(n);
    }

    @Override
    public String getNickname () {
        return nickName;
    }

    @Override
    // Seals are okay with Starfish but no Sharks.
    public boolean isCompatibleWith (Animal animal) {
        return !(animal instanceof Shark);
    }
}
