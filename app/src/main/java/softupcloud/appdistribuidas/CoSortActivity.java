package softupcloud.appdistribuidas;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CoSortActivity extends AppCompatActivity implements Variables{

    private String SOAP_ACTION="http://serviciosImplementados";
    private String METHOD_NAME="insertar";
    private String NAMESPACE="http://serviciosImplementados";
    private String URL="http://"+IP_SERVER+":8086/WebService/services/servicioDescripcion.servicioDescripcionHttpSoap11Endpoint/";

    SoapPrimitive cadenaResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_sort);

        Button btUpload = (Button) findViewById(R.id.btn);

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText dato1= (EditText) findViewById(R.id.editText);
                CoSortActivity.AsynCallWs tarea= new CoSortActivity.AsynCallWs(dato1.getText().toString());
                tarea.execute();
            }
        });
        
        
    }



    private class AsynCallWs extends AsyncTask<Void, Void, Void> {
        private String coworker;


        public AsynCallWs(String addCo){
            this.coworker=addCo;
        }

        protected void onPreExecute(){
            System.out.println("Pre execute -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            enviarDatos(coworker);
            return null;
        }


        protected void onPostExecute(Void resultado){
            AlertDialog.Builder mensajeDialogo = new AlertDialog.Builder(CoSortActivity.this);
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

    public void enviarDatos(String nombreCasa){

        try{
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("dato",nombreCasa);
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
