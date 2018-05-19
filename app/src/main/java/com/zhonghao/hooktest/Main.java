package com.zhonghao.hooktest;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * 项目名称：HookTest
 * 包名：com.zhonghao.hooktest
 * 创建人：小豪
 * 创建时间：2017/1/26 14:15
 * 类描述：
 */

public class Main implements IXposedHookLoadPackage {

    //被HOOK的程序的包名和类名
    String packName = "com.zhonghao.test1";
    String className = "com.zhonghao.test1.MainActivity";
    /**
     * 包加载时候的回调
     */
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // 将包名不是 com.example.login 的应用剔除掉
        if (!loadPackageParam.packageName.equals("com.zhonghao.test1"))
            return;
        XposedBridge.log("Loaded app: " + loadPackageParam.packageName);

        // replaceHookedMethod 替换方法
        // beforeHookedMethod 方法前执行
        // afterHookedMethod 方法后执行
        // 处理是的情况
        // 找到对应类的方法，进行hook，hook的方式有两种
      findAndHookMethod(className,     // 类名
                loadPackageParam.classLoader, // 类加载器
                "CheckRegister", // 方法名
                String.class,   // 参数1
                String.class,   // 参数2
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                        XposedBridge.log("开始劫持了！ ");
                        XposedBridge.log("参数1 = : " + param.args[0]);
                        XposedBridge.log("参数2 = : " + param.args[1]);
                        Log.d("xposedplugin", (String) param.args[0]);
                        Log.d("xposedplugin", (String) param.args[1]);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("劫持结束了！ ");
                        XposedBridge.log("参数1 = : " + param.args[0]);
                        XposedBridge.log("参数2 = : " + param.args[1]);
                        Log.d("xposedplugin", (String) param.args[0]);
                        Log.d("xposedplugin", (String) param.args[1]);
                    }
                });
    }

}
