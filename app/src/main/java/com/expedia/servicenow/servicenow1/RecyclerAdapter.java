package com.expedia.servicenow.servicenow1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.expedia.servicenow.servicenow1.WebService.IncidentService;
import com.expedia.servicenow.servicenow1.util.Incident;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOORAJ on 13-11-2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Incident> mIncidentsList = new ArrayList<>();
    Context mContext;
    RecyclerAdapter(Context mContext, ArrayList<Incident> incidentList){
        mIncidentsList = incidentList;
        this.mContext = mContext;
        for(int i=0;i<mIncidentsList.size();i++){
            Log.d("mIncidentsList"+i+"\t", mIncidentsList.get(i).getNumber()+" "+
                    mIncidentsList.get(i).getIncidentState()+" "+
                    mIncidentsList.get(i).getShortDescription()+" "+
                    mIncidentsList.get(i).getSysUpdatedBy()+" "+
                    mIncidentsList.get(i).getSysId());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvIncidentNumber;
        public TextView tvIncidentState;
        public TextView tvIncidentShortDescription;
        public TextView tvIncidentSysUpdatedBy;
        public TextView tvSysId;

        public Button btnIncidentActivate;
        public Button btnIncidentResolved;
        public Button btnIncidentClosed;

        public ViewHolder(View itemView) {
            super(itemView);
            tvIncidentNumber = (TextView)itemView.findViewById(R.id.tvIncidentNumber);
            tvIncidentState = (TextView)itemView.findViewById(R.id.tvIncidentState);
            tvIncidentShortDescription = (TextView)itemView.findViewById(R.id.tvIncidentShortDescription);
            tvIncidentSysUpdatedBy = (TextView)itemView.findViewById(R.id.tvIncidentSysUpdatedBy);
            tvSysId = (TextView)itemView.findViewById(R.id.tvSysId);

            btnIncidentActivate = (Button)itemView.findViewById(R.id.btnIncidentActivate);
            btnIncidentResolved = (Button)itemView.findViewById(R.id.btnIncidentResolved);
            btnIncidentClosed = (Button)itemView.findViewById(R.id.btnIncidentClosed);

            btnIncidentActivate.setOnClickListener(this);
            btnIncidentResolved.setOnClickListener(this);
            btnIncidentClosed.setOnClickListener(this);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Toast.makeText(mContext, tvIncidentNumber.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public void onClick(View v) {
            //String stateString = tvIncidentState.getText().toString().trim();
            Integer incidentState = getIncidentValueByName(tvIncidentState.getText().toString().trim());
            if(v.getId()==btnIncidentActivate.getId()){
                Toast.makeText(mContext, "active", Toast.LENGTH_SHORT).show();
                incidentState = 2;
            }else if(v.getId()==btnIncidentResolved.getId()){
                incidentState = 6;
                Toast.makeText(mContext, "resloved", Toast.LENGTH_SHORT).show();
            }else if(v.getId()==btnIncidentClosed.getId()){
                incidentState = 7;
                Toast.makeText(mContext, "closed", Toast.LENGTH_SHORT).show();
            }
            String newState1 = Integer.toString(incidentState);
            new UpdateIncidentTableTask().execute(tvSysId.getText().toString(), newState1);

            Intent home=new Intent(mContext, Home.class);
            mContext.startActivity(home);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvIncidentNumber.setText("Incident No : "+mIncidentsList.get(i).getNumber());

        if(mIncidentsList.get(i).getIncidentState() == 1) {
            viewHolder.tvIncidentState.setText("Incident State : " + "NEW");
        } else if(mIncidentsList.get(i).getIncidentState() == 2){
            viewHolder.tvIncidentState.setText("Incident State : " + "ACTIVE");
        }else if(mIncidentsList.get(i).getIncidentState() == 3){
            viewHolder.tvIncidentState.setText("Incident State : " + "AWAITING PROBLEM");
        }else if(mIncidentsList.get(i).getIncidentState() == 4){
            viewHolder.tvIncidentState.setText("Incident State : " + "AWAITING USER INFO");
        }else if(mIncidentsList.get(i).getIncidentState() == 5){
            viewHolder.tvIncidentState.setText("Incident State : " + "AWAITING EVIDENCE");
        }else if(mIncidentsList.get(i).getIncidentState() == 6){
            viewHolder.tvIncidentState.setText("Incident State : " + "RESOLVED");
        }else if(mIncidentsList.get(i).getIncidentState() == 7){
            viewHolder.tvIncidentState.setText("Incident State : " + "CLOSED");
        }


        viewHolder.tvIncidentShortDescription.setText("Description : "+mIncidentsList.get(i).getShortDescription());
        viewHolder.tvIncidentSysUpdatedBy.setText("Created By : "+mIncidentsList.get(i).getSysUpdatedBy());
        viewHolder.tvSysId.setText(mIncidentsList.get(i).getSysId());

        //String stateString = viewHolder.tvIncidentState.getText().toString().trim();
        Integer incidentState = getIncidentValueByName(viewHolder.tvIncidentState.getText().toString().trim());
        Log.d("incidentState22  ", ""+viewHolder.tvIncidentState.getText().toString().trim());
        Log.d("incidentState33  ", ""+incidentState);
        if(incidentState == 1){
        }else if(incidentState > 1 && incidentState < 6){
            viewHolder.btnIncidentActivate.setVisibility(View.GONE);
        }else if(incidentState == 6){
            viewHolder.btnIncidentActivate.setVisibility(View.GONE);
            viewHolder.btnIncidentResolved.setVisibility(View.GONE);
        }else{
            viewHolder.btnIncidentActivate.setVisibility(View.GONE);
            viewHolder.btnIncidentResolved.setVisibility(View.GONE);
            viewHolder.btnIncidentClosed.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mIncidentsList.size();
    }



    public class UpdateIncidentTableTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String response = IncidentService.updateIncidentStatus(mContext, params[0], params[1]);
                return response;
            } catch (Exception e){
                return "false : "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("incident s : ", response);
        }
    }

    public int getIncidentValueByName(String incidentName){


            if(incidentName.contains("NEW")) {
                return 1;
            } else if(incidentName.contains("ACTIVE")) {
                return 2;
            } else if(incidentName.contains("AWAITING PROBLEM")) {
                return 3;
            } else if(incidentName.contains("AWAITING USER INFO")) {
                return 4;
            } else if(incidentName.contains("AWAITING EVIDENCE")) {
                return 5;
            } else if(incidentName.contains("RESOLVED")) {
                return 6;
            } else if(incidentName.contains("CLOSED")){
                return 7;
            }else {
                return 7;
            }

    }
}
