package com.eywa.myplant;

import static com.eywa.myplant.Global.SERVER_URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PostRequestForId implements Runnable {
    private static final String log = "postRequestForId";

    private String requestURL;
    private Handler handler = new Handler(Looper.getMainLooper());
    private HttpCallback callback;


    public PostRequestForId(String requestURL, HttpCallback callback) {
        this.requestURL = requestURL;
        this.callback = callback;
    }

    @Override
    public void run() {
        URL url;
        String response = "";

        // 모든 인증서를 신뢰하는 TrustManager
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        try {
            //Log.d(log, "1");
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCertificates, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            //Log.d(log, "2");

            url = new URL(SERVER_URL + requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Log.d(log, "3");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //Log.d(log, "4");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder responseBuilder = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseBuilder.append(line);
                }
                br.close();
                response = responseBuilder.toString();

                // JSON 응답에서 "id" 값을 추출
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("message");
                String idValue = jsonObject.getString("id");

                if (callback != null) {
                    handler.post(() -> callback.onSuccess(message, idValue));
                }
            } else {
                //Log.d(log, "5");
                // 오류 메시지 읽기
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line;
                StringBuilder responseBuilder = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseBuilder.append(line);
                }
                br.close();
                String errorMessage = responseBuilder.toString();

                if (callback != null) {
                    handler.post(() -> callback.onFailure(errorMessage));
                }
            }
        } catch (Exception e) {
            Log.e(log, "exception: " ,e);
            e.printStackTrace();
        }
    }
}
