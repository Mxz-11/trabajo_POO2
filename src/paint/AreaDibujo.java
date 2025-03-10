package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

public class AreaDibujo extends JComponent {

	  private Image imagen;
	  private Graphics2D gui2d;
	  private int actX, actY, oldX, oldY;
	 
	  /**
	   * Forma de controlar las acciones del raton
	   */
	  public AreaDibujo() {
		  setDoubleBuffered(false);
		  addMouseListener(new MouseAdapter() {
	      public void mousePressed(MouseEvent e) {
	    	  oldX = e.getX();
	    	  oldY = e.getY();
	      }
	  });
	 
		  addMouseMotionListener(new MouseMotionAdapter() {
			  public void mouseDragged(MouseEvent e) {
				  actX = e.getX();
				  actY = e.getY();
	 
		    	  if (gui2d != null) {
		    		  gui2d.drawLine(oldX, oldY, actX, actY);
		    		  repaint();
		    		  oldX = actX;
		    		  oldY = actY;
		    	  }
			  }
		  });
	  }
	 
	  /**
	   * Función para pintar dentro del interfaz gráfico gui2d
	   */
	  protected void paintComponent(Graphics g) {
		  if (imagen == null) {
			  imagen = createImage(getSize().width, getSize().height);
			  gui2d = (Graphics2D) imagen.getGraphics();
			  gui2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			  clear();
		  }
	 
		  g.drawImage(imagen, 0, 0, null);
	  }
	 
	  /**
	   * Función para resetear lo hecho
	   */
	  public void clear() {
		  gui2d.setPaint(Color.white);
		  gui2d.fillRect(0, 0, getSize().width, getSize().height);
		  gui2d.setPaint(Color.black);
		  repaint();
	  }
	 
	  /**
	   * Función que pone el color rojo
	   */
	  public void red() {
		  gui2d.setPaint(Color.red);
	  }
	  
	  /**
	   * Función que pone el color negro
	   */
	  public void black() {
		  gui2d.setPaint(Color.black);
	  }
	  
	  /**
	   * Función que pone el color magenta
	   */
	  public void magenta() {
		  gui2d.setPaint(Color.magenta);
	  }
	 
	  /**
	   * Función que pone el color verde
	   */
	  public void green() {
		  gui2d.setPaint(Color.green);
	  }
	 
	  /**
	   * Función que pone el color azul
	   */
	  public void blue() {
		  gui2d.setPaint(Color.blue);
	  }
	  
	  /**
	   * Función que pone el color naranja
	   */
	  public void orange() {
		  gui2d.setPaint(Color.ORANGE);
	  }
	  
	  /**
	   * Función que pone el color rosa
	   */
	  public void pink() {
		  gui2d.setPaint(Color.PINK);
	  }
}
