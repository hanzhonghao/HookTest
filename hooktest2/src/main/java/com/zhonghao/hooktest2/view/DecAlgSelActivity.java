package com.zhonghao.hooktest2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zhonghao.hooktest2.R;

import utils.SymEncrypt;
import utils.check;
import utils.config;
import utils.fileOperation;

/**
 * 项目名称：HookTest
 * 包名：com.zhonghao.externalencry
 * 创建人：小豪
 * 创建时间：2017/12/11 20:22
 * 类描述：
 */

public class DecAlgSelActivity extends AppCompatActivity {
    private RadioGroup delAlgSelRG;
    private RadioButton decAlgSelR1;
    private RadioButton decAlgSelR2;
    private RadioButton decAlgSelR3;
    private EditText decAlgKey;
    private EditText delOutText;
    private Button decAlgSelBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dec_alg_sel);
        delAlgSelRG=(RadioGroup) this.findViewById(R.id.delAlgSelRG);
        decAlgSelR1=(RadioButton) this.findViewById(R.id.decAlgSelR1);
        decAlgSelR2=(RadioButton) this.findViewById(R.id.decAlgSelR2);
        decAlgSelR3=(RadioButton) this.findViewById(R.id.decAlgSelR3);
        decAlgKey=(EditText) this.findViewById(R.id.decAlgKey);
        delOutText=(EditText) this.findViewById(R.id.delOutText);
        decAlgSelBtn=(Button) this.findViewById(R.id.decAlgSelBtn);
        decAlgSelBtn.setOnClickListener(new decAlgSelBtnListener());

    }

    class decAlgSelBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(check.isNull(delOutText.getText().toString())){
                Toast toast = Toast.makeText(getApplicationContext(),"请输入输出文件名", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            if(fileOperation.exist(config.path()+delOutText.getText().toString())){
                Toast toast = Toast.makeText(getApplicationContext(),"输出文件已存在", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            if(check.isNull(decAlgKey.getText().toString())){
                Toast toast = Toast.makeText(getApplicationContext(),"请输入密钥", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            Intent intent=getIntent();
            Bundle getb=intent.getBundleExtra("FILESEL");
            if(getb==null){
                Toast toast = Toast.makeText(getApplicationContext(), "error: evalid Bundle", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            String selFile=getb.getString("selectFile");
            String action=getb.getString("action");
            if(selFile==null){
                Toast toast = Toast.makeText(getApplicationContext(), "error: no selFile", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            if(action==null||!action.equals("SelFileBtn")){
                Toast toast = Toast.makeText(getApplicationContext(), "error: evalid action: "+action, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }

            byte[] inputBytes=fileOperation.readBytes(config.path()+selFile);
            if(inputBytes==null){
                Toast toast = Toast.makeText(getApplicationContext(), "加密文件读取错误", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }

            String alg;
            if(delAlgSelRG.getCheckedRadioButtonId()==decAlgSelR1.getId()){
                alg="DES";
            }
            else if(delAlgSelRG.getCheckedRadioButtonId()==decAlgSelR2.getId()){
                alg="DESede";
            }
            else if(delAlgSelRG.getCheckedRadioButtonId()==decAlgSelR3.getId()){
                alg="AES";
            }
            else{
                alg="DES";
            }
            String outputStr= SymEncrypt.decrypt(inputBytes, decAlgKey.getText().toString(), alg);
            int fowws=fileOperation.writeString(config.path()+delOutText.getText().toString(), outputStr,0);
            if(fowws!=0){
                Toast toast = Toast.makeText(getApplicationContext(), "文件写入出错", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }

            Toast toast = Toast.makeText(getApplicationContext(), "解密已完成", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }

    }
}