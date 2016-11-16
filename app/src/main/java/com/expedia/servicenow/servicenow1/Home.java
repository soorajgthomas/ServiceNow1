package com.expedia.servicenow.servicenow1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.expedia.servicenow.servicenow1.WebService.IncidentService;
import com.expedia.servicenow.servicenow1.util.Constants;
import com.expedia.servicenow.servicenow1.util.Incident;
import com.expedia.servicenow.servicenow1.util.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog pDialog;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<Incident> incidentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView tvNavDrawerUsername = (TextView) header.findViewById(R.id.navDrawerUsername);
        tvNavDrawerUsername.setText(SharedPref.getData(getApplicationContext(), Constants.USER_NAME));
        TextView tvNavDrawerEmail = (TextView) header.findViewById(R.id.navDrawerEmail);
        tvNavDrawerEmail.setText(SharedPref.getData(getApplicationContext(), Constants.USER_NAME)+"@dev25388.service-now.com");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pDialog = new ProgressDialog(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new GetIncidentTableTask().execute();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            SharedPref.removeData(getApplicationContext());
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class GetIncidentTableTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            pDialog.setMessage("Loading Incidents...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Response response = IncidentService.getResponseTable(getApplicationContext());
                return response.body().string();
            } catch (IOException e){
                return "false";
            }
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("incident s : ", response);
            pDialog.hide();
            try {
                JSONObject jsonObjectResult = new JSONObject(response);
                JSONArray jsonArray = jsonObjectResult.getJSONArray("result");
                JSONObject jsonObjectIncident;// = new JSONObject();
                incidentList = new ArrayList<>();

                for(int i=0;i<jsonArray.length();i++){
                    Incident mIncident = new Incident();
                    jsonObjectIncident = jsonArray.getJSONObject(i);
                    mIncident.setNumber(jsonObjectIncident.getString("number"));
                    mIncident.setIncidentState(jsonObjectIncident.getInt("incident_state"));
                    mIncident.setShortDescription(jsonObjectIncident.getString("short_description"));
                    mIncident.setSysUpdatedBy(jsonObjectIncident.getString("sys_updated_by"));
                    mIncident.setSysId(jsonObjectIncident.getString("sys_id"));
                    incidentList.add(mIncident);
                }

                Collections.reverse(incidentList);

            }catch (JSONException e){

            }

            if(incidentList!=null) {
                adapter = new RecyclerAdapter(getApplicationContext(), incidentList);
                recyclerView.setAdapter(adapter);
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
