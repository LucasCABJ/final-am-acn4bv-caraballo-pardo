package com.davinci.sge_luju.model;

import java.time.LocalDate;
import java.time.Period;

public class Alumno {
  // TODO: Definir atributos de la clase Alumno
  private String nombre;
  private String apellido;
  private String dni;
  private String telefono;
  private String direccion;
  private Integer edad;
  private String curso;
  private int id;

  public Alumno(String nombre, String apellido, String curso, String imageURL) {
    setNombre(nombre);
    setApellido(apellido);
    getEdadHardcodeada();
    setCurso(curso);
  }

  public Alumno(String nombre, String apellido, String curso, String imageURL, Integer edad) {
    setNombre(nombre);
    setApellido(apellido);
    getEdadHardcodeada();
    setEdad(edad);
    setCurso(curso);
  }

  // Setters y Getters

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  public String getNombre() {
    return nombre;
  }

  private void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getCurso() {
    return curso;
  }

  public void setCurso(String curso) {
    this.curso = curso;
  }

  public String getFullName() {
    return getNombre() + " " + getApellido();
  }

  public int getEdadHardcodeada() {
    return (int) ((Math.random()*10) + 20);
  }
}