package com.divyanshu.transactionmodule.Utils;

import android.util.Log;

import com.divyanshu.transactionmodule.Models.SMSData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parsers {

    public static String parseAmount(SMSData smsData){
        Pattern regEx
                = Pattern.compile("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");
        // Find instance of pattern matches
        Matcher m = regEx.matcher(smsData.getBody());
        if (m.find()) {
            try {
                Log.e("amount_value= ", "" + m.group(0));
                String amount = (m.group(0).replaceAll("inr", ""));
                amount = amount.replaceAll("rs", "");
                amount = amount.replaceAll("inr", "");
                //amount = amount.replaceAll(" ", "");
                amount = amount.replaceAll(",", "");
                Log.e("matchedValue= ", "" + amount);
                return amount;


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("No_matchedValue ", "No_matchedValue ");
        }
        return null;

    }

    public static String parseTransactionType(SMSData smsData){

        String type = null;
        if (smsData.getBody().contains("debited") ||
                smsData.getBody().contains("purchasing") || smsData.getBody().contains("purchase") || smsData.getBody().contains("dr")) {
            type = "0";
        } else if (smsData.getBody().contains("credited") || smsData.getBody().contains("cr")) {
            type = "1";
        }

        return type;

    }

    public static String parseTransactionDate(SMSData smsData){
        Pattern regEx
                = Pattern.compile("((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)"
                + "|(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))"
                + "|(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))"
                + "|(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))");
        // Find instance of pattern matches
        //String sms = "Dear SBI UPI User, your account is debited INR 152.0 on Date 2019-06-22 11:23:41 AM by UPI Ref No 917335047788.Download YONO @ www.yonosbi.com";
        Matcher m = regEx.matcher(smsData.getBody());
        if (m.find()) {
            try {
                Log.e("transaction_date= ", "" + m.group(0));
                String transactionDate = (m.group(0).replaceAll("at", ""));
                // merchantName = merchantName.replaceAll("in", "");
                //merchantName = merchantName.replaceAll("at", "");
                //amount = amount.replaceAll(" ", "");
                //amount = amount.replaceAll(",", "");
                Log.e("matchedValue= ", "" + transactionDate);
                return transactionDate;


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("No_matchedValue ", "No_matchedValue ");
        }
        return null;

    }
}
