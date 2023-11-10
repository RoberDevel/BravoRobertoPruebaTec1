package com.pruebatecnica1.gestorempleados.logic.validation;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {

    public static boolean validationId(String opcion) {

        String patron = "^[0-9]+$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(opcion);

        // se comprueba si la cadena contiene solo números
        if (matcher.matches()) {
            //devuelve true si sí
            return true;
        } else {
            //devuelve false si no
            return false;
        }
    }

    public static boolean fechaInicioValidation(String fecha) {

        String pattern = "^\\d{2}/\\d{2}/\\d{4}$";

        //se compara si la fecha introducida coincide con el patron
        if (!fecha.matches(pattern)) {
            //si no coincide devuelve false y manda el siguiente mensaje
            System.out.println("-------> Error: Formato de fecha inválido. Introduzca la fecha como en el ejemplo: 30/08/2020");
            return false;
        }

        String[] componentesFecha = fecha.split("/");
        int dia = Integer.parseInt(componentesFecha[0]);
        int mes = Integer.parseInt(componentesFecha[1]);
        int ano = Integer.parseInt(componentesFecha[2]);


        // se valida el mes
        if (mes < 1 || mes > 12) {
            System.out.println("-------> Error: Número de mes inválido. Debe ser mayor a 0 y menor a 13.");
            return false;
        }

        // se valida el día
        if (dia < 1 || dia > 31) {
            System.out.println("-------> Error: Número de día inválido. Debe ser mayor a 0 y menor a 32.");
            return false;
        }

        // se crea un objeto LocalDate para la fecha introducida
        LocalDate fechaIntroducida = LocalDate.of(ano, mes, dia);

        // Comparamos la fecha introducida con la fecha actual
        if (fechaIntroducida.isAfter(LocalDate.now())) {
            //si es posterior devuelve false con el siguiente mensaje
            System.out.println("-------> Error: La fecha introducida debe ser anterior o igual a la fecha actual " + LocalDate.now());
            return false;
        }
        //si es anterior o el mismo dia, devuelve true
        return true;
    }

    public static boolean cadenaSinNumeroNiVacia(String opcion) {
        String patron = "^[^0-9]*$";
        Pattern pattern = Pattern.compile(patron);
        //Se comprueba que la cadena no esté vacia
        if (!opcion.isEmpty()) {
            //si no lo está, comprobamos que tenga el patrón correcto
            Matcher matcher = pattern.matcher(opcion);
            if (matcher.matches()) {
                //si está correcto devuelve true
                return true;
            } else {
                //si no lo está devuelve false con el siguiente mensaje
                System.out.print("-------> Error: Palabra con números, ");
                return false;
            }
        } else {
            //si está vacio devuelve false y manda este mensaje
            System.out.print("-------> Error: Palabra vacia, ");
            return false;
        }

    }

    public static boolean esNumero(String cadena) {
        //se comprueba que el String introducido se pueda transformar en un numero
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean esCargo(String cadena) {
        //se comprueba que la cadena introducida sea lo que se pide (1 a 5) cuando se introduce el cargo
        if (!cadena.equals("1") && !cadena.equals("2") && !cadena.equals("3") && !cadena.equals("4") && !cadena.equals("5")) {
            return false;
        } else return true;
    }

    public static String queCargo(String cargoCodigo) {
        //"Programador junior", "Programador senior", "Community manager", "Administrativo", "Lead developer"

        //metodo para transformar el código al cargo en si,
        //si se guardase en la bbdd con el codigo no seria necesario, pero se decidió realizarlo de esta forma

        switch (cargoCodigo) {
            case "1":
                cargoCodigo = "Programador Junior";
                return cargoCodigo;
            case "2":
                cargoCodigo = "Programador Senior";
                return cargoCodigo;
            case "3":
                cargoCodigo = "Community manager";
                return cargoCodigo;
            case "4":
                cargoCodigo = "Administrativo";
                return cargoCodigo;
            case "5":
                cargoCodigo = "Lead developer";
                return cargoCodigo;
            default:
                return "";
        }


    }

}
