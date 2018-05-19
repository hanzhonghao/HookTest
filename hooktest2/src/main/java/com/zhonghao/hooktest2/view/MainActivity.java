package com.zhonghao.hooktest2.view;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghao.hooktest2.R;

import utils.check;
import utils.config;
import utils.fileOperation;

public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private int id = 1;
    private Button EncFileBtn;
    private EditText SelFileText;
    private Button ChooseFileBtn;
    private Button DencFileBtn;

    private int REQUEST_GET_FILENAME = 0;
    private String mFileName;
    private TextView mTv_splash_info;
    private TextView mTv_splash_info2;
    private ImageButton mIvb;
    private String mData1;
    public final static String SEND_OK_MESSAGE = "send.ok.message";
    public final static String SEND_OK_MESSAGE2 = "send.ok.message2";
    private String mData2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EncFileBtn = (Button) this.findViewById(R.id.EncFileBtn);
        DencFileBtn = (Button) this.findViewById(R.id.DencFileBtn);

        mTv_splash_info = (TextView) findViewById(R.id.tv_instru);
        mTv_splash_info2 = (TextView) findViewById(R.id.tv_instru2);

        ChooseFileBtn = (Button) this.findViewById(R.id.ChooseFileBtn);
        SelFileText = (EditText) this.findViewById(R.id.SelFileText);
        EncFileBtn.setOnClickListener(new EncFileBtnListener());
        ChooseFileBtn.setOnClickListener(new chooseFileListener());
        DencFileBtn.setOnClickListener(new DeEncFileBtnListener());


        Typeface fontType = Typeface.createFromAsset(this.getAssets(), "fonts/xd.ttf");
        mTv_splash_info.setTypeface(fontType);//设置字体
        mTv_splash_info2.setTypeface(fontType);

//        mIvb = (ImageButton) findViewById(R.id.ivb);
//        mIvb.setOnLongClickListener(new VisibleListener());
    }


    class VisibleListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            Intent intent = new Intent(MainActivity.this, VisibleActivity.class);
            startActivity(intent);
            return true;
        }


    }

    class chooseFileListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, LookFileActivity.class);
            startActivityForResult(intent, REQUEST_GET_FILENAME);
        }
    }

    class EncFileBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (check.isNull(SelFileText.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(), "请输入一个文件名", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            if (!fileOperation.exist(config.path() + SelFileText.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(), "文件不存在", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
//            Intent intent = new Intent(MainActivity.this, EncAlgSelActivity.class);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.zhonghao.externalencry",
                    "com.zhonghao.externalencry.EncAlgSelActivity"));

            Bundle b = new Bundle();
            b.putString("action", "SelFileBtn");
            b.putString("selectFile", SelFileText.getText().toString());


            intent.putExtra("FILESEL", b);

            startActivityForResult(intent, id);
        }

    }

    class DeEncFileBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (check.isNull(SelFileText.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(), "请输入一个文件名", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            if (!fileOperation.exist(config.path() + SelFileText.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(), "文件不存在", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.zhonghao.externalencry",
                    "com.zhonghao.externalencry.DecAlgSelActivity"));

            Bundle b = new Bundle();
            b.putString("action", "SelFileBtn");
            b.putString("selectFile", SelFileText.getText().toString());
//            b.putString("fileName", mFileName);
            intent.putExtra("FILESEL", b);

            startActivityForResult(intent, id);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_FILENAME && resultCode == RESULT_OK) {
            mFileName = data.getStringExtra("fileName");
            SelFileText.setText(mFileName);
        }
    }
}
