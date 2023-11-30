package mx.GPS.healthec;

public class Modelo {


    private String prioridadactividad;
    private String nombreactividad;

    Modelo()
    {

    }

    public Modelo(String prioridadactividad,String nombreactividad ) {
        this.prioridadactividad = prioridadactividad;
        this.nombreactividad = nombreactividad;

    }

    public String getNombreactividad() {
        return nombreactividad;
    }

    public void setNombreactividad(String nombreactividad) {
        this.nombreactividad = nombreactividad;
    }

    public String getPrioridadactividad() {
        return prioridadactividad;
    }

    public void setPrioridadactividad(String prioridadactividad) {
        this.prioridadactividad = prioridadactividad;
    }
}
