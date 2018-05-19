package com.zhonghao.externalencry;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookFileActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    ListView mListView;
    TextView mTextView;
    Button mParent;
    // 记录当前的父文件夹
    File mCurrentParent;
    // 记录当前路径下的所有文件夹的文件数组
    File[] mCurrentFiles;
    //获取外部存储路径目录
    File path = Environment.getExternalStorageDirectory();
    private TextView mTv_sdcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list);
        mTextView = (TextView) findViewById(R.id.path);
        sdcard();
        // 获取系统的SD卡目录
        File root = new File("/mnt/sdcard/");
        // 如果SD卡不存在
        if (root.exists()) {
            mCurrentParent = root;
            mCurrentFiles = mCurrentParent.listFiles();
            // 使用当前目录下的全部文件，文件夹来填充ListView
            inflateListView(mCurrentFiles);
        }
        //为mListView的列表项的单击事件绑定监听器
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 用户单击了文件，直接返回，不做任何处理
                if (mCurrentFiles[position].isFile()) {
                    String fileName = mCurrentFiles[position].getName();
                    Intent intent = new Intent(LookFileActivity.this,FirstActivity.class);
                    intent.putExtra("fileName",fileName);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                // 获取用户点击的文件夹下的所有文件
                File[] tmp = mCurrentFiles[position].listFiles();
                if (tmp == null || tmp.length == 0) {
                    if(!mCurrentFiles[position].isFile()) {
                        Toast.makeText(LookFileActivity.this,
                                "当前路径不可访问或该路径下没有文件", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                    mCurrentParent = mCurrentFiles[position]; // ②
                    // 保存当前的父文件夹内的全部文件和文件夹
                    mCurrentFiles = tmp;
                    // 再次更新ListView
                    inflateListView(mCurrentFiles);
                }
            }

        });

        // 获取上一级目录的按钮
        Button parent = (Button) findViewById(R.id.parent);
        parent.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try {
                    if (!mCurrentParent.getCanonicalPath().equals("/mnt/sdcard")) {
                        // 获取上一级目录
                        mCurrentParent = mCurrentParent.getParentFile();
                        // 列出当前目录下所有文件
                        mCurrentFiles = mCurrentParent.listFiles();
                        // 再次更新ListView
                        inflateListView(mCurrentFiles);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
    }

    private void inflateListView(File[] files) {
        // 创建一个List集合，List集合的元素是Map
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            //如果当前File是文件夹，使用folder图标；否则使用file图标
            if (files[i].isDirectory()) {
                listItem.put("icon", R.mipmap.document);
            } else {
                listItem.put("icon", R.mipmap.file);
            }
            listItem.put("fileName", files[i].getName());
            //添加List项
            listItems.add(listItem);
        }
        // 创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.line, new String[]{"icon", "fileName"}, new int[]{
                R.id.icon, R.id.file_name});
        // 为ListView设置Adapter
        mListView.setAdapter(simpleAdapter);

        try {
            mTextView.setText("  当前路径为：" + mCurrentParent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //获取SD卡剩余容量
    private void sdcard() {
        StatFs statfs = new StatFs(path.getPath());
        long blockSize;
        long availableBlocks;
        //获取当前系统版本的等级
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statfs.getBlockSizeLong();
            availableBlocks = statfs.getAvailableBlocksLong();
        } else {
            blockSize = statfs.getBlockSize();
            availableBlocks = statfs.getAvailableBlocks();
        }
        mTv_sdcard = (TextView) findViewById(R.id.rongliang);
//        区块大小(totalBlocks = stat.getBlockCountLong();) * 区块数量 等于 存储设备的总大小
        mTv_sdcard.setText("  sd卡剩余空间：" + formatSize(availableBlocks * blockSize));

    }

    private String formatSize(long size) {
        return Formatter.formatFileSize(this, size);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return true;
//        }
//        return false;
//    }
}
