package com.example.coutryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coutryinfo.WebService.Asynchtask;
import com.example.coutryinfo.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> datos_webservice = new HashMap<>();
        String URL = "http://www.geognos.com/api/en/countries/info/all.json";
        WebService web_service = new WebService(URL, datos_webservice, MainActivity.this, MainActivity.this);
        web_service.execute("GET");

    }

    String codigo_pais;

    public void bnt_enviar_pais(View view) {
        Spinner sp_pais = findViewById(R.id.sp_pais);
//        System.out.println(sp_pais.getSelectedItemId());
        Intent intent = new Intent(MainActivity.this, info.class);
        Bundle bundle = new Bundle();
        bundle.putString("codigo_pais", list_paises_codigo.get(sp_pais.getSelectedItemPosition()));

        intent.putExtras(bundle);

        startActivity(intent);
    }

    ArrayList<String> list_paises = new ArrayList<>();
    ArrayList<String> list_paises_codigo = new ArrayList<>();

    @Override
    public void processFinish(String result) throws JSONException {

        try {
            JSONObject object_result_web = new JSONObject(result);
            JSONObject object_pais = object_result_web.getJSONObject("Results");
            Iterator x = object_pais.keys();
            JSONArray jsonArray = new JSONArray();

            while (x.hasNext()){
                String key = (String) x.next();
                jsonArray.put(object_pais.get(key));
            }

            list_paises.add("Seleccione un país");
            list_paises_codigo.add("");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject pais = jsonArray.getJSONObject(i);
                list_paises.add(pais.getString("Name"));
                JSONObject codigo_pais = pais.getJSONObject("CountryCodes");
                list_paises_codigo.add(codigo_pais.getString("iso2"));
            }

            Spinner sp = findViewById(R.id.sp_pais);
            ArrayAdapter<CharSequence> adapter;
            adapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, list_paises);
            sp.setAdapter(adapter);

        }catch (Exception e){
            Toast.makeText(this, "Los datos de la web service no se han cargado", Toast.LENGTH_LONG).show();
        }
    }
}