package com.example.exerciseone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.os.Vibrator;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageButton button_left;
    private ImageButton button_right;
    private ImageView motor_right;
    private ImageView motor_middle;
    private ImageView motor_left;
    private Handler ha=new Handler();
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;
    private static Timer timer = new Timer();

    ArrayList <ImageView> all_rock_middle=new ArrayList();
    ArrayList <ImageView> all_rock_left=new ArrayList();
    ArrayList <ImageView> all_rock_right=new ArrayList();

    private int state=1;
    private int i;
    private int count = 0;
    private int random=1;
    public int hearts_number=3;
    private Runnable r;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAllElements();
        changePositionToMotor();
        ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

        Glide
                .with(this)
                .load("road.jpg")
                .into(imageView);
        initialRocks();
        putRandomStone();

        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (state>0){
                    state--;
                }
                changePositionToMotor();
            }
        });
        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state<2){
                    state++;
                }
                changePositionToMotor();

            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        if(count<7)
        {
                handleTimerAndGame();
        }

    }
    public void initialRocks()
    {
        for (i=0; i<all_rock_middle.size();i++)
        {
            all_rock_middle.get(i).setVisibility(View.INVISIBLE);
            all_rock_left.get(i).setVisibility(View.INVISIBLE);
            all_rock_right.get(i).setVisibility(View.INVISIBLE);
        }

    }
    public void checkCountIfDamage()
    {
        if(count==5)
        {
            if(motor_left.getVisibility()==View.VISIBLE && all_rock_left.get(count+2).getVisibility()==View.VISIBLE)
            {
                //heart gone
                hearts_number--;
                disapearHeart();
            }else if(motor_right.getVisibility()==View.VISIBLE && all_rock_right.get(count+2).getVisibility()==View.VISIBLE)
            {
                hearts_number--;
                disapearHeart();
            }else if(motor_middle.getVisibility()==View.VISIBLE && all_rock_middle.get(count+2).getVisibility()==View.VISIBLE)
            {
                hearts_number--;
                disapearHeart();


            }
        }
        else if(count==6)
        {
            if(motor_left.getVisibility()==View.VISIBLE && all_rock_left.get(count+1).getVisibility()==View.VISIBLE)
            {
                //heart gone
                hearts_number--;
                disapearHeart();
            }else if(motor_right.getVisibility()==View.VISIBLE && all_rock_right.get(count+1).getVisibility()==View.VISIBLE)
            {
                hearts_number--;
                disapearHeart();
            }else if(motor_middle.getVisibility()==View.VISIBLE && all_rock_middle.get(count+1).getVisibility()==View.VISIBLE)
            {
                hearts_number--;
                disapearHeart();

            }
        }
        else if(count==7)
        {
            if(motor_left.getVisibility()==View.VISIBLE && all_rock_left.get(count).getVisibility()==View.VISIBLE)
            {
                //heart gone
                hearts_number--;
                disapearHeart();
            }else if(motor_right.getVisibility()==View.VISIBLE && all_rock_right.get(count).getVisibility()==View.VISIBLE)
            {
                hearts_number--;
                disapearHeart();
            }else if(motor_middle.getVisibility()==View.VISIBLE && all_rock_middle.get(count).getVisibility()==View.VISIBLE)
            {
                hearts_number--;
                disapearHeart();

            }

        }

    }
    public void changePositionToStone()
    {
        if (random==2){
            all_rock_middle.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_middle.get(count).setVisibility(View.VISIBLE);
        }else if(random==1){//one rock at the left
            all_rock_left.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_left.get(count).setVisibility(View.VISIBLE);
        }else if(random==3){//one rock at the right
            all_rock_right.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_right.get(count).setVisibility(View.VISIBLE);
        }else if(random==4){//two rocks at the middle and right
            all_rock_right.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_right.get(count).setVisibility(View.VISIBLE);
            all_rock_middle.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_middle.get(count).setVisibility(View.VISIBLE);
        }else if(random==5){//two rocks at the middle and left
            all_rock_left.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_left.get(count).setVisibility(View.VISIBLE);
            all_rock_middle.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_middle.get(count).setVisibility(View.VISIBLE);
        }else if(random==6){//two rocks at the left and right
            all_rock_left.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_left.get(count).setVisibility(View.VISIBLE);
            all_rock_right.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_right.get(count).setVisibility(View.VISIBLE);
        }else if(random==7){
            all_rock_left.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_middle.get(count-1).setVisibility(View.INVISIBLE);
            if(count<7){
                all_rock_left.get(count+1).setVisibility(View.VISIBLE);
                all_rock_middle.get(count+1).setVisibility(View.VISIBLE);
            }
        }else if(random==8){
            all_rock_left.get(count-1).setVisibility(View.INVISIBLE);
            all_rock_right.get(count-1).setVisibility(View.INVISIBLE);
            if(count<7){
                all_rock_left.get(count+1).setVisibility(View.VISIBLE);
                all_rock_right.get(count+1).setVisibility(View.VISIBLE);
            }
        }else if(random==9) {
            all_rock_left.get(count - 1).setVisibility(View.INVISIBLE);
            all_rock_left.get(count).setVisibility(View.VISIBLE);
            if(count < 7) {
                all_rock_right.get(count + 1).setVisibility(View.INVISIBLE);
                all_rock_middle.get(count + 1).setVisibility(View.INVISIBLE);

            }if (count < 6) {
                all_rock_right.get(count + 2).setVisibility(View.VISIBLE);
                all_rock_middle.get(count + 2).setVisibility(View.VISIBLE);
            }
        }else if (random == 10) {
            all_rock_right.get(count - 1).setVisibility(View.INVISIBLE);
            all_rock_right.get(count).setVisibility(View.VISIBLE);
            if(count<7) {
                all_rock_left.get(count + 1).setVisibility(View.INVISIBLE);
                all_rock_middle.get(count + 1).setVisibility(View.INVISIBLE);
            }if (count < 6) {
                all_rock_left.get(count + 2).setVisibility(View.VISIBLE);
                all_rock_middle.get(count + 2).setVisibility(View.VISIBLE);
            }
        }else if(random==11) {
            all_rock_left.get(count - 1).setVisibility(View.INVISIBLE);
            all_rock_right.get(count - 1).setVisibility(View.INVISIBLE);
            all_rock_left.get(count).setVisibility(View.VISIBLE);
            all_rock_right.get(count).setVisibility(View.VISIBLE);
            if(count<7)
                all_rock_middle.get(count + 1).setVisibility(View.INVISIBLE);
            if (count < 6) {
                all_rock_middle.get(count + 2).setVisibility(View.VISIBLE);
            }
        }else if (random == 12) {
            all_rock_middle.get(count - 1).setVisibility(View.INVISIBLE);
            all_rock_middle.get(count).setVisibility(View.VISIBLE);
            if(count<7){
                all_rock_right.get(count + 1).setVisibility(View.INVISIBLE);
                all_rock_left.get(count + 1).setVisibility(View.INVISIBLE);
            }
            if (count < 6) {
                all_rock_right.get(count + 2).setVisibility(View.VISIBLE);
                all_rock_left.get(count + 2).setVisibility(View.VISIBLE);

            }
        }
    }
    public void handleTimerAndGame()
    {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        if(count<8){
                    //one rock at the middle
                              changePositionToStone();
                              checkCountIfDamage();

                          }else{
                            initialRocks();
                            count=0;
                            putRandomStone();
                }
                    }
                });
            }
        }, 0, 1000);

    }
    public void massage(boolean flagGameOver)
    {
        CharSequence text = "Ouch you got Crashed";
        if(flagGameOver){
             text = "Game Over !!\n"+"See you next time :)";
        }
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;


        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void vibrate()
    {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
    public void gameOver()
    {
        findViewById(R.id.gameOverText).setVisibility(View.VISIBLE);
        //finish all tasks
        timer.cancel();

        //put a nice massage
        massage(true);
       
        


    }
    public void disapearHeart()
    {
        if(heart3.getVisibility()==View.VISIBLE){
            //vibration
            vibrate();
            //toast massage
            massage(false);
            heart3.setVisibility(View.INVISIBLE);
        }else if(heart2.getVisibility()==View.VISIBLE){
            heart2.setVisibility(View.INVISIBLE);
            vibrate();
            massage(false);
        }else{
            heart1.setVisibility(View.INVISIBLE);
            vibrate();
            massage(false);
            gameOver();
        }
    }
    public void putRandomStone()
    {
        random = new Random().nextInt(12) + 1; // [0, 60] + 20 => [20, 80]

        if (random==1){
            all_rock_left.get(0).setVisibility(View.VISIBLE);
            all_rock_right.get(0).setVisibility(View.INVISIBLE);
            all_rock_middle.get(0).setVisibility(View.INVISIBLE);
        }else if (random==2){
            all_rock_right.get(0).setVisibility(View.INVISIBLE);
            all_rock_left.get(0).setVisibility(View.INVISIBLE);
            all_rock_middle.get(0).setVisibility(View.VISIBLE);
        }else if (random==3){
            all_rock_left.get(0).setVisibility(View.INVISIBLE);
            all_rock_right.get(0).setVisibility(View.VISIBLE);
            all_rock_middle.get(0).setVisibility(View.INVISIBLE);
        }else if (random==4){
            all_rock_right.get(0).setVisibility(View.VISIBLE);
            all_rock_left.get(0).setVisibility(View.INVISIBLE);
            all_rock_middle.get(0).setVisibility(View.VISIBLE);
        }else if (random==5){
            all_rock_left.get(0).setVisibility(View.VISIBLE);
            all_rock_right.get(0).setVisibility(View.INVISIBLE);
            all_rock_middle.get(0).setVisibility(View.VISIBLE);
        }else if (random==6){
            all_rock_left.get(0).setVisibility(View.VISIBLE);
            all_rock_right.get(0).setVisibility(View.VISIBLE);
            all_rock_middle.get(0).setVisibility(View.INVISIBLE);
        }else if (random==7){
            all_rock_left.get(0).setVisibility(View.VISIBLE);
            all_rock_right.get(0).setVisibility(View.INVISIBLE);
            all_rock_middle.get(0).setVisibility(View.VISIBLE);
            all_rock_left.get(1).setVisibility(View.VISIBLE);
            all_rock_right.get(1).setVisibility(View.INVISIBLE);
            all_rock_middle.get(1).setVisibility(View.VISIBLE);
        }else if (random==8){
            all_rock_left.get(0).setVisibility(View.VISIBLE);
            all_rock_right.get(0).setVisibility(View.VISIBLE);
            all_rock_middle.get(0).setVisibility(View.INVISIBLE);
            all_rock_left.get(1).setVisibility(View.VISIBLE);
            all_rock_right.get(1).setVisibility(View.VISIBLE);
            all_rock_middle.get(1).setVisibility(View.INVISIBLE);
        }else if (random==9){
            all_rock_left.get(0).setVisibility(View.VISIBLE);
            all_rock_right.get(0).setVisibility(View.INVISIBLE);
            all_rock_middle.get(0).setVisibility(View.INVISIBLE);
            all_rock_left.get(2).setVisibility(View.INVISIBLE);
            all_rock_right.get(2).setVisibility(View.VISIBLE);
            all_rock_middle.get(2).setVisibility(View.VISIBLE);
        }else if (random==10){
            all_rock_left.get(0).setVisibility(View.INVISIBLE);
            all_rock_right.get(0).setVisibility(View.VISIBLE);
            all_rock_middle.get(0).setVisibility(View.INVISIBLE);
            all_rock_left.get(2).setVisibility(View.VISIBLE);
            all_rock_right.get(2).setVisibility(View.INVISIBLE);
            all_rock_middle.get(2).setVisibility(View.VISIBLE);
        }else if (random==11){
            all_rock_left.get(0).setVisibility(View.VISIBLE);
            all_rock_right.get(0).setVisibility(View.VISIBLE);
            all_rock_middle.get(0).setVisibility(View.INVISIBLE);
            all_rock_left.get(2).setVisibility(View.INVISIBLE);
            all_rock_right.get(2).setVisibility(View.INVISIBLE);
            all_rock_middle.get(2).setVisibility(View.VISIBLE);
        }else if (random==12){
            all_rock_left.get(0).setVisibility(View.INVISIBLE);
            all_rock_right.get(0).setVisibility(View.INVISIBLE);
            all_rock_middle.get(0).setVisibility(View.VISIBLE);
            all_rock_left.get(2).setVisibility(View.VISIBLE);
            all_rock_right.get(2).setVisibility(View.VISIBLE);
            all_rock_middle.get(2).setVisibility(View.INVISIBLE);
        }
    }
    public void initAllElements()
    {
        count = 0;
        findViewById(R.id.gameOverText).setVisibility(View.INVISIBLE);
        //hearts
        heart1=findViewById(R.id.panel_IMG_heart1);
        heart2=findViewById(R.id.panel_IMG_heart2);
        heart3=findViewById(R.id.panel_IMG_heart3);
        //motor
        motor_left=findViewById(R.id.motor_left);
        motor_middle=findViewById(R.id.motor_middle);
        motor_right=findViewById(R.id.motor_right);

        //left
        all_rock_left.add(findViewById(R.id.rock_left1));
        all_rock_left.add(findViewById(R.id.rock_left2));
        all_rock_left.add(findViewById(R.id.rock_left3));
        all_rock_left.add(findViewById(R.id.rock_left4));
        all_rock_left.add(findViewById(R.id.rock_left5));
        all_rock_left.add(findViewById(R.id.rock_left6));
        all_rock_left.add(findViewById(R.id.rock_left7));
        all_rock_left.add(findViewById(R.id.rock_left8));
        //middle
        all_rock_middle.add(findViewById(R.id.rock_middle1));
        all_rock_middle.add(findViewById(R.id.rock_middle2));
        all_rock_middle.add(findViewById(R.id.rock_middle3));
        all_rock_middle.add(findViewById(R.id.rock_middle4));
        all_rock_middle.add(findViewById(R.id.rock_middle5));
        all_rock_middle.add(findViewById(R.id.rock_middle6));
        all_rock_middle.add(findViewById(R.id.rock_middle7));
        all_rock_middle.add(findViewById(R.id.rock_middle8));
        //right
        all_rock_right.add(findViewById(R.id.rock_right1));
        all_rock_right.add(findViewById(R.id.rock_right2));
        all_rock_right.add(findViewById(R.id.rock_right3));
        all_rock_right.add(findViewById(R.id.rock_right4));
        all_rock_right.add(findViewById(R.id.rock_right5));
        all_rock_right.add(findViewById(R.id.rock_right6));
        all_rock_right.add(findViewById(R.id.rock_right7));
        all_rock_right.add(findViewById(R.id.rock_right8));

        //buttons
        button_left= findViewById(R.id.button_left_arrow);
        button_right= findViewById(R.id.button_right_arrow);





    }
    public void changePositionToMotor()
    {
        if (state==1){
            motor_right.setVisibility(View.INVISIBLE);
            motor_left.setVisibility(View.INVISIBLE);
            motor_middle.setVisibility(View.VISIBLE);


        }else if(state==2){
            motor_left.setVisibility(View.VISIBLE);
            motor_right.setVisibility(View.INVISIBLE);
            motor_middle.setVisibility(View.INVISIBLE);
        }else if(state==0){
            motor_right.setVisibility(View.VISIBLE);
            motor_left.setVisibility(View.INVISIBLE);
            motor_middle.setVisibility(View.INVISIBLE);
        }
    }
}