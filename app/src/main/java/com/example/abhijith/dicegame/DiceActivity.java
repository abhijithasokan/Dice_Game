package com.example.abhijith.dicegame;
import java.util.Random;
/**
 * Created by abhijith on 30/7/16.
 */
public class DiceActivity {


    public int humanScore,computerScore,humanCurrentScore,computerCurrentScore;
    public boolean human;
    public Random random;

    DiceActivity() {
        random = new Random();
        reset();
    }

    void reset()
    {
        human = true;
        humanScore = computerScore = computerCurrentScore = humanCurrentScore = 0;
    }


    public int rollDice(){
        return random.nextInt(6) + 1;

    }



}
