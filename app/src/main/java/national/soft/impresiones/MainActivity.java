package national.soft.impresiones;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.singhajit.sherlock.core.Sherlock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.List;

import national.soft.impresiones.util.AidlUtil;
import national.soft.impresiones.util.BaseApp;
import national.soft.impresiones.util.GeneraLogs;
import national.soft.impresiones.util.Permisos;
import national.soft.impresiones.util.PrinterCallback;

public class MainActivity extends AppCompatActivity implements ILog {
        public static String cadena = "";
        public static String salto="\n";
        public TextView textView;
        public String ticketdemo="DQobIQggICAgICAgIERFTU8NChtADSAgICAgICAgREVNTw0KICAgUkZDOkEwMDAwMDAwMA0KQVZFTklEQSBDQU1BUkEgREUgQ09NRVJDSU8gTUVSSUQNCkEgWVVDQVRBTiBNRVhJQ08gIENQICA5NzEzMw0KU1VDVVJTQUw6REVNTyA5LjUgTUVSSURBIFlVQ0FUQU4NCj09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09DQpNRVNBOjI1ICAgICAgICBNRVNFUk86TEFVUkEgRURJVEggRElBWiBBUk1BRElMTE8NCg0KGyEIICAgICBGT0xJTzoxNzg2DQobQA0xNC8wMi8yMDIwIDA1OjIxOjI1IFBNDQpQRVJTT05BUzo0ICAgICAgICAgICAgICAgT1JERU46MTAwDQo9PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PQ0KDQpDQU5UIERFU0NSSVBDSU9OICAgICAgICAgSU1QT1JURQ0KMSAgICBNQVJBVE9OIFlPRyAgICAgICAkNTkuMDANCjEgICAgRU5TQUxBREEgRlJVICAgICAgJDY5LjAwDQoxICAgIE9SREVOIEZSVVRBUyAgICAgICQ1Mi4wMA0KICAgICBTQU5ESUEgICAgICAgICAgICAkMC4wMA0KDQpTVUJUT1RBTDogICAgICAkMTU1LjE3DQpJVkE6ICAgICAgICAgICAgJDI0LjgzDQobIRBUT1RBTDogICAgICAgICAkMTgwLjAwDQoNChtADVNPTjpDSUVOVE8gT0NIRU5UQSBQRVNPUyAwMC8xMDANCk0uTi4NChshCEdSQUNJQVMgUE9SIFNVIFBSRUZFUkVOQ0lBDQobQA0NCioqKlNPRlQgUkVTVEFVUkFOVCBWOS41IFBSTyoqKg0KDQoNCg==";
        public AidlUtil aidl;
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
        aidl = AidlUtil.getInstance();
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AidlUtil.getInstance().connectPrinterService(MainActivity.this);
                }catch (Exception e){
                    GeneraLogs.commitToFile("Error",e.getMessage());
                    e.printStackTrace();
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

                byte[]data= Base64.decode(ticketdemo,Base64.DEFAULT);
                String dato = new String(data, StandardCharsets.UTF_8);
                String tiket = dato.replace("@","").replace("!","");
                    AidlUtil.getInstance().initPrinter();
                    AidlUtil.getInstance().printText(tiket,20f,false,false);
                    AidlUtil.getInstance().print3Line();
                //final String finalResult = result;
                List<String>info = AidlUtil.getInstance().getPrinterInfo(new PrinterCallback() {
                    
                    @Override
                    public String getResult() {
                        return null;
                    }

                    @Override
                    public void onReturnString(String result) {

                    }
                });
                if (info!=null){
                 //   Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                }
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
