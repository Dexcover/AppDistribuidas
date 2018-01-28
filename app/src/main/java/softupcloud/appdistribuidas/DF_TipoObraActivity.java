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
import android.widget.RadioGroup;
import android.widget.RadioButton;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import softupcloud.appdistribuidas.variables.Variables;

public class DF_TipoObraActivity extends AppCompatActivity implements Variables {
    private String SOAP_ACTION="http://serviciosImplementados";
    private String METHOD_NAME="insertar";
    private String NAMESPACE="http://serviciosImplementados";
    private String URL="http://"+IP_SERVER+"/WebService/services/servicioTipoObra.servicioTipoObraHttpSoap11Endpoint/";

    SoapPrimitive cadenaResultado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_df__tipo_obra);

        Button btUpload = (Button) findViewById(R.id.upload);

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText dato1= (EditText) findViewById(R.id.editText24);
/*                EditText dato2= (EditText) findViewById(R.id.editText3);
                EditText dato3= (EditText) findViewById(R.id.editText4);
                EditText dato4= (EditText) findViewById(R.id.editText5);
                EditText dato5= (EditText) findViewById(R.id.editText6);
                EditText dato6= (EditText) findViewById(R.id.editText7);*/


                RadioGroup rg = (RadioGroup)findViewById(R.id.radioGrupo);
                String radiovalue = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                switch (radiovalue){
                    case "1":
                        radiovalue="./imagenes/IconosParaArbol/TOAutogestionada.png";
                        break;
                    case "2":
                        radiovalue="./imagenes/IconosParaArbol/TOEducativa.png";
                        break;
                    case "3":
                        radiovalue="./imagenes/IconosParaArbol/TOMedioComunicacion.png";
                        break;
                    case "4":
                        radiovalue="./imagenes/IconosParaArbol/TOPastoral.png";
                        break;
                    case "5":
                        radiovalue="./imagenes/IconosParaArbol/TOSalud.png";
                        break;
                    case "6":
                        radiovalue="./imagenes/IconosParaArbol/TOSocial.png";
                        break;
                    case "7":
                        radiovalue="./imagenes/IconosParaArbol/TOMundial.png";
                        break;
                    case "8":
                        radiovalue="./imagenes/IconosParaArbol/TODiscapacidad.png";
                        break;
                    case "9":
                        radiovalue="./imagenes/IconosParaArbol/TOTecnologica.png";
                        break;
                }


                //String nombreCasa=dato1.getText().toString();
               String descrtipoObra=dato1.getText().toString();
               /* String addCasa=dato2.getText().toString();
                String telfCasa=dato3.getText().toString();
                String correoCasa=dato4.getText().toString();
                String dirCasa=dato5.getText().toString();
                String ncortoCasa=dato6.getText().toString();*/


//                ShActivity.AsynCallWs tarea= new ShActivity.AsynCallWs(nombreCasa, addCasa, telfCasa, correoCasa, dirCasa, ncortoCasa);
                AsynCallWs tarea= new AsynCallWs(descrtipoObra,radiovalue);
                tarea.execute();
            }
        });


    }


    private class AsynCallWs extends AsyncTask<Void, Void, Void> {
        private String descrtipoObra;
        private String radiovalue;
        /*private String addCasa;
        private String telfCasa;
        private String correoCasa;
        private String dirCasa;
        private String ncortoCasa;*/

        public AsynCallWs(String descrtipoObra, String radiovalue){
            this.descrtipoObra=descrtipoObra;
            this.radiovalue=radiovalue;
        }

        protected void onPreExecute(){
            System.out.println("Pre execute -- ");
        }

        @Override
        protected Void doInBackground(Void... hola) {
            enviarDatos(descrtipoObra, radiovalue);
            return null;
        }


        protected void onPostExecute(Void resultado){
            AlertDialog.Builder mensajeDialogo = new AlertDialog.Builder(DF_TipoObraActivity.this);
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


    public void enviarDatos(String descrtipoObra, String radiovalue){

        try{
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("txt",descrtipoObra);
            Request.addProperty("icon",radiovalue);


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
