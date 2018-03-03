package me.android.liujie.network;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/3 0003.
 */

public class HttpUtil {

    private static final String TAG = "HttpUtil";

    /**
     * GET请求
     * @param urlStr 请求地址
     * @param params 请求参数
     * @return 请求的结果
     */
    public static String get(String urlStr, HashMap<String, String> params) {
        String result = null;
        URL url = null;
        HttpURLConnection conn = null;
        InputStreamReader isr = null;

        try {
            String paramsEncoded = "";
            if (params != null) {
                paramsEncoded = urlParamsFormat(params, "UTF-8");
            }

            String fullURL = urlStr + "?" + paramsEncoded;

            Log.d(TAG, "HttpUtil.get the fullURL is ----" + fullURL);

            url = new URL(fullURL);
            conn = (HttpURLConnection) url.openConnection();

            int code = conn.getResponseCode();
            if (code == 200) {
                isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
            } else {
                Log.e(TAG, "HttpUtil.get connection failed. code:" + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * POST请求
     * @param urlStr 请求地址
     * @param params 请求参数
     * @return 请求结果
     */
    public static String post(String urlStr, Map<String, String> params) {
        String result = null;
        URL url = null;
        HttpURLConnection conn = null;
        InputStreamReader isr = null;

        try {
            String paramsEncoded = "";
            if (params != null) {
                paramsEncoded = urlParamsFormat(params, "UTF-8");
            }

            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            // 设置允许输入，默认为值为true，可省略，当为true时才可以使用conn.getInputStream().read()
            // 因为总是使用conn.getInputStream()获取服务端的响应，因此默认值是true。
            conn.setDoInput(true);

            // 设置允许输出，默认值为false，POST请求必须设置为true，当为true时才可以使用conn.getOutputStream().write()
            // get请求用不到conn.getOutputStream()，因为参数直接追加在地址后面，因此默认是false。
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Charset", "utf-8");
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(paramsEncoded);
            dos.flush();
            dos.close();

            int code = conn.getResponseCode();
            if (conn.getResponseCode() == 200) {
                isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                StringBuffer strBuffer = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    strBuffer.append(line);
                }
                result = strBuffer.toString();
            } else {
                Log.e(TAG, "HttpUtil.post connection failed. code:" + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 对请求的参数格式化。key和value之间用“=”连接，键值对之间用“&”连接，并对key和value进行URL编码
     *
     * @param params  参数
     * @param charset URL编码格式
     * @return 格式化后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String urlParamsFormat(Map<String, String> params, String charset) throws UnsupportedEncodingException {
        final StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            final String val = params.get(key);
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(val)) {
                final String encodedName = URLEncoder.encode(key, charset);
                final String encodedValue = URLEncoder.encode(val, charset);
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(encodedName).append("=").append(encodedValue);
            }
        }
        return sb.toString();
    }
}
