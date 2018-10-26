# Multicast en Java

* Para iniciar el servidor desde la carpeta src se debe ejecutar

``
java serverpackage.Server <ipMulticast>
``
* Para iniciar el cliente desde la carpeta src se debe ejecutar

``
java serverpackage.Client <ipMulticast> <binarioMediciones> <binarioRecuperaciónHistorial>
``

* Las variables que entrega el servidor son:
1. Presión
2. Temperatura
3. Humedad

* El servidor envía mediciones cada 1 segundo.

* Las mediciones se envían mediante los puertos:
1. Presión: 10001
2. Temperatura: 10002
3. Humedad: 10003
4. Historial: 1004

* Integrantes:
  * Ignacio Valenzuela Albornoz 201473055-1
  * Lukas Zamora Jabre 201430047-6
