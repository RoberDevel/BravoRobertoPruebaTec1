package com.pruebatecnica1.gestorempleados.logic.functionsCRUD;

import com.pruebatecnica1.gestorempleados.logic.entity.Empleado;
import com.pruebatecnica1.gestorempleados.logic.validation.DataValidation;
import com.pruebatecnica1.gestorempleados.persistence.PersistenceController;
import java.util.List;
import java.util.Scanner;

public class FunctionsCrud {

    public FunctionsCrud() {
    }

    //metodo para añadir un empleado
    public boolean anadirEmpleado(PersistenceController controller) {

        String opcion = "";
        String nombre = "";
        String apellido = "";
        String cargoCodigo = "";
        String cargo = "";
        String salario = "";
        String fechaInicio = "";
        int cont = 0;
        Scanner scanner = new Scanner(System.in);
        boolean caso = false;

        Empleado empleado1 = new Empleado("Alfonso", "Rodriguez", "2", 2000.0, "12/10/2023");
        Empleado empleado2 = new Empleado("Ricardo", "Lage", "2", 1500.0, "12/07/2023");
        Empleado empleado3 = new Empleado("Maria", "Almunia", "3", 2500.0, "07/04/2020");
        Empleado empleado4 = new Empleado("Laura", "Sanchez", "4", 3000.0, "28/07/2021");
        Empleado empleado5 = new Empleado("Marcos", "Seoane", "2", 3000.0, "28/04/2023");

        //se pide confirmación para continuar con la opcion elegida
        System.out.println("Pulse 1 para continuar u otra tecla para volver");
        opcion = scanner.nextLine();
        if (!opcion.equals("1")) {
            return true;
        }

        //Si se desea añadir empleados modelo, sólo aparece esta opcion cuando la bbdd está vacia.. (hack)
        if (controller.listaEmpleados().isEmpty()) {
            System.out.println("<<<HACK>>> BBDD vacia. Desea introducir empleados de prueba? Pulse \"1\" para confirmar u otra tecla para cancelar");
            opcion = scanner.nextLine();
            if (opcion.equals("1")) {
                controller.crearEmpleado(empleado1);
                controller.crearEmpleado(empleado2);
                controller.crearEmpleado(empleado3);
                controller.crearEmpleado(empleado4);
                controller.crearEmpleado(empleado5);
                System.out.println("Empleados guardados.. fin HACK");
            }
        }

        //se entra en un while para que si el usuario decide ingresar otro empleado pueda hacerlo sin salir del case elegido
        while (!caso) {

            //se piden datos y si es necesario se validan, si se introdujeron mal se vuelven a pedir
            System.out.println("Introduzca el nombre del empleado");
            nombre = scanner.nextLine();

            while (!DataValidation.cadenaSinNumeroNiVacia(nombre)) {
                System.out.println("introduzca de nuevo el apellido correctamente");
                nombre = scanner.nextLine();
            }

            System.out.println("Introduzca apellido del empleado");
            apellido = scanner.nextLine();
            while (!DataValidation.cadenaSinNumeroNiVacia(apellido)) {
                System.out.println("introduzca de nuevo el apellido correctamente");
                apellido = scanner.nextLine();
            }

            System.out.println("Introduzca el cargo, pulse \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer");
            cargoCodigo = scanner.nextLine();

            while (!DataValidation.esNumero(cargoCodigo)) {
                System.out.println("-------> Error: Debe introducir un numero, pulse \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer");
                cargoCodigo = scanner.nextLine();
            }
            while (!DataValidation.esCargo(cargoCodigo)) {
                System.out.println("-------> Error: Introduzca un valor entre 1 y 5, pulse \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer");
                cargoCodigo = scanner.nextLine();
            }
            cargo = DataValidation.queCargo(cargoCodigo);

            System.out.println("Introduzca el salario");
            salario = scanner.nextLine();

            while (!DataValidation.esNumero(salario)) {
                System.out.println("Introduzca el salario correctamente");
                salario = scanner.nextLine();
            }

            System.out.println("Introduzca la fecha de inicio, debe tener el siguiente formato: DD/MM/YYYY");
            fechaInicio = scanner.nextLine();

            while (!DataValidation.fechaInicioValidation(fechaInicio)) {
                fechaInicio = scanner.nextLine();
            }

            //se muestran los datos al usuario, y si le son correctos, se le pide confirmacion para guardar el empleado
            System.out.println("Los datos Nombre: " + nombre.toUpperCase() + ", Apellido: " + apellido.toUpperCase() + ", Cargo: " + cargo.toUpperCase() + " Salario: " + salario + " y Fecha de inicio: " + fechaInicio + " son correctos? Escriba \"1\" para confirmar y guardar u otra tecla para cancelar");
            opcion = scanner.nextLine();
            if (opcion.equals("1")) {
                //si confirma se guarda el empleado en la bbdd
                Empleado empleado = new Empleado(nombre, apellido, cargo, Double.parseDouble(salario), fechaInicio);
                controller.crearEmpleado(empleado);
                //y se le confirma que se realizó la operacion
                System.out.println("Empleado registrado correctamente");

            } else {
                //si el usuario ve algun error en los datos o no quiere guardar el empleado, se le muestra el siguiente mensaje
                System.out.println("No se ha añadido nuevo empleado ");
            }
            //se le pregunta si quiere añadir un nuevo empleado o si quiere salir
            System.out.println("Escriba \"1\" para añadir un nuevo empleado u otra tecla para salir");
            opcion = scanner.nextLine();
            if (!opcion.equals("1")) {
                break;
            }
        }
        return true;
    }

