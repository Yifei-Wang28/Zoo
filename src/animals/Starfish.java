package animals;

public class Starfish extends Animal implements Aqua {

    public Starfish(String n) {
        super(n);
    }

    @Override
    public String getNickname () {
        return nickName;
    }

    @Override
    // Starfish are okay with both sharks and seals
    public boolean isCompatibleWith (Animal animal) {
        return true;
    }
}
