package com.example.abhijith.dicegame;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button roll, reset, hold, playAgain;
    TextView computerScore, humanScore, currentScore ,computerCurrentScore, message, finalMessage;
    ImageView diceImage;
    DiceActivity game;
    LinearLayout buttons;
    int ids, move;
    boolean isHold;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new DiceActivity();

        buttons = (LinearLayout) findViewById(R.id.buttons);

        roll = (Button) findViewById(R.id.roll);
        reset = (Button) findViewById(R.id.reset);
        hold = (Button) findViewById(R.id.hold);
        playAgain = (Button) findViewById(R.id.play_again);

        computerScore = (TextView) findViewById(R.id.computer_score);
        humanScore = (TextView) findViewById(R.id.player_score);
        currentScore = (TextView) findViewById(R.id.current_score);
        computerCurrentScore = (TextView) findViewById(R.id.computer_current_score);
        message = (TextView) findViewById(R.id.message);
        finalMessage = (TextView) findViewById(R.id.final_message);


        diceImage = (ImageView) findViewById(R.id.dice);

//        hold.getBackground().setColorFilter(0xFF64B5F6, PorterDuff.Mode.MULTIPLY);
//        reset.getBackground().setColorFilter(0xFF64B5F6, PorterDuff.Mode.MULTIPLY);
//        roll.getBackground().setColorFilter(0xFF64B5F6, PorterDuff.Mode.MULTIPLY);
//        playAgain.getBackground().setColorFilter(0xFF64B5F6, PorterDuff.Mode.MULTIPLY);



        hold.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchTurn();

        }
    });

    playAgain.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resetGame();
        }
    });

    reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resetGame();
        }
    });

    roll.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View view){

        int move = game.rollDice();
        makeTheDiceRoll(move);
        if(move==1)
        {
            game.humanCurrentScore = 0;
            switchTurn();
        }
        else
        {
            game.humanCurrentScore += move;
        }


       currentScore.setText(""+game.humanCurrentScore);

    }
    }
    );
    }


    void makeTheDiceRoll(int move)
    {
        switch (move){
            case 1:
                ids = R.drawable.dice1;
                break;
            case 2:
                ids = R.drawable.dice2;
                break;

            case 3:
                ids = R.drawable.dice3;
                break;

            case 4:
                ids = R.drawable.dice4;
                break;

            case 5:
                ids = R.drawable.dice5;
                break;

            case 6:
                ids = R.drawable.dice6;
                break;
        }
        diceImage.setImageResource(ids);
    }

    void switchTurn()
    {
        game.human = !game.human;
        if(game.human)
            {
                reset.setClickable(true);
                updateComputerScore();
                if(game.computerScore>=100)
                    {   endGame(false); return; }
                roll.setClickable(true);
                hold.setClickable(true);
                roll.setText("ROLL");
                hold.setText("HOLD");
                game.humanCurrentScore = 0;
                computerCurrentScore.setText("0");
                message.setText("Your turn..");

            }
        else
            {
                reset.setClickable(false);
                updateHumanScore();
                if(game.humanScore>=100)
                {   endGame(true); return; }
                roll.setClickable(false);
                hold.setClickable(false);
                roll.setText("WAIT");
                hold.setText("WAIT");
                game.computerCurrentScore = 0;
                currentScore.setText("0");

                message.setText("Computer's turn..");
                computerPlay();
            }
    }

    void updateComputerScore() {
        game.computerScore += game.computerCurrentScore;
        computerScore.setText("" + game.computerScore);
    }

    void updateHumanScore()
    {
        game.humanScore += game.humanCurrentScore;
        humanScore.setText("" +game.humanScore );
    }

//    void setHold(boolean val)
//    {
//        isHold = val;
//    }

    void computerPlay() {
        final Handler handler = new Handler();
        isHold = false;
        game.computerCurrentScore = 0;
        final String choice = "000011";
        int counter = 0;
        while (!isHold) {
            counter++;
            final int  mv = move = game.rollDice();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    makeTheDiceRoll(mv);
                    Log.d("The value of move is: ",""+mv);

                        if(mv!=1) {
                            game.computerCurrentScore += mv;
                            computerCurrentScore.setText(Integer.toString(game.computerCurrentScore));
                        }
                        // Log.d("sCORE : ",""+game.computerCurrentScore);



                }
            },1000*counter);

            if(move==1)
            {
                game.computerCurrentScore = 0;
                break;
            }
            isHold = ( 1 == Integer.valueOf(""+choice.charAt(game.rollDice()-1)) );


        }
        counter++;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Current com score: ",""+game.computerCurrentScore);
                if(move==1)
                    game.computerCurrentScore = 0;
                switchTurn();

            }
        },1000*counter);


    }

    void endGame(boolean isHuman)
    {
        if(isHuman) {
            finalMessage.setText("You won the Game!");
            Toast.makeText(MainActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();
        }
        else
            finalMessage.setText("Computer won the Game");

        buttons.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        finalMessage.setVisibility(View.VISIBLE);
        playAgain.setVisibility(View.VISIBLE);

    }

    void resetGame()
    {
        game.reset();
        humanScore.setText("0");
        currentScore.setText("0");
        computerScore.setText("0");
        computerCurrentScore.setText("0");
        message.setText("Your turn..");

        hold.setText("HOLD");
        roll.setText("ROLL");

        roll.setClickable(true);
        hold.setClickable(true);
        reset.setClickable(true);
        diceImage.setImageResource(R.drawable.dice1);

        buttons.setVisibility(View.VISIBLE);
        message.setVisibility(View.VISIBLE);
        finalMessage.setVisibility(View.GONE);
        playAgain.setVisibility(View.GONE);
    }

}
