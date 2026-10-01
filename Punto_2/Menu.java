import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Metodos m = new Metodos();

        // Cargar registros iniciales directamente en una LinkedList de Paciente
        LinkedList<Paciente> registrados = m.CargarPacientesIniciales();

        // Estructuras dinámicas de Colas para la jornada
        Queue<Paciente> colaAtendidos = new LinkedList<>();
        Queue<Paciente> colaCancelados = new LinkedList<>();
        Queue<Paciente> colaPrioritarios = new LinkedList<>();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=========================================");
            System.out.println("   SISTEMA DE GESTIÓN HOSPITALARIA / BANCO ");
            System.out.println("=========================================");
            System.out.println("1) Enviar paciente a la fila de atención");
            System.out.println("2) Atender un paciente");
            System.out.println("3) Cancelar cita de un paciente");
            System.out.println("4) Cambiar servicio de un paciente");
            System.out.println("5) Marcar paciente como prioritario");
            System.out.println("6) Retirar paciente de atención");
            System.out.println("7) Volver a solicitar atención (Reingreso)");
            System.out.println("8) Ver reporte general final (Lista, Pila, Colas e Historial)");
            System.out.println("9) Salir");
            System.out.print("Seleccione una opción: ");

            int opt = m.ValidarEntero(sc);

            switch (opt) {
                case 1:
                    registrados = m.EnviarAFila(registrados, sc);
                    break;
                case 2:
                    colaAtendidos = m.AtenderPaciente(registrados, colaAtendidos, colaPrioritarios, sc);
                    break;
                case 3:
                    colaCancelados = m.CancelarCita(registrados, colaCancelados, sc);
                    break;
                case 4:
                    registrados = m.CambiarServicio(registrados, sc);
                    break;
                case 5:
                    colaPrioritarios = m.MarcarPrioritario(registrados, colaPrioritarios, sc);
                    break;
                case 6:
                    registrados = m.RetirarAtencion(registrados, sc);
                    break;
                case 7:
                    registrados = m.ReorganizarAtencion(registrados, sc);
                    break;
                case 8:
                    m.MostrarPacientesRegistrados(registrados);
                    m.MostrarPilaPendientes(registrados);
                    m.MostrarCola(colaAtendidos, "PACIENTES ATENDIDOS");
                    m.MostrarCola(colaCancelados, "PACIENTES CANCELADOS");
                    m.MostrarCola(colaPrioritarios, "PACIENTES PRIORITARIOS");
                    m.MostrarHistorial();
                    break;
                case 9:
                    System.out.println("Saliendo del sistema...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
        sc.close();
    }
}