public class Paciente {
    private long id;
    private String nombre;
    private int edad;
    private String servicio;
    private int estado; // 1: Pendiente, 2: En Fila, 3: Atendido, 4: Cancelado, 5: Prioritario, 6: Retirado
    private boolean prioritario;

    public Paciente() {
    }

    public Paciente(long id, String nombre, int edad, String servicio, int estado, boolean prioritario) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.servicio = servicio;
        this.estado = estado;
        this.prioritario = prioritario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean isPrioritario() {
        return prioritario;
    }

    public void setPrioritario(boolean prioritario) {
        this.prioritario = prioritario;
    }
}