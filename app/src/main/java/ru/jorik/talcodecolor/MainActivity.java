package ru.jorik.talcodecolor;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {

    NumberTag[] tagsNum = {new NumberTag(1),
            new NumberTag(2),
            new NumberTag(3),
            new NumberTag(4),
            new NumberTag(5),
            new NumberTag(6),
            new NumberTag(7),
            new NumberTag(8),
            new NumberTag(9)};

    NumberTag[] tagsCode = {new NumberTag(1),
            new NumberTag(2),
            new NumberTag(3),
            new NumberTag(4),
            new NumberTag(5),
            new NumberTag(6),
            new NumberTag(7),
            new NumberTag(8),
            new NumberTag(9)};

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// TODO: 25.07.2017 hardcode
        findViewById(R.id.number1).setTag(tagsNum[0]);
        findViewById(R.id.number2).setTag(tagsNum[1]);
        findViewById(R.id.number3).setTag(tagsNum[2]);
        findViewById(R.id.number4).setTag(tagsNum[3]);
        findViewById(R.id.number5).setTag(tagsNum[4]);
        findViewById(R.id.number6).setTag(tagsNum[5]);
        findViewById(R.id.number7).setTag(tagsNum[6]);
        findViewById(R.id.number8).setTag(tagsNum[7]);
        findViewById(R.id.number9).setTag(tagsNum[8]);

        //// TODO: 25.07.2017 hardcode
        findViewById(R.id.code1).setTag(tagsCode[0]);
        findViewById(R.id.code2).setTag(tagsCode[1]);
        findViewById(R.id.code3).setTag(tagsCode[2]);
        findViewById(R.id.code4).setTag(tagsCode[3]);
        findViewById(R.id.code5).setTag(tagsCode[4]);
        findViewById(R.id.code6).setTag(tagsCode[5]);
        findViewById(R.id.code7).setTag(tagsCode[6]);
        findViewById(R.id.code8).setTag(tagsCode[7]);
        findViewById(R.id.code9).setTag(tagsCode[8]);

        prefs = getSharedPreferences("colors", MODE_PRIVATE);
        int[] colors = readPrefs();
        //// TODO: 25.07.2017 hardcode
        changeColor(findViewById(R.id.colorPicker1), colors[0]);
        changeColor(findViewById(R.id.colorPicker2), colors[1]);
        changeColor(findViewById(R.id.colorPicker3), colors[2]);
        changeColor(findViewById(R.id.colorPicker4), colors[3]);
        changeColor(findViewById(R.id.colorPicker5), colors[4]);
        changeColor(findViewById(R.id.colorPicker6), colors[5]);
        changeColor(findViewById(R.id.colorPicker7), colors[6]);
        changeColor(findViewById(R.id.colorPicker8), colors[7]);
        changeColor(findViewById(R.id.colorPicker9), colors[8]);
    }

    public void pickColor(final View v){
        final int tag;
        //// TODO: 25.07.2017 hardcode
        switch (v.getId()){
            case R.id.colorPicker1:
                tag = 0;
                break;
            case R.id.colorPicker2:
                tag = 1;
                break;
            case R.id.colorPicker3:
                tag = 2;
                break;
            case R.id.colorPicker4:
                tag = 3;
                break;
            case R.id.colorPicker5:
                tag = 4;
                break;
            case R.id.colorPicker6:
                tag = 5;
                break;
            case R.id.colorPicker7:
                tag = 6;
                break;
            case R.id.colorPicker8:
                tag = 7;
                break;
            case R.id.colorPicker9:
                tag = 8;
                break;
            default:
                tag = 9;
        }

        View parent = (View) v.getParent();
        final TextView numberView = (TextView)parent.findViewWithTag(tagsNum[tag]);
        final TextView codeView = (TextView)parent.findViewWithTag(tagsCode[tag]);
        final ColorPicker colorPick = new ColorPicker(this);
        new AlertDialog.Builder(this)
                .setView(colorPick)
                .setTitle("Выбери цвет")
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int color = colorPick.getColor();
                        v.setBackgroundColor(color);
                        numberView.setTextColor(color);
                        codeView.setText(getResources().getString(R.string.code, color));
                        savePrefs(tag+1, color);
                    }
                })
                .show();
    }

    private void changeColor(View colorSelectorView, int newColor){
        final int tag;
        //// TODO: 25.07.2017 hardcode
        switch (colorSelectorView.getId()){
            case R.id.colorPicker1:
                tag = 0;
                break;
            case R.id.colorPicker2:
                tag = 1;
                break;
            case R.id.colorPicker3:
                tag = 2;
                break;
            case R.id.colorPicker4:
                tag = 3;
                break;
            case R.id.colorPicker5:
                tag = 4;
                break;
            case R.id.colorPicker6:
                tag = 5;
                break;
            case R.id.colorPicker7:
                tag = 6;
                break;
            case R.id.colorPicker8:
                tag = 7;
                break;
            case R.id.colorPicker9:
                tag = 8;
                break;
            default:
                tag = 9;
        }
        View parent = (View) colorSelectorView.getParent();
        final TextView numberView = (TextView)parent.findViewWithTag(tagsNum[tag]);
        final TextView codeView = (TextView)parent.findViewWithTag(tagsCode[tag]);
        colorSelectorView.setBackgroundColor(newColor);
        numberView.setTextColor(newColor);
        codeView.setText(getResources().getString(R.string.code, newColor));

    }

    private int[] readPrefs(){
        int[] rArray = new int[9];
        for (int i=0; i<9; i++){
            //// TODO: 25.07.2017 переделать строку color в String.format
            rArray[i] = prefs.getInt("color"+(i+1), Color.BLACK);
        }
        return rArray;
    }

    private void savePrefs(int num, int color){
        SharedPreferences.Editor editor = prefs.edit();
        //// TODO: 25.07.2017 переделать строку color в String.format
        String numColor = "color" + num;
        editor.putInt(numColor, color);
        editor.apply();
    }

    private class NumberTag {
        int numberTag;

        public NumberTag(int numberTag) {
            this.numberTag = numberTag;
        }

        public int getNumberTag() {
            return numberTag;
        }
    }
}
