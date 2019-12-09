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

public class VocaTestActivity extends Activity {


    // 시험을 보는데에 있어서 5개의 문제가 중복으로 나오지 않게 하기위해서 설정해놓은
    // 상수 5, 5개의 인덱스 정적 배열, Random 클래스 입니다.
    private final static int NUMBER = 5;
    static int[] randArr = new int[NUMBER];
    Random rd = new Random();

    // 문제 제출 버튼 입니다.
    Button submitBtn;

    // 남은시간과 다음이로 화면전환전 남은 시간을 보여주는 Timeset 텍스트 뷰와 문제가 보여지는 tvEng 텍스트 뷰입니다.
    TextView Timeset, tvEng;
    // 단어 뜻을 입력한 에디트 텍스트 입니다.
    EditText etKor;
    // visibility를 이용해서 맞췄을 경우와 틀렸을경우 보여질 이미지 두개 입니다.
    ImageView correctImg, incorrectImg;

    // 문제를 맞췄을시 설정할 맞춤플래그
    boolean is_right = false;
    // 5문제의 맞춘개수를 표현할 count
    static int correctCnt = 0;
//    //동적 배열 사이즈중 랜덤으로 뽑혀진 index를 담을 정수형 변수
//    int num;

    // 카운트 다운을 위한 위젯
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

        // vocaList의 텍스트뷰의 데이터값들이 중복으로 들어오지않게 하기위해 만든 조건문
        if (!is_through == true) {
            load();                         //load를 먼저 해야 voca.txt에 있는 데이터가 vocaArr에 담김
            is_through = true;              //여기서 로드되었으면 다른 곳에 들어갔을때 로드 되지 않는다.
        }

        //랜덤으로 vocaArr에 있는 Eng를 뿌림
        //중복 불허이니 최소 vocaArr에는 5개가 들어가 있어야함
        rd = new Random();
        for (int i = 0; i < randArr.length; i++) {
            randArr[i] = rd.nextInt(VocaAddActivity.vocaArr.size());
            for (int j = 0; j < i; j++) {
                if (randArr[i] == randArr[j]) {
                    i--;
                }
            }
        }
        //랜덤 중복X된 randArr의 값의 중 첫번째의 vocaArr의 eng를 tv에 뿌림
        tvEng.setText((VocaAddActivity.vocaArr.get(randArr[0]).eng));



        //남은 시간 : 1O초 드립니다.
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                Timeset.setText(String.format(Locale.getDefault(), "남은 시간 : %d초", millisUntilFinished / 1000L));
            }

            //시간 초과 할 경우
            public void onFinish() {
                // 시간초과 토스트메세지를 띄우고
                Toast.makeText(getApplicationContext(), "시간초과", Toast.LENGTH_SHORT).show();
                // 한글입력칸과 제출버튼을 지우고 X이미지를 띄워줍니다.
                visibilliyWidget("invisible");
                //그러고 나서 10초동안 정답을 체크하는 메서드로 이동합니다.
                checkAnswer(10);
            }
        }.start();


        //답을 제출할 경우
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 한글이 맞는지 확인하는 조건문
                if (!isKorean(etKor.getText().toString()).equals("kor")) {
                    Toast.makeText(getApplicationContext(), "한국어를 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                if (isKorean(etKor.getText().toString()).equals("kor")) {
                    //맞췄다면 is_right플래그가 true가 됩니다. 4초 동안 보여지는 checkAnswer메서드에서 true면 O이미지를 띄우고 count를 올림
                    if (etKor.getText().toString().equals(VocaAddActivity.vocaArr.get(randArr[0]).kor)) {
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

        //맞다면 정답 소리를 틀어줌
        if (is_right == true) {
            MediaPlayer player = MediaPlayer.create(this, R.raw.correct); // 미디어 플레이어 기능을 이용한 사운드 출력 raw 폴더 아래에 있는 사운드 출력
            player.start(); // 사운드 스타트
            // 정답 이미지가 보여짐
            correctImg.setVisibility(View.VISIBLE);
            correctCnt++;
        //틀렸으니 틀린 소리를 틀어줌
        } else {
            MediaPlayer player = MediaPlayer.create(this, R.raw.incorrect); // 미디어 플레이어 기능을 이용한 사운드 출력 raw 폴더 아래에 있는 사운드 출력
            player.start(); // 사운드 스타트
            incorrectImg.setVisibility(View.VISIBLE);
        }
        //기존 카운트 다운을 멈춤
        countDownTimer.cancel();
        //새로운 카운트 다운을 함
        //들어오는 인자값에 카운트다운 시간을 달리함
        CountDownTimer countDownTimer2 = new CountDownTimer(second * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //정답을 띄워 줍니다.
                tvEng.setText((VocaAddActivity.vocaArr.get(randArr[0]).kor));
                Timeset.setText(String.format(Locale.getDefault(), "다음으로 넘어가기 %d초 전", millisUntilFinished / 1000L));
            }

            // 끝났다면 2번째 SecondTestActivity로 이동하게 됩니다.
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), SecondTestActivity.class);
                startActivity(intent);

                // 애니메이션 효과
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout); //애니메이션 기능 xml 이용

                //다음으로 라는 토스메세지를 띄워주고 안보이게했던 에디트텍스트와 제출버튼을 다시보이게 합니다.
                Toast.makeText(getApplicationContext(), "다음으로", Toast.LENGTH_SHORT).show();
                visibilliyWidget("visible");
                //맞춸을때와 틀렸을때의 이미지를 다시 감춥니다.
                correctImg.setVisibility(View.INVISIBLE);
                incorrectImg.setVisibility(View.INVISIBLE);

            }
        }.start();

    }

    // BufferredReader 스트림을 이용하여 한줄한줄씩 읽어주는 방식을 이용하였습니다.
    public void load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/voca.txt"));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                // ,로 스플릿을 통해 2개로 나뉘어지고
                String[] tempArr = line.split(",");
                // 그 값들이 Voca클래스의 eng와 kor 차례로 들어가서
                Voca voca = new Voca();
                voca.eng = tempArr[0];
                voca.kor = tempArr[1];
                //vocaArr로 담기게 됩니다.
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

    // 에디트텍스트와 버튼을 가리기 위한 용도
    public void visibilliyWidget(String flag) {
        if (flag.equals("visible")) {
            etKor.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.VISIBLE);
        } else {
            etKor.setVisibility(View.INVISIBLE);
            submitBtn.setVisibility(View.INVISIBLE);
        }
    }

    // 웹서칭을 통해 사용자 오류를 잡기위한 메서드
    public String isKorean(String str) {
        if (str.matches("^[가-힣ㄱ-ㅎㅏ-ㅣ]*$")) {
            return "kor";
        }
        return "";
    }
}
