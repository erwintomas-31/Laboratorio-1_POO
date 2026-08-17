public class Digimon {
    private String nombre;
    private String tipo;
    private int ataque;
    private int defensa;
    private boolean usadoRonda;
    private Digievolución digievolución;

    public Digimon(String nombre, String tipo, int ataque, int defensa) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.usadoRonda = false;
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

    public Digievolución getDigievolución() {
        return digievolución;
    }

    @Override
    public String toString() {
        return "Digimon{" + "nombre='" + nombre + ", tipo='" + tipo + ", ataque=" + ataque + ", defensa=" + defensa + '}';
    }

}
