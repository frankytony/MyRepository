package javaeetutorial.hello2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpsTest333 {
	public static void main(String[] arg) throws IOException{       
	    	//String httpsURL = "https://129.6.116.3:6310/oauth/token";  
	       // String trustStor="./vnfm.jks";  
//	        
	    	String httpsURL = "http://localhost:8080/helloAsync/hi";  
	       
	        URL myurl = new URL(httpsURL);  
	        HttpURLConnection con = (HttpURLConnection) myurl.openConnection();  
	        InputStream ins = con.getInputStream();  
	        InputStreamReader isr = new InputStreamReader(ins);  
	        BufferedReader in = new BufferedReader(isr);  
	        String inputLine=null;  
	        while ((inputLine = in.readLine()) != null) {  
	            System.out.println(inputLine);  
	        }  
	        in.close();  
	}
}
