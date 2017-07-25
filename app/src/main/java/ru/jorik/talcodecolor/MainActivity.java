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

        prefs = getSharedPreferences("colors", MODE_PRIVATE);
        int[] colors = readPrefs();

        ViewGroup liner = (ViewGroup) findViewById(R.id.linerLayout);
        for (int i=0; i<9; i++){
            changeTableRow(liner.getChildAt(i), colors[i]);
        }
    }

    public void pickColor(final View v){
        int oldColor = Color.BLACK;
        final ColorPicker colorPick = new ColorPicker(this);
        Drawable background = v.getBackground();
        if (background instanceof ColorDrawable){
            oldColor = ((ColorDrawable) background).getColor();
        }
        colorPick.setColor(oldColor);
        colorPick.setOldCenterColor(oldColor);
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
                /*Костыль:
                * Данное решение помогает определить где какой TextView.
                * Это нужно чтобы не писать на каждый View Tag.
                * */
                TextView textView = (TextView)child;
                char fChar = textView.getText().charAt(0);
                if (fChar != '#'){
                    textView.setTextColor(newColor);
                } else {
                    textView.setText(getResources().getString(R.string.code, newColor));
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
            rArray[i] = prefs.getInt("color"+(i+1), Color.BLACK);
        }
        return rArray;
    }

    private void savePrefs(int num, int color){
        SharedPreferences.Editor editor = prefs.edit();
        String numColor = "color" + num;
        editor.putInt(numColor, color);
        editor.apply();
    }
}
