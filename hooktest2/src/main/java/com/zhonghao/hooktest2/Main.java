package com.zhonghao.hooktest2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Random;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import utils.RC4Util;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * 项目名称：HookTest
 * 包名：com.zhonghao.hooktest
 * 创建人：小豪
 * 创建时间：2017/1/26 14:15
 * 类描述：
 */

public class Main implements IXposedHookLoadPackage {
    public RC4Util mRc4 = new RC4Util();
    //被HOOK的程序的包名和类名
    String packName = "com.zhonghao.testdemo1";
    String packName1 = "com.zhonghao.testdemo2";
    String packName2 = "com.zhonghao.hooktest2";
    String className = "java.io.FileInputStream";
    String className2 = "java.io.FileOutputStream";
    public final static String SEND_OK_MESSAGE = "send.ok.message";
    public Context storedContext;
    private String mKeyByMD5;
    private String mKeyByMD5s = "8C73BC32F8DA1B56FCC83B9B804766B1";
    private SharedPreferences mKeyStore;
    public static final String MyTAG="LogOutput";

    /**
     * 包加载时候的回调
     */
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals(packName2) && !loadPackageParam.packageName.equals(packName)
                && !loadPackageParam.packageName.equals(packName1))
            return;
        XposedBridge.log("系统加载的应用是（按包名打印）: " + loadPackageParam.packageName);
        XposedBridge.log("                                                            ");
        Log.d(MyTAG, "系统加载的应用是（按包名打印）: " + loadPackageParam.packageName);
        Log.d(MyTAG, "                                                            ");

        /***
         * 以下内容是获取ShareUserId
         */

        findAndHookMethod("android.content.ContextWrapper", loadPackageParam.classLoader,
                "getApplicationContext", new XC_MethodHook() {
                    protected void afterHookedMethod(MethodHookParam param) throws
                            Throwable {
                        storedContext = (Context) param.getResult();
//                        XposedBridge.log("storedContext is   : " + storedContext);
                        XposedBridge.log("系统加载的当前应用Context is : " + storedContext);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "系统加载的当前应用Context is : " + storedContext);

                        System.out.println("storedContext is   : " + storedContext);
//                            PackageManager pm = storedContext.getPackageManager();
//                            PackageInfo pInfo = pm.getPackageInfo(packName, 0);
//                            String packageName = pInfo.packageName;  //得到安装包名称
//                            XposedBridge.log("pInfo is   : " + pInfo);

//                            String sharedUserId = pInfo.sharedUserId;
//                            XposedBridge.log("sharedUserId is   : " + sharedUserId);

//                            mKeyByMD5 = MD5Util.getKeyByMD5(sharedUserId, packageName, getRandomInfo());
                        System.out.println("mKeyByMD5 is :" + mKeyByMD5);
                        /**mKeyStore = storedContext.getSharedPreferences("keyStore", Context.MODE_PRIVATE);
                         SharedPreferences.Editor editor = mKeyStore.edit();//获取编辑器
                         editor.putString("key", mKeyByMD5);
                         editor.commit();//提交修改*/
                    }

                    //生成10个随机字符拼接的字符传
                    private String getRandomInfo() {
                        Random random = new Random();
                        StringBuilder sb = new StringBuilder(0);
                        while (sb.length() < 10) {
                            int number = random.nextInt('z' + 1);
                            if ((number >= 'A' && number <= 'Z')
                                    || (number >= 'a' && number <= 'z')) {
                                sb.append((char) number);
                            } else if (number <= 9) {
                                sb.append(number);
                            }
                        }
                        return sb.toString();
                    }


                });


        /***
         * 以下内容是透明加密
         */

