package softupcloud.appdistribuidas;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import softupcloud.appdistribuidas.variables.Variables;


/**
 * Created by hp on 27/12/2017.
 *
 * This is about the second one of the list.
 *
 */

public class ShActivity extends AppCompatActivity implements Variables {
    private String SOAP_ACTION="http://serviciosImplementados";
    private String METHOD_NAME="insertar";
    private String NAMESPACE="http://serviciosImplementados";
    private String URL="http://"+IP_SERVER+"/WebService/services/servicioCasa.servicioCasaHttpSoap11Endpoint/";

    SoapPrimitive cadenaResultado;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh);

        Button btUpload = (Button) findViewById(R.id.upload);

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText dato1= (EditText) findViewById(R.id.editText2);
                EditText dato2= (EditText) findViewById(R.id.editText3);
                EditText dato3= (EditText) findViewById(R.id.editText4);
                EditText dato4= (EditText) findViewById(R.id.editText5);
                EditText dato5= (EditText) findViewById(R.id.editText6);
                EditText dato6= (EditText) findViewById(R.id.editText7);

                String nombreCasa=dato1.getText().toString();
                String addCasa=dato2.getText().toString();
                String telfCasa=dato3.getText().toString();
                String correoCasa=dato4.getText().toString();
                String dirCasa=dato5.getText().toString();
                String ncortoCasa=dato6.getText().toString();


                AsynCallWs tarea= new AsynCallWs(nombreCasa, addCasa, telfCasa, correoCasa, dirCasa, ncortoCasa);
                tarea.execute();
            }
        });

    }



    private class AsynCallWs extends AsyncTask<Void, Void, Void> {
        private String nombreCasa;
        private String addCasa;
        private String telfCasa;
        private String correoCasa;
        private String dirCasa;
        private String ncortoCasa;

        public AsynCallWs(String nombreCasa,String addCasa,String telfCasa,String correoCasa, String dirCasa,String ncortoCasa){
            this.nombreCasa=nombreCasa;
            this.addCasa=addCasa;
            this.telfCasa=telfCasa;
            this.correoCasa=correoCasa;
            this.dirCasa=dirCasa;
            this.ncortoCasa=ncortoCasa;
        }

        protected void onPreExecute(){
            System.out.println("Pre execute -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            enviarDatos(nombreCasa, addCasa, telfCasa, correoCasa, dirCasa, ncortoCasa);
            return null;
        }


        protected void onPostExecute(Void resultado){
            AlertDialog.Builder mensajeDialogo = new AlertDialog.Builder(ShActivity.this);
            mensajeDialogo.setTitle("server status response.");
            mensajeDialogo.setMessage(cadenaResultado.toString())
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

    public void enviarDatos(String nombreCasa,String addCasa,String telfCasa,String correoCasa, String dirCasa,String ncortoCasa){

        try{
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("nombre",nombreCasa);
            Request.addProperty("dir",addCasa);
            Request.addProperty("telf",telfCasa);
            Request.addProperty("email",correoCasa);
            Request.addProperty("rep",dirCasa);
            Request.addProperty("shortname",ncortoCasa);

            SoapSerializationEnvelope soapEnvelop= new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet=true;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE trasporte = new HttpTransportSE(URL);
            trasporte.call(SOAP_ACTION,soapEnvelop);
            cadenaResultado = (SoapPrimitive) soapEnvelop.getResponse();

        }catch(Exception e){

            Log.i("","EXCEPTION: -------------- ******************* "+ e.getMessage());

        }

    }

}
