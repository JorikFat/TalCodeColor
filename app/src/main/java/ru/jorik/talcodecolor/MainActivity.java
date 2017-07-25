package ru.jorik.talcodecolor;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// TODO: 25.07.2017 hardcode
        findViewById(R.id.number1).setTag(new NumberTag());
        findViewById(R.id.number2).setTag(new NumberTag());
        findViewById(R.id.number3).setTag(new NumberTag());
        findViewById(R.id.number4).setTag(new NumberTag());
        findViewById(R.id.number5).setTag(new NumberTag());
        findViewById(R.id.number6).setTag(new NumberTag());
        findViewById(R.id.number7).setTag(new NumberTag());
        findViewById(R.id.number8).setTag(new NumberTag());
        findViewById(R.id.number9).setTag(new NumberTag());

        //// TODO: 25.07.2017 hardcode
        findViewById(R.id.code1).setTag(new CodeTag());
        findViewById(R.id.code2).setTag(new CodeTag());
        findViewById(R.id.code3).setTag(new CodeTag());
        findViewById(R.id.code4).setTag(new CodeTag());
        findViewById(R.id.code5).setTag(new CodeTag());
        findViewById(R.id.code6).setTag(new CodeTag());
        findViewById(R.id.code7).setTag(new CodeTag());
        findViewById(R.id.code8).setTag(new CodeTag());
        findViewById(R.id.code9).setTag(new CodeTag());

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
        int oldColor = Color.TRANSPARENT;
        final ColorPicker colorPick = new ColorPicker(this);
        Drawable background = v.getBackground();
        if (background instanceof ColorDrawable){
            oldColor = ((ColorDrawable) background).getColor();
        }
        colorPick.setColor(oldColor);
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
                //TAGS: единственное их использование во всем коде
                //// TODO: 25.07.2017 придумать способ убрать Tags
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


    //TAGS.classes
    private class NumberTag {}

    private class CodeTag{}
}
