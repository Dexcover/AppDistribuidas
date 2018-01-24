package softupcloud.appdistribuidas;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import softupcloud.appdistribuidas.variables.Variables;

public class PlaceActivity extends AppCompatActivity implements Variables {
    private String SOAP_ACTION="http://serviciosImplementados";
    private String METHOD_NAME="devolverCasa";
    private String NAMESPACE="http://serviciosImplementados";
    private String URL="http://"+IP_SERVER+"/WebService/services/servicioLugar.servicioLugarHttpSoap11Endpoint/";
    SoapPrimitive cadenaResultado;
    SoapObject objeto;
    SoapObject objeto2;
    Spinner house;
    Spinner obra;
    Button id_cargar;

    List<String> values;
    List<String> values_obra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        AsynCallWs tarea= new AsynCallWs();
        tarea.execute();

        obra= (Spinner) findViewById(R.id.id_work);
        house= (Spinner) findViewById(R.id.id_house);

        house.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String casa= house.getSelectedItem().toString();
                Toast.makeText(PlaceActivity.this, casa, Toast.LENGTH_LONG).show();
                AsynCallWs_obra obra= new AsynCallWs_obra(casa);
                obra.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                AsynCallWs tarea= new AsynCallWs();
                tarea.execute();
            }
        });


        id_cargar = (Button) findViewById(R.id.id_cargar);

        id_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String casa= house.getSelectedItem().toString();
                String nobra= obra.getSelectedItem().toString();
                EditText dato1= (EditText) findViewById(R.id.editText10);
                EditText dato2= (EditText) findViewById(R.id.editText11);
                EditText dato3= (EditText) findViewById(R.id.editText12);
                EditText dato4= (EditText) findViewById(R.id.editText13);
                EditText dato5= (EditText) findViewById(R.id.editText14);

                String placeName=dato1.getText().toString();
                String placeDes=dato2.getText().toString();
                String placeRes=dato3.getText().toString();
                String placeAd=dato4.getText().toString();
                String placeNum=dato5.getText().toString();

                AsynCallWs_enviar tarea= new AsynCallWs_enviar(casa, nobra, placeName,placeDes,placeRes,placeAd,placeNum);
                tarea.execute();

            }
        });

    }


    private class AsynCallWs extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute(){
            System.out.println("Pre execute -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            cargarCombo();
            return null;
        }


        protected void onPostExecute(Void resultado){

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(PlaceActivity.this, android.R.layout.simple_spinner_item, values);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            house = (Spinner) findViewById(R.id.id_house);
            house.setAdapter(dataAdapter);

        }

    }

    private class AsynCallWs_obra extends AsyncTask<Void, Void, Void> {

        private String nombreCasa;

        public AsynCallWs_obra(String nCasa){
            this.nombreCasa=nCasa;
        }

        protected void onPreExecute(){
            System.out.println("Pre execute obra -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            cargarCombo_obra(nombreCasa);
            return null;
        }


        protected void onPostExecute(Void resultado){

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(PlaceActivity.this, android.R.layout.simple_spinner_item, values_obra);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            obra = (Spinner) findViewById(R.id.id_work);
            obra.setAdapter(dataAdapter);

        }

    }

    public void cargarCombo_obra(String nombreCasa){

        try{
            METHOD_NAME="devolverCasaObra";
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("nombreCasa",nombreCasa);
            SoapSerializationEnvelope soapEnvelop= new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet=true;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE trasporte = new HttpTransportSE(URL);
            trasporte.call(SOAP_ACTION,soapEnvelop);
            objeto2 = (SoapObject) soapEnvelop.bodyIn;
            values_obra = new ArrayList<String>();
            for (int i = 0; i < objeto2.getPropertyCount(); i++) {
                values_obra.add(objeto2.getProperty(i).toString());
            }
        }catch(Exception e){

            Log.i("","EXCEPTION: -------------- ******************* "+ e.getMessage());

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
            objeto = (SoapObject) soapEnvelop.bodyIn;
            values = new ArrayList<String>();
            for (int i = 0; i < objeto.getPropertyCount(); i++) {
                values.add(objeto.getProperty(i).toString());
            }
        }catch(Exception e){

            Log.i("","EXCEPTION: -------------- ******************* "+ e.getMessage());

        }

    }

    private class AsynCallWs_enviar extends AsyncTask<Void, Void, Void> {
        private String casa;
        private String nobra;
        private String placeName;
        private String placeDes;
        private String placeRes;
        private String placeAd;
        private String placeNum;


        public AsynCallWs_enviar(String casa, String nobra, String placeName, String placeDes, String placeRes,String placeAd, String placeNum){
            this.casa=casa;
            this.nobra=nobra;
            this.placeName=placeName;
            this.placeDes=placeDes;
            this.placeRes=placeRes;
            this.placeAd=placeAd;
            this.placeNum=placeNum;
        }

        protected void onPreExecute(){
            System.out.println("Pre execute -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            enviarDatos(casa, nobra, placeName,placeDes,placeRes,placeAd,placeNum);
            return null;
        }


        protected void onPostExecute(Void resultado){
            AlertDialog.Builder mensajeDialogo = new AlertDialog.Builder(PlaceActivity.this);
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

    public void enviarDatos(String casa, String nobra, String placeName, String placeDes, String placeRes,String placeAd, String placeNum){

        try{
            METHOD_NAME="insertarLugar";
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("casa",casa);
            Request.addProperty("nobra",nobra);
            Request.addProperty("placeName",placeName);
            Request.addProperty("placeDes",placeDes);
            Request.addProperty("placeRes",placeRes);
            Request.addProperty("placeAd",placeAd);
            Request.addProperty("placeNum",placeNum);

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
