package com.expedia.servicenow.servicenow1;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                    mIncidentsList.get(i).getSysUpdatedBy());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvIncidentNumber;
        public TextView tvIncidentState;
        public TextView tvIncidentShortDescription;
        public TextView tvIncidentSysUpdatedBy;

        public ViewHolder(View itemView) {
            super(itemView);
            tvIncidentNumber = (TextView)itemView.findViewById(R.id.tvIncidentNumber);
            tvIncidentState = (TextView)itemView.findViewById(R.id.tvIncidentState);
            tvIncidentShortDescription = (TextView)itemView.findViewById(R.id.tvIncidentShortDescription);
            tvIncidentSysUpdatedBy = (TextView)itemView.findViewById(R.id.tvIncidentSysUpdatedBy);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

//                    Snackbar.make(v, "Click detected on item " + position,
//                            Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();


                    Toast.makeText(mContext, tvIncidentNumber.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            });
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
        viewHolder.tvIncidentState.setText("Incident State : "+mIncidentsList.get(i).getIncidentState()+"");
        viewHolder.tvIncidentShortDescription.setText("Description : "+mIncidentsList.get(i).getShortDescription());
        viewHolder.tvIncidentSysUpdatedBy.setText("Created By : "+mIncidentsList.get(i).getSysUpdatedBy());
    }

    @Override
    public int getItemCount() {
        return mIncidentsList.size();
    }
}
