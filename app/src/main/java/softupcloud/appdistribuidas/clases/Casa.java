package softupcloud.appdistribuidas.clases;

/**
 * Created by hp on 15/1/2018.
 */

import java.io.Serializable;

public class Casa  {
    /**
     *
     */

    private int idcasa;
    private String nombrecasa;
    private String dircasa;
    private String telcasa;
    private String correocasa;
    private String directorcasa;
    private String iconocasa;
    private String cortocasa;
    private boolean estcasa;

    public Casa(int idc,String nomc,String dirc, String telc, String correoc, String directorc, String iconoc, String cortoc, boolean ec){
        super();
        this.idcasa=idc;
        this.nombrecasa=nomc;
        this.dircasa=dirc;
        this.telcasa=telc;
        this.correocasa=correoc;
        this.directorcasa=directorc;
        this.iconocasa=iconoc;
        this.cortocasa=cortoc;
        this.estcasa=ec;
    }

    public boolean isEstcasa() {
        return estcasa;
    }

    public void setEstcasa(boolean estcasa) {
        this.estcasa = estcasa;
    }

    public String getIconocasa() {
        return iconocasa;
    }

    public void setIconocasa(String iconocasa) {
        this.iconocasa = iconocasa;
    }

    public String getCortocasa() {
        return cortocasa;
    }

    public void setCortocasa(String cortocasa) {
        this.cortocasa = cortocasa;
    }

    public Casa(){
        super();
    }

    public int getIdcasa() {
        return idcasa;
    }

    public void setIdcasa(int idcasa) {
        this.idcasa = idcasa;
    }

    public String getNombrecasa() {
        return nombrecasa;
    }
    public void setNombrecasa(String nombrecasa) {
        this.nombrecasa = nombrecasa;
    }
    public String getDircasa() {
        return dircasa;
    }
    public void setDircasa(String dircasa) {
        this.dircasa = dircasa;
    }

    public String getTelcasa() {
        return telcasa;
    }

    public void setTelcasa(String telcasa) {
        this.telcasa = telcasa;
    }

    public String getCorreocasa() {
        return correocasa;
    }
    public void setCorreocasa(String correocasa) {
        this.correocasa = correocasa;
    }
    public String getDirectorcasa() {
        return directorcasa;
    }
    public void setDirectorcasa(String directorcasa) {
        this.directorcasa = directorcasa;
    }
}
