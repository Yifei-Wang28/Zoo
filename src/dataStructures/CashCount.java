package dataStructures;

public class CashCount implements ICashCount{

    private int NrNotes_20pounds;
    private int NrNotes_10pounds;
    private int NrNotes_5pounds;
    private int NrCoins_2pounds;
    private int NrCoins_1pound;
    private int NrCoins_50p;
    private int NrCoins_20p;
    private int NrCoins_10p;

    public CashCount () {
    }

    @Override
    public void setNrNotes_20pounds (int noteCount) {
        NrNotes_20pounds += noteCount;
    }

    @Override
    public void setNrNotes_10pounds (int noteCount) {
        NrNotes_10pounds += noteCount;
    }

    @Override
    public void setNrNotes_5pounds (int noteCount) {
        NrNotes_5pounds += noteCount;
    }

    @Override
    public void setNrCoins_2pounds (int coinCount) {
        NrCoins_2pounds += coinCount;
    }

    @Override
    public void setNrCoins_1pound (int coinCount) {
        NrCoins_1pound += coinCount;
    }

    @Override
    public void setNrCoins_50p (int coinCount) {
        NrCoins_50p += coinCount;
    }

    @Override
    public void setNrCoins_20p (int coinCount) {
        NrCoins_20p += coinCount;
    }

    @Override
    public void setNrCoins_10p (int coinCount) {
        NrCoins_10p += coinCount;
    }

    @Override
    public int getNrNotes_20pounds () {
        return NrNotes_20pounds;
    }

    @Override
    public int getNrNotes_10pounds () {
        return NrNotes_10pounds;
    }

    @Override
    public int getNrNotes_5pounds () {
        return NrNotes_5pounds;
    }

    @Override
    public int getNrCoins_2pounds () {
        return NrCoins_2pounds;
    }

    @Override
    public int getNrCoins_1pound () {
        return NrCoins_1pound;
    }

    @Override
    public int getNrCoins_50p () {
        return NrCoins_50p;
    }

    @Override
    public int getNrCoins_20p () {
        return NrCoins_20p;
    }

    @Override
    public int getNrCoins_10p () {
        return NrCoins_10p;
    }

}
