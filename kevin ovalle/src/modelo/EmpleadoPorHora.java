package modelo;

// Autor: Kevin Ovalle - Programación II - Práctica 11: Nómina mensual

public class EmpleadoPorHora extends Empleado {

    private double pagoPorHora;
    private double horasTrabajadas;

    public EmpleadoPorHora(String dpi, String nombre, double pagoPorHora, double horasTrabajadas) {
        super(dpi, nombre);
        this.pagoPorHora = pagoPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getPagoPorHora() {
        return pagoPorHora;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    @Override
    public double calcularPago() {
        return pagoPorHora * horasTrabajadas;
    }

    @Override
    public String obtenerTipo() {
        return "Por hora";
    }
}