    //metodo para actualizar el empleado
    public boolean actualizarEmpleado(PersistenceController controller) {
        String opcion = "";
        boolean caso = false;
        String id = "";
        Scanner scanner = new Scanner(System.in);

        caso = false;
        System.out.println("Pulse 1 para continuar u otra tecla para volver");
        opcion = scanner.nextLine();
        if (!opcion.equals("1")) {
            return true;
        }
        while (!caso) {
            //se pide el id del empleado a modificar
            System.out.println("Introduzca el id del empleado");
            id = scanner.nextLine();
            // se comprueba si el id es valido
            if (DataValidation.validationId(id)) {
                //y se comprueba que el empleado exista en la bbdd
                if (controller.encontrarEmpleado(Integer.parseInt(id)) != null) {
                    Empleado empleado = controller.encontrarEmpleado(Integer.parseInt(id));
                    //si el id es valido, se muestra el nombre completo del empleado y se le pide que elija que desea modificar
                    System.out.println("El empleado que va a mofificar es " + empleado.getNombre().toUpperCase() + " " + empleado.getApellido().toUpperCase());
                    System.out.println("Escriba \"1\" para modificar nombre, \"2\" para apellido, \"3\" para cargo, \"4\" para salario, \"5\" para la fecha de inicio u otra tecla para volver al menu");
                    opcion = scanner.nextLine();
                    String confirmar = "";
                    switch (opcion) {
                        //dependiendo de la opcion elegida se modificará una cosa u otra, incluye validaciones y confirmacion
                        case "1":
                            System.out.println("Introduce nuevo nombre");
                            opcion = scanner.nextLine();
                            if (DataValidation.cadenaSinNumeroNiVacia(opcion)) {

                                System.out.println("El nuevo nombre va a ser " + opcion.toUpperCase() + ", pulse \"1\" para confirmar u otra tecla para cancelar");
                                confirmar = scanner.nextLine();
                                if (!confirmar.equals("1")) {
                                    caso = true;
                                    break;
                                }
                                empleado.setNombre(opcion);
                                controller.editarEmpleado(empleado);
                                System.out.println("Nombre modificado: " + opcion);

                            } else {
                                System.out.println("-------> Error: nombre no debe incluir numeros <-------");
                            }
                            System.out.println("Escriba \"1\" para modificar un nuevo empleado u otra tecla para salir");
                            opcion = scanner.nextLine();
                            if (!opcion.equals("1")) {
                                caso = true;
                                break;
                            }

                            break;

                        case "2":
                            System.out.println("Introduce nuevo apellido");
                            opcion = scanner.nextLine();
                            if (DataValidation.cadenaSinNumeroNiVacia(opcion)) {

                                System.out.println("El nuevo apellido va a ser " + opcion.toUpperCase() + ", pulse \"1\" para confirmar u otra tecla para cancelar");
                                confirmar = scanner.nextLine();
                                if (!confirmar.equals("1")) {
                                    caso = true;
                                    break;
                                }

                                empleado.setApellido(opcion);
                                controller.editarEmpleado(empleado);
                                System.out.println("Apellido modificado: " + opcion);
                            } else {
                                System.out.println("-------> Error: apellido no debe incluir numeros <-------");
                            }

                            System.out.println("Escriba \"1\" para modificar un nuevo empleado u otra tecla para salir");
                            opcion = scanner.nextLine();
                            if (!opcion.equals("1")) {
                                caso = true;
                                break;
                            }

                            break;
                        case "3":
                            System.out.println("Introduce nuevo cargo, pulse \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer");
                            opcion = scanner.nextLine();
                            boolean isCargo = false;
                            while (isCargo) {
                                if (DataValidation.esNumero(opcion)) {
                                    if (DataValidation.esCargo(opcion)) {
                                        opcion = DataValidation.queCargo(opcion);
                                        isCargo = false;

                                    } else {
                                        System.out.println("-------> Error: debe pulsar un número entre 1 y 5, \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer <-------");
                                        opcion = scanner.nextLine();
                                        break;
                                    }
                                } else {
                                    System.out.println("-------> Error: debe pulsar un número, \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer <-------");
                                    opcion = scanner.nextLine();
                                    break;
                                }
                                controller.editarEmpleado(empleado);
                                System.out.println("Cargo modificado: " + controller.encontrarEmpleado(Integer.parseInt(id)).getCargo());
                                System.out.println("Escriba \"1\" para modificar un nuevo empleado u otra tecla para salir");

                                if (!opcion.equals("1")) {
                                    caso = true;
                                    break;
                                }
                            }
                            break;
                        case "4":
                            System.out.println("Introduce nuevo salario");
                            opcion = scanner.nextLine();
                            if (DataValidation.esNumero(opcion)) {

                                System.out.println("El nuevo salario va a ser " + opcion + ", pulse \"1\" para confirmar u otra tecla para cancelar");
                                confirmar = scanner.nextLine();
                                if (!confirmar.equals("1")) {
                                    break;
                                }

                                empleado.setSalario(Double.parseDouble(opcion));
                                controller.editarEmpleado(empleado);
                                System.out.println("Salario modificado: " + opcion);
                            } else System.out.println("-------> Error: salario invalido <-------");
                            System.out.println("Escriba \"1\" para modificar un nuevo empleado u otra tecla para salir");
                            opcion = scanner.nextLine();
                            if (!opcion.equals("1")) {
                                caso = true;
                                break;
                            }

                            break;
                        case "5":
                            System.out.println("Introduce nueva fecha de inicio, FORMATO: XX/XX/XXXX");
                            opcion = scanner.nextLine();

                            while (!DataValidation.fechaInicioValidation(opcion)) {

                                opcion = scanner.nextLine();

                            }

                            System.out.println("La nueva fecha va a ser " + opcion + ", pulse \"1\" para confirmar u otra tecla para cancelar");
                            confirmar = scanner.nextLine();
                            if (!confirmar.equals("1")) {
                                caso = true;
                                break;
                            }

                            empleado.setFechaInicio(opcion);
                            controller.editarEmpleado(empleado);
                            System.out.println("Fecha de inicio modificada: " + opcion);

                            System.out.println("Escriba \"1\" para modificar un nuevo empleado u otra tecla para salir");
                            opcion = scanner.nextLine();
                            if (!opcion.equals("1")) {
                                break;
                            }

                            break;
                        default:
                            caso = true;
                    }
                } else {
                    //si el empleado con el id indicado no existe en la bbdd, se indica mediante mensaje y se le pide si quiere introducir de nuevo el id o salir
                    System.out.println("-------> Empleado con el id " + opcion + " no existe  <-------");
                    System.out.println("Escriba \"1\" para modificar un nuevo empleado u otra tecla para salir");
                    opcion = scanner.nextLine();
                    if (!opcion.equals("1")) {
                        break;
                    }

                }
            } else {
                //si el id está mal escrito, se le indica mediante mensaje y se le pide si quiere introducir de nuevo el id o salir
                System.out.println("-------> Error: El id introducido no es un número <-------");
                System.out.println("Escriba \"1\" para modificar un nuevo empleado u otra tecla para salir");
                opcion = scanner.nextLine();
                if (!opcion.equals("1")) {
                    break;
                }
            }
        }
        return true;
    }

