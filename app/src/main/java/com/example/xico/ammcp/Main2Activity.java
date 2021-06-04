package com.example.xico.ammcp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridLayout;
import android.view.View.OnClickListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    public String Data = "No se ha podido obtener la información adecuada, por favor intente de nuevo más tarde";
    TextView nombre, folio, compania, area, subarea, puesto, vigencia, supervisor, temporalidad, altura, confinados, caliente, loto, gases;
    RequestQueue request;
    JsonObjectRequest json;
    ProgressDialog progreso;

    GridLayout mainGrid;

    CardView Card_altura, Card_confinados, Card_caliente, Card_loto, Card_gases, Card_sust_peligrosas, Card_maquinaria_pesada, Card_gruas, Card_plat_elevacion, Card_trab_electricos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nombre = (TextView)findViewById(R.id.txtNombre);
        folio = (TextView)findViewById(R.id.txtFolio);
        compania = (TextView)findViewById(R.id.txtCompania);
        area = (TextView)findViewById(R.id.txtArea);
        subarea = (TextView)findViewById(R.id.txtSubarea);
        puesto = (TextView)findViewById(R.id.txtPuesto);
        supervisor = (TextView)findViewById(R.id.txtSupervisor);
        temporalidad = (TextView)findViewById(R.id.txtTemporalidad);
        vigencia = (TextView)findViewById(R.id.txtIMSS);
        altura = (TextView)findViewById(R.id.txtAltura);
        confinados = (TextView)findViewById(R.id.txtConfinados);
        caliente = (TextView)findViewById(R.id.txtCaliente);
        loto = (TextView)findViewById(R.id.txtLoto);
        gases = (TextView)findViewById(R.id.txtGases);
        Bundle extras = getIntent().getExtras();
        String folio = extras.getString("folio");
        request = Volley.newRequestQueue(this);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Consultando...");
        progreso.show();
        String url="http://192.168.0.105/tecate/trabajador.php?folio="+folio;
        json = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(json);
        setupActionBar();

        //GRIDVIEW EVENTO
        mainGrid = (GridLayout)findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
        //CARDS
        Card_altura = (CardView)findViewById(R.id.gridView_trabajo_alturas);
        Card_confinados = (CardView)findViewById(R.id.gridView_espacios_confinados);
        Card_caliente = (CardView)findViewById(R.id.gridView_trabajo_caliente);
        Card_loto = (CardView)findViewById(R.id.gridView_loto);
        Card_gases = (CardView)findViewById(R.id.gridView_gases_cilindros);
        //otras cards
        Card_sust_peligrosas = (CardView)findViewById(R.id.gridView_manejo_sustancias);
        Card_maquinaria_pesada = (CardView)findViewById(R.id.gridView_maquinaria_pesada);
        Card_gruas = (CardView)findViewById(R.id.gridView_operacion_gruas);
        Card_plat_elevacion = (CardView)findViewById(R.id.gridView_plataformas_elevacion);
        Card_trab_electricos = (CardView)findViewById(R.id.gridView_trabajos_electricos);

        Card_altura.setVisibility(View.GONE);
        Card_confinados.setVisibility(View.GONE);
        Card_caliente.setVisibility(View.GONE);
        Card_loto.setVisibility(View.GONE);
        Card_gases.setVisibility(View.GONE);

        //otras cards que aun no hemos puesto en la consulta
        Card_sust_peligrosas.setVisibility(View.GONE);
        Card_maquinaria_pesada.setVisibility(View.GONE);
        Card_gruas.setVisibility(View.GONE);
        Card_plat_elevacion.setVisibility(View.INVISIBLE);
        Card_trab_electricos.setVisibility(View.GONE);
    }

    private void setupActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Atrás");

        }
    }

    @Override
    public void onBackPressed()
    {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this,"No se pudo consultar"+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("Error", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Trabajador traba = new Trabajador();
        JSONArray jarray = response.optJSONArray("trabajador");
        JSONObject jsonObject = null;
        try
        {
            jsonObject=jarray.getJSONObject(0);
            traba.setFolio(jsonObject.getString("folio"));
            traba.setNombre(jsonObject.getString("nombre"));
            traba.setCompania(jsonObject.getString("compania"));
            traba.setArea(jsonObject.getString("area"));
            traba.setSubarea(jsonObject.getString("subarea"));
            traba.setPuesto(jsonObject.getString("puesto"));
            traba.setSupervisor(jsonObject.getString("supervisor"));
            traba.setTemporalidad(jsonObject.getString("temporalidad"));
            traba.setImss(jsonObject.getString("imss"));
            traba.setAlturas(jsonObject.getString("alturas"));
            traba.setConfinados(jsonObject.getString("confinados"));
            traba.setCaliente(jsonObject.getString("caliente"));
            traba.setLoto(jsonObject.getString("loto"));
            traba.setGases(jsonObject.getString("gases"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        folio.setText(traba.getFolio());
        nombre.setText(traba.getNombre());
        compania.setText(traba.getCompania());
        area.setText(traba.getArea());
        subarea.setText(traba.getSubarea());
        puesto.setText(traba.getPuesto());
        supervisor.setText(traba.getSupervisor());
        temporalidad.setText(traba.getTemporalidad());
        vigencia.setText(traba.getImss());

        altura.setText(traba.getAlturas());
        confinados.setText(traba.getConfinados());
        caliente.setText(traba.getCaliente());
        loto.setText(traba.getLoto());
        gases.setText(traba.getGases());

        //PONER VISIBLES LAS CARDS DE LAS VIGENCIAS CORRESPONDIENTES (LOS ICONOS)
        if(altura.getText()=="" || altura.getText()=="TextView" || confinados == null){
            Card_altura.setVisibility(View.VISIBLE);
        }else{
        }

        if(confinados.getText()=="" || confinados.getText()=="TextView" || confinados == null){
        }else{
            Card_confinados.setVisibility(View.VISIBLE);
        }


        if(caliente.getText()=="" || caliente.getText()=="TextView" || confinados == null){
        }else{
            Card_caliente.setVisibility(View.VISIBLE);
        }

        if(loto.getText()=="" || loto.getText()=="TextView" || confinados == null){
        }else{
            Card_loto.setVisibility(View.VISIBLE);
        }

        if(gases.getText()=="" || gases.getText()=="TextView" || confinados == null){
        }else{
            Card_gases.setVisibility(View.VISIBLE);
        }
    }

    //EVENTO ONCLICK DE CADA CARD
    public void setSingleEvent(GridLayout mainGrid) {
        for(int i=0;i<mainGrid.getChildCount();i++){
            CardView cardView = (CardView)mainGrid.getChildAt(i);

            if (i == 0){

            }
            if (i == 1){

            }
            if (i == 2){

            }
            if (i == 3){
                Data = loto.getText().toString();
            }
            if (i == 4){
                Data = gases.getText().toString();
            }
            if (i == 5){
                Data = confinados.getText().toString();
            }
            if (i == 6){
                Data = altura.getText().toString();
            }
            if (i == 7){
                Data = caliente.getText().toString();
            }
            if (i == 8){

            }
            if (i == 9){

            }
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Main2Activity.this);
                        a_builder.setMessage(Data.toString())
                                .setCancelable(false)
                                .setNegativeButton("Cerrar",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Vigencia");
                        alert.show();
                }
            });

        }
    }
}
