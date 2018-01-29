package palpac.text_to_speech;

//https://www.youtube.com/watch?v=Ro12hgmd9ZE

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech toSpeech;
    public int result;
    EditText editText;
    public String text;

    AudioManager audioManager;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        setSupportActionBar(toolbar);

        editText = findViewById(R.id.editText);
        toSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS)
                {
                    result = toSpeech.setLanguage(Locale.FRANCE);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Not supported", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void TTS(View view)
    {
        switch (view.getId())
        {
            case R.id.bspeak:
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                {
                    Toast.makeText(getApplicationContext(), "Not supported", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    text = editText.getText().toString();
                    toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case R.id.bstop:
                if (toSpeech != null)
                {
                    toSpeech.stop();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), "Destroy", Toast.LENGTH_SHORT).show();
        if (toSpeech != null)
        {
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }

}
