package com.preemptive.moneybank;

import android.app.Application;
import com.preemptive.moneybank.api.RequestQueueSingleton;
import com.preemptive.moneybank.data.LoginDataSource;
import com.preemptive.moneybank.data.LoginRepository;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class MoneyBankApplication extends Application {

    /**
     * https://newfivefour.com/android-trust-all-ssl-certificates.html
     *
     * This is to simplify networking between the emulator and a dev machine.
     * Don't ever do this in real life.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        X509TrustManager trustAllCerts = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[] { trustAllCerts }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException | KeyManagementException exc) {
            throw new RuntimeException(exc);
        }


        LoginRepository.init(new LoginDataSource(RequestQueueSingleton.getInstance(getApplicationContext())));
    }
}
