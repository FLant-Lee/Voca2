package com.example.voca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import static com.example.voca.VocaAddActivity.is_through;
import static com.example.voca.VocaAddActivity.vocaArr;

public class VocaTestActivity extends Activity {

    Button backbtn3, submitBtn;
    TextView Timeset, tvEng;
    EditText etKor;
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

        if (!is_through == true) {
            load();                         //load를 먼저 해야 vocaArr에 담김
            is_through = true;
        }
        rd = new Random();
        num = rd.nextInt(VocaAddActivity.vocaArr.size());
        tvEng.setText((VocaAddActivity.vocaArr.get(num).eng));


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etKor.getText().toString().equals(VocaAddActivity.vocaArr.get(num).kor)) {
                    is_right = true;
                    checkAnswer(4);
                    countDownTimer.cancel();
                    Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "틀렸습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Timeset = (TextView) findViewById(R.id.Timeset);

        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                Timeset.setText(String.format(Locale.getDefault(), "남은 시간 : %d초", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                Timeset.setText("TimeOver");
                checkAnswer(4);
            }
        }.start();


        if (is_right == true) {

            //Disable the cancel, pause and resume button
            Timeset.setEnabled(false);
            countDownTimer.cancel();

            //Notify the user that CountDownTimer is canceled/stopped
            Timeset.setText("CountDownTimer Canceled/stopped.");
        }

        backbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void checkAnswer(int second) {
        countDownTimer.cancel();
        CountDownTimer countDownTimer2 = new CountDownTimer(second * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                Timeset.setText(String.format(Locale.getDefault(), "다음으로 넘어가기 %d초 전", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                submitBtn.performClick();

            }
        }.start();

        if (Timeset.getText().equals("다음으로 넘어가기 0초 전")) {

            Intent intent = new Intent(getApplicationContext(), SecondTestActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"다음으로",Toast.LENGTH_SHORT).show();

        }

    }

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
}
