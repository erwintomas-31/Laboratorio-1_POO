import java.util.Random;

public class Digievolucion {
    private String nombre;
    private String tipoEfecto;
    private int valorEfecto;
    private int probabilidad;
    private int duración;

    public Digievolucion(String nombre, String tipoEfecto, int valorEfecto, int probabilidad) {
        this.nombre = nombre;
        this.tipoEfecto = tipoEfecto;
        this.valorEfecto = valorEfecto;
        this.probabilidad = probabilidad;
        this.duración = 0;
    }

    public boolean aplicarEfecto(Digimon digimon) {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(101); 
        boolean efectoActivado = numeroAleatorio <= probabilidad;
        if (efectoActivado){
            duración = 4;
        }
        return efectoActivado;
    }

    public boolean efectoActivo() {
        return duración > 0;
    }

    public void reducirDuración() {
        if (duración > 0) {
            duración--;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoEfecto() {
        return tipoEfecto;
    }

    public int getValorEfecto() {
        return valorEfecto;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public int getDuración() {
        return duración;
    }
}