//        FileInputStream
        // replaceHookedMethod 替换方法
        // beforeHookedMethod 方法前执行
        // afterHookedMethod 方法后执行
        // 处理是的情况
        // 找到对应类的方法，进行hook，hook的方式有两种

        findAndHookMethod(className,     // 类名
                loadPackageParam.classLoader, // 类加载器
                "read", // 方法名
                byte[].class,   // 参数1
                int.class,   // 参数2
                int.class,//参数3
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("读操作开始劫持了！ ");
//
//                        String param0 = new String((byte[]) param.args[0]);
//                        XposedBridge.log("参数1 = : " + param0);
//                        XposedBridge.log("参数2 = : " + param.args[1]);
//                        XposedBridge.log("参数3 = : " + param.args[2]);
//
//                        XposedBridge.log("实际调用read方法的对象 = : " + param.thisObject);
//                        XposedBridge.log("1实际读取到的数据大小 = : " + param.getResult());
//
//                        XposedBridge.log("2实际读取到的数据大小 = : " + param.getResult());
//
//
//                        String str = mRc4.decry_RC4("618b1a01435b7ff713", "123456");
//                        XposedBridge.log("解密之后的数据是： " + str);
//
//                        param.args[0] = str.getBytes();
//                        String param1 = new String((byte[]) param.args[0]);
//                        int length = param1.length();
////                        param.args[1] = 5;
//                        param.args[2] = length;
//                        XposedBridge.log("解密操作之后的参数是如下三行 ");
//                        XposedBridge.log("参数1 = : " + param1);
//                        XposedBridge.log("参数2 = : " + param.args[1]);
//                        XposedBridge.log("参数3 = : " + param.args[2]);
//                        XposedBridge.log("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ ");


                    }


                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("************************************************************");
                        XposedBridge.log("系统底层读操作劫持开始了！ ");
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "系统底层读操作劫持开始了！ ");
                        Log.d(MyTAG, "                                                            ");

                        String param0 = new String((byte[]) param.args[0]);
                        XposedBridge.log("劫持读操作获取到的数据参数是 = : " + param0);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "劫持读操作获取到的数据参数是 = : " + param0);
                        Log.d(MyTAG, "                                                            ");
//                        XposedBridge.log("参数2 = : " + param.args[1]);
//                        XposedBridge.log("劫持读操作获取到的数据长度参数是 = : " + param.args[2]);
//                        XposedBridge.log("                                                            ");
//                        Log.d(MyTAG, "劫持读操作获取到的数据长度参数是 = : " + param.args[2]);
//                        Log.d(MyTAG, "                                                            ");


                        XposedBridge.log("底层劫持写操作实际读取到的数据大小 = : " + param.getResult());
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层劫持写操作实际读取到的数据大小 = : " + param.getResult());
                        Log.d(MyTAG, "                                                            ");

                        XposedBridge.log("底层劫持写操作实际调用read方法的对象 = : " + param.thisObject);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层劫持写操作实际调用read方法的对象 = : " + param.thisObject);
                        Log.d(MyTAG, "                                                            ");

                        if (((int) param.getResult()) == -1) {
                            return;
                        }
                        XposedBridge.log("底层将要执行解密操作 ");
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层将要执行解密操作 ");
                        Log.d(MyTAG, "                                                            ");

                        String encryNum = param0.substring(0, (int) param.getResult());
                        XposedBridge.log("底层劫持将要解密数据是： " + encryNum);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层劫持将要解密数据是： " + encryNum);
                        Log.d(MyTAG, "                                                            ");

                        // String key = mKeyStore.getString("key","");
                        String str = mRc4.decry_RC4(encryNum, mKeyByMD5s);
                        XposedBridge.log("底层解密操作之后的数据是： " + str);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层解密操作之后的数据是： " + str);
                        Log.d(MyTAG, "                                                            ");

                        param.args[0] = str.getBytes();
                        String param1 = new String((byte[]) param.args[0]);
//                        param.args[1] = 5;
//                        param.args[2] = 10;
                        XposedBridge.log("详细解密操作之后的参数是如下  ");
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG,"详细解密操作之后的参数是如下  ");
                        Log.d(MyTAG, "                                                            ");
