package com.zhonghao.externalencry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utils.check;
import utils.config;
import utils.fileOperation;

/**
 * 项目名称：HookTest
 * 包名：com.zhonghao.externalencry
 * 创建人：小豪
 * 创建时间：2017/12/11 20:12
 * 类描述：
 */

public class SecondActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private int id=1;
    private Button SelFileBtn;
    private EditText SelFileText;
    private Button ChooseFileBtn;

    private int REQUEST_GET_FILENAME=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_select);
        SelFileBtn=(Button) this.findViewById(R.id.SelFileBtn);
        ChooseFileBtn=(Button) this.findViewById(R.id.ChooseFileBtn);
        SelFileText=(EditText) this.findViewById(R.id.SelFileText);
        SelFileBtn.setOnClickListener(new SelFileBtnListener());
        ChooseFileBtn.setOnClickListener(new chooseFileListener());
        Intent intent = getIntent();
        String jiami = intent.getStringExtra("jiami");
        String jiemi = intent.getStringExtra("jiemi");
        System.out.println("jiami and jiemi is :"+ jiami +"  ,"+jiemi );
        if(jiami!=null){
            SelFileBtn.setText(jiami);
        }
        if(jiemi!=null){
            SelFileBtn.setText(jiemi);
        }
    }

    class chooseFileListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SecondActivity.this,LookFileActivity.class);
            startActivityForResult(intent,REQUEST_GET_FILENAME);
        }
    }

    class SelFileBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(check.isNull(SelFileText.getText().toString())){
                Toast toast = Toast.makeText(getApplicationContext(),"请输入一个文件名", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            Bundle tmp=getIntent().getBundleExtra("MAINMENU");
            if(tmp==null) return;
            String action=tmp.getString("action");
            if(action==null) return;
            Intent intent;
            if(action.equals("menuEncryptBtn")){
                if(!fileOperation.exist(config.path()+SelFileText.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"文件不存在", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                intent=new Intent(SecondActivity.this,EncAlgSelActivity.class);
            }
            else if(action.equals("menuDecryptBtn")){
                if(!fileOperation.exist(config.path()+SelFileText.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"文件不存在", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                intent=new Intent(SecondActivity.this,DecAlgSelActivity.class);
            }
            else if(action.equals("menuDelBtn")){
                if(!fileOperation.exist(config.path()+SelFileText.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"文件不存在", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                java.io.File delfile=new java.io.File(config.path()+SelFileText.getText().toString());
                boolean delr=delfile.delete();
                if(delr){
                    Toast toast = Toast.makeText(getApplicationContext(),"删除成功", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"删除失败", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                return;
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "error: evalid action: "+action, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            Bundle b=new Bundle();
            b.putString("action", "SelFileBtn");
            b.putString("selectFile", SelFileText.getText().toString());
            intent.putExtra("FILESEL", b);
            startActivityForResult(intent,id);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GET_FILENAME&&resultCode==RESULT_OK){
            String fileName =data.getStringExtra("fileName");
            SelFileText.setText(fileName);
//            Toast.makeText(this,"文件名是： "+fileName,Toast.LENGTH_LONG).show();
        }
    }
}
