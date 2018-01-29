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

import softupcloud.appdistribuidas.variables.Variables;

public class DF_ColaboradorActivity extends AppCompatActivity implements Variables {
    private String SOAP_ACTION="http://serviciosImplementados";
    private String METHOD_NAME="devolverCasa";
    private String NAMESPACE="http://serviciosImplementados";
    private String URL="http://"+IP_SERVER+"/WebService/services/servicioColaborador.servicioColaboradorHttpSoap11Endpoint/";
    SoapPrimitive cadenaResultado;
    SoapObject objeto; //Objeto para combo Casa
    SoapObject objeto2; //Objeto para combo Obra
    Spinner house;
    Spinner obra;
    Button id_cargar;

    List<String> values; //Values Casa
    List<String> values_obra; //Values Obra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_df__colaborador);
        AsynCallWs tarea= new AsynCallWs();
        tarea.execute();

        obra= (Spinner) findViewById(R.id.id_work);
        house= (Spinner) findViewById(R.id.id_house);

        house.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String casa= house.getSelectedItem().toString();
                Toast.makeText(DF_ColaboradorActivity.this, casa, Toast.LENGTH_LONG).show();
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
                // String de "spinner8", o lugar; seteado <---------------------------------------------------------- AQUÍ PARÁMETRO DE SPINNER LUGAR
                String lugar = "1";
                EditText dato1= (EditText) findViewById(R.id.editText22);

                // String de spinner de colaborador; seteado <---------------------------------------------------------- AQUÍ PARÁMETRO DE SPINNER LUGAR
                String tipocolab = "2";
                //EditText dato2= (EditText) findViewById(R.id.editText21);

                String numcolab=dato1.getText().toString();


                AsynCallWs_enviar tarea= new AsynCallWs_enviar(casa, nobra, lugar, numcolab, tipocolab);
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

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(DF_ColaboradorActivity.this, android.R.layout.simple_spinner_item, values);
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

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(DF_ColaboradorActivity.this, android.R.layout.simple_spinner_item, values_obra);
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
        private String lugar; //placeName;
        private String numcolab; //placeDes;
        private String tipocolab; //placeRes;


        public AsynCallWs_enviar(String casa, String nobra, String lugar, String numcolab, String tipocolab){
            this.casa=casa;
            this.nobra=nobra;
            this.lugar=lugar;
            this.numcolab=numcolab;
            this.tipocolab=tipocolab;
        }

        protected void onPreExecute(){
            System.out.println("Pre execute -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            enviarDatos(casa, nobra, lugar, numcolab, tipocolab);
            return null;
        }


        protected void onPostExecute(Void resultado){
            AlertDialog.Builder mensajeDialogo = new AlertDialog.Builder(DF_ColaboradorActivity.this);
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

    public void enviarDatos(String casa, String nobra, String lugar, String numcolab, String tipocolab){

        try{
            METHOD_NAME="insertar";
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("casa",casa);
            Request.addProperty("nobra",nobra);
            Request.addProperty("lugar",lugar);
            Request.addProperty("numcolab",numcolab);
            Request.addProperty("tipolab",tipocolab);

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
