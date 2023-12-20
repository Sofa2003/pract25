package com.example.pract25;
import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mPolishSound, mCherepSound, mHappySound, mKumiSound, mTarakanSound, mShampuniSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton polishImageButton = findViewById(R.id.image_polishcow);
        polishImageButton.setOnClickListener(this);

        ImageButton chrepImageButton = findViewById(R.id.image_chrep);
        chrepImageButton.setOnClickListener(this);

        ImageButton happyImageButton = findViewById(R.id.image_happy);
        happyImageButton.setOnClickListener(this);

        ImageButton kumiImageButton = findViewById(R.id.image_Kumi);
        kumiImageButton.setOnClickListener(this);

        ImageButton tarakanImageButton = findViewById(R.id.image_tarakan);
        tarakanImageButton.setOnClickListener(this);

        ImageButton shumpuImageButton = findViewById(R.id.image_shumpuni);
        shumpuImageButton.setOnClickListener(this);



    }


        @Override
        public void onClick(View v) {
            int id =v.getId();
                if(id == R.id.image_polishcow) {
                    playSound(mPolishSound);

                }else if (id == R.id.image_chrep) {
                    playSound(mCherepSound);

                } else if  (id== R.id.image_happy) {
                    playSound(mHappySound);

                }else if (id == R.id.image_Kumi) {
                    playSound(mKumiSound);
                }else if (id == R.id.image_tarakan) {
                    playSound(mTarakanSound);
                }else if (id == R.id.image_shumpuni){
                    playSound(mShampuniSound);

                }
        }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mPolishSound = loadSound("PolishCow.m4a");
        mCherepSound = loadSound("leon.m4a");
        mHappySound = loadSound("happy.m4a");
        mKumiSound= loadSound("Kumi.m4a");
        mTarakanSound = loadSound("Tarakan.m4a");
        mShampuniSound = loadSound("Shampuni.m4a");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}
