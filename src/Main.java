import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner scanner;

    public Main() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main vista = new Main();
        vista.ejecutar();
    }

    private void ejecutar() {
        mostrarBienvenida();

        List<Digimon> catalogo = crearCatalogo();
        mostrarCatalogo(catalogo);

        Entrenador entrenador1 = new Entrenador(leerTexto("Nombre del entrenador 1: "));
        Entrenador entrenador2 = new Entrenador(leerTexto("Nombre del entrenador 2: "));

        registrarEntrenadores(entrenador1, entrenador2, catalogo);

        Combate combate = new Combate(entrenador1, entrenador2, this);
        combate.iniciarBatalla();

        for (int ronda = 1; ronda <= 4; ronda++) {
            mostrarMensaje("RONDA " + ronda);

            mostrarMensaje("Turno de " + entrenador1.getNombre());
            Digimon d1 = elegirDigimonRonda(entrenador1);
            boolean usoHab1 = preguntarUsoHabilidad(d1);

            mostrarMensaje("Turno de " + entrenador2.getNombre());
            Digimon d2 = elegirDigimonRonda(entrenador2);
            boolean usoHab2 = preguntarUsoHabilidad(d2);

            String resultado = combate.ejecutarRonda(d1, usoHab1, d2, usoHab2);
            mostrarResultadoRonda(resultado);
            mostrarMarcador(entrenador1, entrenador2);
        }

        Entrenador ganador = combate.determinarGanadorFinal();
        mostrarGanadorFinal(ganador, entrenador1, entrenador2);

        scanner.close();
    }

    public List<Digimon> crearCatalogo() {
        List<Digimon> catalogo = new ArrayList<>();

        catalogo.add(new Digimon("Agumon", "Fuego", 50, 30,
                new Digievolucion("Greymon", "ataque", 15, 40)));
        catalogo.add(new Digimon("Gabumon", "Planta", 45, 35,
                new Digievolucion("Garurumon", "defensa", 10, 35)));
        catalogo.add(new Digimon("Gomamon", "Agua", 40, 40,
                new Digievolucion("Ikkakumon", "ataque", 20, 30)));
        catalogo.add(new Digimon("Patamon", "Electrico", 42, 32, 
                new Digievolucion("Angemon", "dano", 10, 45)));
        catalogo.add(new Digimon("Palmon", "Planta", 38, 38,
                new Digievolucion("Togemon", "ataque", 12, 50)));
        catalogo.add(new Digimon("Biyomon", "Fuego", 48, 28,
                new Digievolucion("Birdramon", "dano", 15, 30)));
        catalogo.add(new Digimon("Tentomon", "Electrico", 44, 36,
                new Digievolucion("Kabuterimon", "ataque", 18, 25)));
        catalogo.add(new Digimon("Betamon", "Agua", 46, 30,
                new Digievolucion("Seadramon", "defensa", 15, 40)));

        return catalogo;
    }

    public void registrarEntrenadores(Entrenador entrenador1, Entrenador entrenador2, List<Digimon> catalogo) {
        mostrarMensaje(entrenador1.getNombre() + ", elige 4 Digimon (uno por uno):");
        entrenador1.elegirEquipo(seleccionarCuatroDigimon(catalogo));
        mostrarEquipoSeleccionado(entrenador1);

        mostrarMensaje(entrenador2.getNombre() + ", elige 4 Digimon (uno por uno):");
        entrenador2.elegirEquipo(seleccionarCuatroDigimon(catalogo));
        mostrarEquipoSeleccionado(entrenador2);
    }

    public Digimon elegirDigimonRonda(Entrenador entrenador) {
        mostrarDigimonDisponiblesEntrenador(entrenador);
        List<Digimon> equipo = entrenador.getEquipo();
        Digimon elegido = null;
        while (elegido == null) {
            System.out.print("Elige el numero del Digimon a usar: ");
            int indice = leerEntero() - 1;
            if (indice < 0 || indice >= equipo.size() || equipo.get(indice).usadoRonda()) {
                mostrarMensaje("Opcion invalida, intenta de nuevo.");
                continue;
            }
            elegido = equipo.get(indice);
        }
        return elegido;
    }

    public boolean preguntarUsoHabilidad(Digimon digimon) {
        System.out.print("Quieres usar la habilidad especial de " + digimon.getNombre()
                + " (" + digimon.getDigievolución().getNombre() + ")?");
        String respuesta = scanner.next();
        return respuesta.equalsIgnoreCase("si");
    }

    private List<Digimon> seleccionarCuatroDigimon(List<Digimon> catalogo) {
        List<Digimon> seleccion = new ArrayList<>();
        while (seleccion.size() < 4) {
            System.out.print("Numero de Digimon (" + (seleccion.size() + 1) + "/4): ");
            int indice = leerEntero() - 1;
            if (indice < 0 || indice >= catalogo.size()) {
                mostrarMensaje("Numero invalido, intenta de nuevo.");
                continue;
            }
            Digimon elegido = catalogo.get(indice);
            if (seleccion.contains(elegido)) {
                mostrarMensaje("Ese Digimon ya fue elegido, elige uno distinto.");
                continue;
            }
            seleccion.add(elegido);
        }
        return seleccion;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.print("Ingresa un numero valido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); 
        return valor;
    }

    public void mostrarBienvenida() {
        System.out.println("   BATALLA DIGIMON - 4 RONDAS");
    }

    public void mostrarCatalogo(List<Digimon> catalogo) {
        System.out.println("Digimon disponibles:");
        for (int i = 0; i < catalogo.size(); i++) {
            Digimon d = catalogo.get(i);
            System.out.println((i + 1) + ". " + d + " | Digievolucion: "
                    + d.getDigievolución().getNombre()
                    + " (" + d.getDigievolución().getTipoEfecto()
                    + " " + d.getDigievolución().getValorEfecto()
                    + ", " + d.getDigievolución().getProbabilidad() + "% probabilidad)");
        }
    }

    public void mostrarEquipoSeleccionado(Entrenador entrenador) {
        System.out.println("Equipo de " + entrenador.getNombre() + ":");
        for (Digimon d : entrenador.getEquipo()) {
            System.out.println("  - " + d);
        }
    }

    public void mostrarDigimonDisponiblesEntrenador(Entrenador entrenador) {
        System.out.println("Digimon disponibles de " + entrenador.getNombre() + ":");
        List<Digimon> equipo = entrenador.getEquipo();
        for (int i = 0; i < equipo.size(); i++) {
            Digimon d = equipo.get(i);
            if (!d.usadoRonda()) {
                System.out.println((i + 1) + ". " + d);
            }
        }
    }

    public void mostrarResultadoRonda(String resultado) {
        System.out.println(resultado);
    }

    public void mostrarMarcador(Entrenador e1, Entrenador e2) {
        System.out.println("Marcador: " + e1.getNombre() + ": " + e1.getRondasGanadas()
                + " | " + e2.getNombre() + ": " + e2.getRondasGanadas());
    }

    public void mostrarGanadorFinal(Entrenador ganador, Entrenador e1, Entrenador e2) {
        if (ganador == null) {
            System.out.println("Resultado final: EMPATE");
        } else {
            System.out.println("Ganador: " + ganador.getNombre());
        }
        System.out.println(e1.getNombre() + " ganó " + e1.getRondasGanadas() + " rondas");
        System.out.println(e2.getNombre() + " ganó " + e2.getRondasGanadas() + " rondas");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
