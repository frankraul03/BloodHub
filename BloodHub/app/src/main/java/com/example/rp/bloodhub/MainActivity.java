package com.example.rp.bloodhub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button login_button;
    EditText user_name, pass_word;
    String username, password;
    String login_url = "http://192.168.0.106/bloodhub/login.php";
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.new_reg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
//                Bundle bundle = new Bundle();
//                intent.putExtras(bundle);
//                startActivity(intent);
//
//                OR

                startActivity(new Intent(MainActivity.this,RegisterActivity.class));

            }
        });
        builder = new AlertDialog.Builder(MainActivity.this);
        login_button = (Button)findViewById(R.id.login_user);
        user_name = (EditText) findViewById(R.id.user_name);
        pass_word = (EditText) findViewById(R.id.login_password);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user_name.getText().toString();
                password = pass_word.getText().toString();

                if(username.equals("")||password.equals(""))
                {
                    builder.setTitle("Something went wrong");
                    displayAlert("You cannot leave these field empty");
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                if(code.equals("login_failed"))
                                {
                                    builder.setTitle("Login error");
                                    displayAlert(jsonObject.getString("message"));

                                }
                                else
                                {
                                    Intent intent = new Intent(MainActivity.this,LoginSuccessful.class);
                                    Bundle bundle = new Bundle();
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                //  startActivity(new Intent(MainActivity.this,LoginSuccessful.class));
                                }

                            } catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })

                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("user_name",username);
                            params.put("password",password);
                            return params;
                        }
                    };
                    MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);
                }
            }
        });
    }

    public void displayAlert(String message){

        builder.setMessage(message);
        builder.setPositiveButton("OK",     new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user_name.setText("");
                pass_word.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }







}
