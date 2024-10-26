# Ejercicio H - Aplicación JavaFX de Gestión de Personas

Este proyecto es una aplicación JavaFX para gestionar información sobre personas, utilizando una base de datos para almacenar los datos. La aplicación permite realizar operaciones básicas como agregar, modificar y eliminar personas, así como filtrar la lista de personas.

## Características
    
    - Interfaz gráfica de usuario intuitiva basada en JavaFX.
    - Conexión a una base de datos (MariaDB/MySQL) para almacenamiento persistente de datos.
    - Funcionalidades para agregar, modificar y eliminar registros de personas.
    - Filtros para facilitar la búsqueda de personas por nombre.

## Requisitos

    - Java Development Kit (JDK) 11 o superior.
    - MariaDB/MySQL instalado y configurado.
    - Dependencias de JavaFX.

## Instalación
    
    1. Clona este repositorio:
       ```bash
       git clone https://github.com/andreatticj/ejercicioH_DEIN.git
    2. Asegúrate de tener MariaDB/MySQL funcionando.
    
    3. Crea la base de datos y las tablas necesarias según el script proporcionado en el directorio sql.
    
    4. Configura la conexión a la base de datos en el archivo de configuración.
       
    5. Ejecuta la aplicación desde tu IDE favorito o utilizando Maven/Gradle.

  ## Estructura del Proyecto

    src/: Carpeta que contiene el código fuente de la aplicación.
        eu/andreatt/ejercicioh_dein/application/: Contiene la clase principal HelloApplication.java.
        eu/andreatt/ejercicioh_dein/bbdd/: Contiene la clase ConexionBBDD.java para la gestión de la base de datos.
        eu/andreatt/ejercicioh_dein/controller/: Contiene los controladores como HelloController.java y ModalHController.java para la lógica de la interfaz gráfica.
        eu/andreatt/ejercicioh_dein/dao/: Contiene la clase PersonaDAO.java para la interacción con la base de datos.
        eu/andreatt/ejercicioh_dein/model/: Contiene la clase Persona.java, que define el modelo de datos.
    resources/: Carpeta que contiene los recursos de la aplicación.
        fxml/: Archivos FXML que definen la estructura de la interfaz gráfica (ej. ejercicioH.fxml, modalH.fxml).
        images/: Iconos e imágenes utilizados en la aplicación (ej. agenda.png, agregar.png, contactos.jpeg, editar.png, eliminar.png).

## Uso

    Al abrir la aplicación, verás una tabla con la lista de personas.
    Puedes filtrar la lista escribiendo en el campo de texto correspondiente.
    Usa los botones para agregar, modificar o eliminar personas de la base de datos.

