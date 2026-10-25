package modelo;

// Autor: Kevin Ovalle - Programación II - Práctica 11: Nómina mensual

/**
 * Clase abstracta: concentra los datos comunes (DPI y nombre).
 * No se puede hacer new Empleado(...). Las hijas definen el cálculo.
 */
public abstract class Empleado {

    // Atributos privados (encapsulación)
    private String dpi;
    private String nombre;

    // Constructor sin tipo de retorno. "dpi" y "nombre" son PARÁMETROS;
    // this.dpi y this.nombre son los ATRIBUTOS.
    public Empleado(String dpi, String nombre) {
        this.dpi = dpi;
        this.nombre = nombre;
    }

    // Getters (métodos de acceso)
    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    // Métodos abstractos: sin cuerpo, cada hija los sobrescribe
    public abstract double calcularPago();

    public abstract String obtenerTipo();
}
