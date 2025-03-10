package Imagen;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

/**
 * Clase que gestiona los metadatos de una imagen
 */
public class metadata implements Comparable<metadata> {	
	private String iso;
	private String flash;
	private String creador; 
	private int alto;	
	private int ancho;	
	private String fecha; //"Año:mes:dia hora(2 digitos):min:seg" PE: 2022:01:02 00:00:00
	private String ruta;
	private long tam;
	
	
	/**
	 * Crea una instancia de la clase metadata, siempre que la ruta que se pasa por parámetro
	 * sea la ruta real de una imagen.
	 */
	public metadata(String ruta) {
		this.ruta = ruta;
		File imagen = new File(ruta);
		BufferedImage img;
		try {
			img = ImageIO.read(imagen);
			alto = img.getHeight();
			ancho = img.getWidth();
			Path path = Paths.get(ruta);
			tam = Files.size(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Actualiza los valores de la clase metadata con los valores que es encuentran en la imagen que se encuentra en la
	 * ruta metadata.ruta. Siempre que esta sea de tipo ".jpg" o ".jpeg".
	 * @throws ImagingException
	 * @throws IOException
	 */
	public void getMet() throws ImagingException, IOException {
		File imagen = new File(ruta);
		if (ruta.endsWith(".jpg") || ruta.endsWith(".jpeg")) {
			JpegImageMetadata m = (JpegImageMetadata) Imaging.getMetadata(imagen);

			if(m.getExif() != null) {
				TiffImageMetadata t = m.getExif();
				TiffField a = null;
				
				if (t.findField(ExifTagConstants.EXIF_TAG_ISO) != null) {
					a = t.findField(ExifTagConstants.EXIF_TAG_ISO);
					iso = a.getValueDescription();
				}
				if(t.findField(ExifTagConstants.EXIF_TAG_CAMERA_OWNER_NAME) != null) {
					a = t.findField(ExifTagConstants.EXIF_TAG_CAMERA_OWNER_NAME);
					creador = a.getValueDescription();
				}
				if(t.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL) != null) {
					a = t.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
					fecha = a.getValueDescription();
				}
				if(t.findField(ExifTagConstants.EXIF_TAG_FLASH) != null) {
					a = t.findField(ExifTagConstants.EXIF_TAG_FLASH);
					flash = new String(a.getValueDescription());
				}							
			}
		}
	}
	
	/**
	 * Cambia los valores Exif de las constantes (Fecha, Creador, ISO y Flash) de la imagen que se encuentra en la 
	 * ruta metadata.ruta. Siempre que esta sea de tipo ".jpg" o ".jpeg".
	 * @throws IOException
	 * @throws ImagingException
	 * @throws ImagingException
	 */
	public void setMetadata() throws IOException, ImagingException, ImagingException {
		File jpegImageFile = new File(ruta);
		TiffOutputSet out = null;
		ImageMetadata metadata = Imaging.getMetadata(jpegImageFile);
        JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        if (null != jpegMetadata) {
            TiffImageMetadata exif = jpegMetadata.getExif();

            if (null != exif) {
            	out = exif.getOutputSet();
            }
        }
        
        if (null == out) {
        	out = new TiffOutputSet();
        }
        
        TiffOutputDirectory exifDirectory = out.getOrCreateExifDirectory();

        if (fecha != null) {
        	exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
        	exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, fecha);	
	    }
        
		if (creador != null) {
        	exifDirectory.removeField(ExifTagConstants.EXIF_TAG_CAMERA_OWNER_NAME);
        	exifDirectory.add(ExifTagConstants.EXIF_TAG_CAMERA_OWNER_NAME, creador);
	    }
        
		if (iso != null) {
	        exifDirectory.removeField(ExifTagConstants.EXIF_TAG_ISO);
	        exifDirectory.add(ExifTagConstants.EXIF_TAG_ISO, Short.parseShort(iso));
	    }
		if (flash != null) {
	        exifDirectory.removeField(ExifTagConstants.EXIF_TAG_FLASH);
	        exifDirectory.add(ExifTagConstants.EXIF_TAG_FLASH, Short.parseShort(flash));
	    }
        
            ExifRewriter modificadorMetadatos =new ExifRewriter();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            modificadorMetadatos.updateExifMetadataLossless(jpegImageFile, baos, out);

            FileOutputStream fos = new FileOutputStream(jpegImageFile);

            fos.write(baos.toByteArray());

            fos.close();
    }
	
	/**
	 * Actualiza el valor metadata.fecha
	 * @param iso: nuevo valor de metadata.iso
	 */
	public void setIso(String iso) {
		this.iso = iso;
	}
	
	/**
	 * Actualiza el valor metadata.fecha
	 * @param creador: nuevo valor de metadata.creador
	 */
	public void setCreador(String creador) {
		this.creador = creador;
	}
	
	/**
	 * Actualiza el valor metadata.fecha
	 * @param flash: nuevo valor de metadata.flash
	 */
	public void setFlash(String flash) {
		this.flash = flash;
	}
	
	/**
	 * Actualiza el valor metadata.fecha
	 * @param fecha: nuevo valor de metadata.fecha
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve metadata.iso
	 * @return metadata.iso
	 */
	public String getIso() {
		return iso;
	}

	/**
	 * Devuelve metadata.creador
	 * @return metadata.creador
	 */
	public String getCreador() {
		return creador;
	}
	
	/**
	 * Devuelve metadata.flash
	 * @return metadata.flash
	 */
	public String getFlash() {
		return flash;
	}
	
	/**
	 * Devuelve metadata.fecha
	 * @return metadata.fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Devuelve metadata.alto
	 * @return metadata.alto
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * Devuelve metadata.ancho
	 * @return metadata.ancho
	 */
	public int getAncho() {
		return ancho;
	}
	
	/**
	 * Devuelve metadata.tam
	 * @return metadata.tam
	 */
	public long getTam() {
		return tam;
	}
	
	/**
	 * Devuelve metadata.ruta
	 * @return metadata.ruta
	 */
	public String getRuta() {
		return ruta;
	}
	
	@Override
	public String toString() {
		return "Imagen " + ruta + "\n "
				+ "ISO: " + iso + "\n"
				+ "Creador: " + creador
				+ "\n" + "Fecha: " + fecha
				+ "\n" + "Alto: " + alto + "\n" + "Ancho: " + ancho + "\n"
				+ "Tamanio: " + tam;
	}

	/**
	 * Compara la fecha de dos instancias de la clase metadata
	 */
	@Override
	public int compareTo(metadata other) {
		return this.fecha.compareTo(other.fecha);
    }
	
	/**
	 * Compara el ISO de dos instancias de la clase metadata
	 * @param other: instancia de la clase metadata
	 * @return -1 SI other.iso > iso || 1 SI iso > other.iso || 0 en cualquier otro caso
	 */
	public int compareToIso(metadata other) {
        if (Integer.parseInt(this.iso) > Integer.parseInt(other.iso)) {
        	return 1;
        } else if(Integer.parseInt(this.iso) < Integer.parseInt(other.iso)) {
        	return -1;
        }
        return 0;
    }
}
