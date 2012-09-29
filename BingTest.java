package BeingAPI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.simple.*;

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
	/*
	public static ArrayList<ArrayList<String>> parseJSON(String jsonStr) {
		
	}
	*/
	
	public static void main(String[] args) throws IOException {
		/*
		File newTextFile = new File("test.xml");
        FileWriter fileWriter = new FileWriter(newTextFile);
        fileWriter.write(content);
        fileWriter.close();
        */
		System.out.println("=======decode=======");
        
		  String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		  Object obj=JSONValue.parse(s);
		  JSONArray array=(JSONArray)obj;
		  System.out.println("======the 2nd element of array======");
		  System.out.println(array.get(1));
		  System.out.println();
		                
		  JSONObject obj2=(JSONObject)array.get(1);
		  System.out.println("======field \"1\"==========");
		  System.out.println(obj2.get("1"));    

		                
		  s="{}";
		  obj=JSONValue.parse(s);
		  System.out.println(obj);
		                
		  s="[5,]";
		  obj=JSONValue.parse(s);
		  System.out.println(obj);
		                
		  s="[5,,2]";
		  obj=JSONValue.parse(s);
		  System.out.println(obj);
        
        
	}

}
