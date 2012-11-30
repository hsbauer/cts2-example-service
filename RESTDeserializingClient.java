
import edu.mayo.cts2.framework.core.client.Cts2RestClient;
import edu.mayo.cts2.framework.core.json.JsonConverter;
import edu.mayo.cts2.framework.core.xml.Cts2Marshaller;
import edu.mayo.cts2.framework.core.xml.DelegatingMarshaller;
import edu.mayo.cts2.framework.model.core.EntitySynopsis;
import edu.mayo.cts2.framework.model.valueset.ValueSetCatalogEntryDirectory;
import edu.mayo.cts2.framework.model.valueset.ValueSetCatalogEntrySummary;
import edu.mayo.cts2.framework.model.valuesetdefinition.IteratableResolvedValueSet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * Author: Scott Bauer bauer.scott@mayo.edu
 * Date: 9/25/12
 * Time: 2:18 PM
 */
public class RESTDeserializingClient {


    public void unMarshallandPrintValueSets() throws Exception {
        System.out.println("\nUnmarshalling and printing using CTS2 REST client\n");
        Cts2Marshaller marshaller = new DelegatingMarshaller();
        Cts2RestClient client = new Cts2RestClient(marshaller);
        ValueSetCatalogEntryDirectory result =
                client.getCts2Resource("http://informatics.mayo.edu/cts2/rest/valuesets", ValueSetCatalogEntryDirectory.class);
        System.out.println(result);
    }
     public void unMarshallandPrintFromJson() throws IOException {
         System.out.println("\nUnmarshalling and printing from json\n");
         String uri =
                 "http://informatics.mayo.edu/cts2/rest/valuesets?matchvalue=Sequence&format=json";
         URL url;
         HttpURLConnection connection;
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
         StringBuilder builder = new StringBuilder();

         while ((output = br.readLine()) != null) {
             builder.append(output);
         }
         JsonConverter converter = new JsonConverter();
         ValueSetCatalogEntryDirectory valuesetcat = converter.fromJson(builder.toString(), ValueSetCatalogEntryDirectory.class);
         ValueSetCatalogEntrySummary[] entries = valuesetcat.getEntry();
         for(ValueSetCatalogEntrySummary sum : entries) {
             System.out.println("Value Set Name: " + sum.getValueSetName());
             System.out.println("Value Set Definition: " + sum.getCurrentDefinition());
             System.out.println("Value Set About: "  + sum.getAbout());
             System.out.println("Value Set Formal Name: " + sum.getFormalName());
             System.out.println("Value Set Resource Synopsis: " + sum.getResourceSynopsis());
             System.out.println("Value Set Href: " + sum.getHref());
             System.out.println("Value Set Resource Name: " + sum.getResourceName());
             System.out.println("Resolving Value Set Href");
             StringBuilder vsBuffer = getRestFromHref(sum.getHref());
             IteratableResolvedValueSet iterableVS = getEntryMsg(vsBuffer);
             Iterator<? extends EntitySynopsis > iterator = iterableVS.iterateEntry();
             while(iterator.hasNext()) {
                 EntitySynopsis entry = iterator.next();
                 System.out.println("Value designation" + entry.getDesignation());
                 System.out.println("Entity href" + entry.getHref());
                 System.out.println("Entity unique Identifier: " + entry.getName());
                 System.out.println("Entity namespace: " + entry.getNamespace());
             }


         }
     }
    private StringBuilder getRestFromHref(String uri) throws IOException {
        URL url;
        uri = uri.concat("/resolution");
        if(!uri.endsWith("?format=json"))
            uri = uri.concat("?format=json");
        HttpURLConnection connection;
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
        StringBuilder buffer = new StringBuilder();

        while ((output = br.readLine()) != null) {
            buffer.append(output);
        }
        return buffer;
    }

    private IteratableResolvedValueSet getEntryMsg(StringBuilder buffer){
        JsonConverter converter = new JsonConverter();
        return  converter.fromJson(buffer.toString(), IteratableResolvedValueSet.class);
    }


    public static void main(String[] args) throws Exception {

        RESTDeserializingClient client = new RESTDeserializingClient();
        client.unMarshallandPrintFromJson();
        client.unMarshallandPrintValueSets();

    }


}
