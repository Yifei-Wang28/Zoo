package zoo;

import animals.*;
import areas.*;
import dataStructures.CashCount;
import dataStructures.ICashCount;
import java.util.HashMap;
import java.util.ArrayList;

public class Zoo implements IZoo{
    //used to be 2 arrayLists and the become hashmap.

    private final HashMap<Integer, IArea> areas = new HashMap<>();
    private int id = 0;
    private int entranceFeePounds;
    private int entranceFeePence;
    private final CashCount cashSupply = new CashCount();

    public Zoo() {
        Entrance entrance = new Entrance();
        areas.put(0, entrance);
    }

    @Override
    public int addArea (IArea area) {
        if (areas.containsValue(area)) {
            // One area should be only added once
            throw new IllegalArgumentException("Can't duplicate same area");
        }
        if (area instanceof Entrance) {
            // There must be only one Entrance
            throw new IllegalArgumentException("There should be only one entrance");
        }
        else {
            // change the id each time to make it distinct.
            id ++;
            areas.put(id, area);
            return id;
        }
    }

    @Override
    public void removeArea (int areaId) {
        // The Entrance could not be removed since there must be always one.
        if (areaId == 0) {
            throw new IllegalArgumentException("The entrance cannot be removed");
        }
        // use hash map remove method to help remove object.
        areas.remove(areaId);

        //After delete that area, also have to delete the connection
        for (int restAreaId: areas.keySet()) {
            IArea area = getArea(restAreaId);
            if (area.getAdjacentAreas().contains(areaId)) {
                area.getAdjacentAreas().remove(areaId);
            }
        }
    }

    @Override
    public IArea getArea (int areaId) {
        // call the key(ID) to get the value
        return areas.get(areaId);
    }

    @Override
    public byte addAnimal (int areaId, Animal animal) {
        IArea area = getArea(areaId);


        if (area instanceof NonHabitatArea) {
            return Codes.NOT_A_HABITAT;
        }

        else if
        ((area instanceof Aquarium  && !(animal instanceof Aqua))  ||
         (area instanceof Cage      && !(animal instanceof Birds)) ||
         (area instanceof Enclosure && !(animal instanceof Land))) {
            // as the names suggest, aquariums accommodate aquatic animals
            // cages are for birds and
            // animals lived on land should be in enclosures.
            return Codes.WRONG_HABITAT;
        }

        else if
        (((HabitatArea) area).getMaxCapacity() == ((HabitatArea) area).getAnimals().size()) {
            // Since we have checked it's a habitat area, so it's safe to cast
            // if the size of the animal list is already equal to the max capacity
            // then it returns full message.
            return Codes.HABITAT_FULL;
        }

        else if (((HabitatArea) area).getAnimals().size() == 0) {
            // After checking the habitats,
            // If there are animals yet, directly add it in
            ((HabitatArea) area).getAnimals().add(animal);
            return Codes.ANIMAL_ADDED;
        }
        else {
            for (Animal thatAnimal: ((HabitatArea) area).getAnimals()) {
                // for each animal in the given area animal list
                if (!(thatAnimal.isCompatibleWith(animal))) {
                    // if the animal going to be added is not compatible with
                    // any of the existing animal, then it should return message
                    return Codes.INCOMPATIBLE_INHABITANTS;
                }
            }
        }
        // after all the conditions checked,
        // it's safe then to add animal in.
        ((HabitatArea) area).getAnimals().add(animal);
        return Codes.ANIMAL_ADDED;
    }

    @Override
    public void connectAreas (int fromAreaId, int toAreaId) {
        IArea startingPoint = getArea(fromAreaId);

        // Add the area to the area's list of adjacent areas.
        startingPoint.getAdjacentAreas().add(toAreaId);

    }

