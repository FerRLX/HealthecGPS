package mx.GPS.healthec.modelos;

public class UserModel {

    //Atributos de la clase usuario
    private Long idUser;
    private String email;
    private String password;
    private String nombre;

    //Constructor de la clase Usuario
    public UserModel(Long idUser, String email, String password, String nombre) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
    }
    //Constructor vacío de la clase usuario
    public UserModel() {
    }

    //Constructor para validar
    public UserModel(Long idUser, String email, String password) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
    }

    //toString es necesario para imprimir el contenido de objetos de la clase


    @Override
    public String toString() {
        return "UserModel{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    //getters and setters de los atributos de la clase
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