    //metodo para listar empleados por el mismo rango
    public boolean empleadosPorRango(PersistenceController controller) {

        //buscar empleados por cargo
        boolean caso = false;
        String opcion = "";
        String cargoCodigo = "";

        Scanner scanner = new Scanner(System.in);


        while (!caso) {

            System.out.println("Introduzca el cargo, pulse \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer");
            opcion = scanner.nextLine();
            while (!DataValidation.esNumero(opcion)) {
                System.out.println("-------> Error: Debe introducir un numero, pulse \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer");
                cargoCodigo = scanner.nextLine();
            }
            while (!DataValidation.esCargo(opcion)) {
                System.out.println("-------> Error: Introduzca un valor entre 1 y 5, pulse \"1\" para Programador junior, \"2\" para Programador senior, \"3\" para Community manager, \"4\" para Administrativo, \"5\" para Lead developer");
                cargoCodigo = scanner.nextLine();
            }
            opcion = DataValidation.queCargo(opcion);

            List<Empleado> list = controller.listaEmpleadoPorCargo(opcion);

            //probando programacion funcional dada en clase
            controller.listaEmpleadoPorCargo(opcion).forEach(empleado -> {
                System.out.println("------> Nombre completo: " + empleado.getNombre().toUpperCase() + " " + empleado.getApellido().toUpperCase() + ", cargo: " + empleado.getCargo().toUpperCase() + " salario: " + empleado.getSalario() + ", fecha de inicio: " + empleado.getFechaInicio());
            });
            if (list.isEmpty()) System.out.println("No hay ningún empleado con ese cargo.");
            break;
        }
        return true;
    }

