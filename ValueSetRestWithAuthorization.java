
import sun.misc.BASE64Encoder;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * Author: Scott Bauer bauer.scott@mayo.edu
 * Date: 9/27/12
 * Time: 2:52 PM
 */
public class ValueSetRestWithAuthorization {

    public void getValueSet() throws KeyManagementException, NoSuchAlgorithmException {
        String uri =
                "https://informatics.mayo.edu/cts2/services/mat/valueset/2.16.840.1.113883.3.526.02.99/resolution?format=json";
        URL url;
        HttpsURLConnection connection = null;
        try {
            url = new URL(uri);

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);

            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "text/json");
            String user_pwd = ("hsbauer:Norm10$$");
            BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String encodedAuthorization = enc.encode( user_pwd.getBytes() );

            connection.setRequestProperty("Authorization","Basic " +  encodedAuthorization);
            connection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            System.out.println(connection.getResponseCode());

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : The HTTP error code is : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            System.out.println("\nOutput from CTS2 Service .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

    public static void main(String[] args){
       ValueSetRestWithAuthorization vsrest = new ValueSetRestWithAuthorization();
        try {
            vsrest.getValueSet();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

}
