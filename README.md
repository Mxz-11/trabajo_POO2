# Descripción

Este proyecto es una aplicación en Java que actúa como una biblioteca de imágenes, permitiendo la organización, visualización y manipulación de metadatos de un conjunto de imágenes. El objetivo es aplicar los conocimientos adquiridos en la asignatura Programación Orientada a Objetos II.

# Características Principales

## 1. Creación de Colección de Imágenes

Estructura de Carpetas: Genera una jerarquía aleatoria de carpetas con niveles de profundidad y nombres configurables.

Generación de Imágenes: Utiliza la librería Graphics2D de Java para crear imágenes aleatorias con formas y colores variados.

Edición de Metadatos: Modifica metadatos de imágenes (fecha de captura, ubicación GPS, etc.) con la librería Apache Commons Imaging.

## 2. Análisis de Imágenes

Almacenamiento de Datos: Define clases para guardar información relevante de cada imagen.

Recorrido de Carpetas: Analiza imágenes en un directorio y extrae datos relevantes.

Ordenación: Implementa criterios de ordenación (fecha, dimensiones, etc.).

Filtros: Filtra imágenes según diferentes criterios (tamaño, fecha de creación, etc.).

## 3. Interfaz Gráfica de Usuario (GUI)

TableView: Muestra la información de las imágenes en una tabla con opciones de ordenación.

ImageView: Visualiza la imagen seleccionada con ajustes automáticos de tamaño.

Menús y Diálogos: Permite acceder a todas las funcionalidades del programa.

## 4. Funcionalidades Adicionales

Este proyecto incorpora una herramienta de dibujo similar a un Paint, permitiendo crear y modificar imágenes desde la interfaz gráfica.

Tecnologías Utilizadas

Java SE 8+

JavaFX (para la interfaz gráfica)

Apache Commons Imaging (para la edición de metadatos)

Graphics2D (para la generación de imágenes)

Instalación y Uso

Clona este repositorio:

git clone https://github.com/Mxz-11/trabajo_POO2.git

Abre el proyecto en tu IDE favorito (Eclipse, IntelliJ IDEA, NetBeans).

Asegúrate de tener configurado JavaFX para la GUI.

Compila y ejecuta la aplicación.

Contribución

Si deseas contribuir a este proyecto, sigue estos pasos:

Haz un fork del repositorio.

Crea una nueva rama con tu función o mejora.

Realiza un commit con una descripción clara de los cambios.

Envía un pull request para revisión.

Licencia

Este proyecto está bajo la licencia MIT. Puedes utilizarlo y modificarlo libremente.
