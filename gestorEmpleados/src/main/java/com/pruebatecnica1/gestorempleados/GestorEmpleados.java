package com.pruebatecnica1.gestorempleados;

import com.pruebatecnica1.gestorempleados.logic.functionsCRUD.FunctionsCrud;
import com.pruebatecnica1.gestorempleados.persistence.PersistenceController;
import java.util.Scanner;


public class GestorEmpleados {

    public static void main(String[] args) {

        String opcion = "";
        boolean entrarFlag = true;
        boolean inicioFlag = true;
        FunctionsCrud funcion = new FunctionsCrud();

        //INICIO PROGRAMA
        System.out.println("--------------------------------Bienvenido al gestor de empleados--------------------------------");

        //se entra en un while que incluye el código de tdo el programa, con este while se permite volver a ejecutar este código si la conexion con la bbdd falla
        while (entrarFlag) {
            //Simulando la espera para conectarse con la base de datos, si se teclea algo mientras se pinta el programa se rompe, unica excepcion no controlada
            System.out.print("Conectando con la base de datos");
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(".");
            }
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println();
                PersistenceController controller = new PersistenceController();
                String[] listaMenu = new String[]{
                        "añadir empleado", "actualizar informacion de empleado", "buscar empleados por cargo", "eliminar un empleado", "mostrar todos los empleados"
                };

                //se entra en un while
                while (inicioFlag) {

                    //se pide elegir una opcion del menu
                    System.out.println("\n--------------------------------Menu Principal--------------------------------");
                    System.out.print("Pulse ");
                    for (int i = 0; i < listaMenu.length; i++) {
                        System.out.print("\"" + (i + 1) + "\" para " + listaMenu[i] + ", ");
                    }
                    System.out.println("u otra tecla para salir");
                    opcion = scanner.nextLine();

                    //si se pulsa uno de los numeros que se pide se entrará en la opcion correspondiente, pulsar cualquier otra tecla se saldrá del programa
                    if (opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4") || opcion.equals("5")) {
                        System.out.println("Ha introducido " + listaMenu[Integer.parseInt(opcion) - 1].toUpperCase());
                    } else {
                        System.out.println("Hasta la próxima!");
                        inicioFlag = false;
                        entrarFlag = false;
                        break;
                    }
                    //dependiendo de la opcion pedida, se realizará la funcion correspondiente
                    switch (opcion) {
                        case "1":
                            if (funcion.anadirEmpleado(controller)) break;
                        case "2":
                            if (funcion.actualizarEmpleado(controller)) break;
                        case "3":
                            if (funcion.empleadosPorRango(controller)) break;
                        case "4":
                            if (funcion.eliminarEmpleado(controller)) break;
                        case "5":
                            if (funcion.listarEmpleado(controller)) break;
                    }
                }

            } catch (Exception e) {
                //si hay algun tipo de error, como por ejemplo que no se pueda conectar a la bbdd, lanzará el siguiente mensaje con el error
                System.out.println("Ha habido un error: " + e.getMessage());

                //se pintan puntos para separar el error del siguiente mensaje
                for (int i = 0; i < 5; i++) {
                    System.out.println(".");
                }

                //se pide elegir entre volver a intentar conectar o por el contrario cerrar el programa
                System.out.println("No se ha podido conectar con la base de datos. Pulse \"1\" para volver a intentarlo u otra tecla para salir");
                opcion = scanner.nextLine();
                if (!opcion.equals("1")) {
                    inicioFlag = false;
                    entrarFlag = false;
                    System.out.println("Hasta la próxima!");
                }
            }
        }
    }
}
