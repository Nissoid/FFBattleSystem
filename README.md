# Sistema de batalla de Final Fantasy para consola (v2.0) ⚔️

Un motor de simulador de combate RPG por turnos basado en texto, desarrollado en Java e inspirado en la mecánica clásica de Final Fantasy. Este proyecto demuestra conceptos fundamentales de la Programación Orientada a Objetos (POO), principios SOLID (SRP) y Arquitectura MVC.

## 🚀 Características

* **Arquitectura MVC Limpia:** Separación total entre la lógica de negocio (Modelos) y la interfaz gráfica de consola (Vista). Los modelos procesan datos de forma independiente al entorno visual.
* **Gestión de Plantilla (Patrón Factory):** Inclusión de personajes de FFVII (Cloud, Sephiroth, Barret) y FFX (Tidus, Yuna, Wakka) generados dinámicamente mediante una fábrica de objetos centralizada.
* **Sistema de Inventario Completo:** Los personajes cuentan con un inventario propio de objetos consumibles (Pociones, Éteres) que restauran salud (HP) o magia (MP) sin sobrepasar los límites máximos.
* **Sistema de Habilidades Dinámicas y Curación:** Los personajes utilizan un sistema de listas (`ArrayList`) para almacenar y gestionar múltiples hechizos. Las habilidades pueden ser ofensivas o curativas, consumiendo MP dinámicamente.
* **Enemigo Controlado por IA:** El enemigo toma decisiones de forma autónoma utilizando un sistema pseudoaleatorio ponderado (`Random`) para decidir entre ataques físicos o habilidades especiales.
* **Control Interactivo y Validación:** Menú de consola dinámico y a prueba de errores. El sistema detecta entradas no válidas (typos) sin saltarse ni penalizar el turno del jugador.

## 🧱 Conceptos y Patrones de Diseño Aplicados

* **Principio de Responsabilidad Única (SRP):** Cada clase tiene un único propósito. El controlador orquesta, la vista dibuja, la fábrica construye y el modelo calcula.
* **Patrón Factory (Fábrica):** Centralización de la creación de personajes, habilidades y objetos en una única clase, aislando la lógica de inicialización del motor de juego.
* **Encapsulación y Delegación:** Los atributos se mantienen privados. Las responsabilidades se delegan (ej. el atacante calcula el daño base, pero delega la reducción real de vida al método interno del objetivo).
* **Composición:** La clase `Character` establece relaciones "tiene un" al contener listas de objetos `Skill` e `Item` como atributos principales.

## 📂 Resumen de Clases

* `FFBattleSystem.java` *(Controlador)*: El motor principal. Orquesta el flujo del combate, enlaza los modelos con la vista y administra los turnos hasta declarar un ganador.
* `TerminalUI.java` *(Vista)*: La única clase autorizada para interactuar con la consola (`Scanner` y `System.out`). Genera los menús, lee los inputs del jugador y muestra el HUD de batalla.
* `GameFactory.java` *(Fábrica/Base de Datos)*: El "director de casting". Se encarga de instanciar y equipar a todos los personajes con sus estadísticas, inventarios y habilidades correspondientes.
* `Character.java` *(Modelo)*: Entidad de dominio pura. Gestiona las estadísticas (HP, MP, Ataque, etc.), el inventario y calcula los resultados matemáticos del combate.
* `Skill.java` *(Modelo)*: Plantilla de datos para magias y ataques especiales (coste de MP, daño, tipo de efecto).
* `Item.java` *(Modelo)*: Plantilla de datos para objetos consumibles y gestión de cantidades.

## 🗺️ Hoja de ruta de desarrollo

* ✅ **Fase 1:** Ciclo de batalla principal, clases y estadísticas básicas (v1.0)
* ✅ **Fase 1.5:** Listas de habilidades dinámicas y lógica de curación (v1.1)
* ✅ **Fase 2:** Sistema de inventario y artículos consumibles (pociones, éteres) integrados (v2.0)
* ✅ **Fase 3:** Refactorización arquitectónica (MVC), Patrón Factory y ampliación del *Roster* de personajes (v2.0)
* ⏳ **Fase 4:** Efectos de estado alterado (Veneno, Ceguera, Parálisis, etc.)
* ⏳ **Fase 5:** Funciones de combate avanzadas (Sistema de Velocidad, Invocaciones y Límites/Overdrive)
* ⏳ **Fase 6:** Interfaz gráfica de usuario (Migración de `TerminalUI` a una GUI con JavaFX/Swing)

## 🛠️ Cómo ejecutar

1. Clona este repositorio: `git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git`
2. Navega al directorio del proyecto: `cd YOUR_REPOSITORY_NAME`
3. Compila los archivos Java: `javac *.java`
4. Ejecuta la simulación: `java FFBattleSystem`

*Desarrollado por Dani*
