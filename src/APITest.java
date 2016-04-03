import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class APITest {
    public static void main(String[] args) throws IOException {
               
        getDataFromAPI("BusRouteInfoInqireService", "getCtyCodeList", "%2FINPAsm7NTY0H7pQwDLNdW5dFd%2FhZxqvngMPEUKPW2de5TVRU2fhgI6x6CsUpkhjJYmH5tG4vYCahsntFWxJ%2Bg%3D%3D",
        		"numOfRows=999", "pageNo=1");
        
    }
    
    public static void getDataFromAPI(String serviceName, String operationName, String serviceKey, String... parameterDic) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/"); /*URL*/
        urlBuilder.append(serviceName);
        urlBuilder.append("/");
        urlBuilder.append(operationName);
        urlBuilder.append("?ServiceKey=");
        urlBuilder.append(serviceKey);
        for(String parameter : parameterDic) {
            urlBuilder.append("&");
        	urlBuilder.append(parameter);
        }
        
        System.out.println(urlBuilder);
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println(conn.getHeaderFields());
        System.out.println("Response code: " + conn.getResponseCode());
        
        BufferedReader rd;
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = rd.readLine()) != null) {
            	System.out.println(line);
            }
            rd.close();
        }
        conn.disconnect();
    }
}