package com.example.guesstheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static int score = 0;
    String answer;
    private TextView date, scoreTextView;
    private RadioGroup radioGroup;
    private RadioButton selectedButton, radioButton1, radioButton2 ,radioButton3, radioButton4;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.textView2);
        scoreTextView = findViewById(R.id.textView3);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        button = findViewById(R.id.button);
        generateQues();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                if (checkedId == -1){
                    Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }else{
                    selectedButton = findViewById(checkedId);
                    radioGroup.clearCheck();
                    if (selectedButton.getText() == answer){
                        score++;
                        generateQues();
                        scoreTextView.setText("Current Score: " + score);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Wrong answer", Toast.LENGTH_SHORT).show();
                        openActivity2();
                    }
                }
            }
        });

    }

    public void generateQues(){
        int year,month,day;
        year = Random(1800,2021);
        month = Random(1,12);
        if (month == 2) {
            if (isLeap(year))
                day = Random(1,29);
            else
                day = Random(1,28);
        } else {
            if (month==4 || month==6 || month==9 || month==11)
                day = Random(1,30);
            else
                day = Random(1,31);
        }
        date.setText(day+"/"+month+"/"+year);

        Calendar c = Calendar.getInstance();
        c.set(year,(month-1),day);
        int ans = c.get(Calendar.DAY_OF_WEEK);

        String[] daysArray = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        answer = daysArray[ans-1];
        String[] ansRemovedArray = deleteElement(daysArray,(ans-1));
        String[] optionsArray = {daysArray[ans-1],ansRemovedArray[0],ansRemovedArray[1],ansRemovedArray[2]};
        shuffleArray(optionsArray);
        radioButton1.setText(optionsArray[0]);
        radioButton2.setText(optionsArray[1]);
        radioButton3.setText(optionsArray[2]);
        radioButton4.setText(optionsArray[3]);
    }

    public int Random(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static boolean isLeap(int year)
    {
        if (year % 400 == 0)
            return true;
        if (year % 100 == 0)
            return false;
        return year % 4 == 0;
    }

    public static void shuffleArray(String[] arr) {
        Random rgen = new Random();

        for (int i = 0; i < arr.length; i++) {
            int randPos = rgen.nextInt(arr.length);
            String tmp = arr[i];
            arr[i] = arr[randPos];
            arr[randPos] = tmp;
        }
    }

    public static String[] deleteElement(String[] arr, int index)
    {
        String[] anotherArray = new String[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k] = arr[i];
            k++;
        }
        return anotherArray;
    }

    public void openActivity2(){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
        finish();
    }

}