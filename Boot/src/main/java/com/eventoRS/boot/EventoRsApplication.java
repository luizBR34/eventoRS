package com.eventoRS.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@SpringBootApplication
@EnableFeignClients("com.eventoRS.clients")
//@EnableEurekaClient
@ComponentScan("com.eventoRS")
public class EventoRsApplication {

/* INSTRUCTIONS FOR BUILD USING DOCKERFILE
1) Cean Root Module
2) Build Client Module
3) Build Service Module
4) Build Web Module
5) Build Root Module
    docker build -t luizpovoa/eventors:0.0.1-SNAPSHOT .
    docker run -p 8084:8084 -d -e VAULT_HOST=172.18.0.2 -e VAULT_ROOT_TOKEN=s.MvyZ4i7suJRAzO3tMVCRadPC -e EVENTOAS_HOST=https://172.18.0.6:8081 -e EVENTO_CACHE_URI=http://172.18.0.9:8585 -e EVENTO_WS_URI=http://172.18.0.11:9090 --network eventoapp-network --name EventoRS luizpovoa/eventors:0.0.1-SNAPSHOT
*/

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting Host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting Host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        disableSslVerification();
        SpringApplication.run(EventoRsApplication.class, args);
    }
}
