package mx.GPS.healthec;

public class Ejercicio {
    String nombre;
    String descripcion;
    int imagen;

    public Ejercicio(String nombre, int imagen, String descripcion) {
        this.nombre = nombre;
        this.descripcion=descripcion;
        this.imagen = imagen;
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

    public int getImagen() {return imagen;}

    public void setImagen(int imagen) {
        this.imagen= imagen;
    }
}
