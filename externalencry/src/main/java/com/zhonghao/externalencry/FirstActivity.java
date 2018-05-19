package com.zhonghao.externalencry;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FirstActivity  extends AppCompatActivity {
    /** Called when the activity is first created. */
    private int id = 0;
    private Button menuEncryptBtn;
    private Button menuDecryptBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty);
        TextView em = (TextView) findViewById(R.id.em);

        Typeface fontType = Typeface.createFromAsset(this.getAssets(), "fonts/FONT.TTF");
        em.setTypeface(fontType);//设置字体
//        menuEncryptBtn = (Button) this.findViewById(R.id.menuEncryptBtn);
//        menuDecryptBtn = (Button) this.findViewById(R.id.menuDecryptBtn);
//
//
//        menuEncryptBtn.setOnClickListener(new menuEncryptBtnListener());
//        menuDecryptBtn.setOnClickListener(new menuDecryptBtnListener());
//
//        java.io.File path = new java.io.File(config.path());
//        if (!path.exists()) {
//            path.mkdirs();
//        }
    }


    class menuEncryptBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(FirstActivity.this,
                    SecondActivity.class);
            Bundle b = new Bundle();
            b.putString("action", "menuEncryptBtn");
            intent.putExtra("MAINMENU", b);
            intent.putExtra("jiami","文件加密");
            startActivityForResult(intent, id);
        }
    }

    class menuDecryptBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(FirstActivity.this,
                    SecondActivity.class);
            Bundle b = new Bundle();
            b.putString("action", "menuDecryptBtn");
            intent.putExtra("MAINMENU", b);
            intent.putExtra("jiemi","文件解密");
            startActivityForResult(intent, id);
        }
    }

}
