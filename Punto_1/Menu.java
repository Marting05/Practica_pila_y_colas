import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Cliente> cola = new LinkedList<>();
        Metodos m = new Metodos();
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=========================================");
            System.out.println(" BANCO - SISTEMA DE ATENCIÓN PREFERENCIAL");
            System.out.println("=========================================");
            System.out.println("1) Registrar un cliente");
            System.out.println("2) Consultar clientes esperando");
            System.out.println("3) Llamar al siguiente cliente");
            System.out.println("4) Marcar cliente como atendido");
            System.out.println("5) Cambiar cliente a atención preferencial");
            System.out.println("6) Cancelar turno");
            System.out.println("7) Buscar cliente por identificación");
            System.out.println("8) Consultar cuántas personas están esperando");
            System.out.println("9) Mostrar pendientes por tipo (Normal / Preferencial)");
            System.out.println("10) Salir");
            System.out.print("Seleccione una opción: ");

            int opt = m.ValidarEentero(sc);

            switch (opt) {
                case 1:
                    cola = m.RegistrarCliente(cola, sc);
                    break;
                case 2:
                    m.ConsultarClientesEsperando(cola);
                    break;
                case 3:
                    cola = m.LlamarSiguienteCliente(cola);
                    break;
                case 4:
                    cola = m.MarcarAtendido(cola, sc);
                    break;
                case 5:
                    cola = m.CambiarAPreferencial(cola, sc);
                    break;
                case 6:
                    cola = m.CancelarTurno(cola, sc);
                    break;
                case 7:
                    m.BuscarPorIdentificacion(cola, sc);
                    break;
                case 8:
                    m.ConsultarTotalEsperando(cola);
                    break;
                case 9:
                    m.MostrarPendientesPorTipo(cola);
                    break;
                case 10:
                    System.out.println("¡Gracias por utilizar el sistema del banco! Hasta luego.");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
        sc.close();
    }
}
