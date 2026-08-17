public class Digimon {
    private String nombre;
    private String tipo;
    private int ataque;
    private int defensa;
    private boolean usadoRonda;
    private Digievolucion digievolución;

    public Digimon(String nombre, String tipo, int ataque, int defensa, Digievolucion digievolución) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.digievolución = digievolución;
    }

    public void marcarUsado() {
        this.usadoRonda = true;
    }

    public boolean usadoRonda() {
        return this.usadoRonda;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public Digievolucion getDigievolución() {
        return digievolución;
    }

    public Digievolucion getDigievolucion() {
        return digievolución;
    }

    @Override
    public String toString() {
        return "Digimon{" + "nombre='" + nombre + "', tipo='" + tipo + "', ataque=" + ataque + ", defensa=" + defensa + '}';
    }
}
