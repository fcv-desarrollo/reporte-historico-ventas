# reporte-hstorico-ventas

## Tabla de contenido

1. [Descripcion](#descripcion)
2. [Version](#version)
3. [Librerias](#librerias)
4. [Instalacion](#instalacion)
5. [Estructura de directorios](#estructura-de-directorios)

## Descripcion

La aplicacion genera un archivo .xls (Excel) el cual contiene la información de todas las ventas generadas en la historia de **CrediContino**.

Este reporte puede ser generado en cualquier momento, pero usualmente se consulta cada mes. El horario para ejecutarse debería ser después de las 11:00 hrs para obtener la información actualizada del día inmediato anterior.

## Version

**Version actual: 1.1**

Desarrollado en Java Platform JDK 1.8

El proyecto actualmente se encuentra en producción y es una versión estable. No obstante, podría tener cambios en el futuro.

## Librerias

Las librerías utilizadas para este proyecto son:

- commons-collections4-4.4.jar
- commons-compress-1.19.jar
- commons-math3-3.6.1.jar
- mysql-connector-java-5.1.40-bin.jar
- ojdbc6.jar
- poi-4.1.2.jar
- poi-ooxml-4.1.2.jar
- poi-ooxml-schemas-4.1.2.jar
- swingx-all-1.6.4.jar
- xmlbeans-3.1.0.jar

## Instalacion

- Para la instalacion es necesario contar con las herramientas de desarrollo:

  - Java JDK 1.8
  - NetBeans IDE 8.1

- Teniendo los requisitos mencionados descargar el repositorio:

```
cd /path/project/
git clone https://github.com/fcv-desarrollo/reporte-historico-ventas.git
```

- Ejecutar NetBeans y abrir el proyecto.
- Ejecutar *Clean and Build* para construir el ejecutable.
- La accion anterior generará una carpeta con el nombre **dist** la cual contiene el archivo ejecutable listo para su distribuición.

## Estructura de directorios

Los archivos del codigo fuente del proyecto se encuentran dentro de la carpeta ***/src*** el cual tiene la siguiente estructura:

```
└─ src
   ├─ connection
   ├─ controllers
   ├─ img
   ├─ models
   ├─ reportehistoricoventas
   └─ views
```
- **connection** ─ Contiene las clases para la conexión con la base de datos y las consultas.
- **controllers** ─ Clases para generar el archivo de Excel y el archvio de logs por si ocurre un error.
- **img** ─ Son los recursos de imagen utilizados por la interfaz gráfica.
- **models** ─ Estructura del modelo de las facturas y el reporte.
- **reportehistoricoventas** ─ Contiene el archivo principal de la aplicación.
- **views** ─ Contiene las clases del diseño de la interfaz gráfica.
