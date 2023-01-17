package com.example.inventory.data.model;

import java.util.Objects;

public class Section {
    String nombre;
    String nombreCorto;
    Dependency dependency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return Objects.equals(getNombre(), section.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Dependency getDependency() {
        return dependency;
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public Section(String nombre, String nombreCorto, Dependency dependency) {
        this.nombre = nombre;
        this.nombreCorto = nombreCorto;
        this.dependency = dependency;
    }
}
