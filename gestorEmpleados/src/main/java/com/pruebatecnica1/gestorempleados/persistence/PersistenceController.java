package com.pruebatecnica1.gestorempleados.persistence;

import com.pruebatecnica1.gestorempleados.logic.entity.Empleado;
import com.pruebatecnica1.gestorempleados.persistence.exceptions.NonexistentEntityException;
import java.util.List;

public class PersistenceController {

    EmpleadoJpaController empleadoJpa = new EmpleadoJpaController();


    public void crearEmpleado(Empleado empleado) {
        empleadoJpa.create(empleado);
    }

    public void borrarEmpleado(int id) {
        try {
            empleadoJpa.destroy(id);
        } catch (NonexistentEntityException e) {
            System.out.println(e.getMessage());
        }
    }

    public Empleado encontrarEmpleado(int id) {
        try {
            return empleadoJpa.findEmpleado(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void editarEmpleado(Empleado empleado) {
        try {
            empleadoJpa.edit(empleado);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Empleado> listaEmpleados() {
        return empleadoJpa.findEmpleadoEntities();
    }

    public List<Empleado> listaEmpleadoPorCargo(String cargo) {
        return empleadoJpa.findEmpleadoEntitiesByDepartamento(cargo, 100, 0);
    }

}
