package softupcloud.appdistribuidas;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import softupcloud.appdistribuidas.variables.Variables;

public class SwActivity extends AppCompatActivity implements Variables{
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
        setContentView(R.layout.activity_sw);

        //Cargar Combo House
        AsynCallWs tarea= new AsynCallWs();
        tarea.execute();

        AsynCallWs_Tipoobra tarea2= new AsynCallWs_Tipoobra();
        tarea2.execute();

        id_cargar= (Button) findViewById(R.id.id_cargar);

        id_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String casa= house.getSelectedItem().toString();
                String nobra= obra.getSelectedItem().toString();
                EditText dato1= (EditText) findViewById(R.id.editText8);
                EditText dato2= (EditText) findViewById(R.id.editText9);
                EditText dato3= (EditText) findViewById(R.id.editText10);
                EditText dato4= (EditText) findViewById(R.id.editText11);
                EditText dato5= (EditText) findViewById(R.id.editText12);
                EditText dato6= (EditText) findViewById(R.id.editText13);
                EditText dato7= (EditText) findViewById(R.id.editText14);

                String wDeno=dato1.getText().toString();
                String wServ=dato2.getText().toString();
                String wProd=dato3.getText().toString();
                String wSched=dato4.getText().toString();
                String wInfo=dato5.getText().toString();
                String wWeb=dato6.getText().toString();
                String wShort=dato7.getText().toString();


                AsynCallWs_enviar tareaEnviar= new AsynCallWs_enviar(casa,nobra,wDeno,wServ,wProd,wSched,wInfo,wWeb,wShort);
                tareaEnviar.execute();
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

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SwActivity.this, android.R.layout.simple_spinner_item, values);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            house = (Spinner) findViewById(R.id.id_house);
            house.setAdapter(dataAdapter);

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
    private class AsynCallWs_Tipoobra extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute(){
            System.out.println("Pre execute obra -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            cargarCombo_Tipoobra();
            return null;
        }


        protected void onPostExecute(Void resultado){
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SwActivity.this, android.R.layout.simple_spinner_item, values_obra);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            obra = (Spinner) findViewById(R.id.id_work);
            obra.setAdapter(dataAdapter);

        }

    }
    public void cargarCombo_Tipoobra(){

        try{
            METHOD_NAME="devuelveTipoObra";
            String URLn="http://"+IP_SERVER+"/WebService/services/servicioObraDC.servicioObraDCHttpSoap11Endpoint/";

            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            SoapSerializationEnvelope soapEnvelop= new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet=true;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE trasporte = new HttpTransportSE(URLn);
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



    private class AsynCallWs_enviar extends AsyncTask<Void, Void, Void> {
        private String casa;
        private String nobra;
        private String wDeno;
        private String wServ;
        private String wProd;
        private String wSched;
        private String wInfo;
        private String wWeb;
        private String wShort;


        public AsynCallWs_enviar(String casa, String nobra,String wDeno,String wServ,String wProd,String wSched,
                                 String wInfo,String wWeb,String wShort){
            this.casa=casa;
            this.nobra=nobra;
            this.wDeno=wDeno;
            this.wServ=wServ;
            this.wProd=wProd;
            this.wSched=wSched;
            this.wInfo=wInfo;
            this.wWeb=wWeb;
            this.wShort=wShort;
        }

        protected void onPreExecute(){
            System.out.println("Pre execute -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            enviarDatos(casa,nobra,wDeno,wServ,wProd,wSched,wInfo,wWeb,wShort);
            return null;
        }


        protected void onPostExecute(Void resultado){
            AlertDialog.Builder mensajeDialogo = new AlertDialog.Builder(SwActivity.this);
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
    public void enviarDatos(String casa, String nobra,String wDeno,String wServ,String wProd,String wSched,
                            String wInfo,String wWeb,String wShort){

        try{
            METHOD_NAME="insertar";
            String URLn="http://"+IP_SERVER+"/WebService/services/servicioObraDC.servicioObraDCHttpSoap11Endpoint/";
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("casa",casa);
            Request.addProperty("nobra",nobra);
            Request.addProperty("wDeno",wDeno);
            Request.addProperty("wServ",wServ);
            Request.addProperty("wProd",wProd);
            Request.addProperty("wSched",wSched);
            Request.addProperty("wInfo",wInfo);
            Request.addProperty("wWeb",wWeb);
            Request.addProperty("wShort",wShort);

            SoapSerializationEnvelope soapEnvelop= new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelop.dotNet=true;
            soapEnvelop.setOutputSoapObject(Request);
            HttpTransportSE trasporte = new HttpTransportSE(URLn);
            trasporte.call(SOAP_ACTION,soapEnvelop);
            cadenaResultado = (SoapPrimitive) soapEnvelop.getResponse();

        }catch(Exception e){

            Log.i("","EXCEPTION: -------------- ******************* "+ e.getMessage());

        }

    }



}
