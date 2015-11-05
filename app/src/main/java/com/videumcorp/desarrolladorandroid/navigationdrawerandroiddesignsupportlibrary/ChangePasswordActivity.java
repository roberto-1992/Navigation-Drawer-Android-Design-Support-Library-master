package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangePasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    String emailCompany,phoneCompany,addressCompany;
    public static final String EMPRESA= "empresa";
    TextView nombreHeaderCo, correoHeaderCo, fechaHeaderCo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        String empresa = (String)getIntent().getExtras().get(EMPRESA);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        TypedValue typedValueColorPrimaryDark = new TypedValue();
        ChangePasswordActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
        SQLiteDatabase db = rcycloDatabaseHelper.getWritableDatabase();

        Cursor cursor = db.query("COMPANY", new String[]{"EMAIL", "PHONE", "ADDRESS" }, "NAME = ? AND ACTIVO = ?", new String[]{empresa, "ACTIVO"}, null, null, null);

        if(cursor.moveToFirst()){
            do {
                emailCompany = cursor.getString(0);
                phoneCompany = cursor.getString(1);
                addressCompany = cursor.getString(2);
            }while (cursor.moveToNext()) ;
        }

        nombreHeaderCo = (TextView) findViewById(R.id.nombreHeaderCo);
        correoHeaderCo = (TextView) findViewById(R.id.correoHeaderCo);
        fechaHeaderCo = (TextView) findViewById(R.id.fechaHeaderCo);

        nombreHeaderCo.setText(empresa);
        correoHeaderCo.setText(emailCompany);
        fechaHeaderCo.setText(dateFormat.format(date));

    }

    public void change_password(View view) {
        EditText etPasswordCo1 = (EditText) findViewById(R.id.passwordCo1);
        EditText etPasswordCo2 = (EditText) findViewById(R.id.passwordCo2);
        String passwordCo1 = etPasswordCo1.getText().toString();
        String passwordCo2 = etPasswordCo2.getText().toString();
        if(passwordCo1.matches("")){
            etPasswordCo1.setError("Debe llenar este campo!");
        }

        else if(passwordCo2.matches("")){
            etPasswordCo2.setError("Debe llenar este campo!");
        }

        else if (!passwordCo1.equals(passwordCo2)) {
            etPasswordCo2.setError("las contraseñas son diferentes");
        }

        else {
            String empresa = (String)getIntent().getExtras().get(EMPRESA);
            SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
            SQLiteDatabase db = rcycloDatabaseHelper.getWritableDatabase();
            ContentValues companyValues = new ContentValues();
            companyValues.put("PASSWORD", passwordCo1);

            db.update("COMPANY", companyValues, "NAME = ? ", new String[]{empresa});

            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(SettingsActivity.EMPRESA, empresa);
            startActivity(intent);
            finish();
        }

    }

}