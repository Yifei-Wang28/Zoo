package animals;

public class Zebra extends Animal implements Land {

    public Zebra(String n) {
        super(n);
    }

    @Override
    public String getNickname () {
        return nickName;
    }

    @Override
    // Zebra can be with Gazelles but no lions.
    public boolean isCompatibleWith (Animal animal) {
        return !(animal instanceof Lion);
    }
}
