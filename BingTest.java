import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
//import org.json.simple.*;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
public class BingTest {
	public static String getResult() throws IOException {
		String bingUrl = "https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Web?Query=%27gates%27&$top=10&$format=Json";
		//Provide your account key here. 
		String accountKey = "wRccq1TMy476bqFdC1GrKeHeJ33Fm+hmzSwYWgmtSrM=";
		
		byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);

		URL url = new URL(bingUrl);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
				
		InputStream inputStream = (InputStream) urlConnection.getContent();		
		byte[] contentRaw = new byte[urlConnection.getContentLength()];
		inputStream.read(contentRaw);
		String content = new String(contentRaw);

		//The content string is the xml/json output from Bing.
		//System.out.println(content);
		return content;
	}
	
	public static ArrayList<ArrayList<String>> parseJSON(String jsonStr) {
		return null;
	}
	
	
    public static void Query(ArrayList<ArrayList<String>> result) {
        for (int i = 0; i < 10; i++){
            System.out.println("Result "+(i+1));
            System.out.println("[");
            System.out.println("URL: "+result.get(i).get(2));
            System.out.println("Title: "+result.get(i).get(0));
            System.out.println("Summary: "+result.get(i).get(1));
            System.out.println("[");
            System.out.println();
            System.out.print("Relevant (Y/N)?");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            try {
            String s = in.readLine();
            while (!s.equals("y") && !s.equals("Y") && !s.equals("n") && !s.equals("N")) {
                System.out.print("Please say Y/N:");

                s = in.readLine();
            }
            if (s.equals("y") || s.equals("Y")) {
                result.get(i).add("1");
            } else {
                result.get(i).add("0");
            }
            } catch (IOException e){
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }
    
    
    public static void test_query(){
        ArrayList<ArrayList<String>> result = new  ArrayList<ArrayList<String>>(10);
        for (int i = 0 ; i < 10 ; i++){
            ArrayList<String> a = new ArrayList<String>(4);
            for (int j = 0; j < 3; j++){
                a.add("lalala");
            }
            result.add(a);
        }
        Query(result);
    }
    
	public static void main(String[] args) throws IOException {
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add(args[0]);
            Float targetPrecision = 0.9f;
            try {
                targetPrecision = Float.parseFloat(args[1]);
            } catch (NumberFormatException e){
                System.err.println(e.getMessage());
                System.exit(1);
            }
            Float precision = 0f;
            while (precision < targetPrecision) {
                String content = getResult(/*keywords*/);
                ArrayList<ArrayList<String>> result = parseJSON(content);
                Query(result);
                //AddNewKeyword(result, keywords);
            }
            
            
	}

}
