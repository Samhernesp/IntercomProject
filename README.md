# IntercomProject

# **Informe**

## **Descripción del problema:**

El programa a desarrollar constaba de tres funcionalidades principales, la primera es un citófono que permite la comunicación entre la portería y los apartamentos de un edificio, en la cual se puede hacer el anuncio de los visitantes, y por su parte los dueños del apartamento pueden aceptar o rechazar este visitante. La segunda funcionalidad consiste en un chat que permite la comunicación entre ambos apartamentos. Y la última es un sistema que en caso de emergencia el residente puede tocar un botón el cual alertará en portería y enviará un correo electrónico a un contacto registrado previamente.

## **Análisis del problema:**
Para abarcar este problema, lo primero que identificamos fue el método o protocolo por el cual se trasmitirá toda la información, de esta manera, concluimos que el protocolo de transporte TCP, era el más indicado para esta tarea, ya que este protocolo nos asegura que la información llegue de manera correcta sin perder ningún tipo de dato, y además nos brinda mayor seguridad. Y estas características son necesarias en este caso, puesto que tanto en el caso del chat como al pulsarse el botón de emergencia es fundamental que se realice correctamente la transmisión de información. Así mismo, a pesar de tener una menor velocidad en comparación con otros protocolos como UDP, son mayores los beneficios que ofrece este protocolo, y la demora de la información no ocasiona grandes problemas.

Después de analizar los requerimientos del problema, nos dimos cuenta que es necesario que tanto la portería, que vamos a considerar nuestro servidor, como los apartamentos, que vamos  a considerar nuestros clientes, necesitan realizar múltiples acciones al mismo tiempo. Esto se debe a que ambos necesitan estar todo el tiempo en la capacidad tanto de escribir y enviar datos, como de estar atentos a cualquier dato que llegue ya sea del servidor o de otro cliente. De esta manera, determinamos que es fundamental el uso de hilos, que nos permitan asignar diversas funcionalidades para que se ejecuten simultáneamente.

Uno de los requerimientos adicionales que se pedía para la aplicación era que se pudiera ejecutar desde diversos computadores, para esto teníamos que independizar las clases cliente y servidor que son los que se van a comunicar, por lo tanto, debíamos tener una estructura completa para el cliente, junto con su respectiva interfaz, así como para el servidor. Así, identificamos que se debían separar en dos archivos distintos, que solo dependen del número de puerto del dispositivo y de su dirección IP.       

## **Dificultades del proyecto:** 
Una de las principales dificultades que afrontamos en la realización del proyecto, fue el uso de hilos, puesto que no conocíamos demasiado el funcionamiento general de estos, por lo que tuvimos que invertir una cantidad considerable de tiempo, en documentarnos adecuadamente para poder implementarlos correctamente. Y a pesar de eso, aun así se nos dificultó su uso, pues no teníamos un conocimiento lo suficientemente profundo para solucionar todos los problemas que se nos presentaron mientras se implementaron los mismos. 




## **Conclusión:**
En conclusión la arquitectura utilizada cliente / servidor es importante para todo lo relacionado con la informática, pues facilita el intercambio de información entre diferentes sistemas, permitiendo usar diferentes máquinas existentes con la diferencia de poder tener una interfaz más amigable y fácil de usar. El servidor es muy favorable en cuanto a la seguridad ya que controla el acceso a sus datos por lo que se necesita que el servidor realice una  autorización para poder acceder a él .
