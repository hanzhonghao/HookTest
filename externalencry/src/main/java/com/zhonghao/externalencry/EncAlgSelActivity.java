package com.zhonghao.externalencry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import utils.SymEncrypt;
import utils.check;
import utils.config;
import utils.fileOperation;

/**
 * 项目名称：HookTest
 * 包名：com.zhonghao.externalencry
 * 创建人：小豪
 * 创建时间：2017/12/11 20:19
 * 类描述：
 */

public class EncAlgSelActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private RadioGroup encAlgSelRG;
    private RadioButton encAlgSelR1;
    private RadioButton encAlgSelR2;
    private RadioButton encAlgSelR3;
    private EditText encAlgKey;
    private EditText encOutText;
    private Button encAlgSelBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setTitle("外部存储加密");// 设置标题
        // 设置左边图标
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                android.R.drawable.ic_dialog_alert);
        setContentView(R.layout.enc_alg_sel);

        encAlgSelRG = (RadioGroup) this.findViewById(R.id.encAlgSelRG);
        encAlgSelR1 = (RadioButton) this.findViewById(R.id.encAlgSelR1);
        encAlgSelR2 = (RadioButton) this.findViewById(R.id.encAlgSelR2);
        encAlgSelR3 = (RadioButton) this.findViewById(R.id.encAlgSelR3);
        encAlgKey = (EditText) this.findViewById(R.id.encAlgKey);
        encOutText = (EditText) this.findViewById(R.id.encOutText);
        encAlgSelBtn = (Button) this.findViewById(R.id.encAlgSelBtn);
        encAlgSelBtn.setOnClickListener(new encAlgSelBtnListener());


    }

    class encAlgSelBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (check.isNull(encOutText.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(), "请输入输出文件名", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            if (fileOperation.exist(config.path() + encOutText.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(), "输出文件已存在", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            if (check.isNull(encAlgKey.getText().toString())) {
                Toast toast = Toast.makeText(getApplicationContext(), "请输入密钥", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            Intent intent = getIntent();
            Bundle getb = intent.getBundleExtra("FILESEL");
            if (getb == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "error: evalid Bundle", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            String selFile = getb.getString("selectFile");
            String action = getb.getString("action");
            if (selFile == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "error: no selFile", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            if (action == null || !action.equals("SelFileBtn")) {
                Toast toast = Toast.makeText(getApplicationContext(), "error: evalid action: " + action, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }

            String inputStr = fileOperation.readString(config.path() + selFile);
            if (inputStr == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "文件不存在: " + selFile, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }

            String alg;
            if (encAlgSelRG.getCheckedRadioButtonId() == encAlgSelR1.getId()) {
                alg = "DES";
            } else if (encAlgSelRG.getCheckedRadioButtonId() == encAlgSelR2.getId()) {
                alg = "DESede";
            } else if (encAlgSelRG.getCheckedRadioButtonId() == encAlgSelR3.getId()) {
                alg = "AES";
            } else {
                alg = "DES";
            }
            byte[] outputBytes = SymEncrypt.encrypt(inputStr, encAlgKey.getText().toString(), alg);
            //加密完之后删除原文件
            fileOperation.delFile(selFile);

            int fowbr = fileOperation.writeBytes(config.path() + encOutText.getText().toString(), outputBytes);
            if (fowbr != 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "文件写入出错", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            Toast toast = Toast.makeText(getApplicationContext(), "加密已完成", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }

    }
}