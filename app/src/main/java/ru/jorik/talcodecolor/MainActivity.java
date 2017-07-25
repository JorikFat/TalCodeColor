package ru.jorik.talcodecolor;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {

    NumberTag[] tagsNum = {new NumberTag(),
            new NumberTag(),
            new NumberTag(),
            new NumberTag(),
            new NumberTag(),
            new NumberTag(),
            new NumberTag(),
            new NumberTag(),
            new NumberTag()};

    CodeTag[] tagsCode = {new CodeTag(),
            new CodeTag(),
            new CodeTag(),
            new CodeTag(),
            new CodeTag(),
            new CodeTag(),
            new CodeTag(),
            new CodeTag(),
            new CodeTag()};

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
        changeTableRow(findViewById(R.id.row1), colors[0]);
        changeTableRow(findViewById(R.id.row2), colors[1]);
        changeTableRow(findViewById(R.id.row3), colors[2]);
        changeTableRow(findViewById(R.id.row4), colors[3]);
        changeTableRow(findViewById(R.id.row5), colors[4]);
        changeTableRow(findViewById(R.id.row6), colors[5]);
        changeTableRow(findViewById(R.id.row7), colors[6]);
        changeTableRow(findViewById(R.id.row8), colors[7]);
        changeTableRow(findViewById(R.id.row9), colors[8]);
    }

    public void pickColor(final View v){
        final ColorPicker colorPick = new ColorPicker(this);
        new AlertDialog.Builder(this)
                .setView(colorPick)
                .setTitle("Выбери цвет")
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View parent = (View)v.getParent();
                        int color = colorPick.getColor();
                        changeTableRow(parent, color);
                        savePrefs(getNumRow(parent), color);
                    }
                })
                .show();
    }

    private void changeTableRow(View tableRow, int newColor){
        ViewGroup viewGroup = (ViewGroup)tableRow;
        int childCount = viewGroup.getChildCount();
        for (int i=0; i<childCount; i++){
            View child = viewGroup.getChildAt(i);
            if (child.getClass() == RelativeLayout.class){
                child.setBackgroundColor(newColor);
            } else {
                if (child.getTag().getClass() == NumberTag.class){
                    ((TextView)child).setTextColor(newColor);
                } else {
                    ((TextView)child).setText(getResources().getString(R.string.code, newColor));
                }
            }
        }
    }

    private int getNumRow(View tableRow){
        ViewGroup viewGroup = (ViewGroup)tableRow;
        TextView textView = (TextView)viewGroup.getChildAt(0);
        String string = String.valueOf(textView.getText());
        return Integer.parseInt(string);
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

    private class NumberTag {}

    private class CodeTag{}
}
