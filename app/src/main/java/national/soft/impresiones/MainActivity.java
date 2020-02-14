package national.soft.impresiones;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.singhajit.sherlock.core.Sherlock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import national.soft.impresiones.util.AidlUtil;
import national.soft.impresiones.util.BaseApp;
import national.soft.impresiones.util.GeneraLogs;
import national.soft.impresiones.util.Permisos;

public class MainActivity extends AppCompatActivity implements ILog {
        public static String cadena = "";
        public static String salto="\n";
        public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.textlog);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton disconect = findViewById(R.id.disconect);
        FloatingActionButton print = findViewById(R.id.print);
      //  Sherlock.init(MainActivity.this);
        Permisos.solicitarPermisos(MainActivity.this,Permisos.permisos());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AidlUtil.getInstance().connectPrinterService(MainActivity.this);
                if (AidlUtil.getInstance().isConnect()){
                    escribirlog("conectado al servicio de impresion");
                    GeneraLogs.commitToFile("Logs","contectado al servicio");
                }else{
                    escribirlog("sin conexion, la instancia no se inicializo.");

                }
            }
        });

        disconect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AidlUtil.getInstance().isConnect()){
                    AidlUtil.getInstance().disconnectPrinterService(MainActivity.this);
                    escribirlog("se desconecto servicio de impresión");
                }else{
                    escribirlog("la impresora no se encuentra conectada, conecte primero para desconectar servicio.");
                }
            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    GeneraLogs.commitToFile("Conectado","servicio activo");
                    AidlUtil.getInstance().initPrinter();
                    AidlUtil.getInstance().printText("Prueba de impresión",20f,false,false);
            }
        });
//        AidlUtil.getInstance().connectPrinterService(MainActivity.this);
//        if (AidlUtil.getInstance().isConnect()){
//            escribirlog("ya estaba conectado");
//        }else{
//            escribirlog("no existia la instancia");
//            AidlUtil.getInstance().connectPrinterService(MainActivity.this);
//            if (AidlUtil.getInstance().isConnect()){
//                escribirlog("se conecto a la primera llamada");
//            }else{
//                escribirlog("no se conecto a la primera");
//                AidlUtil.getInstance().connectPrinterService(MainActivity.this);
//                if (AidlUtil.getInstance().isConnect()){
//                    escribirlog("se conecto a la segunda llamada");
//                }else{
//                    escribirlog("no se conecto a la segunda");
//                    AidlUtil.getInstance().connectPrinterService(MainActivity.this);
//                    if (AidlUtil.getInstance().isConnect()){
//                        escribirlog("se conecto a la tercera llamada");
//                    }else{
//                        escribirlog("no se conecto a la tercera");
//                        AidlUtil.getInstance().connectPrinterService(MainActivity.this);
//                        if (AidlUtil.getInstance().isConnect()){
//                            escribirlog("se conecto a la cuarta llamada");
//                        }else{
//                            escribirlog("no se conecto a la cuarta");
//                        }
//                    }
//                }
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void escribirlog(String text) {
        cadena = cadena+text+salto;
        textView.setText(cadena);
    }
}
