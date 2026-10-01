import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Metodos {

    // Historial dinámico usando LinkedList
    private LinkedList<String> historial = new LinkedList<>();

    // Registrar acción en el historial
    public void RegistrarHistorial(String accion) {
        historial.add((historial.size() + 1) + ". " + accion);
    }

    // Validar enteros
    public int ValidarEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Por favor ingrese un número entero válido: ");
            sc.next();
        }
        return sc.nextInt();
    }

    // Validar longs (IDs)
    public long ValidarLong(Scanner sc) {
        while (!sc.hasNextLong()) {
            System.out.print("Por favor ingrese una identificación válida: ");
            sc.next();
        }
        return sc.nextLong();
    }

    // Convierte el entero del estado a su representación en Texto
    public String ObtenerTextoEstado(int estado) {
        switch (estado) {
            case 1: return "Pendiente";
            case 2: return "En Fila";
            case 3: return "Atendido";
            case 4: return "Cancelado";
            case 5: return "Prioritario";
            case 6: return "Retirado";
            default: return "Desconocido";
        }
    }

    // Imprimir paciente usando Getters
    public void ImprimirPaciente(Paciente p) {
        System.out.println("ID: " + p.getId() + 
                           " | Nombre: " + p.getNombre() + 
                           " | Edad: " + p.getEdad() + 
                           " | Servicio: " + p.getServicio() + 
                           " | Estado: " + ObtenerTextoEstado(p.getEstado()) + " (" + p.getEstado() + ")" +
                           " | Prioritario: " + (p.isPrioritario() ? "SÍ" : "NO"));
    }

    // Cargar pacientes iniciales con estado numérico (1 = Pendiente)
    public LinkedList<Paciente> CargarPacientesIniciales() {
        LinkedList<Paciente> lista = new LinkedList<>();
        lista.add(new Paciente(101, "Ana", 32, "Medicina", 1, false));
        lista.add(new Paciente(102, "Carlos", 67, "Medicina", 1, false));
        lista.add(new Paciente(103, "Laura", 25, "Odontología", 1, false));
        lista.add(new Paciente(104, "Pedro", 71, "Medicina", 1, false));
        lista.add(new Paciente(105, "Marta", 45, "Odontología", 1, false));

        RegistrarHistorial("Carga inicial de pacientes realizada en la LinkedList.");
        return lista;
    }

    // Buscar paciente por ID en la LinkedList
    public Paciente BuscarPaciente(LinkedList<Paciente> lista, long id) {
        for (Paciente p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // 1. Enviar a fila de atención (Retorna LinkedList)
    public LinkedList<Paciente> EnviarAFila(LinkedList<Paciente> lista, Scanner sc) {
        System.out.print("Ingrese ID del paciente a enviar a la fila: ");
        long id = ValidarLong(sc);
        Paciente p = BuscarPaciente(lista, id);

        if (p != null) {
            if (p.getEstado() == 1 || p.getEstado() == 6) { // Pendiente o Retirado
                p.setEstado(2); // 2 = En Fila
                RegistrarHistorial("Paciente " + p.getNombre() + " (ID: " + id + ") enviado a la fila de atención.");
                System.out.println("Paciente enviado a la fila correctamente.");
            } else {
                System.out.println("El paciente ya se encuentra en estado: " + ObtenerTextoEstado(p.getEstado()));
            }
        } else {
            System.out.println("Paciente no encontrado.");
        }
        return lista;
    }

    // 2. Atender paciente (Retorna Queue<Paciente> de atendidos)
    public Queue<Paciente> AtenderPaciente(LinkedList<Paciente> lista, Queue<Paciente> colaAtendidos, Queue<Paciente> colaPrioritarios, Scanner sc) {
        Paciente pAtender = null;

        if (!colaPrioritarios.isEmpty()) {
            pAtender = colaPrioritarios.poll();
        } else {
            System.out.print("Ingrese ID del paciente a atender: ");
            long id = ValidarLong(sc);
            pAtender = BuscarPaciente(lista, id);
        }

        if (pAtender != null) {
            if (pAtender.getEstado() != 3 && pAtender.getEstado() != 4) { // Ni Atendido ni Cancelado
                pAtender.setEstado(3); // 3 = Atendido
                colaAtendidos.offer(pAtender);
                RegistrarHistorial("Paciente " + pAtender.getNombre() + " (ID: " + pAtender.getId() + ") ha sido ATENDIDO.");
                System.out.println("Paciente " + pAtender.getNombre() + " marcado como ATENDIDO.");
            } else {
                System.out.println("No se puede atender. Estado actual: " + ObtenerTextoEstado(pAtender.getEstado()));
            }
        } else {
            System.out.println("No se encontró paciente para atender.");
        }
        return colaAtendidos;
    }

    // 3. Cancelar cita (Retorna Queue<Paciente> de cancelados)
    public Queue<Paciente> CancelarCita(LinkedList<Paciente> lista, Queue<Paciente> colaCancelados, Scanner sc) {
        System.out.print("Ingrese ID del paciente que desea cancelar la cita: ");
        long id = ValidarLong(sc);
        Paciente p = BuscarPaciente(lista, id);

        if (p != null) {
            if (p.getEstado() != 3) { // Si no ha sido atendido
                p.setEstado(4); // 4 = Cancelado
                colaCancelados.offer(p);
                RegistrarHistorial("Cita del paciente " + p.getNombre() + " (ID: " + id + ") CANCELADA.");
                System.out.println("Cita cancelada con éxito.");
            } else {
                System.out.println("ERROR: No se puede cancelar la cita de un paciente ya ATENDIDO.");
            }
        } else {
            System.out.println("Paciente no encontrado.");
        }
        return colaCancelados;
    }

    // 4. Cambiar servicio (Retorna LinkedList)
    public LinkedList<Paciente> CambiarServicio(LinkedList<Paciente> lista, Scanner sc) {
        System.out.print("Ingrese ID del paciente: ");
        long id = ValidarLong(sc);
        Paciente p = BuscarPaciente(lista, id);

        if (p != null) {
            if (p.getEstado() != 3 && p.getEstado() != 4) { // Ni Atendido ni Cancelado
                sc.nextLine(); // Limpiar búfer
                System.out.print("Ingrese el nuevo servicio (Medicina / Odontología / etc.): ");
                String nuevoServicio = sc.nextLine();
                p.setServicio(nuevoServicio);
                RegistrarHistorial("Paciente " + p.getNombre() + " cambió su servicio a: " + nuevoServicio);
                System.out.println("Servicio actualizado correctamente.");
            } else {
                System.out.println("No se puede cambiar el servicio en estado: " + ObtenerTextoEstado(p.getEstado()));
            }
        } else {
            System.out.println("Paciente no encontrado.");
        }
        return lista;
    }

    // 5. Marcar prioritario (Retorna Queue<Paciente> de prioritarios)
    public Queue<Paciente> MarcarPrioritario(LinkedList<Paciente> lista, Queue<Paciente> colaPrioritarios, Scanner sc) {
        System.out.print("Ingrese ID del paciente prioritario: ");
        long id = ValidarLong(sc);
        Paciente p = BuscarPaciente(lista, id);

        if (p != null) {
            if (p.getEstado() != 3 && p.getEstado() != 4) { // Ni Atendido ni Cancelado
                p.setPrioritario(true);
                p.setEstado(5); // 5 = Prioritario
                colaPrioritarios.offer(p);
                RegistrarHistorial("Paciente " + p.getNombre() + " marcado como PRIORITARIO.");
                System.out.println("Paciente marcado como prioritario y agregado a la cola correspondiente.");
            } else {
                System.out.println("No se puede marcar como prioritario en estado: " + ObtenerTextoEstado(p.getEstado()));
            }
        } else {
            System.out.println("Paciente no encontrado.");
        }
        return colaPrioritarios;
    }

    // 6. Retirar de atención (Retorna LinkedList)
    public LinkedList<Paciente> RetirarAtencion(LinkedList<Paciente> lista, Scanner sc) {
        System.out.print("Ingrese ID del paciente a retirar: ");
        long id = ValidarLong(sc);
        Paciente p = BuscarPaciente(lista, id);

        if (p != null) {
            if (p.getEstado() != 3) { // Si no ha sido atendido
                p.setEstado(6); // 6 = Retirado
                RegistrarHistorial("Paciente " + p.getNombre() + " retirado de atención temporalmente.");
                System.out.println("Paciente retirado de atención.");
            } else {
                System.out.println("No se puede retirar un paciente ya atendido.");
            }
        } else {
            System.out.println("Paciente no encontrado.");
        }
        return lista;
    }

    // 7. Volver a solicitar atención (Retorna LinkedList)
    public LinkedList<Paciente> ReorganizarAtencion(LinkedList<Paciente> lista, Scanner sc) {
        System.out.print("Ingrese ID del paciente que solicita reingreso: ");
        long id = ValidarLong(sc);
        Paciente p = BuscarPaciente(lista, id);

        if (p != null) {
            if (p.getEstado() == 6 || p.getEstado() == 4) { // Si estaba Retirado o Cancelado
                p.setEstado(1); // 1 = Pendiente
                p.setPrioritario(false);
                RegistrarHistorial("Paciente " + p.getNombre() + " volvió a solicitar atención (Estado: Pendiente).");
                System.out.println("Paciente reingresado a estado Pendiente.");
            } else {
                System.out.println("El paciente ya está activo en estado: " + ObtenerTextoEstado(p.getEstado()));
            }
        } else {
            System.out.println("Paciente no encontrado.");
        }
        return lista;
    }

    // --- MÉTODOS DE MOSTRAR RESULTADOS FINALES (VOID) ---

    public void MostrarPacientesRegistrados(LinkedList<Paciente> lista) {
        System.out.println("\n--- PACIENTES REGISTRADOS INICIALMENTE (LINKEDLIST) ---");
        for (Paciente p : lista) {
            ImprimirPaciente(p);
        }
    }

    public void MostrarPilaPendientes(LinkedList<Paciente> lista) {
        System.out.println("\n--- PACIENTES PENDIENTES (PILA EN ORDEN DE LLEGADA) ---");
        Stack<Paciente> pilaAux = new Stack<>();

        for (int i = lista.size() - 1; i >= 0; i--) {
            Paciente p = lista.get(i);
            if (p.getEstado() == 1) { // 1 = Pendiente
                pilaAux.push(p);
            }
        }

        if (pilaAux.isEmpty()) {
            System.out.println("No hay pacientes pendientes.");
        } else {
            while (!pilaAux.isEmpty()) {
                ImprimirPaciente(pilaAux.pop());
            }
        }
    }

    public void MostrarCola(Queue<Paciente> cola, String titulo) {
        System.out.println("\n--- " + titulo + " (COLA) ---");
        if (cola.isEmpty()) {
            System.out.println("No hay registros en esta categoría.");
        } else {
            for (Paciente p : cola) {
                ImprimirPaciente(p);
            }
        }
    }

    public void MostrarHistorial() {
        System.out.println("\n--- HISTORIAL DE OPERACIONES REALIZADAS (LINKEDLIST) ---");
        if (historial.isEmpty()) {
            System.out.println("No se han realizado operaciones.");
        } else {
            for (String reg : historial) {
                System.out.println(reg);
            }
        }
    }
}