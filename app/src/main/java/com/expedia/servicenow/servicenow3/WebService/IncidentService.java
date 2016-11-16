package com.expedia.servicenow.servicenow3.WebService;

import android.content.Context;

import com.expedia.servicenow.servicenow3.util.Constants;
import com.expedia.servicenow.servicenow3.util.SharedPref;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SOORAJ on 13-11-2016.
 */

public class IncidentService {

    public static Response getResponseTable(Context context)  throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://dev25388.service-now.com/api/now/v1/table/incident")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("authorization", "Bearer "+SharedPref.getData(context, Constants.ACCES_TOKEN))
                .addHeader("cache-control", "no-cache")
                //.addHeader("postman-token", "2041ac0e-a38e-e76e-7411-632d9ecc386a")
                .build();

        return  client.newCall(request).execute();

    }


    public static String updateIncidentStatus(Context context, String sys_id, String state) throws Exception {

        String url = "https://dev25388.service-now.com/api/now/table/incident/"+sys_id;

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(JSON, "{'incident_state':'"+state+"'}");

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("accept", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("content-type", "application/json")
                .addHeader("postman-token", "4096bbad-b27b-4633-96c8-174c45034422")
                .addHeader("Authorization", "Bearer "+SharedPref.getData(context, Constants.ACCES_TOKEN))
                .build();

        return client.newCall(request).execute().body().string();







//        String url = "https://dev25388.service-now.com/api/now/table/incident/62263fe2db0722006d3872ffbf96191e";
//        Log.d("sys_id : ",sys_id);
//        Log.d("url : ", url);
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPut httpPut = new HttpPut(url);
//        String json = "";
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Content-type", "application/json");
//        jsonObject.put("incident_state",""+state);
//        json = jsonObject.toString();
//        Log.d("json", json);
//        StringEntity se = new StringEntity(json);
//        httpPut.setEntity(se);
//        httpPut.addHeader("Accept", "application/json");
//        httpPut.addHeader("Content-type", "application/json");
//        httpPut.addHeader("Cache-Control", "no-cache");
//        HttpResponse httpResponse = httpclient.execute(httpPut);
//        return getStringFromInputStream(httpResponse.getEntity().getContent());

    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}
