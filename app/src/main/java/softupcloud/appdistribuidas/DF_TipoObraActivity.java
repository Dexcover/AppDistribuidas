package softupcloud.appdistribuidas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.ksoap2.serialization.SoapPrimitive;

import softupcloud.appdistribuidas.variables.Variables;

public class DF_TipoObraActivity extends AppCompatActivity implements Variables {
    private String SOAP_ACTION="http://serviciosImplementados";
    private String METHOD_NAME="insertar";
    private String NAMESPACE="http://serviciosImplementados";
    private String URL="http://"+IP_SERVER+":8086/WebService/services/servicioTipoObra.servicioTipoObraHttpSoap11Endpoint/";

    SoapPrimitive cadenaResultado;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_df__tipo_obra);


    }
}
