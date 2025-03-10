package Helper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Random;

import FolderStructure.Carpeta;
import Imagen.Imagen;

public class Helper {
	
	/**
	 * Inicializa las carpetas creando tanto dichas carpetas como las imagenes que habrá dentro de ellas
	 * @param nivelesMax: número max de niveles de carpetas a crear
	 * @param carpetasMaxPorNivel: número max de carpetas a crear por 1 nivel
	 */
	public static void InicializarCarpetas(int nivelesMax, int carpetasMaxPorNivel) {
		Carpeta.generarCarpetas(nivelesMax, carpetasMaxPorNivel);
		String ruta = "data/";
		
		try {
            Files.walkFileTree(Paths.get(ruta), new fileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}