package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    TextView textView0801, textView0802,textView0803,textView0804,textView0805,
            textView0901, textView0902,textView0903,textView0904,textView0905,
            textView1001, textView1002,textViee1003,textView1004,textView1005,
            textView1101, textView1102,textViee1103,textView1104,textView1105,
            textView1201, textView1202,textViee1203,textView1204,textView1205,
            textView1301, textView1302,textViee1303,textView1304,textView1305,
            textView1401, textView1402,textViee1403,textView1404,textView1405,
            textView1501, textView1502,textViee1503,textView1504,textView1505,
            textView1601, textView1602,textViee1603,textView1604,textView1605,
            textView1701, textView1702,textViee1703,textView1704,textView1705,
            textView1801, textView1802,textViee1803,textView1804,textView1805,
            textView1901, textView1902,textViee1903,textView1904,textView1905,
            textView2001, textView2002,textViee2003,textView2004,textView2005;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView0801 = findViewById(R.id.textView1);
        textView0802 = findViewById(R.id.textView2);
        textView0801.setLines(2);
        textView0801.setText(Html.fromHtml("font color: " +"<br/>" +
                         "<font color=\"blue\">Text Color</font>, "));
        Spanned text = Html.fromHtml("Multiple style inside android textView: bold text: "
                + "<b>bold text</b>, "
                + "italic text: <i>italic text</i>, small font: <small>small text</small>, font "
                + "color: <font color=\"blue\">Text Color</font>, "
                + "font color with bold text: <frontColor=\"green\"><b>Bold with font "
                + "color</b></font>");
        textView0802.setText(Html.fromHtml(getString(R.string.textStyle)));

    }
}