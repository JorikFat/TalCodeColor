package ru.jorik.talcodecolor;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.OnColorSelectionListener;

public class MainActivity extends AppCompatActivity {

    NumberTag[] tags = {new NumberTag(1),
            new NumberTag(2),
            new NumberTag(3),
            new NumberTag(4),
            new NumberTag(5),
            new NumberTag(6),
            new NumberTag(7),
            new NumberTag(8),
            new NumberTag(9)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.number1).setTag(tags[0]);
        findViewById(R.id.number2).setTag(tags[1]);
        findViewById(R.id.number3).setTag(tags[2]);
        findViewById(R.id.number4).setTag(tags[3]);
        findViewById(R.id.number5).setTag(tags[4]);
        findViewById(R.id.number6).setTag(tags[5]);
        findViewById(R.id.number7).setTag(tags[6]);
        findViewById(R.id.number8).setTag(tags[7]);
        findViewById(R.id.number9).setTag(tags[8]);
    }

    public void pickColor(final View v){
        int tag;
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

        final TextView textView = (TextView)((View)v.getParent()).findViewWithTag(tags[tag]);
        final HSLColorPicker colorPicker = new HSLColorPicker(this);
        colorPicker.setColorSelectionListener(new OnColorSelectionListener() {
            @Override
            public void onColorSelected(int i) {
                v.getBackground().setColorFilter(i, PorterDuff.Mode.MULTIPLY);
                textView.setTextColor(i);
            }

            @Override
            public void onColorSelectionStart(int i) {

            }

            @Override
            public void onColorSelectionEnd(int i) {

            }
        });
        new AlertDialog.Builder(this)
                .setView(colorPicker)
                .setTitle("Выбери цвет")
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        v.setBackgroundColor(textView.getCurrentTextColor());
                    }
                })
                .show();
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
