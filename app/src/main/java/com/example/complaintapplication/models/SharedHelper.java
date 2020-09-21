package com.example.complaintapplication.models;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedHelper(Context context) {
        preferences = context.getSharedPreferences("ComplaintApp", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public SharedHelper() {
    }

    public void saveUserdetails(String uid,String ufullname,String umobile_no,String upassword,String ugender,String ucity,String uaddress,String uaadhar_card,String uemail,String udob,String ustatus,Boolean ulogged){
        Integer idd = Integer.parseInt(uid);
        editor.putInt("id", idd);
        editor.putString("fullname", ufullname);
        editor.putString("mobile_no", umobile_no);
        editor.putString("password", upassword);
        editor.putString("gender", ugender);
        editor.putString("city", ucity);
        editor.putString("address", uaddress);
        editor.putString("aadhar_card", uaadhar_card);
        editor.putString("email", uemail);
        editor.putString("dob", udob);
        editor.putString("status", ustatus);
        editor.putBoolean("isLogged", ulogged);
        editor.commit();
    }
    public Integer getuserId(){
        Integer userid = preferences.getInt("id", 0);
        return userid;
    }
    public Boolean getLoggedIn(){
        Boolean loggedin = preferences.getBoolean("logged", true);
        return loggedin;
    }
    public String getFullName(){
        String fullName = preferences.getString("fullname", "123@gmail.com");
        return fullName;
    }
    public String getMobNo(){
        String mobile_no = preferences.getString("mobile_no", "12345");
        return mobile_no;
    }
    public String getPassword(){
        String password = preferences.getString("password", "John Wick");
        return password;
    }
    public String getGender(){
        String gender = preferences.getString("gender", "9090909090");
        return gender;
    }
    public String getCity(){
        String city = preferences.getString("city", "Nagpur");
        return city;
    }
    public String getAddress(){
        String address = preferences.getString("address", "1234");
        return address;
    }

    public String getAadharCard(){
        String aadhar_card = preferences.getString("aadhar_card", "");
        return aadhar_card;
    }
    public String getEmail(){
        String email = preferences.getString("email", "");
        return email;
    }
    public String getDob(){
        String dob = preferences.getString("dob", "");
        return dob;
    }
    public String getStatus(){
        String status = preferences.getString("status", "");
        return status;
    }

}
