package com.expedia.servicenow.servicenow1.WebService;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SOORAJ on 13-11-2016.
 */

public class AuthenticationService {

    public static Response getOAuth(String username, String password) throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=password" +
                "&client_id=ac0dd3408c1031006907010c2cc6ef6d&client_secret=client_secret" +
                "&username=" + username +
                "&password=" + password);
        Request request = new Request.Builder()
                .url("https://dev25388.service-now.com/oauth_token.do")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                //.addHeader("postman-token", "0267bcbc-0316-d06c-28cd-c6a43fb70f29")
                .build();

        return client.newCall(request).execute();

    }

}