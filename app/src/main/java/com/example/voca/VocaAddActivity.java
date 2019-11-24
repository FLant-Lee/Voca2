package com.example.voca;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;

import java.io.*;
import java.util.ArrayList;

public class VocaAddActivity extends Activity {


    View vocalist;

    static ArrayList<Voca> vocaArr = new ArrayList<>();
    static boolean is_through = false;


    Button btnAdd;
    EditText etAddEng, etAddKor;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocaadd);

        Button backbtn = (Button) findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vocalist = getLayoutInflater().inflate(R.layout.vocalist, null, false);
        tv = (TextView) vocalist.findViewById(R.id.tv);


        btnAdd = (Button) findViewById(R.id.btnAdd);

        etAddEng = (EditText) findViewById(R.id.etAddEng);
        etAddKor = (EditText) findViewById(R.id.etAddKor);


        /////////////////////////////////////////////////////////////////////////
//        load(); //voca.txt - default값 읽어오기
//        showVoca();
        /////////////////////////////////////////////////////////////////////////


        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Voca voca = new Voca();

                voca.eng = String.valueOf(etAddEng.getText());
                voca.kor = String.valueOf(etAddKor.getText());
                vocaArr.add(voca);
                Toast.makeText(getApplicationContext(), "단어가 추가 되었습니다.", Toast.LENGTH_SHORT).show();

//                for (int i = 0; i < vocaArr.size(); i++) {
//                    if (vocaArr.get(i).eng.equals(voca.eng)) {
//                        voca = null;
//                        Toast.makeText(getApplicationContext(),"이미 있는 단어입니다.",Toast.LENGTH_SHORT).show();
//                    } else {
//                        vocaArr.add(voca);
//                        Toast.makeText(getApplicationContext(),"이미 있는 단어입니다.",Toast.LENGTH_SHORT).show();
//                    }
//                }
                save();
                tv.setText("");
                showVoca();
            }
        });


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

    public void save() {
        try {
            FileWriter fw = new FileWriter(getFilesDir() + "/voca.txt", false);


            for (int i = 0; i < vocaArr.size(); i++) {
                Voca temp = vocaArr.get(i);
                fw.write(temp.eng + "," + temp.kor + "\n");
            }

            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void showVoca() {
        for (int i = 0; i < vocaArr.size(); i++) {
            tv.append((i + 1) + ". " + vocaArr.get(i).eng + "  : " + vocaArr.get(i).kor + "\n");
        }
    }
}
