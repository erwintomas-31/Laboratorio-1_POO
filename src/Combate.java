public class Combate {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private int rondaActual;
    private Main main;
    private int bonusPendiente1;
    private int bonusPendiente2;
    private int penalizacionPendiente1;
    private int penalizacionPendiente2;

    public void iniciarBatalla() {
        rondaActual = 0;
        bonusPendiente1 = 0;
        bonusPendiente2 = 0;
        penalizacionPendiente1 = 0;
        penalizacionPendiente2 = 0;
    }

    public Combate(Entrenador entrenador1, Entrenador entrenador2, Main main) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.main = main;
        this.rondaActual = 0;
    }

    public int calcularEfectoTipo(String tipoAtacante, String tipoDefensor) {
        if (tipoVence(tipoAtacante, tipoDefensor)) {
            return 20;
        }
        if (tipoVence(tipoDefensor, tipoAtacante)) {
            return -10;
        }
        return 0;
    }

    private boolean tipoVence(String tipoA, String tipoB) {
        return (tipoA.equalsIgnoreCase("Fuego") && tipoB.equalsIgnoreCase("Planta"))
            || (tipoA.equalsIgnoreCase("Planta") && tipoB.equalsIgnoreCase("Agua"))
            || (tipoA.equalsIgnoreCase("Agua") && tipoB.equalsIgnoreCase("Fuego"))
            || (tipoA.equalsIgnoreCase("Electrico") && tipoB.equalsIgnoreCase("Agua"));
    }

    public int calcularAtaqueTotal(Digimon atacante, Digimon defensor, boolean usoHab) {
        int total = atacante.getAtaque();
        total += calcularEfectoTipo(atacante.getTipo(), defensor.getTipo());
        if (usoHab) {
            Digievolucion evo = atacante.getDigievolución();
            if (evo.aplicarEfecto(atacante) && evo.getTipoEfecto().equalsIgnoreCase("ataque")) {
                total += evo.getValorEfecto();
            }
        }
        return total;
    }

    public String ejecutarRonda(Digimon d1, boolean usoHab1, Digimon d2, boolean usoHab2) {
        rondaActual++;
        d1.marcarUsado();
        d2.marcarUsado();

        int total1 = calcularAtaqueTotal(d1, d2, usoHab1);
        int total2 = calcularAtaqueTotal(d2, d1, usoHab2);

        total1 += bonusPendiente1 - penalizacionPendiente1;
        total2 += bonusPendiente2 - penalizacionPendiente2;
        bonusPendiente1 = 0;
        bonusPendiente2 = 0;
        penalizacionPendiente1 = 0;
        penalizacionPendiente2 = 0;

        if (usoHab1 && d1.getDigievolución().efectoActivo()) {
            Digievolucion evo = d1.getDigievolución();
            if (!evo.getTipoEfecto().equalsIgnoreCase("ataque")) {
                total2 -= evo.getValorEfecto();
                penalizacionPendiente2 = evo.getValorEfecto();
            } else {
                bonusPendiente1 = evo.getValorEfecto();
            }
            evo.reducirDuración();
        }

        if (usoHab2 && d2.getDigievolución().efectoActivo()) {
            Digievolucion evo = d2.getDigievolución();
            if (!evo.getTipoEfecto().equalsIgnoreCase("ataque")) {
                total1 -= evo.getValorEfecto();
                penalizacionPendiente1 = evo.getValorEfecto();
            } else {
                bonusPendiente2 = evo.getValorEfecto();
            }
            evo.reducirDuración();
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("Ronda ").append(rondaActual).append(": ").append(entrenador1.getNombre()).append(" usa ").append(d1.getNombre())
                .append(" (ataqueTotal=").append(total1).append(") vs ")
                .append(entrenador2.getNombre()).append(" usa ").append(d2.getNombre())
                .append(" (ataqueTotal=").append(total2).append(")\n");

        if (total1 > total2) {
            entrenador1.sumarRondaGanada();
            resultado.append("Gana ").append(entrenador1.getNombre());
        } else if (total2 > total1) {
            entrenador2.sumarRondaGanada();
            resultado.append("Gana ").append(entrenador2.getNombre());
        } else {
            resultado.append("Empate en esta ronda");
        }
        return resultado.toString();
    }

    public Entrenador determinarGanadorFinal() {
        if (entrenador1.getRondasGanadas() > entrenador2.getRondasGanadas()) {
            return entrenador1;
        } else if (entrenador2.getRondasGanadas() > entrenador1.getRondasGanadas()) {
            return entrenador2;
        }
        return null;
    }

    public int getRondaActual() {
        return rondaActual;
    }
}
