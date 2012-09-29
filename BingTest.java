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
	
	public static ArrayList<ArrayList<String>> parseJSON(String jsonStr) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		for(int i=0; i<10; i++){
            
            result.add(new ArrayList<String>());
		}
		// find title
		JSONParser parser = new JSONParser();
		KeyFinder finder = new KeyFinder();
		finder.setMatchKey("Title");
		try{
			int count = 0;
		    while(!finder.isEnd()){
                parser.parse(jsonStr, finder, true);
                if(finder.isFound()){
                    finder.setFound(false);
                    String s = finder.getValue().toString();
                    result.get(count).add(s);
                    count++;
                    
                }
		    }
        }
	    catch(ParseException pe){
		    pe.printStackTrace();
        }
		// find Description
		JSONParser parser2 = new JSONParser();
		KeyFinder finder2 = new KeyFinder();
		finder2.setMatchKey("Description");
		try{
		    int count = 0;
		    while(!finder2.isEnd()){
                parser2.parse(jsonStr, finder2, true);
                if(finder2.isFound()){
                    finder2.setFound(false);
                    String s = finder.getValue().toString();
                    result.get(count).add(s);
                    count++;
                    
                }
		    }
        }
	    catch(ParseException pe){
		    pe.printStackTrace();
        }
		
		// find DisplayURl
		JSONParser parser3 = new JSONParser();
		KeyFinder finder3 = new KeyFinder();
		finder3.setMatchKey("DisplayUrl");
		try{
			int count = 0;
		    while(!finder3.isEnd()){
                parser3.parse(jsonStr, finder3, true);
                if(finder3.isFound()){
                    finder3.setFound(false);
                    String s = finder3.getValue().toString();
                    result.get(count).add(s);
                    count++;
                    
                }
		    }           
        }
	    catch(ParseException pe){
		    pe.printStackTrace();
        }
		
        return result;
	}
	
	
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
