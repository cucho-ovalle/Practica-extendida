package aplicacion;

// Autor: Kevin Ovalle - Programación II - Práctica 11: Nómina mensual

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

public class Main {

    // Atributos (estado de la clase). Todos private.
    private static final String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    private static final ArrayList<Empleado> empleados = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int numeroMes;

    public static void main(String[] args) {
        seleccionarMes();

        int opcion;
        do {
            limpiarPantalla();
            mostrarMenuPrincipal();
            opcion = leerOpcion("Seleccione una opción: ", 1, 3);

            switch (opcion) {
                case 1:
                    ingresarNomina();
                    break;
                case 2:
                    imprimirNomina();
                    break;
                default:
                    limpiarPantalla();
                    System.out.println("Gracias por usar el sistema de nómina. ¡Hasta pronto!");
            }
        } while (opcion != 3);
    }

    // ---------- Selección del mes ----------

    private static void seleccionarMes() {
        limpiarPantalla();
        System.out.println("CÁLCULO DE NÓMINA");
        // El método valida el rango 1-12 antes de que se use como índice
        numeroMes = leerOpcion("Ingrese el mes a trabajar (1-12): ", 1, 12);
    }

    // ---------- Menús ----------

    private static void mostrarMenuPrincipal() {
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println("1 - Ingreso de nómina");
        System.out.println("2 - Impresión de nómina");
        System.out.println("3 - Salir");
    }

    private static void ingresarNomina() {
        int otro;
        do {
            limpiarPantalla();
            System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
            System.out.println("INGRESO DE EMPLEADO A NÓMINA");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");
            int tipo = leerOpcion("Seleccione el tipo de empleado: ", 1, 2);

            limpiarPantalla();
            if (tipo == 1) {
                registrarEmpleadoFijo();
            } else {
                registrarEmpleadoPorHora();
            }

            System.out.println();
            System.out.println("Empleado agregado correctamente.");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println("¿Desea ingresar otro empleado?");
            System.out.println("1 - Sí, ingresar otro empleado");
            System.out.println("2 - Volver al menú principal");
            otro = leerOpcion("Seleccione una opción: ", 1, 2);
        } while (otro == 1);
    }

    private static void registrarEmpleadoFijo() {
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println("INGRESO DE EMPLEADO FIJO");
        String dpi = leerDpi();
        String nombre = leerNombre();
        double sueldo = leerMonto("Sueldo mensual (Q): ");
        double bonificacion = leerMonto("Bonificación de este mes (Q): ");

        // Se crea el objeto hasta tener TODOS los datos válidos
        EmpleadoFijo nuevo = new EmpleadoFijo(dpi, nombre, sueldo, bonificacion);
        empleados.add(nuevo);

        System.out.println("Pago del mes = sueldo mensual + bonificación");
        System.out.println("Pago de " + nuevo.getNombre() + " = Q " + formato(nuevo.calcularPago()));
    }

    private static void registrarEmpleadoPorHora() {
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println("INGRESO DE EMPLEADO POR HORA");
        String dpi = leerDpi();
        String nombre = leerNombre();
        double pagoHora = leerMonto("Pago por hora (Q): ");
        double horas = leerMonto("Horas trabajadas este mes: ");

        EmpleadoPorHora nuevo = new EmpleadoPorHora(dpi, nombre, pagoHora, horas);
        empleados.add(nuevo);

        System.out.println("Pago del mes = pago por hora x horas trabajadas");
        System.out.println("Pago de " + nuevo.getNombre() + " = Q " + formato(nuevo.calcularPago()));
    }

    // ---------- Reporte ----------

    private static void imprimirNomina() {
        limpiarPantalla();
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados en la nómina de este mes");
        } else {
            System.out.println(String.format("%-15s %-22s %-10s %12s", "DPI", "Nombre", "Tipo", "Pago (Q)"));
            double total = 0;
            // Polimorfismo: referencia de tipo Empleado, la clase real decide la fórmula
            for (Empleado e : empleados) {
                double pago = e.calcularPago();
                total += pago;
                System.out.println(String.format(Locale.US, "%-15s %-22s %-10s %12.2f",
                        e.getDpi(), e.getNombre(), e.obtenerTipo(), pago));
            }
            System.out.println();
            System.out.println("Total de trabajadores: " + empleados.size());
            System.out.println("Total de nómina: Q " + formato(total));
            pausar();
            return;
        }

        System.out.println();
        System.out.println("Total de trabajadores: " + empleados.size());
        System.out.println("Total de nómina: Q " + formato(0));
        pausar();
    }

    private static void pausar() {
        System.out.println();
        System.out.print("Presione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    // ---------- Lectura y validación ----------

    // Devuelve un entero dentro del rango [min, max]; repite hasta que sea válido
    private static int leerOpcion(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= min && valor <= max) {
                    return valor;
                }
            } catch (NumberFormatException ex) {
                // cae al mensaje de error
            }
            System.out.println("Error: ingrese un número entero entre " + min + " y " + max + ".");
        }
    }

    private static String leerDpi() {
        while (true) {
            System.out.print("DPI: ");
            String dpi = scanner.nextLine().trim();
            if (dpi.matches("\\d{13}")) {
                return dpi;
            }
            System.out.println("Error: el DPI debe tener exactamente 13 dígitos.");
        }
    }

    private static String leerNombre() {
        while (true) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) {
                return nombre;
            }
            System.out.println("Error: el nombre no puede estar vacío.");
        }
    }

    private static double leerMonto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            try {
                double valor = Double.parseDouble(texto);
                if (Double.isFinite(valor) && valor >= 0) {
                    return valor;
                }
                System.out.println("Error: el valor debe ser un número finito mayor o igual a 0.");
            } catch (NumberFormatException ex) {
                System.out.println("Error: ingrese un valor numérico (use punto para decimales).");
            }
        }
    }

    // ---------- Utilidades ----------

    private static String formato(double valor) {
        return String.format(Locale.US, "%.2f", valor);
    }

    // Simula la limpieza de pantalla (válido para consolas de IDE)
    private static void limpiarPantalla() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
}
