package FolderStructure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Carpeta {

	/**
	 * Crea un número de carpetas pseudoaleatorias en cada nivel, siendo tanto el número de carpetas
	 * por nivel y el número de niveles pseudoaleatorios.
	 * 
	 * @param nivelesMax: número de niveles max de profundidad de carpetas que se van a crear
	 * @param carpetasMaxPorNivel: número de carpetas max que se van a crear por nivel
	 */
	public static void generarCarpetas(int nivelesMax, int carpetasMaxPorNivel) {
		Random random = new Random();		
		String ruta = "data/";		//Ruta de datos "data/"
		generarCarpetasRecursivas(ruta, nivelesMax, carpetasMaxPorNivel, random, 0, 2000);
	}
	
	/**
	 * Función que se encarga de crear de forma recursiva carpetasMaxPorNivel las carpetas de un nivel para nivelesMax niveles
	 * 
	 * @param ruta: ruta de datos en la que se van a crear las carpetas
	 * @param nivelesMax: número de subcarpetas que se van a crear
	 * @param carpetasMaxPorNivel: número de carpetas que se van a crear por cada nivel
	 * @param random: Instancia para crear números pseudoaleatorios
	 * @param nivelActual: nivel en el que se encuentra la recursividad
	 * @param agnio: parámetro que da nombre a las carpetas
	 */
	private static void generarCarpetasRecursivas(String ruta, int nivelesMax, int carpetasMaxPorNivel, Random random, int nivelActual, int agnio) {
		if (nivelActual >= nivelesMax) {
			
		}else {
			int cantidadCarpetas = random.nextInt(carpetasMaxPorNivel);
			for (int i = 0; i < cantidadCarpetas; i++) {
				String nombreCarpeta = "photos-year" + agnio;
				agnio++;
				File nuevaCarpeta = new File(ruta, nombreCarpeta + "_" + nivelActual + "_" + i);	
				nuevaCarpeta.mkdir();
				System.out.println("Carpeta creada: " + nuevaCarpeta.getAbsolutePath());
				generarCarpetasRecursivas(nuevaCarpeta.getAbsolutePath() + "/", nivelesMax, carpetasMaxPorNivel, random, nivelActual+1, agnio);
			}
		}
	}
}
