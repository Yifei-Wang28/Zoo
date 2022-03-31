package animals;

public class Lion extends Animal implements Land {

    public Lion (String n) {
        super(n);
    }

    @Override
    public String getNickname () {
        return nickName;
    }

    @Override
    // Lions must live on their own or they eat other species.
    public boolean isCompatibleWith (Animal animal) {
        return !(animal instanceof Zebra) && !(animal instanceof Gazelle);
    }
}