    @Override
    public boolean isPathAllowed (ArrayList<Integer> areaIds) {
        for (int i = 0; i < areaIds.size() - 1; i ++) {
            //check if the previous one's adjacent areas contains the next area.
            if (!(getArea(areaIds.get(i)).getAdjacentAreas().contains(areaIds.get(i + 1)))){
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> visit (ArrayList<Integer> areaIdsVisited) {
        ArrayList<String> visitedAnimals = new ArrayList<>();
        // create a new list which is the list to be output.
        if (isPathAllowed(areaIdsVisited)) {
            // First check if the path is allowed
            for (int id: areaIdsVisited) {
                IArea area = getArea(id);
                // get the area of each Id listed.
                if (area instanceof HabitatArea) {
                    // Since only habitats accommodate animals
                    // We can just leave non-habitats away
                    // if it is a habitat, directly cast to it
                    for (Animal animal: ((HabitatArea) area).getAnimals()) {
                        visitedAnimals.add(animal.getNickname());
                    }
                }
            }
            return visitedAnimals;
        }
        else {
            return null;
        }
    }

    /**
     * A helper method that finds all the areas that can be reached in the zoo.
     * @return All reachable areas in the zoo
     */
    public ArrayList<Integer> findReachableAreas () {
        ArrayList<Integer> reachableAreaIds = new ArrayList<>();

        int entranceId = 0;
        reachableAreaIds.add(0, entranceId);
        for (int i = 0; i < reachableAreaIds.size(); i ++) {
            // start from entrance
            // this list's length is changing
            for (Integer areaId: getArea(reachableAreaIds.get(i)).getAdjacentAreas()) {
                // for the area at index i, add all its adjacent areas in.
                // as the length of the list increases, the loop will continue
                // until the length stops changing which means all reachable areas are in
                if (!(reachableAreaIds.contains(areaId))) {
                    // if it is already in, skip it
                    reachableAreaIds.add(reachableAreaIds.size(), areaId);
                }
            }
        }
        return reachableAreaIds;
    }

    @Override
    public ArrayList<Integer> findUnreachableAreas () {
        ArrayList<Integer> reachableAreaIds = findReachableAreas();
        //first find all the reachable areas
        ArrayList<Integer> unreachableAreaIds = new ArrayList<>();
        for (Integer areaId: areas.keySet()) {
            if (!(reachableAreaIds.contains(areaId))) {
                // for each of all the area ids, if it is not in reachable areas list
                // then it must be unreachable
                unreachableAreaIds.add(unreachableAreaIds.size(), areaId);
            }
        }
        return unreachableAreaIds;
    }

    @Override
    public void setEntranceFee (int pounds, int pence) {
        entranceFeePounds = pounds;
        entranceFeePence  = pence;
    }

    @Override
    public void setCashSupply (ICashCount coins) {
        cashSupply.setNrNotes_20pounds(coins.getNrNotes_20pounds());
        cashSupply.setNrNotes_10pounds(coins.getNrNotes_10pounds());
        cashSupply.setNrNotes_5pounds(coins.getNrNotes_5pounds());
        cashSupply.setNrCoins_2pounds(coins.getNrCoins_2pounds());
        cashSupply.setNrCoins_1pound(coins.getNrCoins_1pound());
        cashSupply.setNrCoins_50p(coins.getNrCoins_50p());
        cashSupply.setNrCoins_20p(coins.getNrCoins_20p());
        cashSupply.setNrCoins_10p(coins.getNrCoins_10p());
    }

    @Override
    public ICashCount getCashSupply () {
        return cashSupply;
    }

    /**
     * A helper to reduce code duplication, same functionality as setter.
     * @param cash the ICashCount to be exposed to the setter
     * @param nPounds denomination
     * @param n the parameter of the setter
     */
    public void set_X_Pounds(ICashCount cash, int nPounds, int n) {
        if (nPounds == 20) {
            cash.setNrNotes_20pounds(n);
        }
        else if (nPounds == 10) {
            cash.setNrNotes_10pounds(n);
        }
        else if (nPounds == 5) {
            cash.setNrNotes_5pounds(n);
        }
        else if (nPounds == 2) {
            cash.setNrCoins_2pounds(n);
        }
        else if (nPounds == 1) {
            cash.setNrCoins_1pound(n);
        }
        else {
            throw new IllegalArgumentException("There's no such denomination");
        }
    }

    public void set_X_Pence(ICashCount cash, int nPence, int n) {
        if (nPence == 50) {
            cash.setNrCoins_50p(n);
        }
        else if (nPence == 20) {
            cash.setNrCoins_20p(n);
        }
        else if (nPence == 10) {
            cash.setNrCoins_10p(n);
        }
        else {
            throw new IllegalArgumentException("There's no such denomination");
        }
    }

    /**
     * A helper to reduce code duplication, same functionality as getter.
     * @param cash the ICashCount to be exposed to the getter
     * @param nPounds denomination
     * @return the output of the getter to be used
     */
    public int get_X_Pounds(ICashCount cash, int nPounds) {
        if (nPounds == 20) {
            return cash.getNrNotes_20pounds();
        }
        else if (nPounds == 10) {
            return cash.getNrNotes_10pounds();
        }
        else if (nPounds == 5) {
            return cash.getNrNotes_5pounds();
        }
        else if (nPounds == 2) {
            return cash.getNrCoins_2pounds();
        }
        else if (nPounds == 1) {
            return cash.getNrCoins_1pound();
        }
        else {
            throw new IllegalArgumentException("There no such denomination");
        }

    }

    public int get_X_Pence(ICashCount cash, int nPence) {
        if (nPence == 50) {
            return cash.getNrCoins_50p();
        }
        else if (nPence == 20) {
            return cash.getNrCoins_20p();
        }
        else if (nPence == 10) {
            return cash.getNrCoins_10p();
        }
        else {
            throw new IllegalArgumentException("There no such denomination");
        }
    }

    /**
     * A helper to use denominations to calculate the real value
     * @param cash the cash to be calculated
     * @return a double number of the value of the money
     */
    public double calculateMoney (ICashCount cash) {
        int pound = 0;
        int pence = 0;
        pound += cash.getNrNotes_20pounds() * 20;
        pound += cash.getNrNotes_10pounds() * 10;
        pound += cash.getNrNotes_5pounds()  * 5 ;
        pound += cash.getNrCoins_2pounds()  * 2 ;
        pound += cash.getNrCoins_1pound()       ;
        pence += cash.getNrCoins_50p()      * 50;
        pence += cash.getNrCoins_20p()      * 20;
        pence += cash.getNrCoins_10p()      * 10;
        return pound + pence / 100.0;
    }

    /**
     * A helper to make changes based on the current cash supply and the cash inserted
     * @param cashInserted The cash and coins inserted in the machine
     * @return an ICashCount object stores the number of each of the denomination that
     * should be given back based on current cash supply
     */
    public CashCount change (ICashCount cashInserted) {
        double entranceFee = getEntranceFeePounds() + getEntranceFeePence() / 100.0;
        double changeNeeded = calculateMoney(cashInserted) - entranceFee;
        CashCount change = new CashCount();
        int[] denomination = {20, 10, 5, 2, 1, 50, 20, 10};

        //For pounds
        for (int i = 0; i < 5; i ++) {
            int needed_n_Pounds = (int)(changeNeeded / denomination[i]);
            if (get_X_Pounds(cashSupply, denomination[i]) < needed_n_Pounds) {
                //if needed denomination is not enough
                //Then put them all in to follow "greedy principle"
                set_X_Pounds(change, denomination[i], get_X_Pounds(cashSupply, denomination[i]));
                changeNeeded -= get_X_Pounds(cashSupply, denomination[i]);
            }
            else {
                //else just add needed amount in and delete same amount from supply
                set_X_Pounds(change, denomination[i], needed_n_Pounds);
                set_X_Pounds(cashSupply, denomination[i], -needed_n_Pounds);
                changeNeeded -= needed_n_Pounds * denomination[i];
            }
        }

        //For pence, it times 100 to make the calculation easier
        changeNeeded = changeNeeded * 100;
        for (int i = 5; i < 8; i ++) {
            int needed_n_Pence = (int)(changeNeeded / denomination[i]);
            if (get_X_Pence(cashSupply, denomination[i]) < needed_n_Pence) {
                set_X_Pence(change, denomination[i], get_X_Pence(cashSupply, denomination[i]));
                changeNeeded -= get_X_Pence(cashSupply, denomination[i]);
            }
            else {
                set_X_Pence(change, denomination[i], needed_n_Pence);
                set_X_Pence(cashSupply, denomination[i], -needed_n_Pence);
                changeNeeded -= needed_n_Pence * denomination[i];
            }
        }
        return change;
    }

    /**
     * A helper that gives money back to the payers by deleting the exact same amount of
     * each denomination paid from the cash machine.
     * @param cashInserted The cash and coins inserted in the machine
     */
    public void giveMoneyBack(ICashCount cashInserted) {
        cashSupply.setNrNotes_20pounds(-(cashInserted.getNrNotes_20pounds()));
        cashSupply.setNrNotes_10pounds(-(cashInserted.getNrNotes_10pounds()));
        cashSupply.setNrNotes_5pounds(-(cashInserted.getNrNotes_5pounds()));
        cashSupply.setNrCoins_2pounds(-(cashInserted.getNrCoins_2pounds()));
        cashSupply.setNrCoins_1pound(-(cashInserted.getNrCoins_1pound()));
        cashSupply.setNrCoins_50p(-(cashInserted.getNrCoins_50p()));
        cashSupply.setNrCoins_20p(-(cashInserted.getNrCoins_20p()));
        cashSupply.setNrCoins_10p(-(cashInserted.getNrCoins_10p()));
    }


    /**
     * a helper to check when the money in the cash supply is able to cover the change
     * if the cash supply have the enough numbers of each denominations to give changes back
     * @param cashInserted The cash and coins inserted in the machine
     * @return a boolean on whether it could be able to give changes back
     */
    public boolean ableToChange (ICashCount cashInserted) {
        boolean changeable;
        double entranceFee = getEntranceFeePounds() + getEntranceFeePence() / 100.0;
        ICashCount change = change(cashInserted);
        changeable =  calculateMoney(change) == (calculateMoney(cashInserted) - entranceFee);
        if (changeable) {
            //Since I call change in 2 lines before, I have to keep it unchanged.
            //I add it back.
            setCashSupply(change);
        }
        return changeable;
    }

    @Override
    public ICashCount payEntranceFee (ICashCount cashInserted) {
        // First add the inserted money back to the cash supply
        setCashSupply(cashInserted);
        double insertedAmount = calculateMoney(cashInserted);
        double suppliedAmount = calculateMoney(cashSupply);
        double entranceFee = entranceFeePounds + entranceFeePence / 100.0;
        double change = insertedAmount - entranceFee;

        if (insertedAmount < entranceFee) {
            giveMoneyBack(cashInserted);
            return cashInserted;
        }
        else if (change > suppliedAmount) {
            giveMoneyBack(cashInserted);
            return cashInserted;
        }
        else if (change == 0) {
            //add back to the cashSupply
            return change(cashInserted);
        }
        else if (change < suppliedAmount) {
            if (ableToChange(cashInserted)) {
                return change(cashInserted);
            }
            //method give change layer by layer;
            else {
                giveMoneyBack(cashInserted);
                return cashInserted;
            }
        }
        return null;
    }

    public HashMap<Integer, IArea> getAreas() {
        return areas;
    }

    public int getEntranceFeePounds() {
        return entranceFeePounds;
    }

    public int getEntranceFeePence() {
        return entranceFeePence;
    }
}
