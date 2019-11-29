package com.example.voca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ResultActivity extends Activity {

    private Spinner spinner2;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocaresult);

        arrayList = new ArrayList<>();
        arrayList.add("철수");
        arrayList.add("영희");
        arrayList.add("람휘");
        arrayList.add("녹지");
        arrayList.add("치치");
        arrayList.add("양가");
        arrayList.add("용병");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);

        spinner2 = (Spinner) findViewById(R.id.spinner);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), arrayList.get(i) + "가 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

//        public void load () {
//            try {
//                BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/voca.txt"));
//                while (true) {
//                    String line = br.readLine();
//                    if (line == null) {
//                        break;
//                    }
//                    String[] tempArr = line.split(",");
//                    Voca voca = new Voca();
//                    voca.eng = tempArr[0];
//                    voca.kor = tempArr[1];
//                    vocaArr.add(voca);
//                }
//
//                br.close();
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                Toast.makeText(getApplicationContext(), "파일 없음", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
    }
}
