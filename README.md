# genopolis-colombia-api

Este es un template de referencia para los microservicios usados en genopolis colombia, se implementa una estructura de 
subprojectos usando gradle para implementar una arquitectura de adaptadores y puertos, también se deben usar testscontainers
para las pruebas de integración. 

## Set up
* Instalar Docker
* Instalar gradle
* Instalar MySQL
* mysql -u root -p (Ingresar a la consola de mysql)
* create database proteins (Crear base de datos)
* create user 'springuser'@'%' identified by "p4t1App"; (crear usuario para spring)
* grant all on favorites_genopolis.* to 'springuser'@'%'; (Dar permisos al nuevo usuario sobre la base de datos creada)

## Running the application
```
./gradlew bootRun
```
