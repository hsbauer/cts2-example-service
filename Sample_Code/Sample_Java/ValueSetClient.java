import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: m029206
 * Date: 9/20/12
 * Time: 11:48 AM.
 */
public class ValueSetClient {

    public  void getValueSets(){
        String uri =
                "http://informatics.mayo.edu/cts2/rest/valuesets";
        URL url;
        try {
            url = new URL(uri);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : The HTTP error code is : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

        connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void getValueSet(){
        String uri =
                "http://informatics.mayo.edu/cts2/rest/valuesets?matchvalue=Sequence&format=json";
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(uri);


            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "text/json");
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

        ValueSetClient client =  new ValueSetClient();
        client.getValueSets();
        client.getValueSet();



    }
}
