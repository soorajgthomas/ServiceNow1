package com.expedia.servicenow.servicenow1.WebService;

import android.content.Context;

import com.expedia.servicenow.servicenow1.util.Constants;
import com.expedia.servicenow.servicenow1.util.SharedPref;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
                .addHeader("postman-token", "2041ac0e-a38e-e76e-7411-632d9ecc386a")
                .build();

        return  client.newCall(request).execute();

    }

}
