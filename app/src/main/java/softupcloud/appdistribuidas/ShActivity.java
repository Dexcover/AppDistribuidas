package softupcloud.appdistribuidas;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import softupcloud.appdistribuidas.clases.Casa;

/**
 * Created by hp on 27/12/2017.
 *
 * This is about the second one of the list.
 *
 */

public class ShActivity extends AppCompatActivity {
    SoapPrimitive cadenaResultado;
    private Button btUpload;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh);

        btUpload = (Button) findViewById(R.id.upload);

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Casa cs= new Casa();

                cs.setNombrecasa("Nombre Casa Prueba01");
                cs.setDirectorcasa("Direccion Prueba01");
                cs.setTelcasa("022634384");
                cs.setCorreocasa("prueba01@prueba01.com");
                cs.setDirectorcasa("ING. Prueba01");
                cs.setCortocasa("Corto Prueba01");

                AsynCallWs tarea= new AsynCallWs();
                tarea.execute();
            }
        });

    }



    private class AsynCallWs extends AsyncTask<Void, Void, Void> {

        private String nDatoA="";



        protected void onPreExecute(){
            System.out.println("Pre execute -- ");

        }

        @Override
        protected Void doInBackground(Void... hola) {
            Casa cs= new Casa();
            cs.setNombrecasa("Nombre Casa Prueba01");
            cs.setDirectorcasa("Direccion Prueba01");
            cs.setTelcasa("022634384");
            cs.setCorreocasa("prueba01@prueba01.com");
            cs.setDirectorcasa("ING. Prueba01");
            cs.setCortocasa("Corto Prueba01");

            enviarDatos(cs,1);
            return null;
        }


        protected void onPostExecute(Void resultado){

            AlertDialog.Builder mensajeDialogo = new AlertDialog.Builder(ShActivity.this);
            mensajeDialogo.setTitle("Servidor: ");
            mensajeDialogo.setMessage("recibido")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog= mensajeDialogo.create();
            alertDialog.show();
        }

    }


    public void enviarDatos(Casa cs, Integer usuarioSession){

        String SOAP_ACTION="http://prueba";
        String METHOD_NAME="insertar";
        String NAMESPACE="http://prueba";
        String URL="http://192.168.1.22:8086/Prueba/services/Saludar.SaludarHttpSoap11Endpoint/";


        try{
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

            Request.addProperty("datosc",cs);
            Request.addProperty("usuarioSession",usuarioSession);
            SoapSerializationEnvelope soapEnvelop= new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet=true;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE trasporte = new HttpTransportSE(URL);
            trasporte.call(SOAP_ACTION,soapEnvelop);
            //cadenaResultado = (SoapPrimitive) soapEnvelop.getResponse();


        }catch(Exception e){

            Log.i("","EXCEPTION: -------------- ******************* "+ e.getMessage());

        }

    }

}
