package com.example.rp.bloodhub;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button user_reg;
    EditText ET_EMAIL, ET_NAME, ET_USERNAME, ET_PASS,ET_CONPASS;
    String name, user_name, user_pass, email, con_pass;
    AlertDialog.Builder builder;
    String reg_url = "http://192.168.0.106/bloodhub/register.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        user_reg = (Button) findViewById(R.id.reg_user);
        ET_NAME = (EditText) findViewById(R.id.name);
        ET_EMAIL = (EditText) findViewById(R.id.email);
        ET_USERNAME = (EditText) findViewById(R.id.user_name);
        ET_PASS = (EditText) findViewById(R.id.put_password);
        ET_CONPASS = (EditText) findViewById(R.id.con_password);
        builder = new AlertDialog.Builder(RegisterActivity.this);



        user_reg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                name = ET_NAME.getText().toString();
                email = ET_EMAIL.getText().toString();
                user_name = ET_USERNAME.getText().toString();
                user_pass = ET_PASS.getText().toString();
                con_pass = ET_CONPASS.getText().toString();
                if(name.equals("")||email.equals("")||user_name.equals("")||user_pass.equals("")||con_pass.equals(""))
                {
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Please fill all the fields");
                    displayAlert("Input Error");
                }
                else
                {
                    if(!(user_pass.equals(con_pass)))
                    {
                        builder.setTitle("Something went wrong");
                        builder.setMessage("Password didn't match");
                        displayAlert("Input Error");
                    }
                    else
                    {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            String message = jsonObject.getString("message");
                                            builder.setTitle("Server Response");
                                            builder.setMessage(message);
                                            displayAlert(code);
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<>();
                                params.put("name",name);
                                params.put("email",email);
                                params.put("user_name",user_name);
                                params.put("password",user_pass);
                                params.put("con_password",con_pass);
                                return params;
                            }
                        };
                        MySingleton.getInstance(RegisterActivity.this).addToRequestque(stringRequest);
                    }
                }


            }
        });
    }

    public void displayAlert(final String code)
    {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (code) {
                    case "Input Error":
                        ET_PASS.setText("");
                        ET_CONPASS.setText("");
                        break;
                    case "reg_success":
                        finish();
                        break;
                    case "reg_failed":
                        ET_NAME.setText("");
                        ET_EMAIL.setText("");
                        ET_USERNAME.setText("");
                        ET_PASS.setText("");
                        ET_CONPASS.setText("");
                        break;
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
