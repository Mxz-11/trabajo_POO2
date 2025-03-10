package Imagen;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class ColeccionImagenes {
	private List<metadata> lista = new ArrayList<>();
	
	public ColeccionImagenes() {
		
	}
	
	/**
	 * Añade a la lista de metadatos de la colección un nuevo elemento
	 * @param i: Metadato a añadir a la colección
	 */
	public void addMetadata(metadata i) {
		lista.add(i);
	}
	
	/**
	 * Devuelve la lista de elementos metadata en la colección
	 * @return
	 */
	public List<metadata> getMetadata(){
		return lista;
	}
	
	/**
	 * Analiza las carpetas que existen y recopila los metadatos de aquellas que sean ".jpeg" o ".jpg"
	 * @param ruta: Ruta de datos en la que se pretende mirar
	 * @throws IOException
	 */
	public void analizador(String ruta) throws IOException {
		List<metadata> listaMetadata = new ArrayList<>();

        Files.walkFileTree(Paths.get(ruta), EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".jpg") || file.toString().endsWith(".jpeg")) {
                    metadata met = new metadata(file.toString());
                    met.getMet();

                    lista.add(met); // los metadatos a la lista

                }
                return FileVisitResult.CONTINUE;
            }
        });
	}
	
	/**
	 * Ordena por fecha los elementos de la lista por orden creciente en función del parámetro metadata.fecha
	 */
	public void ordenarPorFechar() {
		Collections.sort(lista);
	}
	
	/**
	 * Ordena por ISO los elementos de la lista por orden creciente en función del parámetro metadata.ISO
	 */
	public void ordenarPorIso() {
		Collections.sort(lista, (m1, m2) -> m1.compareToIso(m2));
    }

	/**
	 * Filtra las fotos de la coleccion en función del parámetro metadata.ancho
	 * @param min: Valor minimo para filtrar
	 * @return elementos de la colección con mayor ancho que min
	 */
	public List<metadata> filtrarPorAnchoMayor(int min){
		List<metadata> fotosFiltradas = new ArrayList<>();

        for (metadata i : lista) {
            int ancho = i.getAncho();
            if (ancho > min) {
                fotosFiltradas.add(i);
            }
        }
        return fotosFiltradas;
	}
	
	/**
	 * Filtra las fotos de la colección en función del parámetro metadata.alto
	 * @param min: Valor minimo para filtrar
	 * @return elementos de la colección con mayor alto que min
	 */
	public List<metadata> filtrarPorAltoMayor(int min){
		List<metadata> fotosFiltradas = new ArrayList<>();

        for (metadata i : lista) {
            int alto = i.getAlto();
            if (alto > min) {
                fotosFiltradas.add(i);
            }
        }
        return fotosFiltradas;
	}
	
	/**
	 * Filtra las fotos de la colección en función del parámetro metadata.ISO
	 * @param min: Valor minimo para filtrar
	 * @return elementos de la colección con mayor ISO que min
	 */
	public List<metadata> filtrarPorIsoMayor(int min) {
        List<metadata> fotosFiltradas = new ArrayList<>();

        for (metadata i : lista) {
            int iso = Integer.parseInt(i.getIso());
            if (iso > min) {
                fotosFiltradas.add(i);
            }
        }

        return fotosFiltradas;
    }
	
	/**
	 * Filtra las fotos de la colección en función del parámetro metadata.ancho
	 * @param max: Valor maximo para filtrar
	 * @return elementos de la colección con menor ancho que max
	 */
	public List<metadata> filtrarPorAnchoMmenor(int max){
		List<metadata> fotosFiltradas = new ArrayList<>();

        for (metadata i : lista) {
            int ancho = i.getAncho();
            if (ancho < max) {
                fotosFiltradas.add(i);
            }
        }
        return fotosFiltradas;
	}
	
	/**
	 * Filtra las fotos de la colección en función del parámetro metadata.alto
	 * @param max: Valor maximo para filtrar
	 * @return elementos de la colección con menor alto que max
	 */
	public List<metadata> filtrarPorAltoMenor(int max){
		List<metadata> fotosFiltradas = new ArrayList<>();

        for (metadata i : lista) {
            int alto = i.getAlto();
            if (alto < max) {
                fotosFiltradas.add(i);
            }
        }
        return fotosFiltradas;
	}
	
	/**
	 * Filtra las fotos de la colección en función del parámetro metadata.ISO
	 * @param max: Valor maximo para filtrar
	 * @return elementos de la colección con menor ISO que max
	 */
	public List<metadata> filtrarPorIsoMenor(int max) {
        List<metadata> fotosFiltradas = new ArrayList<>();

        for (metadata i : lista) {
            int iso = Integer.parseInt(i.getIso());
            if (iso < max) {
                fotosFiltradas.add(i);
            }
        }

        return fotosFiltradas;
    }
	
}
