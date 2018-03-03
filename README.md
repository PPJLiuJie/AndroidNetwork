# Android网络编程

`Apache`的`HttpClient`和`Java`的`HttpURLConnection`这两个类，是我们在Android网络编程中会用到的。无论我们是封装的网络请求类还是第三方的网络请求框架都离不开这两个类。

但是Android6.0开始取消了对Apache HTTP客户端的支持。参见：<a href="https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html" target="_blank">Android6.0新特性</a>


1. 如果只是发送一些简单的数据(比如json)发送至服务器，并从服务器接收一些简单的数据，可以自行封装一个Http请求的工具类。参见：<a href="https://github.com/PPJLiuJie/AndroidNetwork/blob/dev/Network/app/src/main/java/me/android/liujie/network/HttpUtil.java" target="_blank">HttpUtil.java</a>
2. 如果要应对复杂的网络请求，比如下载，建议使用开源框架<a href="https://github.com/google/volley" target="_blank">Volley</a>或者<a href="https://github.com/square/okhttp" target="_blank">OkHttp</a>，这两个开源框架的使用以及源码分析，参见：<a href="http://blog.csdn.net/itachi85/article/details/50982995" target="_blank">Android网络编程——刘望舒的专栏</a>