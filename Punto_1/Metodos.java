import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    // Validar enteros cortos (opciones, edades, turnos)
    public int ValidarEentero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Por favor ingrese un número entero válido: ");
            sc.next();
        }
        return sc.nextInt();
    }

    // Validar enteros largos (identificaciones/cédulas)
    public long ValidarEenteroLong(Scanner sc) {
        while (!sc.hasNextLong()) {
            System.out.print("Por favor ingrese un número de identificación válido: ");
            sc.next();
        }
        return sc.nextLong();
    }

    // Calcula el número consecutivo del turno
    public int GenerarTurno(Queue<Cliente> cola) {
        return cola.size() + 1;
    }

    // 1. Registrar cliente
    public Queue<Cliente> RegistrarCliente(Queue<Cliente> cola, Scanner sc) {
        Cliente c = new Cliente();
        
        System.out.print("Ingrese el número de identificación: ");
        c.setIdentificacion(ValidarEenteroLong(sc));
        
        sc.nextLine(); // Limpiar búfer de entrada
        System.out.print("Ingrese el nombre completo: ");
        c.setNombre(sc.nextLine());
        
        System.out.print("Ingrese el tipo de trámite: ");
        c.setTipoTramite(sc.nextLine());
        
        System.out.print("Ingrese la edad: ");
        c.setEdad(ValidarEentero(sc));
        
        System.out.println("¿Es atención preferencial? (1: Sí, 2: No)");
        int prefOpt = ValidarEentero(sc);
        c.setPreferencial(prefOpt == 1);
        
        c.setNumeroTurno(GenerarTurno(cola));
        c.setEstado(1); // 1 = Esperando
        
        cola.offer(c);
        System.out.println("-> Cliente registrado exitosamente. Asignado el Turno #" + c.getNumeroTurno());
        return cola;
    }

    // Imprime la información de un cliente individual
    private void ImprimirCliente(Cliente c) {
        System.out.println("Turno: " + c.getNumeroTurno());
        System.out.println("ID: " + c.getIdentificacion() + " | Nombre: " + c.getNombre());
        System.out.println("Trámite: " + c.getTipoTramite() + " | Edad: " + c.getEdad());
        System.out.println("Tipo: " + (c.isPreferencial() ? "PREFERENCIAL" : "Normal"));
        String est = (c.getEstado() == 1) ? "Esperando" : (c.getEstado() == 2 ? "Atendido" : "Cancelado");
        System.out.println("Estado: " + est);
        System.out.println("-----------------------------------------");
    }

    // 2. Consultar clientes esperando
    public void ConsultarClientesEsperando(Queue<Cliente> cola) {
        System.out.println("\n--- CLIENTES EN ESPERA (ORDEN DE ATENCIÓN) ---");
        boolean hayEnEspera = false;
        
        // Primero preferenciales en espera
        for (Cliente c : cola) {
            if (c.getEstado() == 1 && c.isPreferencial()) {
                ImprimirCliente(c);
                hayEnEspera = true;
            }
        }
        
        // Luego normales en espera
        for (Cliente c : cola) {
            if (c.getEstado() == 1 && !c.isPreferencial()) {
                ImprimirCliente(c);
                hayEnEspera = true;
            }
        }

        if (!hayEnEspera) {
            System.out.println("No hay clientes esperando en este momento.");
        }
    }

    // 3. Llamar al siguiente cliente respetando la prioridad
    public Queue<Cliente> LlamarSiguienteCliente(Queue<Cliente> cola) {
        Cliente siguiente = null;

        // Buscar el primer preferencial con estado Esperando
        for (Cliente c : cola) {
            if (c.getEstado() == 1 && c.isPreferencial()) {
                siguiente = c;
                break;
            }
        }

        // Si no hay preferenciales, buscar el primer normal en espera
        if (siguiente == null) {
            for (Cliente c : cola) {
                if (c.getEstado() == 1 && !c.isPreferencial()) {
                    siguiente = c;
                    break;
                }
            }
        }

        if (siguiente != null) {
            System.out.println("\n>>> LLAMANDO AL TURNO #" + siguiente.getNumeroTurno() + " <<<");
            System.out.println("Cliente: " + siguiente.getNombre() + " (ID: " + siguiente.getIdentificacion() + " | " + (siguiente.isPreferencial() ? "PREFERENCIAL" : "Normal") + ")");
            System.out.println("Trámite a realizar: " + siguiente.getTipoTramite());
        } else {
            System.out.println("No hay clientes en espera para llamar.");
        }

        return cola;
    }

    // 4. Marcar cliente como atendido
    public Queue<Cliente> MarcarAtendido(Queue<Cliente> cola, Scanner sc) {
        System.out.print("Ingrese el número de turno del cliente atendido: ");
        int numTurno = ValidarEentero(sc);
        boolean encontrado = false;

        for (Cliente c : cola) {
            if (c.getNumeroTurno() == numTurno) {
                if (c.getEstado() == 1) {
                    c.setEstado(2); // Estado 2 = Atendido
                    System.out.println("El turno #" + numTurno + " fue marcado como ATENDIDO.");
                } else if (c.getEstado() == 2) {
                    System.out.println("El cliente del turno #" + numTurno + " ya había sido atendido.");
                } else {
                    System.out.println("No se puede atender un turno que fue CANCELADO.");
                }
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún cliente con el turno #" + numTurno);
        }

        return cola;
    }

    // 5. Cambiar cliente de atención normal a preferencial
    public Queue<Cliente> CambiarAPreferencial(Queue<Cliente> cola, Scanner sc) {
        System.out.print("Ingrese la identificación numérica del cliente a cambiar a Preferencial: ");
        long id = ValidarEenteroLong(sc);
        boolean encontrado = false;

        for (Cliente c : cola) {
            if (c.getIdentificacion() == id) {
                if (c.getEstado() == 1) {
                    if (!c.isPreferencial()) {
                        c.setPreferencial(true);
                        System.out.println("El cliente " + c.getNombre() + " ahora tiene condición PREFERENCIAL.");
                    } else {
                        System.out.println("El cliente ya contaba con atención preferencial.");
                    }
                } else {
                    System.out.println("No se puede cambiar el tipo de atención porque el cliente ya fue " + 
                                       (c.getEstado() == 2 ? "ATENDIDO" : "CANCELADO") + ".");
                }
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún cliente registrado con la ID: " + id);
        }

        return cola;
    }

    // 6. Cancelar un turno
    public Queue<Cliente> CancelarTurno(Queue<Cliente> cola, Scanner sc) {
        System.out.print("Ingrese el número de turno a cancelar: ");
        int numTurno = ValidarEentero(sc);
        boolean encontrado = false;

        for (Cliente c : cola) {
            if (c.getNumeroTurno() == numTurno) {
                if (c.getEstado() == 1) {
                    c.setEstado(3); // Estado 3 = Cancelado
                    System.out.println("El turno #" + numTurno + " ha sido CANCELADO exitosamente.");
                } else if (c.getEstado() == 2) {
                    System.out.println("ERROR: No es posible cancelar un turno que ya fue ATENDIDO.");
                } else {
                    System.out.println("El turno #" + numTurno + " ya se encontraba cancelado.");
                }
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún cliente con el turno #" + numTurno);
        }

        return cola;
    }

    // 7. Buscar cliente por identificación
    public void BuscarPorIdentificacion(Queue<Cliente> cola, Scanner sc) {
        System.out.print("Ingrese la identificación numérica del cliente a buscar: ");
        long id = ValidarEenteroLong(sc);
        boolean encontrado = false;

        for (Cliente c : cola) {
            if (c.getIdentificacion() == id) {
                System.out.println("\n--- CLIENTE ENCONTRADO ---");
                ImprimirCliente(c);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún cliente registrado con esa identificación.");
        }
    }

    // 8. Consultar cuántas personas están esperando
    public void ConsultarTotalEsperando(Queue<Cliente> cola) {
        int contador = 0;
        for (Cliente c : cola) {
            if (c.getEstado() == 1) {
                contador++;
            }
        }
        System.out.println("\nTotal de personas actualmente esperando: " + contador);
    }

    // 9. Mostrar cuántos clientes normales y preferenciales están pendientes
    public void MostrarPendientesPorTipo(Queue<Cliente> cola) {
        int normalPendiente = 0;
        int preferencialPendiente = 0;

        for (Cliente c : cola) {
            if (c.getEstado() == 1) {
                if (c.isPreferencial()) {
                    preferencialPendiente++;
                } else {
                    normalPendiente++;
                }
            }
        }

        System.out.println("\n--- CLIENTES PENDIENTES POR TIPO ---");
        System.out.println("Clientes Preferenciales en espera: " + preferencialPendiente);
        System.out.println("Clientes Normales en espera: " + normalPendiente);
        System.out.println("Total en espera: " + (preferencialPendiente + normalPendiente));
    }
}