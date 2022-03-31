package animals;

public class Shark extends Animal implements Aqua {
    public Shark(String n) {
        super(n);
    }

    @Override
    public String getNickname () {
        return nickName;
    }

    @Override
    // Sharks cannot be with Seals.
    public boolean isCompatibleWith (Animal animal) {
        return !(animal instanceof Seal);
    }
}
