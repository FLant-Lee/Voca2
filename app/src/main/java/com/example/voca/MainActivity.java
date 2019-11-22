package com.example.voca;

import android.view.View;
import android.widget.*;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Voca> vocaArr = new ArrayList<>();

    Button btnAllRemove;
    Button btnAdd;
    TextView tv;
    EditText etAddEng, etAddKor;

    Button btnDelete;
    EditText etDelteNum;

    Button btnUpdate;
    EditText etUpdateNum, etUpdateEng, etUpdateKor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAllRemove = (Button) findViewById(R.id.btnAllRemove);
        tv = (TextView) findViewById(R.id.tv);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        etAddEng = (EditText) findViewById(R.id.etAddEng);
        etAddKor = (EditText) findViewById(R.id.etAddKor);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        etDelteNum = (EditText) findViewById(R.id.etDeleteNum);

        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        etUpdateNum = (EditText) findViewById(R.id.etUpdateNum);
        etUpdateEng = (EditText) findViewById(R.id.etUpdateEng);
        etUpdateKor = (EditText) findViewById(R.id.etUpdateKor);

        /////////////////////////////////////////////////////////////////////////
        load(); //voca.txt - default값 읽어오기
        showVoca();
        /////////////////////////////////////////////////////////////////////////


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
                tv.setText("");
                save();
                showVoca();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int tmpIndex = Integer.parseInt(etDelteNum.getText().toString()) - 1;
                vocaArr.remove(tmpIndex);
                Toast.makeText(getApplicationContext(), (tmpIndex+1)+"번 삭제됨", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), (tmpIndex+1)+"번 변경됨", Toast.LENGTH_SHORT).show();
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