//   XposedBridge.log("解密操作之后的参数是如下三行 ");

                        XposedBridge.log("底层解密操作之后的数据参数是 = : " + param1);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG,"底层解密操作之后的数据参数是 = : " + param1);
                        Log.d(MyTAG, "                                                            ");

//  XposedBridge.log("参数2 = : " + param.args[1]);
                        XposedBridge.log("底层解密操作之后的数据参数长度是  = : " + param.args[2]);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层解密操作之后的数据参数长度是  = : " + param.args[2]);
                        Log.d(MyTAG, "                                                            ");
                        XposedBridge.log("----------------------------------");


//                        String con = "底层读操作开始劫持了！"+ "\n"+"实际读取到的数据是："+encryNum+"\n"+"将要进行解密操作!!"+"\n"+ "加密之后的数据是："+param1+""
//                                +"\n"+"加密之后的数据长度是："+ param.args[2];
                        Intent intent = new Intent(SEND_OK_MESSAGE);
                        intent.putExtra("data", param1);
                        storedContext.sendBroadcast(intent);

                    }
                });


        findAndHookMethod(className2,     // 类名
                loadPackageParam.classLoader, // 类加载器
                "write", // 方法名
                byte[].class,   // 参数1
                int.class,   // 参数2
                int.class,//参数3

                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("************************************************************");
                        XposedBridge.log("系统底层写操作开始劫持了！ ");
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "系统底层写操作开始劫持了！ ");
                        Log.d(MyTAG, "                                                            ");
//                        XposedBridge.log("参数1 = : " + Arrays.toString((byte [] )param.args[0]));
                        String param0 = new String((byte[]) param.args[0]);
                        XposedBridge.log("劫持写操作获取到的数据参数是 = : " + param0);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "劫持写操作获取到的数据参数是 = : " + param0);
                        Log.d(MyTAG, "                                                            ");
//                        XposedBridge.log("参数2 = : " + param.args[1]);
                        XposedBridge.log("劫持写操作获取到的数据长度参数是 = : " + param.args[2]);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "劫持写操作获取到的数据长度参数是 = : " + param.args[2]);
                        Log.d(MyTAG, "                                                            ");


                        XposedBridge.log("底层将要进行加密操作！ ");
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG,"底层将要进行加密操作！ ");
                        Log.d(MyTAG, "                                                            ");
                        //String key = mKeyStore.getString("key","");
                        String str = mRc4.encry_RC4_string(param0, mKeyByMD5s);
//                        XposedBridge.log("底层加密操作之后的数据是： " + str);


                        param.args[0] = str.getBytes();
                        String encryptNum = new String((byte[]) param.args[0]);
                        int length = encryptNum.length();
                        XposedBridge.log("底层实现加密之后的参数如下： ");
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层实现加密之后的参数如下： ");
                        Log.d(MyTAG, "                                                            ");
                        XposedBridge.log("底层加密之后的数据是 = : " + encryptNum);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层加密之后的数据是 = : " + encryptNum);
                        Log.d(MyTAG, "                                                            ");

                        XposedBridge.log("底层加密之后的数据长度是： " + length);
                        XposedBridge.log("                                                            ");
                        Log.d(MyTAG, "底层加密之后的数据长度是： " + length);
                        Log.d(MyTAG, "                                                            ");
//                        XposedBridge.log("参数2 = : " + param.args[1]);
                        param.args[2] = length;
//                        XposedBridge.log("参数3 = : " + param.args[2]);
//                        XposedBridge.log("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ ");


//                        String con = "底层写操作开始劫持了！"+"\n"+"实际写入的数据内容是："+param0+  "\n"+"将要进行加密操作!!"+"\n"+ "加密之后的数据是："+str+""
//                                +"\n"+"加密之后的数据长度是："+ length;
//

                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("写操作劫持结束了！ ");

                    }
                });


    }

}
