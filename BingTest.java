import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.simple.*;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.commons.codec.binary.Base64;
public class BingTest {
	public static String getResult(String q) throws IOException {
		
		String bingUrl = "https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Web?Query=%27"+q+"%27&$top=10&$format=Json";
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

	public static HashMap<String, Integer> wordCount(String str) {
		HashMap<String, Integer> tfMap = new HashMap<String, Integer>();
		String[] words = str.split(" ");
		for (String word : words) {
			if(!tfMap.containsKey(word)) {
				tfMap.put(word, 1);
			} else {
				tfMap.put(word, tfMap.get(word)+1);
			}
		}
		return tfMap;
		
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
                    String s = finder2.getValue().toString();
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
            if (args.length<2){
                System.out.println("Usage: make run keyword=<keyword> precision=<precision>");
                System.exit(1);
            }
        /*
        System.out.println("Please input query:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String query = null;
		try {
			query = br.readLine();
			
		} catch (IOException ioe) {
			System.out.println("IO error trying to read your Query!");
			System.exit(1);
		}
        
        
        String jsonText = getResult(query);
        ArrayList<ArrayList<String>> result = parseJSON(jsonText);
        
        Iterator<ArrayList<String>> iter = result.iterator();
        int i=0;
        while(iter.hasNext()) {
            i++;
            String str = iter.next().get(1);
            System.out.println("Description"+i+" : "+str);
            HashMap<String, Integer> map = wordCount(str);
            for(String key : map.keySet()) {
                System.out.println(key+"----->>"+map.get(key));
            }
        }
        
        */
            StringBuilder keywords = new StringBuilder();
            keywords.append(args[0]);
            Float targetPrecision = 0.9f;
            try {
                targetPrecision = Float.parseFloat(args[1]);
            } catch (NumberFormatException e){
                System.err.println(e.getMessage());
                System.exit(1);
            }
            Float precision = 0f;
            while (precision < targetPrecision) {
                String content = getResult(keywords.toString());
                ArrayList<ArrayList<String>> result = parseJSON(content);
                Query(result);
                //AddNewKeyword(result, keywords);
            }
            
            
	}

}
