package com.example.voca;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    public View vocalist;
    public TextView tv;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button vocaAdd = (Button) findViewById(R.id.VocaAdd);
        final Button vocaList = (Button) findViewById(R.id.VocaFind);
        Button vocaTest = (Button) findViewById(R.id.VocaTest);
        Button vocaResult = (Button) findViewById(R.id.TestResult);

//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                VocaAddActivity.vocaNum = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        vocaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VocaAddActivity.class);
                startActivity(intent);
            }
        });


        vocaList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VocaListActivity.class);
                startActivity(intent);

            }
        });

        vocaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VocaAddActivity.vocaArr.size() < 5) {
                    Toast.makeText(getApplicationContext(), "단어가 최소 5개 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), VocaTestActivity.class);
                    startActivity(intent);
                }
            }
        });


        vocaResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // 옵션 메뉴를 구현 하는 부분
        switch (item.getItemId()) {
            case R.id.menu1: //토스트메세지로 개발자 정보 출력
                Toast.makeText(this, "201558060 이상욱 201558118 이현우", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu2: //앱 설명 과정을 다이얼로그 메세지 방식으로 출력
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("1. 단어장 추가 (원하는 단어 검색 및 자신만의 단어장의 단어 추가 가능\n" + "2. 단어장 보기 (현재까지 저장한 단어 리스트를 볼 수 있습니다.\n" + "3. 단어 시험 (추가한 단어들로 랜덤으로 나오는 단어 시험을 볼 수있습니다.)\n" +
                        "4.시험 결과(현재까지 본 단어 시험의 결과를 조회 할 수 있는 기능 입니다.)\n"+"5. 메뉴에서 단어 10개 넣기를 선택하시면 자동으로 단어 10개가 추가 됩니다.\n");
                builder1.setTitle("앱 사용 설명서").setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert1 = builder1.create();
                alert1.setTitle("앱 사용 설명서");
                alert1.show();
                break;

            case R.id.menu3:

                String[] inputEng = {"computer", "apple", "coffee", "ant", "cafe", "number", "library", "school", "teacher", "student"};
                String[] inputKor = {"컴퓨터", "사과", "커피", "개미", "카페", "숫자", "도서관", "학교", "선생님", "학생"};
                for(int i = 0 ; i<inputEng.length; i++){
                    Voca voca = new Voca();
                    voca.eng = inputEng[i];
                    voca.kor = inputKor[i];
                    VocaAddActivity.vocaArr.add(voca);
                }

                try {
                    FileWriter fw = new FileWriter(getFilesDir() + "/voca.txt", false);

                    for (int i = 0; i < VocaAddActivity.vocaArr.size(); i++) {
                        Voca temp = VocaAddActivity.vocaArr.get(i);
                        fw.write(temp.eng + "," + temp.kor + "\n");
                    }
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"추가 되었습니다.",Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu4: //다이얼로그 창을 이용한 앱을 종료 하는 기능 구현
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setMessage("정말로 종료하시겠습니까?");
                builder2.setTitle("종료 알림창")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert2 = builder2.create();
                alert2.setTitle("종료 알림창");
                alert2.show();
        }
        return super.onOptionsItemSelected(item);
    }

}