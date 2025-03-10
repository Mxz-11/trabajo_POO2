package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.commons.imaging.ImagingException;
import org.apache.commons.io.FilenameUtils;

import FolderStructure.Carpeta;
import Helper.Helper;
import Imagen.ColeccionImagenes;
import Imagen.Imagen;
import Imagen.metadata;
import paint.AreaDibujo;



public class main {	
	
	public static String rootDir;
	
	/**
	 * Actualiza el árbol con la información de la carpeta Folder
	 * @param folder: Carpeta con la que se actualiza el árbol
	 * @param jTree1: Árbol a actualizar con la información de la carpeta folder
	 */
	private static void updateTree(File folder, JTree jTree1) {
        DefaultMutableTreeNode root = createTreeNodes(folder);
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        jTree1.setModel(treeModel);
    }
	
	/**
	 * Crea los nodos en la estructura de un árbol
	 * @param folder: Carpeta que se toma como referencia para añadir nodos
	 * @return el nodo raiz
	 */
	private static DefaultMutableTreeNode createTreeNodes(File folder) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(folder.getName());
        addChildren(root, folder);
        return root;
    }

	/**
	 * Crea los nodos hijos para cada uno de los archivos o directorios que se ubiquen en
	 * el directorio file
	 * @param node: Nodo del árbol en el que se van a ubicar los archivos
	 * @param file: Archivo con el que se van a modificar los datos
	 */
    private static void addChildren(DefaultMutableTreeNode node, File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(f.getName());
                node.add(childNode);
                if (f.isDirectory()) {
                    addChildren(childNode, f);
                }
            }
        }
    }
    
    /**
     * Crea el interfaz gráfico de la aplicación
     */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Libreria de imagenes");
        frame.setSize(1800, 1200);
        
        ColeccionImagenes c = new ColeccionImagenes();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(400, 50));
        menuBar.setBorder(BorderFactory.createEmptyBorder(0, 400, 0, 50));
        
        frame.setLayout(new BorderLayout());
        String[] nombresColumnas = {"Dato", "Valor"};
        
        DefaultTableModel model = new DefaultTableModel(nombresColumnas, 0);
        
        //String rootDir = "/home/max/eclipse-workspace/trabajo";
        //si no existe, la creo
        Path rootPath = Paths.get(System.getProperty("user.home"), "eclipse-workspace", "trabajo");
        rootDir = rootPath.toString();
        if (!Files.exists(rootPath)) {
            // Crea el directorio y cualquier directorio intermedio necesario
            try {
				Files.createDirectories(rootPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        File rootFolder = new File(rootDir);
        DefaultMutableTreeNode root = createTreeNodes(rootFolder);
        JTable jTable1 = new JTable(model);
        JTree jTree1 = new JTree(root);
        JScrollPane jScrollPane2 = new JScrollPane(jTable1);
        JScrollPane jScrollPane1 = new JScrollPane(jTree1);
        frame.getContentPane().add(jScrollPane2, BorderLayout.EAST);
        frame.getContentPane().add(jScrollPane1, BorderLayout.WEST);
        
        
        JLabel imageLabel = new JLabel();
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);
        
        try {
			ImageIcon a = new ImageIcon(ImageIO.read(new File(rootDir + "/DSCN0010.jpg")));
			imageLabel.setIcon(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        JMenu Carpetas = new JMenu("Carpetas");
        JMenu imagenes = new JMenu("Imagenes");
        JMenu meta = new JMenu("Metadatos");
        JMenu dib = new JMenu("Dibujar");
        JMenu filtrar = new JMenu("Filtrar u Organizar");

        
        menuBar.add(Carpetas);
        menuBar.add(imagenes);
        menuBar.add(meta);
        menuBar.add(dib);
        menuBar.add(filtrar);

		JMenuItem crearCarpetas = new JMenuItem("Crear Carpetas");
		JMenuItem abrirCarpetas = new JMenuItem("Abrir Carpeta");
		JMenuItem inicializar = new JMenuItem("Inicializar Carpetas");

		Carpetas.add(crearCarpetas);
		Carpetas.add(abrirCarpetas);
		Carpetas.add(inicializar);

		abrirCarpetas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = fileChooser.getSelectedFile();
                    updateTree(selectedFolder, jTree1);
                    rootDir = selectedFolder.toString();
                }
            }
        });
		
		crearCarpetas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog(frame, "Creación de carpetas", true);
                dialog.setLayout(new GridLayout(3, 2));

                JLabel label1 = new JLabel("Introduzca el número de niveles:");
                JTextField textField1 = new JTextField();
                JLabel label2 = new JLabel("Introduzca el número de carpetas max por nivel:");
                JTextField textField2 = new JTextField();
                JButton submitButton = new JButton("Aceptar");
          
                dialog.add(label1);
                dialog.add(textField1);
                dialog.add(label2);
                dialog.add(textField2);
                dialog.add(new JLabel());
                dialog.add(submitButton);
                
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int niveles = Integer.parseInt(textField1.getText());
                            int CarpetasXNivel = Integer.parseInt(textField2.getText());
                            JOptionPane.showMessageDialog(frame, "Niveles de carpetas: " + niveles + ", Carpetas Maximas por nivel: " + CarpetasXNivel);
                            dialog.dispose();
                            Carpeta.generarCarpetas(niveles, CarpetasXNivel);
                            updateTree(new File(rootDir), jTree1);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Por favor, introduzca datos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
			}
		});
		
		inicializar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(frame, "Creación de carpetas", true);
                dialog.setLayout(new GridLayout(3, 2));

                JLabel label1 = new JLabel("Introduzca el número de niveles:");
                JTextField textField1 = new JTextField();
                JLabel label2 = new JLabel("Introduzca el número de carpetas max por nivel:");
                JTextField textField2 = new JTextField();
                JButton submitButton = new JButton("Aceptar");

                dialog.add(label1);
                dialog.add(textField1);
                dialog.add(label2);
                dialog.add(textField2);
                dialog.add(new JLabel());
                dialog.add(submitButton);

                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int niveles = Integer.parseInt(textField1.getText());
                            int carpetasXNivel = Integer.parseInt(textField2.getText());
                            JOptionPane.showMessageDialog(frame, "Niveles de carpetas: " + niveles + ", Carpetas Máximas por nivel: " + carpetasXNivel);
                            dialog.dispose();
                            Helper.InicializarCarpetas(niveles, carpetasXNivel);
                            updateTree(new File(rootDir), jTree1);

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Por favor, introduzca datos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        });
		
		JMenuItem crearImagen = new JMenuItem("Crear Imagen");
		
		imagenes.add(crearImagen);
		
		crearImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = fileChooser.getSelectedFile();

                    JDialog dialog = new JDialog(frame, "Introduce el nombre de la imagen", true);
                    dialog.setLayout(new GridLayout(2, 2));

                    JLabel label1 = new JLabel("Nombre de la imagen:");
                    JTextField textField1 = new JTextField();
                    JButton submitButton = new JButton("Crear");

                    dialog.add(label1);
                    dialog.add(textField1);
                    dialog.add(new JLabel());
                    dialog.add(submitButton);
                    dialog.pack();
                    dialog.setLocationRelativeTo(frame);

                    submitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nombre = textField1.getText();
                            if (!nombre.toLowerCase().endsWith(".jpg")) {
                                nombre += ".jpg";
                            }
                            String nombreImg = selectedFolder + "/" + nombre;
                            Imagen img = new Imagen(nombreImg);
                            img.creaImagen();
                            JOptionPane.showMessageDialog(frame, "Imagen creada: " + nombreImg);
                            updateTree(new File(rootDir), jTree1);
                            dialog.dispose();
                       
                        }
                    });

                    dialog.setVisible(true);
                }
            }
        });
		
        JButton openButton = new JButton("Seleccionar Carpeta");
        
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = fileChooser.getSelectedFile();
                }
            }
        });
        
        JMenuItem modif = new JMenuItem("Modificar Metadatos");

		meta.add(modif);
		
		modif.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
                
			        JPanel panel = new JPanel(new GridLayout(0, 2));
	
			        // Etiqueta y campo de texto para modificar el modelo
			        JLabel flashLabel = new JLabel("Flash:");
			        JTextField flashField = new JTextField();
			        panel.add(flashLabel);
			        panel.add(flashField);
	
			        // Etiqueta y campo de texto para modificar el ISO
			        JLabel isoLabel = new JLabel("ISO:");
			        JTextField modelIso = new JTextField();
			        panel.add(isoLabel);
			        panel.add(modelIso);
	
			        // Etiqueta y campo de texto para modificar el creador
			        JLabel creatorLabel = new JLabel("Creador:");
			        JTextField creatorField = new JTextField();
			        panel.add(creatorLabel);
			        panel.add(creatorField);
	
			        // Etiqueta y campo de texto para modificar la fecha
			        JLabel dateLabel = new JLabel("Fecha (Año:mes:dia hora:min:seg):");
			        JTextField dateField = new JTextField();
			        panel.add(dateLabel);
			        panel.add(dateField);
	
			        int option = JOptionPane.showConfirmDialog(frame, panel, "Modificar Imagen", JOptionPane.OK_CANCEL_OPTION);
	
			        if (option == JOptionPane.OK_OPTION) {
			            // Obtener los nuevos valores ingresados por el usuario con getText()
			            String newFlash = flashField.getText();
			            String newIso = (String) modelIso.getText();
			            String newCreator = creatorField.getText();
			            String newDate = dateField.getText();
			            
			            System.out.println(selectedFile);
			            metadata m = new metadata(selectedFile.toString());
			            if (newFlash != null) {
				            m.setFlash(newFlash);
			            }else {
				            m.setFlash(null);
			            }
			            if (newIso != null) {
				            m.setIso(newIso);
			            }else {
				            m.setIso(null);
			            }
			            if (newCreator != null) {
				            m.setCreador(newCreator);
			            }else {
				            m.setCreador(null);
			            }
			            if (newDate != null) {
				            m.setFecha(newDate);
			            }else {
				            m.setFecha(null);
			            }
			            try {
							m.setMetadata();
						} catch (ImagingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        }
                }
		    }
		});

		JMenuItem crearDib = new JMenuItem("Dibujar Imagen");
		dib.add(crearDib);
        
		crearDib.addActionListener(new ActionListener() {
			JMenuBar opciones = new JMenuBar();
			JMenuItem clear, black, blue, red, magenta, orange, save;
		    AreaDibujo area;

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JFrame frame = new JFrame("Paint");
		        Container content = frame.getContentPane();
		        content.setLayout(new BorderLayout());

		        area = new AreaDibujo();
		        content.add(area, BorderLayout.CENTER);

		        // create controls to apply colors and call clear feature
		        JPanel controls = new JPanel();

		        clear = new JMenuItem("Borrar");
		        black = new JMenuItem("Negro");
		        blue = new JMenuItem("Azul");
		        red = new JMenuItem("Rojo");
		        magenta = new JMenuItem("Morado");
		        orange = new JMenuItem("Naranja");
		        save = new JMenuItem("Guardar");

		        ActionListener actionListener = new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		                if (e.getSource() == clear) {
		                    area.clear();
		                } else if (e.getSource() == black) {
		                    area.black();
		                } else if (e.getSource() == blue) {
		                    area.blue();
		                } else if (e.getSource() == red) {
		                    area.red();
		                } else if (e.getSource() == magenta) {
		                    area.magenta();
		                } else if (e.getSource() == orange) {
		                    area.orange();
		                } else if (e.getSource() == save) {
		                    try {
		                        saveImage(area);
		                    } catch (IOException ex) {
		                        ex.printStackTrace();
		                    }
		                }
		            }
		        };

		        clear.addActionListener(actionListener);
		        black.addActionListener(actionListener);
		        blue.addActionListener(actionListener);
		        red.addActionListener(actionListener);
		        magenta.addActionListener(actionListener);
		        orange.addActionListener(actionListener);
		        save.addActionListener(actionListener);

		        controls.add(blue);
		        controls.add(black);
		        controls.add(red);
		        controls.add(magenta);
		        controls.add(clear);
		        controls.add(orange);
		        controls.add(save);

		        content.add(controls, BorderLayout.NORTH);

		        frame.setSize(600, 600);
		        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		        frame.setVisible(true);
		    }

		    private void saveImage(AreaDibujo area) throws IOException {
		    	JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = fileChooser.getSelectedFile();
                    System.out.println(selectedFolder);

                    JDialog dialog = new JDialog(frame, "Introduce el nombre de la imagen", true);
                    dialog.setLayout(new GridLayout(2, 2));

                    JLabel label1 = new JLabel("Nombre de la imagen:");
                    JTextField textField1 = new JTextField();
                    JButton submitButton = new JButton("Crear");

                    dialog.add(label1);
                    dialog.add(textField1);
                    dialog.add(new JLabel());
                    dialog.add(submitButton);
                    dialog.pack();
                    dialog.setLocationRelativeTo(frame);

                    submitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nombre = textField1.getText();
                            if (!nombre.toLowerCase().endsWith(".jpg")) {
                                nombre += ".jpg";
                            }
                            String nombreImg = selectedFolder + "/" + nombre;
                            BufferedImage image = new BufferedImage(area.getWidth(), area.getHeight(), BufferedImage.TYPE_INT_RGB);
                            Graphics2D g2 = image.createGraphics();
                            area.paint(g2);
                            g2.dispose();
		        			try {
								ImageIO.write(image, "png", new File(nombreImg));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                            JOptionPane.showMessageDialog(frame, "Imagen creada: " + nombreImg);
                            updateTree(new File(rootDir), jTree1);
                            dialog.dispose();
                       
                        }
                    });
                    dialog.setVisible(true);
                }
		        
		    }
		});
		
		JMenuItem organizar = new JMenuItem("Organizar por parametro");
		JMenuItem fil = new JMenuItem("Filtrar por parametro");
		
		filtrar.add(organizar);
		filtrar.add(fil);

		organizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(frame, "Opciones de organizar:", true);
                dialog.setLayout(new BorderLayout());
                dialog.setSize(300, 200);

                JPanel optionsPanel = new JPanel();
                optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
                JRadioButton option1 = new JRadioButton("Organizar por fecha");
                JRadioButton option2 = new JRadioButton("Organizar por ISO");
                ButtonGroup group = new ButtonGroup();
                group.add(option1);
                group.add(option2);
                optionsPanel.add(option1);
                optionsPanel.add(option2);
                dialog.add(optionsPanel, BorderLayout.CENTER);

                JButton confirmButton = new JButton("Confirmar");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	ColeccionImagenes c = new ColeccionImagenes();
                    	try {
							c.analizador(rootDir);
							if (option1.isSelected()) {
	                            // Organizar por fecha
								c.ordenarPorFechar();
								List<metadata> fecha = c.getMetadata();
								JFrame fotosFrame = new JFrame("Fotos");
								fotosFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								fotosFrame.setSize(800, 800);

								JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
								JScrollPane scrollPane = new JScrollPane(panel); 
								fotosFrame.add(scrollPane);

								try {
								    // Cargar las fotos
								    for (metadata i: fecha) {
								        // Cargar la imagen
								        JPanel panelFoto = new JPanel(new BorderLayout());
								        JPanel textoPanel = new JPanel(new GridLayout(0, 1));
								        
								        BufferedImage foto = ImageIO.read(new File(i.getRuta()));
								        JLabel imageLabel = new JLabel(new ImageIcon(foto));
								        JLabel ruta = new JLabel("Ruta: " + i.getRuta());
								        JLabel tamanio = new JLabel("Fecha: " + i.getFecha());
								        
								        panelFoto.add(imageLabel, BorderLayout.WEST);
								        textoPanel.add(ruta);
								        textoPanel.add(tamanio);
								        panelFoto.add(textoPanel, BorderLayout.CENTER);
								        
								        panel.add(panelFoto);
								    }
								} catch (IOException ex) {
								    ex.printStackTrace();
								}
								fotosFrame.setVisible(true);
				                
	                        } else if (option2.isSelected()) {
	                            // Organizar por ISO
	                        	c.ordenarPorIso();
								List<metadata> fecha = c.getMetadata();
								JFrame fotosFrame = new JFrame("Fotos");
								fotosFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								fotosFrame.setSize(800, 800);

								JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
								JScrollPane scrollPane = new JScrollPane(panel); 
								fotosFrame.add(scrollPane);

								try {
								    // Cargar las fotos
								    for (metadata i: fecha) {
								        // Cargar la imagen
								        JPanel panelFoto = new JPanel(new BorderLayout());
								        JPanel textoPanel = new JPanel(new GridLayout(0, 1));
								        
								        BufferedImage foto = ImageIO.read(new File(i.getRuta()));
								        JLabel imageLabel = new JLabel(new ImageIcon(foto));
								        JLabel ruta = new JLabel("Ruta: " + i.getRuta());
								        JLabel tamanio = new JLabel("ISO: " + i.getIso());
								        
								        panelFoto.add(imageLabel, BorderLayout.WEST);
								        textoPanel.add(ruta);
								        textoPanel.add(tamanio);
								        panelFoto.add(textoPanel, BorderLayout.CENTER);
								        
								        panel.add(panelFoto);
								    }
								} catch (IOException ex) {
								    ex.printStackTrace();
								}
								fotosFrame.setVisible(true);
				                
	                        }
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        dialog.dispose();
                    }
                });
                dialog.add(confirmButton, BorderLayout.SOUTH);

                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        });
		
		fil.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JDialog dialog = new JDialog(frame, "Filtrar por:", true);
		        dialog.setLayout(new BorderLayout());
		        dialog.setSize(300, 200);

		        JPanel optionsPanel = new JPanel();
		        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		        JRadioButton alto = new JRadioButton("Filtrar por Alto");
		        JRadioButton ancho = new JRadioButton("Filtrar por Ancho");
		        JRadioButton iso = new JRadioButton("Filtrar por ISO");
		        ButtonGroup group = new ButtonGroup();
		        group.add(alto);
		        group.add(ancho);
		        group.add(iso);
		        optionsPanel.add(alto);
		        optionsPanel.add(ancho);
		        optionsPanel.add(iso);
		        dialog.add(optionsPanel, BorderLayout.CENTER);

		        JButton acceptButton = new JButton("Aceptar");
		        acceptButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                // Procesar la opción seleccionada
		                if (alto.isSelected()) {
		                    JDialog dialog2 = new JDialog(dialog, "Filtrar por Alto:", true);
		                    dialog2.setLayout(new BorderLayout());
		                    dialog2.setSize(300, 200);

		                    JPanel panel2 = new JPanel();
		                    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		                    JRadioButton menor = new JRadioButton("Valores Menores");
		                    JRadioButton mayor = new JRadioButton("Valores Mayores");
		                    ButtonGroup group2 = new ButtonGroup();
		                    JTextField valor = new JTextField();
		                    group2.add(menor);
		                    group2.add(mayor);
		                    panel2.add(menor);
		                    panel2.add(mayor);
					        panel2.add(valor);

		                    dialog2.add(panel2, BorderLayout.CENTER);

		                    JButton filtrar = new JButton("Filtrar");
		                    filtrar.addActionListener(new ActionListener() {
		                        @Override
		                        public void actionPerformed(ActionEvent e) {
		    			            int val = Integer.parseInt(valor.getText());
		    			            ColeccionImagenes c = new ColeccionImagenes();
		    			            
		                            if (menor.isSelected()) {
		                            	try {
											c.analizador(rootDir);
											List<metadata> fecha = c.filtrarPorAltoMenor(val);
											JFrame fotosFrame = new JFrame("Fotos");
											fotosFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
											fotosFrame.setSize(800, 800);

											JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
											JScrollPane scrollPane = new JScrollPane(panel); 
											fotosFrame.add(scrollPane);

											try {
											    // Cargar las fotos
											    for (metadata i: fecha) {
											        // Cargar la imagen
											        JPanel panelFoto = new JPanel(new BorderLayout());
											        JPanel textoPanel = new JPanel(new GridLayout(0, 1));
											        
											        BufferedImage foto = ImageIO.read(new File(i.getRuta()));
											        JLabel imageLabel = new JLabel(new ImageIcon(foto));
											        JLabel ruta = new JLabel("Ruta: " + i.getRuta());
											        JLabel tamanio = new JLabel("Altura: " + i.getAlto());
											        
											        panelFoto.add(imageLabel, BorderLayout.WEST);
											        textoPanel.add(ruta);
											        textoPanel.add(tamanio);
											        panelFoto.add(textoPanel, BorderLayout.CENTER);
											        
											        panel.add(panelFoto);
											    }
											} catch (IOException ex) {
											    ex.printStackTrace();
											}
											fotosFrame.setVisible(true);
											
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
		                            } else if (mayor.isSelected()) {
		                            	try {
											c.analizador(rootDir);
											List<metadata> fecha = c.filtrarPorAltoMayor(val);
											JFrame fotosFrame = new JFrame("Fotos");
											fotosFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
											fotosFrame.setSize(800, 800);

											JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
											JScrollPane scrollPane = new JScrollPane(panel); 
											fotosFrame.add(scrollPane);

											try {
											    // Cargar las fotos
											    for (metadata i: fecha) {
											        // Cargar la imagen
											        JPanel panelFoto = new JPanel(new BorderLayout());
											        JPanel textoPanel = new JPanel(new GridLayout(0, 1));
											        
											        BufferedImage foto = ImageIO.read(new File(i.getRuta()));
											        JLabel imageLabel = new JLabel(new ImageIcon(foto));
											        JLabel ruta = new JLabel("Ruta: " + i.getRuta());
											        JLabel tamanio = new JLabel("Altura: " + i.getAlto());
											        
											        panelFoto.add(imageLabel, BorderLayout.WEST);
											        textoPanel.add(ruta);
											        textoPanel.add(tamanio);
											        panelFoto.add(textoPanel, BorderLayout.CENTER);
											        
											        panel.add(panelFoto);
											    }
											} catch (IOException ex) {
											    ex.printStackTrace();
											}
											fotosFrame.setVisible(true);
											
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
		                            }
		                            dialog2.dispose(); 
		                        }
		                    });
		                    dialog2.add(filtrar, BorderLayout.SOUTH);

		                    dialog2.setLocationRelativeTo(dialog);
		                    dialog2.setVisible(true);
		                } else if (ancho.isSelected()) {
		                    // Realizar acción para filtrar por ancho
		                	JDialog dialog2 = new JDialog(dialog, "Filtrar por Alto:", true);
		                    dialog2.setLayout(new BorderLayout());
		                    dialog2.setSize(300, 200);

		                    JPanel panel2 = new JPanel();
		                    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		                    JRadioButton menor = new JRadioButton("Valores Menores");
		                    JRadioButton mayor = new JRadioButton("Valores Mayores");
		                    ButtonGroup group2 = new ButtonGroup();
		                    JTextField valor = new JTextField();
		                    group2.add(menor);
		                    group2.add(mayor);
		                    panel2.add(menor);
		                    panel2.add(mayor);
					        panel2.add(valor);

		                    dialog2.add(panel2, BorderLayout.CENTER);

		                    JButton filtrar = new JButton("Filtrar");
		                    filtrar.addActionListener(new ActionListener() {
		                        @Override
		                        public void actionPerformed(ActionEvent e) {
		    			            int val = Integer.parseInt(valor.getText());
		    			            ColeccionImagenes c = new ColeccionImagenes();
		    			            
		                            if (menor.isSelected()) {
		                            	try {
											c.analizador(rootDir);
											List<metadata> fecha = c.filtrarPorAnchoMmenor(val);
											JFrame fotosFrame = new JFrame("Fotos");
											fotosFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
											fotosFrame.setSize(800, 800);

											JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
											JScrollPane scrollPane = new JScrollPane(panel); 
											fotosFrame.add(scrollPane);

											try {
											    // Cargar las fotos
											    for (metadata i: fecha) {
											        // Cargar la imagen
											        JPanel panelFoto = new JPanel(new BorderLayout());
											        JPanel textoPanel = new JPanel(new GridLayout(0, 1));
											        
											        BufferedImage foto = ImageIO.read(new File(i.getRuta()));
											        JLabel imageLabel = new JLabel(new ImageIcon(foto));
											        JLabel ruta = new JLabel("Ruta: " + i.getRuta());
											        JLabel tamanio = new JLabel("Ancho: " + i.getAncho());
											        
											        panelFoto.add(imageLabel, BorderLayout.WEST);
											        textoPanel.add(ruta);
											        textoPanel.add(tamanio);
											        panelFoto.add(textoPanel, BorderLayout.CENTER);
											        
											        panel.add(panelFoto);
											    }
											} catch (IOException ex) {
											    ex.printStackTrace();
											}
											fotosFrame.setVisible(true);
											
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
		                            } else if (mayor.isSelected()) {
		                            	try {
											c.analizador(rootDir);
											List<metadata> fecha = c.filtrarPorAnchoMayor(val);
											JFrame fotosFrame = new JFrame("Fotos");
											fotosFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
											fotosFrame.setSize(800, 800);

											JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
											JScrollPane scrollPane = new JScrollPane(panel); 
											fotosFrame.add(scrollPane);

											try {
											    // Cargar las fotos
											    for (metadata i: fecha) {
											        // Cargar la imagen
											        JPanel panelFoto = new JPanel(new BorderLayout());
											        JPanel textoPanel = new JPanel(new GridLayout(0, 1));
											        
											        BufferedImage foto = ImageIO.read(new File(i.getRuta()));
											        JLabel imageLabel = new JLabel(new ImageIcon(foto));
											        JLabel ruta = new JLabel("Ruta: " + i.getRuta());
											        JLabel tamanio = new JLabel("Ancho: " + i.getAncho());
											        
											        panelFoto.add(imageLabel, BorderLayout.WEST);
											        textoPanel.add(ruta);
											        textoPanel.add(tamanio);
											        panelFoto.add(textoPanel, BorderLayout.CENTER);
											        
											        panel.add(panelFoto);
											    }
											} catch (IOException ex) {
											    ex.printStackTrace();
											}
											fotosFrame.setVisible(true);
											
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
		                            }
		                            dialog2.dispose(); 
		                        }
		                    });
		                    dialog2.add(filtrar, BorderLayout.SOUTH);

		                    dialog2.setLocationRelativeTo(dialog);
		                    dialog2.setVisible(true);
		                } else if (iso.isSelected()) {
		                    // Realizar acción para filtrar por ISO
		                	JDialog dialog2 = new JDialog(dialog, "Filtrar por Alto:", true);
		                    dialog2.setLayout(new BorderLayout());
		                    dialog2.setSize(300, 200);

		                    JPanel panel2 = new JPanel();
		                    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		                    JRadioButton menor = new JRadioButton("Valores Menores");
		                    JRadioButton mayor = new JRadioButton("Valores Mayores");
		                    ButtonGroup group2 = new ButtonGroup();
		                    JTextField valor = new JTextField();
		                    group2.add(menor);
		                    group2.add(mayor);
		                    panel2.add(menor);
		                    panel2.add(mayor);
					        panel2.add(valor);

		                    dialog2.add(panel2, BorderLayout.CENTER);

		                    JButton filtrar = new JButton("Filtrar");
		                    filtrar.addActionListener(new ActionListener() {
		                        @Override
		                        public void actionPerformed(ActionEvent e) {
		    			            int val = Integer.parseInt(valor.getText());
		    			            ColeccionImagenes c = new ColeccionImagenes();
		    			            
		                            if (menor.isSelected()) {
		                            	try {
											c.analizador(rootDir);
											List<metadata> fecha = c.filtrarPorIsoMenor(val);
											JFrame fotosFrame = new JFrame("Fotos");
											fotosFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
											fotosFrame.setSize(800, 800);

											JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
											JScrollPane scrollPane = new JScrollPane(panel); 
											fotosFrame.add(scrollPane);

											try {
											    // Cargar las fotos
											    for (metadata i: fecha) {
											        // Cargar la imagen
											        JPanel panelFoto = new JPanel(new BorderLayout());
											        JPanel textoPanel = new JPanel(new GridLayout(0, 1));
											        
											        BufferedImage foto = ImageIO.read(new File(i.getRuta()));
											        JLabel imageLabel = new JLabel(new ImageIcon(foto));
											        JLabel ruta = new JLabel("Ruta: " + i.getRuta());
											        JLabel tamanio = new JLabel("ISO: " + i.getIso());
											        
											        panelFoto.add(imageLabel, BorderLayout.WEST);
											        textoPanel.add(ruta);
											        textoPanel.add(tamanio);
											        panelFoto.add(textoPanel, BorderLayout.CENTER);
											        
											        panel.add(panelFoto);
											    }
											} catch (IOException ex) {
											    ex.printStackTrace();
											}
											fotosFrame.setVisible(true);
											
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
		                            } else if (mayor.isSelected()) {
		                            	try {
											c.analizador(rootDir);
											List<metadata> fecha = c.filtrarPorIsoMayor(val);
											JFrame fotosFrame = new JFrame("Fotos");
											fotosFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
											fotosFrame.setSize(800, 800);

											JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
											JScrollPane scrollPane = new JScrollPane(panel); 
											fotosFrame.add(scrollPane);

											try {
											    // Cargar las fotos
											    for (metadata i: fecha) {
											        // Cargar la imagen
											        JPanel panelFoto = new JPanel(new BorderLayout());
											        JPanel textoPanel = new JPanel(new GridLayout(0, 1));
											        
											        BufferedImage foto = ImageIO.read(new File(i.getRuta()));
											        JLabel imageLabel = new JLabel(new ImageIcon(foto));
											        JLabel ruta = new JLabel("Ruta: " + i.getRuta());
											        JLabel tamanio = new JLabel("ISO: " + i.getIso());
											        
											        panelFoto.add(imageLabel, BorderLayout.WEST);
											        textoPanel.add(ruta);
											        textoPanel.add(tamanio);
											        panelFoto.add(textoPanel, BorderLayout.CENTER);
											        
											        panel.add(panelFoto);
											    }
											} catch (IOException ex) {
											    ex.printStackTrace();
											}
											fotosFrame.setVisible(true);
											
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
		                            }
		                            dialog2.dispose(); 
		                        }
		                    });
		                    dialog2.add(filtrar, BorderLayout.SOUTH);

		                    dialog2.setLocationRelativeTo(dialog);
		                    dialog2.setVisible(true);
		                }

		                dialog.dispose();
		            }
		        });
		        dialog.add(acceptButton, BorderLayout.SOUTH);

		        dialog.setLocationRelativeTo(frame);
		        dialog.setVisible(true);
		    }
		});
		
        jTree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    TreeNode[] nodes = selectedNode.getPath();
                    StringBuilder filePathBuilder = new StringBuilder(rootDir + "/");
                    for (int i = 1; i < nodes.length; i++) {
                        filePathBuilder.append(nodes[i].toString());
                        if (i < nodes.length - 1) {
                            filePathBuilder.append("/");
                        }
                    }
                    String filePath = filePathBuilder.toString();

                    File selectedFile = new File(filePath);
                    System.out.println("Selected file: " + selectedFile);
                    String extension = FilenameUtils.getExtension(selectedFile.getName()).toLowerCase();
                    if (selectedFile.isFile() && (extension.equals("jpg") || extension.equals("jpeg"))) {
                    	model.setRowCount(0);
                    	metadata m = new metadata(selectedFile.toString());
                    	Object[][] info= {{"Ruta", m.getRuta()},
                                {"Alto", m.getAlto()},
                                {"Ancho", m.getAncho()},
                                {"Tamaño de la imagen", m.getTam()}}; 
                    	for (Object[] row : info) {
                            model.addRow(row);
                        }
                        try {
                            ImageIcon imageIcon = new ImageIcon(ImageIO.read(selectedFile));
                            imageLabel.setIcon(imageIcon);
                            imageLabel.revalidate();
                            imageLabel.repaint();
                            
                            System.out.println(selectedFile.toString());
                            
                            m.getMet();
                            Object[][] data = {
                                    {"Uso del flash", m.getFlash()},
                                    {"ISO", m.getIso()},
                                    {"Creador", m.getCreador()},
                                    {"Fecha", m.getFecha()}
                            };
                            
                            for (Object[] row : data) {
                                model.addRow(row);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("Selected file is not a .jpg or .jpeg image");
                    }
                } else {
                    System.out.println("No node selected");
                }
            }
        });
        
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		
		//Carpeta.generarCarpetas(4, 4);
		
		/*
		metadata m = new metadata("/i.jpg");
		m.setCreador("Max");
		System.out.println(m);*/
		/*
		ColeccionImagenes c = new ColeccionImagenes();
		try {
			c.analizador("/home/max/eclipse-workspace/trabajo/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.ordenarPorFechar();
		List<metadata> lista = c.getMetadata();
		System.out.println(lista);*/
		
		/*
		Imagen i = new Imagen("i.jpg");
		i.setMetadatos("Canon", "123", "1234", "Max", "2000:01:02 00:00:00");
		Imagen j = new Imagen("j.jpg");
		j.setMetadatos("Canon", "123", "7777", "Max", "2001:01:02 00:00:00");
		Imagen l = new Imagen("l.jpg");
		l.setMetadatos("Canon", "987", "9876", "Max", "2023:01:02 00:00:00");
		System.out.println(i.toString());
		System.out.println(j.toString());
		System.out.println(l.toString());
		*/

	}
}
