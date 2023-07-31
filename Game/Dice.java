package Game;

import java.util.Random;

public class Dice {
    Random rand = new Random();
    private final int numberOfDices,sides;

    public Dice(int numberOfDices,int sides){
        this.numberOfDices = numberOfDices;
        this.sides = sides;
    }

    public int Roll(){
        int results = 0;
        for(int i = 0; i < numberOfDices; i++){
            results += rand.nextInt(1,sides+1);
        }
        return results;
    }

    public int Roll(int bonus){
        return Roll()+bonus;
    }
}
