package com.zhonghao.encrycapability.ThreadPool;

import com.zhonghao.encrycapability.util.RC4Demo;

import java.util.List;

public class EncryExecutor implements Runnable {
    private RC4Demo rc4Demo;
    private List<String> lines;
    private String key;
    private List<String> encodeList;
    private int start;
    private int end;
    private int NUM = 0;

    public EncryExecutor() {

    }

    public EncryExecutor(RC4Demo rc4Demo, List<String> lines, String key, List<String> encodeList, int start, int end) {
        this.rc4Demo = rc4Demo;
        this.lines = lines;
        this.key = key;
        this.encodeList = encodeList;
        this.start = start;
        this.end = end;
    }

    public void run() {
        while(true) {
            for (String line : lines) {
                String encodeLine = rc4Demo.encry_RC4_string(line, key);
                System.out.println(Thread.currentThread().getName() + " " + encodeLine);
                encodeList.set(start+NUM, encodeLine);
                NUM++;
            }
            break;
        }
    }
}
