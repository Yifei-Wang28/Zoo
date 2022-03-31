package animals;

public class Gazelle extends Animal implements Land {

    public Gazelle (String n) {
        super(n);
    }

    @Override
    public String getNickname () {
        return nickName;
    }

    @Override
    // Gazelles are definitely not with lions
    public boolean isCompatibleWith (Animal animal) {
        return !(animal instanceof Lion);
    }
}


