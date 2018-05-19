package com.zhonghao.testdemo2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button saveButton;
    private Button readButton;
    private EditText filenameEt;
    private EditText filecontentEt;
    private EditText filedecontentEt;
    public final static String SEND_OK_MESSAGE = "send.ok.message";
    private String mData;
    private BroadcastReceiver myBroadCast ;
    private TextView mTv_splash_info;
    private EditText  mET_4;//输出手机正常操作读取的内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveButton = (Button) findViewById(R.id.bt_2);
        readButton = (Button) findViewById(R.id.bt_3);

        saveButton.setOnClickListener(this);
        readButton.setOnClickListener(this);

        filenameEt = (EditText) findViewById(R.id.et_1);
        filecontentEt = (EditText) findViewById(R.id.et_2);
        filedecontentEt = (EditText) findViewById(R.id.et_3);

        myBroadCast= new InternetDynamicBroadCastReceiver();
        IntentFilter myFilter = new IntentFilter();
        myFilter.addAction(SEND_OK_MESSAGE);
        registerReceiver(myBroadCast, myFilter);

        mET_4 = (EditText) findViewById(R.id.et_4);

        mTv_splash_info = (TextView) findViewById(R.id.tv_instru);

        Typeface fontType = Typeface.createFromAsset(this.getAssets(), "fonts/xd.ttf");
        mTv_splash_info.setTypeface(fontType);//设置字体

//        filecontentEt.setText("一二三四五六七八九十一二三四五六七八九十");
    }



    public class InternetDynamicBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SEND_OK_MESSAGE)) {
                mData = intent.getStringExtra("data");
                System.out.println("mData is : "+mData);

//                Toast.makeText(context, "收到新广播mData is "+ mData, Toast.LENGTH_SHORT).show();
                mET_4.setText(mData);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_2:
                save();
                break;
            case R.id.bt_3:
                read();
                break;
        }
    }

    private void save() {
        String filename = filenameEt.getText().toString();
        String filecontent = filecontentEt.getText().toString();
        FileOutputStream out = null;
        try {
            out = openFileOutput(filename, Context.MODE_PRIVATE);
            out.flush();
            out.write(filecontent.getBytes("UTF-8"));
            System.out.println("filecontent is :" +filecontent);
            Toast.makeText(this,"文件创建成功，并且写入成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void read() {
        String filename = filenameEt.getText().toString(); //获得读取的文件的名称
        FileInputStream in = null;
        ByteArrayOutputStream bout;
        int fileLength = filecontentEt.length()*10;
        byte[] buf = new byte[fileLength];
        bout = new ByteArrayOutputStream();
        int length ;
        try {
            in = openFileInput(filename); //获得输入流
            while ((length = in.read(buf)) != -1) {
//                bout.flush();
                bout.write(buf, 0, length);
            }
            byte[] content = bout.toByteArray();
            System.out.println("content is :" +new String(content, "UTF-8"));
            filedecontentEt.setText(new String(content, "UTF-8")); //设置文本框为读取的内容
            Toast.makeText(this,"文件读取成功，并且写入成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        filedecontentEt.invalidate(); //刷新屏幕
        try {
            in.close();
            bout.close();
        } catch (Exception e) {
        }
    }

}
