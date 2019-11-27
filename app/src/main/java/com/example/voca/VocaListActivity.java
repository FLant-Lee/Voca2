package com.example.voca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

import static com.example.voca.VocaAddActivity.*;

public class VocaListActivity extends Activity {


    TextView tv;

    Button btnAllRemove;

    Button btnDelete;
    EditText etDelteNum;

    Button btnUpdate;
    EditText etUpdateNum, etUpdateEng, etUpdateKor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocalist);

        tv = (TextView) findViewById(R.id.tv);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        etDelteNum = (EditText) findViewById(R.id.etDeleteNum);

        btnAllRemove = (Button) findViewById(R.id.btnAllRemove);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etUpdateNum = (EditText) findViewById(R.id.etUpdateNum);
        etUpdateEng = (EditText) findViewById(R.id.etUpdateEng);
        etUpdateKor = (EditText) findViewById(R.id.etUpdateKor);

        if (!is_through == true) {
            load();
            is_through = true;
        }
        tv.setText("");
        showVoca();

        Button backbtn2 = (Button) findViewById(R.id.backbtn2);

        backbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnAllRemove.setOnClickListener(new View.OnClickListener() {
            //빈내용으로 txt를 덮어 써버리고
            //컬레션과 텍스트필드를 비워줌
            @Override
            public void onClick(View v) {

                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "/voca.txt", false));
                    bw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "전체 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                tv.setText("");
                vocaArr.clear();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int tmpIndex = Integer.parseInt(etDelteNum.getText().toString()) - 1;
                vocaArr.remove(tmpIndex);
                Toast.makeText(getApplicationContext(), (tmpIndex + 1) + "번 삭제됨", Toast.LENGTH_SHORT).show();
                tv.setText("");
                save();
                showVoca();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tmpIndex = Integer.parseInt(etUpdateNum.getText().toString()) - 1;
                vocaArr.get(tmpIndex).eng = etUpdateEng.getText().toString();
                vocaArr.get(tmpIndex).kor = etUpdateKor.getText().toString();
                Toast.makeText(getApplicationContext(), (tmpIndex + 1) + "번 변경됨", Toast.LENGTH_SHORT).show();
                tv.setText("");
                save();
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