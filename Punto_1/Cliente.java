public class Cliente {
    private long identificacion; // Se ajustó a tipo numérico long
    private String nombre;
    private String tipoTramite;
    private int edad;
    private boolean preferencial; // true: Preferencial, false: Normal
    private int numeroTurno;
    private int estado; // 1: Esperando (Pendiente), 2: Atendido, 3: Cancelado

    public Cliente() {
    }

    public Cliente(long identificacion, String nombre, String tipoTramite, int edad, boolean preferencial, int numeroTurno) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoTramite = tipoTramite;
        this.edad = edad;
        this.preferencial = preferencial;
        this.numeroTurno = numeroTurno;
        this.estado = 1;
    }

    public long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(long identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isPreferencial() {
        return preferencial;
    }

    public void setPreferencial(boolean preferencial) {
        this.preferencial = preferencial;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}