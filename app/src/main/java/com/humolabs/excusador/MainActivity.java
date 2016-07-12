package com.humolabs.excusador;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    KenBurnsView kenBurnsView;

    Spinner spinnerCabecera;
    Spinner spinnerCuando;
    Spinner spinnerExcusa;
    ArrayAdapter<CharSequence> adapCabecera;
    ArrayAdapter<CharSequence> adapCuando;
    ArrayAdapter<CharSequence> adapExcusa;

    Button btnEnviar;
    Button btnRandom;
    Button btnExit;
    String strExcusa;
    String strTitulo;
    String strMensaje;
    String strPreview;
    TypeWriter writer;
    Random rnd;
    TextView txtTitulo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        strTitulo = getString(R.string.app_name);

        txtTitulo = (TextView)findViewById(R.id.txtTitulo);
        Typeface ttf_titulo = Typeface.createFromAsset(getAssets(), "fonts/Grinched.ttf");
        txtTitulo.setTypeface(ttf_titulo);

        //Escribe un caracter cada 50ms
        writer = (TypeWriter) findViewById(R.id.txtPreview);
        writer.setCharacterDelay(50);
        Typeface ttf_preview = Typeface.createFromAsset(getAssets(), "fonts/MyUnderwood.ttf");
        writer.setTypeface(ttf_preview);


        //fadein
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relLayoutMain);
        AlphaAnimation animation = new AlphaAnimation(0.0f , 1.0f ) ;
        animation.setFillAfter(true);
        animation.setDuration(3000);
        layout.startAnimation(animation);



        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set a custom tint color for all system bars
        tintManager.setTintColor(Color.parseColor("#CC000000"));
        // set a custom navigation bar resource
        //tintManager.setNavigationBarTintResource(R.drawable.my_tint);
        // set a custom status bar drawable
        //tintManager.setStatusBarTintDrawable(MyDrawable);


        kenBurnsView = (KenBurnsView) findViewById(R.id.kbvSplash);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kenBurnsView.setTransitionGenerator(generator);

        spinnerCabecera = (Spinner) findViewById(R.id.spnCabecera);
        adapCabecera = ArrayAdapter.createFromResource(this, R.array.strCabecera, R.layout.custom_spinner);
        spinnerCabecera.setAdapter(adapCabecera);
        spinnerCabecera.setSelection(0);

        spinnerCabecera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  strPreview = armoExcusa();
                  writer.animateText(strPreview);
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });

        spinnerCuando = (Spinner) findViewById(R.id.spnCuando);
        adapCuando = ArrayAdapter.createFromResource(this, R.array.strCuando, R.layout.custom_spinner);
        spinnerCuando.setAdapter(adapCuando);
        spinnerCuando.setSelection(0);

        spinnerCuando.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strPreview = armoExcusa();
                writer.animateText(strPreview);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerExcusa = (Spinner) findViewById(R.id.spnExcusa);
        adapExcusa = ArrayAdapter.createFromResource(this, R.array.strExcusa, R.layout.custom_spinner);
        spinnerExcusa.setAdapter(adapExcusa);
        spinnerExcusa.setSelection(0);

        spinnerExcusa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strPreview = armoExcusa();
                writer.animateText(strPreview);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt(strTitulo, strExcusa);
            }
        });


        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnExit.setTextColor(getResources().getColor(R.color.colorAccent));
                strMensaje = getString(R.string.MensajeSalir);
                alertDialog(strTitulo,strMensaje);
            }
        });



        btnRandom = (Button) findViewById(R.id.btnRandom);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPreview = armoExcusaRandom();
                writer.animateText(strPreview);
            }
        });

    } //fin OnCreate


    public void alertDialog(String titulo, final String mensaje){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(titulo);
        alertDialogBuilder.setMessage(mensaje);

        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.salir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancelar),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public String armoExcusa(){

        strExcusa = spinnerCabecera.getSelectedItem().toString() + " " +
                spinnerCuando.getSelectedItem().toString() + " " +
                spinnerExcusa.getSelectedItem().toString();

        return strExcusa;
    }

    public String armoExcusaRandom(){
        int i;
        rnd = new Random();
        i = rnd.nextInt(spinnerCabecera.getAdapter().getCount());
        spinnerCabecera.setSelection(i);
        i = rnd.nextInt(spinnerCuando.getAdapter().getCount());
        spinnerCuando.setSelection(i);
        i = rnd.nextInt(spinnerExcusa.getAdapter().getCount());
        spinnerExcusa.setSelection(i);

        strExcusa = spinnerCabecera.getSelectedItem().toString() + " " +
                spinnerCuando.getSelectedItem().toString() + " " +
                spinnerExcusa.getSelectedItem().toString();

        return strExcusa;
    }

    public void shareIt(String titulo, String mensaje){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, titulo);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
        startActivity(Intent.createChooser(sharingIntent, "Enviar"));
    }

}