    public boolean eliminarEmpleado(PersistenceController controller) {

        //eliminar empleado
        boolean caso = false;
        String opcion = "";
        String id = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pulse 1 para continuar u otra tecla para volver");
        opcion = scanner.nextLine();
        if (!opcion.equals("1")) {
            return true;
        }
        while (!caso) {

            System.out.println("Introduzca el id del empleado");
            id = scanner.nextLine();
            while (!DataValidation.validationId(id)) {
                System.out.println("Id incorrecto, introduzca un id correcto");
                id = scanner.nextLine();
            }

            if (controller.encontrarEmpleado(Integer.parseInt(id)) == null) {
                System.out.println("El empleado con id " + id + " no existe");
                break;
            }

            System.out.println("Va a borrar el empleado " + controller.encontrarEmpleado(Integer.parseInt(id)).getNombre().toUpperCase() + " " + controller.encontrarEmpleado(Integer.parseInt(id)).getApellido().toUpperCase() + "," +
                    " pulse 1 para confirmar u otra tecla para cancelar");
            opcion = scanner.nextLine();
            if (!opcion.equals("1")) {
                System.out.println("Borrado cancelado");
                break;
            }

            controller.borrarEmpleado(Integer.parseInt(id));
            System.out.println("Empleado con id " + id + " ha sido eliminado");


            System.out.println("Escriba \"1\" para eliminar un empleado u otra tecla para salir");
            opcion = scanner.nextLine();
            if (!opcion.equals("1")) {

                break;
            }
        }
        return true;
    }

    public boolean listarEmpleado(PersistenceController controller) {

        //listar empleados
        List<Empleado> listaEmp = controller.listaEmpleados();
        listaEmp.forEach(empleado -> {
            System.out.println("------> Nombre completo: " + empleado.getNombre().toUpperCase() + " " + empleado.getApellido().toUpperCase() + ", cargo: " + empleado.getCargo().toUpperCase() + " salario: " + empleado.getSalario() + ", fecha de inicio: " + empleado.getFechaInicio());
        });
        if (listaEmp.isEmpty()) {

            System.out.println("Todavia no hay ningún empleado registrado");
        }
        return true;

    }
}
