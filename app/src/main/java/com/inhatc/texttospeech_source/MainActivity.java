package com.inhatc.texttospeech_source;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private TextToSpeech objTTS;
    private TextView txtTitle;
    private EditText editTextInput;
    private Button btnTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        editTextInput = (EditText) findViewById(R.id.editTextInput);
        btnTTS=(Button) findViewById(R.id.btnTTS);

        CheckPermission();

        editTextInput.setOnFocusChangeListener(this);
        btnTTS.setOnClickListener(this);

        objTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!= TextToSpeech.ERROR){
                    objTTS.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(objTTS!=null){
            objTTS.stop();
            objTTS.shutdown();
            objTTS=null;
        }
    }

    void CheckPermission(){
        if((ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this,android.Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET, android.Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    @Override
    public void onClick(View view) {
        String strInputText=null;

        switch (view.getId()){
            case R.id.btnTTS:
                strInputText=editTextInput.getText().toString();
                objTTS.setPitch(1.0f);
                objTTS.setSpeechRate(1.0f);

                objTTS.speak(strInputText, TextToSpeech.QUEUE_FLUSH, null);
                break;
            default:
                break;
        }
    }

  @Override
    public void onFocusChange(View view, boolean b){
        switch (view.getId()){
            case R.id.editTextInput:
                editTextInput.setText("");
                editTextInput.setTextColor(Color.BLUE);
                break;
            default:
                break;
        }
  }
}