/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Carlos J Sanchez
 */
public class HTTPSClient {

    private static String HTTPS_URL = "https://fcv.contino.com.mx";

    private final static Logger log = Logger.getLogger(HTTPSClient.class
            .getName());

    public static void main(String[] args) {
        new HTTPSClient().connect();
    }

    private void connect() {
        try {
            URL url = new URL(HTTPS_URL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            Certificate[] certs = conn.getServerCertificates();

            if (conn != null) {
                for (Certificate cert : certs) {
                    log.info("Cert Type: " + cert.getType());
                    log.info("Cert Hash Code: " + cert.hashCode());
                    log.info("Cert Algorithm: "
                            + cert.getPublicKey().getAlgorithm());
                    log.info("Cert Format: " + cert.getPublicKey().getFormat());
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line;

            while ((line = br.readLine()) != null) {
                log.info(line);
            }

            br.close();
        } catch (Exception e) {
        }
    }

}
