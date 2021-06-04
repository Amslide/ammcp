package com.example.xico.ammcp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {

    EditText codigo;
    public Button escaner;
    public static ZXingScannerView vista = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        escaner = (Button)findViewById(R.id.btnCodigo);
        vista = new ZXingScannerView(this);
        escaner.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                vista.setResultHandler(new zxingscanner());
                setContentView(vista);
                vista.startCamera();
            }
        });

    }

    /*
    public void Escaner()
    {
        vista = new ZXingScannerView(this);
        vista.setResultHandler(new zxingscanner());
        setContentView(vista);
        vista.startCamera();
    }*/
    class zxingscanner implements ZXingScannerView.ResultHandler
    {
        @Override
        public void handleResult(Result result) {
            String dato = result.getText();
            setContentView(R.layout.activity_main);
            vista.stopCamera();
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            i.putExtra("folio",dato);
            startActivity(i);
            /*
            codigo = (EditText) findViewById(R.id.txtCodigo);
            codigo.setText(dato);*/
        }
    }

}
