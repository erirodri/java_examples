import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.*;

import org.json.simple.parser.*;



public class JsonSimple {
    public static void main(String[] args) throws Exception {

		// USO DE ORG.JSON.JSONObject

        final String endPoint = "https://jsonmock.hackerrank.com/api/football_matches";
		final int year = 2011;
		final String team = "Chelsea";
		Map<String,Integer> getGoalsAtHome=getPageTotalGoals(
				String.format(endPoint + "?team1=%s&year=%d", URLEncoder.encode(team, "UTF-8"), year),
				"team1",1);
				
		
		System.out.println("TOTAL: "+0); 
    }

    public static Map<String,Integer> getPageTotalGoals(String request, String team, int page) throws Exception {

		Map<String,Integer> result = new HashMap<>();
		JSONParser parserJson = new JSONParser();
        URL url = new URL(request);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setConnectTimeout(3600);
		httpURLConnection.addRequestProperty("Content-Type", "application/json");
		
		int status = httpURLConnection.getResponseCode();
		InputStream in = (status < 200 || status > 299) ?
				httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();
		
		BufferedReader br = new BufferedReader (new InputStreamReader (in));
        
		String responseLine;
		StringBuffer responseContent = new StringBuffer();
		while ((responseLine = br.readLine()) !=null) {
            System.out.println("LINE while::: "+responseLine);
			responseContent.append(responseLine);
		}
		br.close();
		httpURLConnection.disconnect();
		JSONObject jobj = (JSONObject) parserJson.parse(responseContent.toString());
		System.out.println("JSONOBJ::: "+jobj.get("data"));
		JSONArray jdata = (JSONArray) jobj.get("data");
		for(int i=0; i<jdata.size();i++){
			System.out.println("ELEMENTO ["+i+"]:: "+jdata.get(i));
			JSONObject jobjin = (JSONObject) parserJson.parse(jdata.get(i).toString());
			System.out.println(jobjin.get("num_comment"));
		}

		return result;
    }
}
