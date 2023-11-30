package mx.GPS.healthec.modelos;

public class Recetas {
    private String Titulo;
    private String Descripcion;
    private int Imagen;
    private int clave;

    private String Url;

    public Recetas(String titulo, String descripcion, int imagen, int clave, String url) {
        Titulo = titulo;
        Descripcion = descripcion;
        Imagen = imagen;
        this.clave = clave;
        Url = url;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }
}
