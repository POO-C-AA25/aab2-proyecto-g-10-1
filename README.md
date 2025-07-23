# Proyecto bimestral de investigación y desarrollo

## Aplicar POO con estructuras de control/datos (arreglos estáticos/dinámicos), y serialización de archivos

* Usar el programa **DIA-UML** _(Open source)_ (u otros) para generar la representación de su solución _(modelado)_, vía diagramas de clases.
* Genere/agregue 2 archivos _(fuente e img: \*.dia y \*.png \*.jpeg, etc)_. Titular su modelado con el nombre representativo del análisis/solución; el subDirectorio para ello es: **Modelado_UML**
* En el subDirectorio **Solucion_Codigo** cree un único proyecto NetBeans - _Java Aplication_ (o con el IDE de su preferencia) y en él, agregue todas las clases necesarias para la solución _(use el empaquetado para aplicar el patrón arquitectónico MVC)_. Dentro de este mismo directorio almacene su solución Python.
* Respete la arquitectura **MVC**, es decir, no implemente entradas/salidas desde/hacia teclado-consola directamente en las clases base _(paquetes **MC**)_. Los datos de entrada y resultados deben ser ingresados/mostrados desde/hacia teclado-consola, en la clase de prueba/ejecutor _(paquete **V**)_. No olvide el uso de paquetes para ello.
* En el subDirectorio **Varios**, puede agregar archivos adicionales, uno de ellos corresponden a los slides para la defensa grupal y calificación individual de su solución.
* Todos los objetos/información serán almacenados en archivos con extensión .dat, haciendo uso de la serialización de objetos.
* Aquí revise la miscelánea  de temas para el proyecto: **[Miscelanea ejercicios Proyecto.docx](https://github.com/POO-UTPL/AAB1_Proyecto/blob/main/Varios/Miscelanea%20ejercicios%20Proyecto.docx)**, y elija uno de ellos. 
* Considere la rúbrica de evaluación de esta actividad, con el fin de elaborar cada componente de su solución y defensa dados los lineamientos específicos de calificación.  
___

> [!Note]
> La fecha de presentación final de su proyecto con los apartados de: Modelado, Solución y Slides _(del UML, código, resultados)_, es en la **Semana 16** del primer bimestre. 
___

# 5. 🧾 **TEMA DE PROYECTOS NRO. 5: Sistema de facturación del SuperMaxi - Loja**

---

**El objetivo del proyecto** es desarrollar un sistema de facturación para el *SuperMaxi en Loja*. Este sistema deberá permitir la facturación de **N productos**, considerando **precios normales** y **promocionales** cuando existan muchos productos en stock o su fecha de caducidad esté próxima.

Además, se deberá realizar una factura que resuma los totales de **impuestos a la renta deducibles** por productos en las siguientes categorías: *Vivienda*, *Educación*, *Alimentación*, *Vestimenta* y *Salud*.

Al final del día, se generará una **estadística de ventas totales**, por productos y categorías, que ayudará a los gerentes del *SuperMaxi* en la toma de decisiones.

---

## 📌 **Características por considerar:**

•	**Gestión de productos**: Diseñe el modelado para agregar y gestionar productos en el sistema, considerando su *cantidad en stock*, *fecha de caducidad* y *precios normales y promocionales*.  
•	**Facturación**: Desarrollar un sistema que calcule el *monto total de la factura*, teniendo en cuenta los *precios normales y promocionales*, y que muestre un *resumen de los impuestos a la renta deducibles* por cada categoría de producto.  
•	**Estadísticas de ventas**: Generar *estadísticas de ventas diarias*, que incluyan las *ventas totales* y *desgloses por productos y categorías*, para tomar decisiones gerenciales.  
•	Además, el sistema deberá permitir diferenciar entre distintos tipos de productos: *locales, importados, perecibles y no perecibles*, considerando atributos específicos como *país de origen, aranceles, materiales o fecha de vencimiento*.  
•	Se deberá incluir también la gestión de diferentes tipos de clientes: *Persona Natural, Persona Jurídica y Consumidor Final*, cada uno con datos personalizados como *nombre de contacto, estado civil y una clave genérica*. El sistema calculará los *valores deducibles de impuestos* según la categoría de los productos adquiridos y el tipo de cliente.  
•	Se deberá incluir una opción para generar *estadísticas de ventas totales*, que incluyan las *ventas totales y desgloses por productos y categorías*, para tomar decisiones gerenciales a futuro.

