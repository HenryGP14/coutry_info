package com.example.coutryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coutryinfo.WebService.Asynchtask;
import com.example.coutryinfo.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class info extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Bundle bundle = this.getIntent().getExtras();

        Map<String, String> datos_webservice = new HashMap<>();
        String URL = "http://www.geognos.com/api/en/countries/info/"+ bundle.getString("codigo_pais") +".json";
        WebService web_service = new WebService(URL, datos_webservice, info.this, info.this);
        web_service.execute("GET");

    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView texto = findViewById(R.id.txt_r_pais);
        try {
            JSONObject object_result_web = new JSONObject(result);
            JSONObject object_pais = object_result_web.getJSONObject("Results");
            Iterator x = object_pais.keys();
            JSONArray jsonArray = new JSONArray();

            texto.setText(object_pais.getString("Name"));

        }catch (Exception e){
            Toast.makeText(this, "Los datos de la web service no se han cargado", Toast.LENGTH_LONG).show();
        }
    }
}