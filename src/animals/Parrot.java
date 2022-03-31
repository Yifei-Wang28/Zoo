package animals;

public class Parrot extends Animal implements Birds {

    public Parrot(String n) {
        super(n);
    }

    @Override
    public String getNickname () {
        return nickName;
    }

    @Override
    // Birds don't get along.
    public boolean isCompatibleWith (Animal animal) {
        return !(animal instanceof Buzzard);
    }
}
