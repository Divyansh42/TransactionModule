package com.divyanshu.transactionmodule.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.divyanshu.transactionmodule.R;
import com.divyanshu.transactionmodule.Models.SMSData;

import java.util.List;

public class ListAdapter extends ArrayAdapter<SMSData> {

    private final Context context;
    private final List<SMSData> smsList;

    public ListAdapter(Context context, List<SMSData> smsList) {
        super(context, R.layout.activity_main, smsList);
        this.context = context;
        this.smsList = smsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_main, parent, false);


        TextView transactionAmount = (TextView) rowView.findViewById(R.id.tv_transactien_amount);
        transactionAmount.setText(String.valueOf(smsList.get(position).getAmount()));

        TextView transactiontype = (TextView) rowView.findViewById(R.id.tv_transaction_type);
        if(smsList.get(position).getTransactionType().equalsIgnoreCase("0")) {
            transactiontype.setText("Debited");
            transactiontype.setTextColor(Color.parseColor("#ff0000"));
        }
        else if(smsList.get(position).getTransactionType().equalsIgnoreCase("1")) {

            transactiontype.setText("Credited");
            transactiontype.setTextColor(Color.parseColor("#006400"));
        }
        TextView transactionDateTv  = (TextView) rowView.findViewById(R.id.tv_transactien_date);
        transactionDateTv.setText(String.format("Date: %s", smsList.get(position).getTransactionDate()));

        return rowView;
    }
}
