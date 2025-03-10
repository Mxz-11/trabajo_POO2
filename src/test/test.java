package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.imaging.ImagingException;
import org.junit.jupiter.api.Test;

import FolderStructure.Carpeta;
import Imagen.Imagen;
import Imagen.metadata;

class test {

	/**
	 * El test verifica que se crean correctamente las imagenes
	 */
	@Test
	void test() {
		
		Imagen z = new Imagen("z.jpg");
		z.creaImagen();
		
		File file = new File("z.jpg");
		assertEquals(true, file.exists());
	}

	/**
	 * El test verifica que al llamar a la función generarCarpetas por lo menos crea 1 directorio
	 * con el nombre pertinente, en este caso "photos-year2000_0_0", por ser el primero
	 */
	@Test
	void test2() {
		Carpeta.generarCarpetas(5, 5);
		File directory = new File("data/photos-year2000_0_0");
		assertEquals(true, directory.exists() && directory.isDirectory());
	}
}
