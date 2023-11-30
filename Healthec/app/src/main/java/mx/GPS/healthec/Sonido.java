package mx.GPS.healthec;

public class Sonido {

    String nombre;
    String descripcion;
    String sonido;

    public Sonido(String nombre, String sonido, String descripcion) {
        this.nombre = nombre;
        this.descripcion=descripcion;
        this.sonido = sonido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSonido() {
        return sonido;
    }

    public void setSonido(String sonido) {
        this.sonido = sonido;
    }
}
