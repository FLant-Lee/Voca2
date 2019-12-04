package com.example.voca;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import static com.example.voca.VocaAddActivity.is_through;
import static com.example.voca.VocaAddActivity.vocaArr;

public class FourthTestActivity extends Activity {

    Button backbtn3, submitBtn;
    TextView Timeset, tvEng;
    EditText etKor;
    ImageView correctImg, incorrectImg;

    Random rd;
    boolean is_right = false;
    int num;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocatest);

        backbtn3 = (Button) findViewById(R.id.backbtn3);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        tvEng = (TextView) findViewById(R.id.tvEng);
        etKor = (EditText) findViewById(R.id.etKor);
        Timeset = (TextView) findViewById(R.id.Timeset);
        correctImg = (ImageView) findViewById(R.id.correct);
        incorrectImg = (ImageView) findViewById(R.id.incorrect);

        if (!is_through == true) {
            load();                         //load를 먼저 해야 vocaArr에 담김
            is_through = true;
        }

        //랜덤으로 vocaArr에 있는 Eng를 뿌림
        rd = new Random();
        num = rd.nextInt(VocaAddActivity.vocaArr.size());
        tvEng.setText((VocaAddActivity.vocaArr.get(VocaTestActivity.randArr[3]).eng));

        backbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //남은 시간 : OO초
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                Timeset.setText(String.format(Locale.getDefault(), "남은 시간 : %d초", millisUntilFinished / 1000L));
            }

            //시간 초과 할 경우
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "시간초과", Toast.LENGTH_SHORT).show();
                visibilliyWidget("invisible");
                checkAnswer(10);
            }
        }.start();


        //답을 제출할 경우
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //맞췄다면
                if (etKor.getText().toString().equals(VocaAddActivity.vocaArr.get(VocaTestActivity.randArr[3]).kor)) {
                    is_right = true;
                    checkAnswer(4);
                    countDownTimer.cancel();
                    Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
                    visibilliyWidget("invisible");
                }
                //틀렸다면
                else {
                    Toast.makeText(getApplicationContext(), "틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //시간이 초가 된 경우 or 정답을 맞춘 경우
    public void checkAnswer(int second) {
        if (is_right == true) {
            MediaPlayer player = MediaPlayer.create(this,R.raw.correct);
            player.start();
            correctImg.setVisibility(View.VISIBLE);

        } else {
            MediaPlayer player = MediaPlayer.create(this,R.raw.incorrect);
            player.start();
            incorrectImg.setVisibility(View.VISIBLE);
        }
        countDownTimer.cancel();
        CountDownTimer countDownTimer2 = new CountDownTimer(second * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvEng.setText((VocaAddActivity.vocaArr.get(VocaTestActivity.randArr[3]).kor));
                Timeset.setText(String.format(Locale.getDefault(), "다음으로 넘어가기 %d초 전", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), FifthTestActivity.class);
                startActivity(intent);


                overridePendingTransition(R.anim.fadein,R.anim.fadeout);

                Toast.makeText(getApplicationContext(), "다음으로", Toast.LENGTH_SHORT).show();
                visibilliyWidget("visible");
                correctImg.setVisibility(View.INVISIBLE);
                incorrectImg.setVisibility(View.INVISIBLE);
            }
        }.start();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/voca.txt"));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] tempArr = line.split(",");
                Voca voca = new Voca();
                voca.eng = tempArr[0];
                voca.kor = tempArr[1];
                vocaArr.add(voca);
            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "파일 없음", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void visibilliyWidget(String flag){
        if(flag.equals("visible")){
            etKor.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.VISIBLE);
        }else{
            etKor.setVisibility(View.INVISIBLE);
            submitBtn.setVisibility(View.INVISIBLE);
        }
    }
}
