
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class usingJsonObjec {
    public static void main(String[] strg) throws Exception {
        System.out.println("Hola Mundo");
        final String urlApi = "https://jsonmock.hackerrank.com/api/football_matches";
        JSONObject jsob = conexionApi(urlApi);
        System.out.println("Despues de llamar 'conexionApi:: "+jsob);
        System.out.println("Se obtiene solo lo que esta en data: "+jsob.get("data"));
        int totalPages= Integer.parseInt(jsob.get("total_pages").toString());
        int totalGoalsTeam1=0;
        int totalGoalsTeam2=0;
        for(int i=1;i<100;i++){
            System.out.println("Page: "+i);
            jsob=conexionApiPerPage(urlApi,i);
            // ++++++++++++++++ METODO para obtener  Arreglo dentro del JSON ++++++++++++++++++++
            JSONArray jsArrPerPage = (JSONArray) jsob.get("data");
            for (int j=0;j<jsArrPerPage.size();j++){
                JSONObject obJs = (JSONObject) jsArrPerPage.get(j);
                System.out.println(obJs);
                totalGoalsTeam1 = totalGoalsTeam1+Integer.parseInt(obJs.get("team1goals").toString());
                totalGoalsTeam2 = totalGoalsTeam1+Integer.parseInt(obJs.get("team2goals").toString());
            }
        }

        // ++++++++++++++++ METODO para obtener  Arreglo dentro del JSON ++++++++++++++++++++
    /*
        JSONArray jsonArrResul = new JSONArray();
        jsonArrResul = (JSONArray) jsob.get("data");
        System.out.println("JSON ARRAY:: "+ jsonArrResul.size());
        jsonArrResul.forEach(m -> System.out.println(m));
        int totalGoalsTeam1 = 0;
        int totalGoalsTeam2 =0;
        for(int i =0; i<jsonArrResul.size();i++){
            JSONObject jsbArr = (JSONObject) jsonArrResul.get(i);
            System.out.println("GOAL TEAM 1: "+jsbArr.get("team1goals"));
            System.out.println("GOAL TEAM 2: "+jsbArr.get("team2goals"));
            totalGoalsTeam1 = totalGoalsTeam1+ Integer.parseInt(jsbArr.get("team1goals").toString());
            totalGoalsTeam2 = totalGoalsTeam2+ Integer.parseInt(jsbArr.get("team2goals").toString());
        }

     */
        System.out.println("TEAM 1: "+totalGoalsTeam1);
        System.out.println("TEAM 2: "+totalGoalsTeam2);
    }
    public static JSONObject conexionApiPerPage(String urlApi, Integer page) throws Exception {
        urlApi = urlApi+"?page="+page;
        URL apiUrl = new URL(urlApi);

        HttpURLConnection httpConection = (HttpURLConnection) apiUrl.openConnection();
        httpConection.setRequestMethod("GET");
        httpConection.setConnectTimeout(5000);
        httpConection.addRequestProperty("Content-Type","application/json");

        int statusRequest = httpConection.getResponseCode();
        InputStream in= (statusRequest < 200 || statusRequest>299)?
            httpConection.getErrorStream() : httpConection.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        StringBuffer strBf= new StringBuffer();
        String bufferByString;
        while((bufferByString=bf.readLine())!= null){
             strBf.append(bufferByString);
        }
        httpConection.disconnect();
        bf.close();
        JSONParser libJsonParse = new JSONParser();
        JSONObject jsnObjResult = (JSONObject) libJsonParse.parse(strBf.toString());
        return jsnObjResult;
    }


    public static JSONObject conexionApi(String urlApi) throws Exception {

        URL apiUrl = new URL(urlApi);
        // ++++++++++++++++ Configuración de la conexión a la API ++++++++++++++++++++
        HttpURLConnection httpConnect = (HttpURLConnection) apiUrl.openConnection();

        httpConnect.setRequestMethod("GET");
        httpConnect.setConnectTimeout(5000);
        httpConnect.addRequestProperty("Content-Type", "application/json");
        // ++++++++++++++++ +++++++++++++++++++++++++++++++++++++ ++++++++++++++++++++

        int responseCode = httpConnect.getResponseCode();
        // ++++++++++++++++ Obtención del contenido en Stream ++++++++++++++++++++
        InputStream inpStream= (responseCode < 200 || responseCode > 299)
                            ? //Es igual que if, solo que aqui se aplica para instantaneamente asignar el resultado a una variable
                                httpConnect.getErrorStream()  //Si es true la evaualicon
                                            :  // En otro caso
                                httpConnect.getInputStream(); // Si es false la evaluacion
        // ++++++++++++++++ +++++++++++++++++++++++++++++++++ ++++++++++++++++++++

        // ++++++++++++++++ Lectura del resultado almacenado en InputStream ++++++++++++++++++++
        BufferedReader buffRead= new BufferedReader(new InputStreamReader(inpStream)); // Se utiliza bufferRead para almacenar el contenido de un solo golpe
        StringBuffer stringBuffer = new StringBuffer(); //Se utiliza para convertir el contenido a StringBuffer
        String bufferByLine;
        //   >>>>>>>>   Se realiza un while para leer linea por linea el contenido e irlo almacenando en StringBuffer
        while ((bufferByLine= buffRead.readLine()) != null){
            System.out.println("BufferByLine While: "+bufferByLine);
            stringBuffer.append(bufferByLine);
        }
        //  >>>>>>>> Una vez leido el contenido se cierra conexión y Buffer para limpiar espacio
        buffRead.close();
        httpConnect.disconnect();
        // ++++++++++++++++ +++++++++++++++++++++++++++++++++++++++++++++++ ++++++++++++++++++++

        // ++++++++++++++++ Convertir StringStream a un objeto JSON ++++++++++++++++++++
        JSONObject jsonResult = null; // Se define objeto tipo Json
        JSONParser libParseJson = new JSONParser(); //Se hace llamado de lib para hacer parseo a JSON
        jsonResult = (JSONObject) libParseJson.parse(stringBuffer.toString()); //Se asigna el contendio al Objeto JSON
        return  jsonResult;
    }
}



