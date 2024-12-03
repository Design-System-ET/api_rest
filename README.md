# api_rest

## Resumen del Proyecto

Este proyecto es un ejemplo funcional que implementa un CRUD (Crear, Leer, Actualizar, Eliminar) utilizando Spring Boot, JDK 23 y NetBeans IDE 23 utilizanzon algunos metodos para los endpoints recibiendo parametros por url, query, parametros de consulta y en el cuerpo de la solicitud.

## Características principales

- Estructura basada en MVC para separar lógica, control y vista.
- CRUD funcional que gestiona operaciones con persistencia en base de datos.
- Uso de tecnologías modernas como Spring Boot.
# Tecnologías Usadas

El proyecto utiliza las siguientes herramientas y tecnologías:

- **NetBeans**: Versión 23 como entorno de desarrollo integrado (IDE).
- **Java**: JDK versión 23.
- **MySQL**: Sistema de gestión de bases de datos, usando el conector com.mysql:mysql-connector-j.
- **Spring Boot**: para manejar las request y response HTTP

# Requisitos Previos
## Software necesario

- **NetBeans IDE**: Versión 23.
- **Java JDK**: Versión 23.
- **MySQL**: para crear el schema de la BD

## Notas importantes

- Todas las dependencias necesarias están gestionadas en el archivo pom.xml del proyecto.
- El schema de la base de datos debe configurarse previamente, pero no requiere tablas creadas ya que se crearan automaticamente.

# Instalación
## 1\. Clonar el repositorio
En tu terminal, ejecuta el siguiente comando para clonar el proyecto:

```
git clone https://github.com/Design-System-ET/api_rest.git
```

## 2\. Configuración del entorno
1. **Base de datos**:
    - Crea un esquema vacío en tu base de datos MySQL.

## 3\. Dependencias

Construye el proyecto y descarga todas las dependencias necesarias:

1. Haz clic derecho sobre el proyecto en NetBeans.
2. Selecciona **"Build With Dependencies"**.

# Configuración del Proyecto

## 1\. Base de datos

No es necesario crear tablas manualmente, pero asegúrate de tener un esquema vacío listo.

## 2\. Archivo application.yaml

Configura las siguientes propiedades en el archivo:
- **NOMBRE-BD**: de la base de datos indicando el nombre del schema (modifica la url segun tu entorno y necesidades)
- **USER**: usuario para la conexion a la BD
- **PASSWORD**: Seña de la conexion a la BD
- Tambien puedes indicar el **port** y el **context-path** para indicar el puerto y el endpoint de inicio de ejecucion.

```yml

spring:
  application:
    name: api-rest
  jpa:
    hibernate:
      ddl-auto: update
    open-in-view: false
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/NOMBRE-BD?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC
    username: USUARIO
    password: PASSWORDD

server:
  port: 8080                         #AQUI EL PUERTO DONDE QUIERES QUE SE EJECUTE EL TOMCAT INTERNO
  servlet:
    context-path: /servicios         #AQUI EL PATH DONDE INIZIALIZAS LOS CONTROLLERS EJ: localhost:8080/servicios/

```

Asegúrate de que el nombre del esquema, usuario y contraseña coincidan con tu configuración de MySQL.

## 3\. GlobalExceptionHandler
- Para manejar de forma global las excepciones, captura y maneja todas estas excepciones no controladas de forma centralizada evitando que se propagen y generen respuestas no personalizadas al cliente.

```java

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}

```

## 4\. CorsConfig
- Esto es esencial para controlar qué orígenes tienen permitido acceder a los recursos de la API desde navegadores. Recuerda modificar la URL indicando donde se va a exponer el servicio, el dominio de acceso a la api en el servidor, por ejemplo si haces pruebas en IIS redireccionando un dominio a la URL localhost de la api

```java

@Configuration
public class CorsConfig extends CorsFilter {
	
	public CorsConfig() {
        super(configurationSource());
    }

    private static UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http:URL-DONDE-SE-EXPONGA-EL-SERVICIO"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

```


# Estructura del Proyecto

La estructura está organizada siguiendo el patrón MVC para garantizar modularidad y claridad:


# Uso
## 1\. Iniciar la aplicación

Ejecuta el proyecto desde NetBeans:

Una vez desplegado, accede a la aplicación desde tu navegador en:

```java

	<http://localhost:8080/servicios>

```

## 2\. Funcionalidades CRUD
Las operaciones disponibles son:

- **Crear**: Agregar nuevos registros a la base de datos.
- **Leer**: Ver y listar registros almacenados.
- **Actualizar**: Modificar registros existentes.
- **Eliminar**: Borrar registros.

## 3\. Se agrega una carpeta HTML
- Dicha carpeta contiene archivos HTML para probar cada metodo implementado en el controlador, estos metodos estan comentado con el nombre del archivo HTML para hacer las pruebas.
- Modifica el js de cada HTML para indicar la URL que estes implementando.

# Licencia
Este proyecto está licenciado bajo la **MIT License**.

Puedes encontrar el texto completo de la licencia en el archivo LICENSE.
