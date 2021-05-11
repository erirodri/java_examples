import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;



public class App {
    public static void main(String[] args) throws Exception {



        final String endPoint = "https://jsonmock.hackerrank.com/api/football_matches";
		final int year = 2011;
		final String team = "Chelsea";
		
		/*int totalGoalsAtHome = getPageTotalGoals(
				String.format(endPoint + "?team1=%s&year=%d", URLEncoder.encode(team, "UTF-8"), year),
				"team1",1);*/
                Map<String,Integer> getGoalsAtHome=getPageTotalGoals(
				String.format(endPoint + "?team1=%s&year=%d", URLEncoder.encode(team, "UTF-8"), year),
				"team1",1);
		
		/*int totalGoalsAtVisiting = getPageTotalGoals(
				String.format(endPoint + "?team2=%s&year=%d", URLEncoder.encode(team, "UTF-8"), year),
				"team2",1);*/
                Map<String,Integer> getGoalsAtVisiting=getPageTotalGoals(
				String.format(endPoint + "?team2=%s&year=%d", URLEncoder.encode(team, "UTF-8"), year),
				"team2",1);
				int totalGoals=0;
				for(int page=1;page<=getGoalsAtHome.get("totalPages");page++){
					Map<String,Integer> getGoalsAtHomeSum=getPageTotalGoals(
						String.format(endPoint + "?team1=%s&year=%d", URLEncoder.encode(team, "UTF-8"), year),
						"team1",page);
					 Map<String,Integer> getGoalsAtVisitingSum=getPageTotalGoals(
						String.format(endPoint + "?team2=%s&year=%d", URLEncoder.encode(team, "UTF-8"), year),
						"team2",page);
					totalGoals+=(getGoalsAtHomeSum.get("totalGoals")+getGoalsAtVisitingSum.get("totalGoals"));
				}
		
		System.out.println("TOTAL: "+totalGoals); 
    }

    public static Map<String,Integer> getPageTotalGoals(String request, String team, int page) throws Exception {

		Map<String,Integer> result = new HashMap<>();

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
            //System.out.println("LINE::: "+responseLine);
			responseContent.append(responseLine);
		}
		br.close();
		httpURLConnection.disconnect();
        ScriptEngineManager manager= new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		//System.out.println(responseContent);
		
		String script = "var obj = JSON.parse('"+ responseContent.toString()+"');";
		script += "var total_pages = obj.total_pages;";
		
        script += "var total_goals = obj.data.reduce(function(accumulator, current){ return accumulator + parseInt(current."+ team +"goals); }, 0);";
        
		engine.eval(script);
        if(engine.get("total_pages") == null){
            throw new RuntimeErrorException(null, "No se puede obtener información");
        }
        int totalPages = (int) engine.get("total_pages");
        int totalGoals = (int) Double.parseDouble(engine.get("total_goals").toString());
        result.put("totalPages", totalPages);
		result.put("totalGoals", totalGoals);
		System.out.println("TEAM: "+team+" PAGE: "+page+" TotalGoals: "+totalGoals);
		
		return result;
    }
    
}
