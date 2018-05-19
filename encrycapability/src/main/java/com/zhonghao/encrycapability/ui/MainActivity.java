package com.zhonghao.encrycapability.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhonghao.encrycapability.R;
import com.zhonghao.encrycapability.util.RC4Demo;
import com.zhonghao.encrycapability.util.RC4Util2;
import com.zhonghao.encrycapability.util.SymEncrypt;
import com.zhonghao.encrycapability.util.config;
import com.zhonghao.encrycapability.util.fileOperation;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.R.id.message;
import static com.zhonghao.encrycapability.util.RC4Demo.decryDataUserRc4;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv_2;
    private TextView mTv_3;
    private TextView mTv_4;

    private TextView mTv_a;
    private TextView mTv_b;
    private TextView mTv_c;

    private TextView mTv_en;
    private TextView mTv_dn;

    private Button mBt_2;
    private Button mBt_1;
    private Button mBt_3;

    private Button mBt_b;
    private Button mBt_a;
    private Button mBt_c;

    private Button mBt_en;
    private Button mBt_dn;

    private EditText filenameEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv_2 = (TextView) findViewById(R.id.tv_2);
        mTv_3 = (TextView) findViewById(R.id.tv_3);
        mTv_4 = (TextView) findViewById(R.id.tv_4);

        mTv_a = (TextView) findViewById(R.id.tv_a);
        mTv_b = (TextView) findViewById(R.id.tv_b);
        mTv_c = (TextView) findViewById(R.id.tv_c);

        mTv_en = (TextView) findViewById(R.id.tv_mulen);
        mTv_dn = (TextView) findViewById(R.id.tv_muldn);

        filenameEt = (EditText) findViewById(R.id.et_1);

        mBt_1 = (Button) findViewById(R.id.bt_1);
        mBt_2 = (Button) findViewById(R.id.bt_2);
        mBt_3 = (Button) findViewById(R.id.bt_3);

        mBt_a = (Button) findViewById(R.id.bt_a);
        mBt_b = (Button) findViewById(R.id.bt_b);
        mBt_c = (Button) findViewById(R.id.bt_c);

        mBt_en = (Button) findViewById(R.id.bt_mulen);
        mBt_dn = (Button) findViewById(R.id.bt_muldn);

        mBt_1.setOnClickListener(this);
        mBt_2.setOnClickListener(this);
        mBt_3.setOnClickListener(this);

        mBt_a.setOnClickListener(this);
        mBt_b.setOnClickListener(this);
        mBt_c.setOnClickListener(this);

        mBt_en.setOnClickListener(this);
        mBt_dn.setOnClickListener(this);

    }

    /**
     * long startTime = System.currentTimeMillis();
     * <p>
     * // 业务代码
     * long endTime = System.currentTimeMillis();
     * System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
     */


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1://RC4加密

                RC4Thread rc4Thread = new RC4Thread();
                rc4Thread.start();

                break;
            case R.id.bt_a://RC4解密

                RC4DeThread rc4deThread = new RC4DeThread();
                rc4deThread.start();

                break;
            case R.id.bt_2://AES加密
                AESThread aesThread = new AESThread();
                aesThread.start();
                break;
            case R.id.bt_b://AES解密

                AESDeThread rc4DeThread1 = new AESDeThread();
                rc4DeThread1.start();

                break;
            case R.id.bt_3://DES加密
                DESThread desThread = new DESThread();
                desThread.start();
                break;
            case R.id.bt_c://DES解密

                DESDeThread DESdeThread = new DESDeThread();
                DESdeThread.start();

                break;
            case R.id.bt_mulen://DES解密

                MulRcenThread mulRcenThread = new MulRcenThread();
                mulRcenThread.start();

                break;
            case R.id.bt_muldn://DES解密

                MulRcDeThread mulRcDeThread = new MulRcDeThread();
                mulRcDeThread.start();

                break;
        }
    }

    class MulRcenThread extends Thread {

        @Override
        public void run() {
            final int THREADNUM = 8;
            final String KEY = "123456";
            String filename = filenameEt.getText().toString(); //获得读取的文件的名称
            String PATH = "/storage/emulated/0/" + filename;

            final ExecutorService executorService = Executors
                    .newFixedThreadPool(THREADNUM);
            final RC4Demo rc4Demo = new RC4Demo();
            final List<String> encodeList = new ArrayList<String>();
            final File file = new File(PATH);


            final String result = rc4Demo.encryDataUserRc4(THREADNUM, KEY, executorService, rc4Demo,
                    encodeList,
                    file);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_en.setText(result + "ms");
                }
            });


        }

    }

    class MulRcDeThread extends Thread {
        @Override
        public void run() {
            final int THREADNUM = 8;
            final String KEY = "123456";
            String filename = filenameEt.getText().toString(); //获得读取的文件的名称
            String PATH = "/storage/emulated/0/" + filename;

            final ExecutorService executorService = Executors
                    .newFixedThreadPool(THREADNUM);
            final RC4Demo rc4Demo = new RC4Demo();
            final List<String> encodeList = new ArrayList<String>();
            final File file = new File(PATH);


            final String result = decryDataUserRc4(THREADNUM, KEY, executorService, rc4Demo,
                    encodeList,
                    file);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_dn.setText(result + "ms");
                }
            });

        }

    }


    class RC4Thread extends Thread {
        @Override
        public void run() {
            String filename = filenameEt.getText().toString(); //获得读取的文件的名称

            String inputStr = fileOperation.readString(config.path() + filename);
            System.out.println("inputStr is :" + inputStr);


            long startTime = System.nanoTime(); // 获取开始时间

            String encry_rc4_string = RC4Util2.RC4Encry(inputStr, "123456");
            long endTime = System.nanoTime(); // 获取结束时间
            final String result1 = new Formatter().format("%.2f", (double) ((endTime - startTime)) / 1000000).toString();
            System.out.println("程序运行时间： " + result1 + "ms");//输出程序运行时间

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_2.setText(result1 + "ms");
                }
            });
            System.out.println("encry_rc4_string is :" + encry_rc4_string);
            fileOperation.writeBytes(config.path() + filename, encry_rc4_string.getBytes());

        }
    }

    class RC4DeThread extends Thread {
        @Override
        public void run() {
            String filename = filenameEt.getText().toString(); //获得读取的文件的名称

            String inputStr = fileOperation.readString(config.path() + filename);
            System.out.println("inputStr is :" + inputStr);

            long startTime = System.nanoTime();
            String decry_rc4 = RC4Util2.RC4DeEncry(inputStr, "123456");
            long endTime = System.nanoTime();

            final String result1 = new Formatter().format("%.2f", (double) ((endTime - startTime)) / 1000000).toString();
            System.out.println("程序运行时间： " + result1 + "ms");//输出程序运行时间

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_a.setText(result1 + "ms");
                }
            });

            System.out.println("decry_rc4 is " + decry_rc4);
            fileOperation.writeBytes(config.path() + filename, decry_rc4.getBytes());
        }
    }


    class AESThread extends Thread {
        @Override
        public void run() {
            String filename = filenameEt.getText().toString(); //获得读取的文件的名称

            String inputStr = fileOperation.readString(config.path() + filename);
            System.out.println("inputStr is :" + inputStr);

            long startTime = System.nanoTime();
            byte[] aes = SymEncrypt.encrypt(inputStr, "123456", "AES");
            long endTime = System.nanoTime();
            final String result1 = new Formatter().format("%.2f", (double) ((endTime - startTime)) / 1000000).toString();
            System.out.println("程序运行时间： " + result1 + "ms");//输出程序运行时间

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_3.setText(result1 + "ms");
                }
            });
            byte[] bytes = null;
            /**
             * 先对加密后的数据进行Base64加密，不然解密会报错
             */
            try {
                String message = new String(Base64.encode(aes, Base64.DEFAULT), "UTF-8");
                bytes = message.getBytes();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("message is " + message);
            System.out.println("bytes is " + new String(bytes));
            fileOperation.writeBytes(config.path() + filename, bytes);
        }
    }

    class AESDeThread extends Thread {
        @Override
        public void run() {
            String filename = filenameEt.getText().toString(); //获得读取的文件的名称

            String inputStr = fileOperation.readString(config.path() + filename);
            System.out.println("inputStr is :" + inputStr);
            /**
             * 需要先对密文Base64解密。然后调用AES解密才不会报错
             */
            byte[] tmpt = null;
            try {
                tmpt = Base64.decode(inputStr.getBytes("UTF-8"), Base64.DEFAULT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            long startTime = System.nanoTime();
            String deaes = SymEncrypt.decrypt(tmpt, "123456", "AES");
            System.out.println("deaes is " + deaes);
            long endTime = System.nanoTime();
            final String result1 = new Formatter().format("%.2f", (double) ((endTime - startTime)) / 1000000).toString();
            System.out.println("程序运行时间： " + result1 + "ms");//输出程序运行时间

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_b.setText(result1 + "ms");
                }
            });

            fileOperation.writeString(config.path() + filename, deaes, 1);
        }
    }

    class DESThread extends Thread {
        @Override
        public void run() {
            String filename = filenameEt.getText().toString(); //获得读取的文件的名称

            String inputStr = fileOperation.readString(config.path() + filename);
            System.out.println("inputStr is :" + inputStr);

            long startTime = System.nanoTime();
            byte[] des = SymEncrypt.encrypt(inputStr, "123456", "DES");

            long endTime = System.nanoTime();
            final String result1 = new Formatter().format("%.2f", (double) ((endTime - startTime)) / 1000000).toString();
            System.out.println("程序运行时间： " + result1 + "ms");//输出程序运行时间

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_4.setText(result1 + "ms");
                }
            });

            byte[] bytes = null;
            /**
             * 先对加密后的数据进行Base64加密，不然解密会报错
             */
            try {
                String message = new String(Base64.encode(des, Base64.DEFAULT), "UTF-8");
                bytes = message.getBytes();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("message is " + message);
            System.out.println("bytes is " + new String(bytes));
            fileOperation.writeBytes(config.path() + filename, bytes);

        }
    }

    class DESDeThread extends Thread {
        @Override
        public void run() {
            String filename = filenameEt.getText().toString(); //获得读取的文件的名称

            String inputStr = fileOperation.readString(config.path() + filename);
            System.out.println("inputStr is :" + inputStr);
            /**
             * 需要先对密文Base64解密。然后调用DES解密才不会报错
             */
            byte[] tmpt = null;
            try {
                tmpt = Base64.decode(inputStr.getBytes("UTF-8"), Base64.DEFAULT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            long startTime = System.nanoTime();
            String deaes = SymEncrypt.decrypt(tmpt, "123456", "DES");
            System.out.println("deaes is " + deaes);
            long endTime = System.nanoTime();
            final String result1 = new Formatter().format("%.2f", (double) ((endTime - startTime)) / 1000000).toString();
            System.out.println("程序运行时间： " + result1 + "ms");//输出程序运行时间

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_c.setText(result1 + "ms");
                }
            });

            fileOperation.writeString(config.path() + filename, deaes, 1);
        }
    }

}
