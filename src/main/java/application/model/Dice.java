package application.model;

import java.util.Random;

public class Dice {
    int firstDice;
    int secondDice;

    public int rollFirstDice(){
        Random random = new Random();
        firstDice = random.nextInt(6) + 1;
        return firstDice;
    }
    public int rollSecondDice(){
        Random random = new Random();
        secondDice = random.nextInt(6) + 1;
        return secondDice;
    }
    public int rollDice(){
        return rollFirstDice() + rollSecondDice();
    }
    public boolean areEqual(){
        return  firstDice == secondDice;
    }

}
