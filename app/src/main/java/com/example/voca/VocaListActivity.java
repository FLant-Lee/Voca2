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


    TextView tv;                    // load된 vocaArr의 값이 showVoca메서드를 통해 전달된다.
    Button btnAllRemove;            // 전체삭제 버튼
    Button btnDelete;               // index값을 입력받아 삭제하는 버튼
    EditText etDelteNum;            // 몇번째 것을 삭제할지 넣는 칸
    Button btnUpdate;               // 업데이트 버튼
    EditText etUpdateNum, etUpdateEng, etUpdateKor; //업데이트할 인덱스값, 업데이트할 영단어, 업데이트할 단어뜻

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocalist);

        // load될 텍스트 뷰
        tv = (TextView) findViewById(R.id.tv);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        etDelteNum = (EditText) findViewById(R.id.etDeleteNum);

        btnAllRemove = (Button) findViewById(R.id.btnAllRemove);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etUpdateNum = (EditText) findViewById(R.id.etUpdateNum);
        etUpdateEng = (EditText) findViewById(R.id.etUpdateEng);
        etUpdateKor = (EditText) findViewById(R.id.etUpdateKor);


        if (!is_through == true) {      // 다른 Activity에서 로드가 된적이 있다면 또 로드 할필요가 없음
            load();                     //vocaArr로 로드해야 vocaShow등 모든게 가능
            is_through = true;
        }
        tv.setText("");                 //tv 내용 없엠
        showVoca();                     //  vocaArr에 있는 내용 tv에 출력



        btnAllRemove.setOnClickListener(new View.OnClickListener() {
            //빈내용으로 txt를 덮어 써버리고
            //컬레션vocaArr과 텍스트필드를 비워줌
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

        //삭제할 인덱스값을 받아 삭제하고 세이브하고 tv에 있는내용을 다 지우고(덮어쓰기 방식이므로) 다시 tv에 vocaArr에 있는 내용을 뿌려줌
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!whatLanguage(etDelteNum.getText().toString()).equals("num")) {
                    Toast.makeText(getApplicationContext(), "숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    int tmpIndex = Integer.parseInt(etDelteNum.getText().toString()) - 1;
                    vocaArr.remove(tmpIndex);
                    Toast.makeText(getApplicationContext(), (tmpIndex + 1) + "번 삭제됨", Toast.LENGTH_SHORT).show();
                    tv.setText("");
                    save();
                    showVoca();
                }
                etDelteNum.setText("");

            }
        });

        //삭제할 인덱스값을 받아 업데이트 세이브하고 tv에 있는내용을 다 지우고(덮어쓰기 방식이므로) 다시 tv에 vocaArr에 있는 내용을 뿌려줌

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!whatLanguage(etUpdateNum.getText().toString()).equals("num")) {
                    Toast.makeText(getApplicationContext(), "숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etUpdateNum.setText("");
                }
                if (!whatLanguage(etUpdateEng.getText().toString()).equals("eng")) {
                    Toast.makeText(getApplicationContext(), "영어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etUpdateEng.setText("");
                }
                if (!whatLanguage(etUpdateKor.getText().toString()).equals("kor")) {
                    Toast.makeText(getApplicationContext(), "한글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etUpdateKor.setText("");
                }
                if (whatLanguage(etUpdateNum.getText().toString()).equals("num") && whatLanguage(etUpdateEng.getText().toString()).equals("eng") && whatLanguage(etUpdateKor.getText().toString()).equals("kor")) {
                    int tmpIndex = Integer.parseInt(etUpdateNum.getText().toString()) - 1;
                    vocaArr.get(tmpIndex).eng = etUpdateEng.getText().toString();
                    vocaArr.get(tmpIndex).kor = etUpdateKor.getText().toString();
                    Toast.makeText(getApplicationContext(), (tmpIndex + 1) + "번 변경됨", Toast.LENGTH_SHORT).show();
                    tv.setText("");
                    save();
                    showVoca();
                    etUpdateNum.setText("");
                    etUpdateEng.setText("");
                    etUpdateKor.setText("");
                }
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


    //덮어쓰기 방식 FileWriter
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

    public String whatLanguage(String str) {
        if (str.matches("^[가-힣ㄱ-ㅎㅏ-ㅣ]*$")) {
            return "kor";
        } else if (str.matches("^[a-z]*$")) {
            return "eng";
        } else if (str.matches("^[0-9]]*$")) {
            return "num";
        }
        return "";
    }

}