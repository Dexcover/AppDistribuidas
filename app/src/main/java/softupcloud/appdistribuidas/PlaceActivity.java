package softupcloud.appdistribuidas;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import softupcloud.appdistribuidas.variables.Variables;

public class PlaceActivity extends AppCompatActivity implements Variables {
    private String SOAP_ACTION="http://serviciosImplementados";
    private String METHOD_NAME="insertar";
    private String NAMESPACE="http://serviciosImplementados";
    private String URL="http://"+IP_SERVER+":8086/WebService/services/servicioCasa.servicioCasaHttpSoap11Endpoint/";
    SoapPrimitive cadenaResultado;
    Spinner comboHouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        comboHouse = (Spinner) findViewById(R.id.id_house);



    }


    private class AsynCallWs_Combo_House extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute(){
            System.out.println("Pre execute -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            cargarCombo();
            return null;
        }


        protected void onPostExecute(Void resultado){

        }

    }


    public void cargarCombo(){

        try{
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);


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
