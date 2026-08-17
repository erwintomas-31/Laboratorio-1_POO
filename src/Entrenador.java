import java.util.ArrayList;
import java.util.List;

public class Entrenador {
    private String nombre; 
    private List<Digimon> equipo;
    private int rondasGanadas;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ArrayList<>();
        this.rondasGanadas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getRondasGanadas() {
        return rondasGanadas;
    }

    public List<Digimon> getEquipo() {
        return equipo;
    }
    
    public void elegirEquipo(List<Digimon> digimonsDisponibles) {
        this.equipo = new ArrayList<>(digimonsDisponibles);
    }

    public Digimon elegirDigimon() {
        for (Digimon digimon : equipo) {
            if (!digimon.usadoRonda()) {
                return digimon;
            }
        }
        return null; 
    }

    public void sumarRondaGanada() {
        this.rondasGanadas++;
    }
}
