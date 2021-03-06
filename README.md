Este proyecto fue construido para impartir el curso de spring mvc y servicios rest para Hábil Mx

http://www.habil.mx


# Actividades realizadas

## Sesión 1

1.- Instalación de STS última versión

2.- Creación de cuenta

3.- Instalación de aplicación de escritorio GitHub

4.- Instalación de MySQL

5.- Creación de workspace

6.- Creación de proyectos

7.- Ajustes a proyectos creados

8.- Creación de repositorio local git

9.- Creación de repositorio remoto GitHub

10.- Primer commit en git

11.- Primer Sync en GitHub

12.- Creación del archivo .gitignore

13.- Exceptuar \*/target/\*

14.- Descarga de los repositorios remotos habil y hb-curso-parent

15.- Puesta a punto de los proyectos en STS descargados de los repositorios maestros

16.- Creación de la base de datos y usuario en MySQL

17.- Ejecución de la prueba unitaria del proyecto hb-curso-parent para validar conectividad a la BBDD


# Configuración de los proyectos para compilación con la JDK en la versión 7

Para que los proyectos sean compilados en la versión 7 de java debemos

## Agregar la versión del la JDK a utilizar

En el archivo pom.xml del parent debemos definir como propiedad la versión de compilación
a utilizar

    <jdk.version>1.7</jdk.version>

## Configurar los proyectos para compilar en la versión

En los archivos pom.xml del commons, model, business y front debemos agregar el siguiente build

    <build>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>2.3.2</version>
          <configuration>
            <source>${jdk.version}</source>
            <target>${jdk.version}</target>
          </configuration>
        </plugin>
      </plugins>
    </build>

Después de agregar esta configuración los proyectos marcaran un error, será necesario seleccionarlos
dar click con el botón derecho y en el menú emergente dar click a "**Maven -> Update project ...**", con eso será suficiente para que los proyectos cambien su compilación a la versión 1.7

# Configuración de la base de datos

    mysql>CREATE DATABASE hb_bit;

    mysql>CREATE USER 'bitacora'@'localhost' IDENTIFIED BY '12345678';

    mysql>GRANT ALL ON hb_bit.* TO 'bitacora'@'localhost';

    mysql>FLUSH PRIVILEGES;

Con esto podemos ejecutar las pruebas unitarias de la sesión 1

#Configuración de la base de datos embebida

Para trabajar sin necesidad de instalar un manejador de base de datos se ha decidido
utilizar la base de datos embebida H2, para ocuparla debemos tener creada la carpeta
"**/opt/db/h2**" y ejecutar los siguientes pasos

##Agregar versión y dependencias

Debemos agregar al pom.xml del parent la versión del H2 a utilizar

    <h2.version>1.4.185</h2.version>

Debemos agregar la dependencia al pom.xml del model

    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <version>${h2.version}</version>
    </dependency>

Debemos configurar el data source en el archivo model.application.context.xml para crear/usar la base de datos embebida

    <bean id="BitacoraDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
      <property name="driverClassName" value="org.h2.Driver" />
      <property name="url" value="jdbc:h2:/opt/db/h2/hb_bit;MODE=Oracle;TRACE_LEVEL_SYSTEM_OUT=2" />
      <property name="username" value="sa" />
      <property name="password" value="" />
    </bean>

Ver que estamos ocupando H2 en modo **Oracle**, con el objetivo de no tener problemas al momento de conectar la aplicación a Oracle.


#Explorar la base de datos embebida

Para explorar la base de datos embebida será importante que la aplicación web se esté ejecutando, una vez confirmada la ejecución debemos ingresar a la siguiente url

    http://localhost:11111

En el formulario que se nos presenta debemos capturar la siguiente información

    Configuraciones guardadas: Generic H2 (Embedded)
    Nombre de la configuración: Generic H2 (Embedded)
    Controlador: org.h2.Driver
    URL JDBC: jdbc:h2:/opt/db/h2/hb_bit;MODE=Oracle;TRACE_LEVEL_SYSTEM_OUT=2
    Nombre de usuario: sa
    Contraseña:

Un vez capturados, debemos dar click al botón **Conectar**. Listo, podremos navegar a través
de la base de datos.

# Probar los servicios REST de la aplicación

Con las siguientes indicaciones podremos probar los servicios REST de la aplicación

## Probar el recuperar usuario por su clave

Para probar este método debemos tener un navegador abierto, recomiendo las pruebas sean realizadas
a través de firefox, si colocamos la siguiente url en el navegador cambiando la variable
**{clave del usuario}** por la de un usuario existente en la base de datos deberemos obtener la información del usuario.

    http://localhost:8080/hb-curso/protected/service/usuario/{clave del usuario}

Es importante que previamente hayamos creado usuarios dentro de la base de datos, demos por
hecho que se ha creado el usuario dchavez, si quisieramos recuperar su información sería con
la siguiente url

    http://localhost:8080/hb-curso/protected/service/usuario/dchavez

Otro mecanismo para invocar el servicio es usando la utilidad curl, el comando sería

    curl -i -X GET http://localhost:8080/hb-curso/protected/service/usuario/dchavez

Un ejemplo de la respuesta sería

    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Sun, 01 Mar 2015 08:41:58 GMT
    {
      "id":"a28c4bed-3b18-4e37-8c56-6a58f6d889e4",
      "cveUsuario":"a28c4bed-3b18-4e37-8c56-6a58f6d889e4",
      "nombre":"Dorian",
      "apPaterno":"Chavez",
      "apMaterno":"Juarez",
      "activo":true,
      "fechaRegistro":null
    }

##Porbar el guardar usuario

Eso es todo por hoy :)

curl -i -X POST -d "cveUsuario=mrojas" http://localhost:8080/hb-curso/protected/service/usuario
