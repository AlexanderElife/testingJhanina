import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class main {

    public static void main(String[] args) {
        SopaDeLetras sopa = new SopaDeLetras();
        Scanner sc = new Scanner(System.in);
        ArbolB arbol = new ArbolB();
        System.out.println("Ingrese el número de palabras que desee agregar:");
        int nPalabras = Integer.parseInt(sc.nextLine());

        List<String> palabrasPredefinidas = new ArrayList<>();
        for (int i = 0; i < nPalabras; i++) {
            System.out.println("Ingrese la palabra: " + (i + 1) + ":");
            String palabra = sc.nextLine();
            palabrasPredefinidas.add(palabra);
        }
       /* palabrasPredefinidas.add("JAVA");
        palabrasPredefinidas.add("CODIGO");
        palabrasPredefinidas.add("ALGORITMO");
        palabrasPredefinidas.add("COMPUTADORA");
        palabrasPredefinidas.add("PROGRAMACION");*/

        sopa.llenarSopa();
        sopa.insertarPalabrasAleatorias(palabrasPredefinidas);

        System.out.println("Ingrese el numero de jugadores:");
        int nPlayers = Integer.parseInt(sc.nextLine());

        List<Jugador> jugadores = new ArrayList<>();
        for (int i = 0; i < nPlayers; i++) {
            System.out.println("Ingrese el nombre del jugador: " + (i + 1) + ":");
            String nombre = sc.nextLine();
            jugadores.add(new Jugador(nombre));
        }

        int turno = 0;
        boolean cerrar = false;

        do {
            Jugador jugadorActual = jugadores.get(turno);
            System.out.println("Turno de " + jugadorActual.getNombre() + ":");
            System.out.println("La sopa de letras es:");
            sopa.imprimirSopa();
            
            System.out.println("Ingresa las coordenadas iniciales (fila.columna) y finales (fila.columna) de la palabra a buscar, separadas por un espacio:");
            String[] coordenadas = sc.nextLine().split(" ");
            int filaInicio = Integer.parseInt(coordenadas[0].split("\\.")[0]);
            int columnaInicio = Integer.parseInt(coordenadas[0].split("\\.")[1]);
            int filaFin = Integer.parseInt(coordenadas[1].split("\\.")[0]);
            int columnaFin = Integer.parseInt(coordenadas[1].split("\\.")[1]);
            System.out.println(filaInicio +""+columnaInicio+"  "+filaFin+""+columnaFin);
            long startTime = System.currentTimeMillis();
            boolean encontrado = false;
    
            while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(30)) {
                System.out.print("Escribe la palabra a buscar: ");
                String cadena = sc.nextLine();
                if (sopa.resolverSopa(cadena, filaInicio, columnaInicio, filaFin, columnaFin)) {
                    jugadorActual.agregarPuntaje(cadena.length());
                    encontrado = true;
                    break;
                }
            }
    
            if (!encontrado) {
                System.out.println("Tiempo agotado para " + jugadorActual.getNombre());
            }
    
            turno = (turno + 1) % nPlayers;
    
            System.out.print("¿Deseas continuar con la ejecución? (y/n) ");
            String next = sc.nextLine();
            if (next.equalsIgnoreCase("n")) {
                cerrar = true;
            }
    
        } while (!cerrar);

        sc.close();

        for (Jugador jugador : jugadores) {
            arbol.insertar(jugador);
        }

        System.out.println("Resultados finales:");
        arbol.imprimirEnOrden();
    }
}
