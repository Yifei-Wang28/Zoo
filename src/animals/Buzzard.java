package animals;

public class Buzzard extends Animal implements Birds {

    public Buzzard (String n) {
        super(n);
    }

    @Override
    public String getNickname () {
        return nickName;
    }

    @Override
    // Buzzards can't live with Parrots.
    public boolean isCompatibleWith (Animal animal) {
        return !(animal instanceof Parrot);
    }
}

