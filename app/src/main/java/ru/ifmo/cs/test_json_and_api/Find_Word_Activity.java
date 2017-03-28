package ru.ifmo.cs.test_json_and_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class Find_Word_Activity extends AppCompatActivity {

    private TextView textView;
    private Button saveBtn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_word);

        saveBtn = (Button) findViewById(R.id.FW_saveBtn);
        textView = (TextView) findViewById(R.id.FW_textView);
        editText = (EditText) findViewById(R.id.FW_editText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    try {
                        find(v);
                    } catch (IOException | ParserConfigurationException | XmlPullParserException | SAXException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                json = "";
                textView.setText("");
                saveBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private String json = "";

    public void find(View view) throws IOException, ParserConfigurationException, SAXException, XmlPullParserException {
        String req = editText.getText().toString();
        json = (new Json_Class(getApplicationContext(), req).getStr());
        String res = Json_Class.parseJson(json);
        if (res.equals("")) {
            Toast.makeText(getApplicationContext(), "the word hasned been found", Toast.LENGTH_SHORT).show();
            return;
        }
        saveBtn.setVisibility(View.VISIBLE);
        textView.setText(res);
    }


    public void saveWord(View view) {
        saveBtn.setVisibility(View.INVISIBLE);
        String s = File_Class.readFile(getApplicationContext(), "saved_words");
        if (!s.contains(json)) {
         Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
            File_Class.writeFile(getApplicationContext(), "saved_words", json);
        }else {
            Toast.makeText(getApplicationContext(), "you have saved the word already", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textView", textView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString("textView"));
    }

}
