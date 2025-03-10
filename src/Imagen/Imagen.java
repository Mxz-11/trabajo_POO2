package Imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import java.awt.Graphics2D;


public class Imagen {
	private final int id;
	private static int contador = 0;
	private int alto;
	private int ancho;
	private String ruta;
	private metadata metadatos;

	
	public Imagen(String ru) {
		ruta = ru;
		id = contador;
		contador++;
	}

	/**
	 * Crea una imagen con elementos pseudoaleatorios, usando la biblioteca Graphics2D
	 * 
	 */
	public void creaImagen() {
		Random rand = new Random();
		alto = rand.nextInt(1920 - 200) + 200;
		ancho = rand.nextInt(1920 - 200) + 200;
	    BufferedImage bufferedImage = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

	    // Obtener el objeto Graphics2D de la imagen
	    Graphics2D g2d = bufferedImage.createGraphics();
	    int color;
	    int terminar = rand.nextInt(50);
	    
	    Color[] colors = new Color[30];
	    
	    colors[0] = Color.BLACK;
        colors[1] = Color.BLUE;
        colors[2] = Color.CYAN;
        colors[3] = Color.DARK_GRAY;
        colors[4] = Color.GRAY;
        colors[5] = Color.GREEN;
        colors[6] = Color.LIGHT_GRAY;
        colors[7] = Color.MAGENTA;
        colors[8] = Color.ORANGE;
        colors[9] = Color.PINK;
        colors[10] = Color.RED;
        colors[11] = Color.WHITE;
        colors[12] = Color.YELLOW;
        colors[13] = new Color(255, 128, 0); // Naranja
        colors[14] = new Color(128, 0, 255); // Violeta
        colors[15] = new Color(255, 0, 128); // Rosa
        colors[16] = new Color(0, 255, 128); // Verde claro
        colors[17] = new Color(128, 255, 0); // Verde amarillo
        colors[18] = new Color(0, 128, 255); // Azul claro
        colors[19] = new Color(255, 0, 0); // Rojo
        colors[20] = new Color(0, 255, 0); // Verde
        colors[21] = new Color(0, 0, 255); // Azul
        colors[22] = new Color(255, 255, 0); // Amarillo
        colors[23] = new Color(255, 0, 255); // Magenta
        colors[24] = new Color(0, 255, 255); // Cian
        colors[25] = new Color(192, 192, 192); // Plata
        colors[26] = new Color(128, 128, 128); // Gris
        colors[27] = new Color(128, 0, 0); // Marrón
        colors[28] = new Color(0, 128, 0); // Verde oscuro
        colors[29] = new Color(0, 0, 128); // Azul oscuro
        
        for (int i = 0; i < 50; i++) { // Se cambia el bucle infinito a un bucle finito
            int colorIndex = rand.nextInt(30);
            g2d.setColor(colors[colorIndex]);
            
            int x1 = rand.nextInt(ancho);
            int y1 = rand.nextInt(alto);
            int x2 = rand.nextInt(ancho);
            int y2 = rand.nextInt(alto);
            
            // Dibujar una figura aleatoria
            int figura = rand.nextInt(3);
            switch (figura) {
                case 0: // Rectángulo
                    int width = rand.nextInt(ancho - x1);
                    int height = rand.nextInt(alto - y1);
                    g2d.fillRect(x1, y1, width, height);
                    break;
                case 1: // Óvalo
                    int diameter1 = rand.nextInt(ancho - x1);
                    int diameter2 = rand.nextInt(alto - y1);
                    g2d.fillOval(x1, y1, diameter1, diameter2);
                    break;
                case 2: // Línea
                    g2d.drawLine(x1, y1, x2, y2);
                    break;
            }
        }
        
        // Liberar recursos
        g2d.dispose();
	    
	    // Guardar la imagen en un archivo
	    try {
	        ImageIO.write(bufferedImage, "jpg", new File(ruta));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * 
	 * @return Imagen.ruta
	 */
	public String getRuta() {
		return ruta;
	}
	
	/**
	 * 
	 * @return Imagen.metadatos
	 */
	public metadata getMetadata() {
		return metadatos;
	}
	
	@Override
	public String toString() {
		return metadatos.toString() + "\n";
	}
	
}
