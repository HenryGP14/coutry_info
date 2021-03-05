package com.example.coutryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coutryinfo.WebService.Asynchtask;
import com.example.coutryinfo.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String codigo_pais;

    public void bnt_enviar_pais(View view) {
        Intent intent = new Intent(MainActivity.this, info.class);
        Bundle bundle = new Bundle();
        bundle.putString("codigo_pais", codigo_pais);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void btn_buscar_pais(View view){
        Map<String, String> datos_webservice = new HashMap<>();
        String URL = "http://www.geognos.com/api/en/countries/info/all.json";
        WebService web_service = new WebService(URL, datos_webservice, MainActivity.this, MainActivity.this);
        web_service.execute("GET");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView respuesta = findViewById(R.id.txt_resultados);
        ArrayList<String> list_paises = new ArrayList<>();
        ArrayList<String> list_paises_codigo = new ArrayList<>();

        try {
            JSONObject objet_result_web = new JSONObject(result);
            System.out.println(objet_result_web.getJSONArray("Results"));

            JSONArray json_array = new JSONArray(objet_result_web.getJSONArray("Results"));

            for (int i = 0; i < json_array.length(); i++)
            {
                JSONObject pais = json_array.getJSONObject(i);
            }
        }catch (Exception e){
            Toast.makeText(this, "Los datos de la web service no se han cargado", Toast.LENGTH_LONG).show();
        }
    }
}