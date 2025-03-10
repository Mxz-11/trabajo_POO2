package Helper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import Imagen.Imagen;

/**
 * Clase para recorrer un directorio contando con subcarpetas
 */
public class fileVisitor implements FileVisitor<Path>{
	@Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Directory: " + dir.toString());
        Imagen img = new Imagen(dir.toString() + "/prueba.jpg");
        img.creaImagen();
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.err.println("Failed to visit file: " + file.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (exc == null) {
            System.out.println("Finished visiting directory: " + dir.toString());
            return FileVisitResult.CONTINUE;
        } else {
            throw exc;
        }
    }
}
