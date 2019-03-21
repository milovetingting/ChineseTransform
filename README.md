项目中用到繁体中文语言适配，目前已经有开源的框架可以将简体中文转换成繁体中文，在此基础上封装了一个工具类，可以直接将简体中文的strings.xml转换成繁体中文的strings.xml。


## 引用Jar包

目前没有上传远程仓库，因此需要手动下载回来，再依赖Jar包。Jar包下载地址:[transform.jar](https://raw.githubusercontent.com/milovetingting/ChineseTransform/master/transform.jar "transform.jar")

## Android中使用:

1、将下载回来的Jar包放入模块下的libs文件夹，在模块的Gradle.build文件中添加依赖:

    implementation files('libs/transform.jar')

2、将中文对应的strings.xml放置到手机的指定位置，如:/sdcard/strings.xml，在代码中引用：

    TransformUtil.simpleToTraditional("/sdcard/strings.xml", "/sdcard/strings_traditional.xml");

> Android6.0以上手机注意动态申请存储权限。转换xml是耗时操作，请在子线程中执行，以免出现ANR的问题。


## Eclipse中使用

直接新建Java工程，引用Jar包，调用方法：

    TransformUtil.simpleToTraditional("D:\\strings.xml", "D:\\strings_traditional.xml");

> 建议直接在Java工程中处理xml文件，再将生成的xml文件放置到对应的Android工程的values-zh-rTW/strings.xml。

