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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.number1).setTag(tagsNum[0]);
        findViewById(R.id.number2).setTag(tagsNum[1]);
        findViewById(R.id.number3).setTag(tagsNum[2]);
        findViewById(R.id.number4).setTag(tagsNum[3]);
        findViewById(R.id.number5).setTag(tagsNum[4]);
        findViewById(R.id.number6).setTag(tagsNum[5]);
        findViewById(R.id.number7).setTag(tagsNum[6]);
        findViewById(R.id.number8).setTag(tagsNum[7]);
        findViewById(R.id.number9).setTag(tagsNum[8]);

        findViewById(R.id.code1).setTag(tagsCode[0]);
        findViewById(R.id.code2).setTag(tagsCode[1]);
        findViewById(R.id.code3).setTag(tagsCode[2]);
        findViewById(R.id.code4).setTag(tagsCode[3]);
        findViewById(R.id.code5).setTag(tagsCode[4]);
        findViewById(R.id.code6).setTag(tagsCode[5]);
        findViewById(R.id.code7).setTag(tagsCode[6]);
        findViewById(R.id.code8).setTag(tagsCode[7]);
        findViewById(R.id.code9).setTag(tagsCode[8]);
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

        View parent = (View) v.getParent();
        final TextView numberView = (TextView)parent.findViewWithTag(tagsNum[tag]);
        final TextView codeView = (TextView)parent.findViewWithTag(tagsCode[tag]);
        final HSLColorPicker colorPicker = new HSLColorPicker(this);
        colorPicker.setColorSelectionListener(new OnColorSelectionListener() {
            @Override
            public void onColorSelected(int i) {
                v.getBackground().setColorFilter(i, PorterDuff.Mode.MULTIPLY);
                numberView.setTextColor(i);
                codeView.setText(getResources().getString(R.string.code, i));
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
                        int color = numberView.getCurrentTextColor();
                        v.setBackgroundColor(color);

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
