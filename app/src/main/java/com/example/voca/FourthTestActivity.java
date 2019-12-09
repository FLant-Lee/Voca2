package com.example.voca;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import java.util.Locale;
import java.util.Random;

public class FourthTestActivity extends Activity {

    Button submitBtn;
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

        submitBtn = (Button) findViewById(R.id.submitBtn);
        tvEng = (TextView) findViewById(R.id.tvEng);
        etKor = (EditText) findViewById(R.id.etKor);
        Timeset = (TextView) findViewById(R.id.Timeset);
        correctImg = (ImageView) findViewById(R.id.correct);
        incorrectImg = (ImageView) findViewById(R.id.incorrect);


        //랜덤으로 vocaArr에 있는 Eng를 뿌림
        rd = new Random();
        num = rd.nextInt(VocaAddActivity.vocaArr.size());
        tvEng.setText((VocaAddActivity.vocaArr.get(VocaTestActivity.randArr[3]).eng));

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
                if (!isKorean(etKor.getText().toString()).equals("kor")) {
                    Toast.makeText(getApplicationContext(), "한국어를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if (isKorean(etKor.getText().toString()).equals("kor")) {

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
            }
        });

    }

    //시간이 초가 된 경우 or 정답을 맞춘 경우
    public void checkAnswer(int second) {
        if (is_right == true) {
            MediaPlayer player = MediaPlayer.create(this, R.raw.correct);
            player.start();
            correctImg.setVisibility(View.VISIBLE);
            VocaTestActivity.correctCnt++;

        } else {
            MediaPlayer player = MediaPlayer.create(this, R.raw.incorrect);
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


                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                Toast.makeText(getApplicationContext(), "다음으로", Toast.LENGTH_SHORT).show();
                visibilliyWidget("visible");
                correctImg.setVisibility(View.INVISIBLE);
                incorrectImg.setVisibility(View.INVISIBLE);
            }
        }.start();

    }

    public void visibilliyWidget(String flag) {
        if (flag.equals("visible")) {
            etKor.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.VISIBLE);
        } else {
            etKor.setVisibility(View.INVISIBLE);
            submitBtn.setVisibility(View.INVISIBLE);
        }
    }

    public String isKorean(String str) {
        if (str.matches("^[가-힣ㄱ-ㅎㅏ-ㅣ]*$")) {
            return "kor";
        }
        return "";
    }
}
