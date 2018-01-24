package softupcloud.appdistribuidas.clases;

import java.util.List;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.HeaderProperty;
import java.util.Hashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import android.os.AsyncTask;
import org.ksoap2.serialization.MarshalFloat;

public class servicioCasa {
    
    public String NAMESPACE ="http://serviciosImplementados";
    public String url="http://192.168.43.31:8086/WebService/services/servicioCasa.servicioCasaHttpSoap11Endpoint/";
    public int timeOut = 180;
    public IWsdl2CodeEvents eventHandler;
    public WS_Enums.SoapProtocolVersion soapVersion;
    
    public servicioCasa(){}
    
    public servicioCasa(IWsdl2CodeEvents eventHandler)
    {
        this.eventHandler = eventHandler;
    }
    public servicioCasa(IWsdl2CodeEvents eventHandler,String url)
    {
        this.eventHandler = eventHandler;
        this.url = url;
    }
    public servicioCasa(IWsdl2CodeEvents eventHandler,String url,int timeOutInSeconds)
    {
        this.eventHandler = eventHandler;
        this.url = url;
        this.setTimeOut(timeOutInSeconds);
    }
    public void setTimeOut(int seconds){
        this.timeOut = seconds * 1000;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public void insertarAsync(String nombre,String dir,String telf,String email,String rep,String shortname) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        insertarAsync(nombre, dir, telf, email, rep, shortname, null);
    }
    
    public void insertarAsync(final String nombre,final String dir,final String telf,final String email,final String rep,final String shortname,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, String>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected String doInBackground(Void... params) {
                return insertar(nombre, dir, telf, email, rep, shortname, headers);
            }
            @Override
            protected void onPostExecute(String result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("insertar", result);
                }
            }
        }.execute();
    }
    
    public String insertar(String nombre,String dir,String telf,String email,String rep,String shortname){
        return insertar(nombre, dir, telf, email, rep, shortname, null);
    }
    
    public String insertar(String nombre,String dir,String telf,String email,String rep,String shortname,List<HeaderProperty> headers){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject("http://serviciosImplementados","insertar");
        soapReq.addProperty("nombre",nombre);
        soapReq.addProperty("dir",dir);
        soapReq.addProperty("telf",telf);
        soapReq.addProperty("email",email);
        soapReq.addProperty("rep",rep);
        soapReq.addProperty("shortname",shortname);
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
        try{
            if (headers!=null){
                httpTransport.call("urn:insertar", soapEnvelope,headers);
            }else{
                httpTransport.call("urn:insertar", soapEnvelope);
            }
            Object retObj = soapEnvelope.bodyIn;
            if (retObj instanceof SoapFault){
                SoapFault fault = (SoapFault)retObj;
                Exception ex = new Exception(fault.faultstring);
                if (eventHandler != null)
                    eventHandler.Wsdl2CodeFinishedWithException(ex);
            }else{
                SoapObject result=(SoapObject)retObj;
                if (result.getPropertyCount() > 0){
                    Object obj = result.getProperty(0);
                    if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                        SoapPrimitive j =(SoapPrimitive) obj;
                        String resultVariable = j.toString();
                        return resultVariable;
                    }else if (obj!= null && obj instanceof String){
                        String resultVariable = (String) obj;
                        return resultVariable;
                    }
                }
            }
        }catch (Exception e) {
            if (eventHandler != null)
                eventHandler.Wsdl2CodeFinishedWithException(e);
            e.printStackTrace();
        }
        return "";
    }
    
}
