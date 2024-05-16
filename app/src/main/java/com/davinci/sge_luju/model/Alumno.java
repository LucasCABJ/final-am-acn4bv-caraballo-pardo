package com.davinci.sge_luju.model;

import java.time.LocalDate;
import java.time.Period;

public class Alumno {
  // TODO: Definir atributos de la clase Alumno
  private String nombre;
  private String apellido;
  private String dni;
  private String email;
  private String telefono;
  private String direccion;
  private LocalDate fechaNacimiento;
  private String foto;
  // TODO: Implementar relación con Curso
  //private Curso curso;
  private String curso;
  private int id;

  public Alumno(String nombre, String apellido, String email) {
    setNombre(nombre);
    setApellido(apellido);
    setEmail(email);
    getEdadHardcodeada();
    setCurso("Desarrollo de Aplicaciones Moviles");
    setFoto("R.drawable.logoescuela"); // <- IMG hardcodeada
  }

  // Setters y Getters
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

  public String getFoto() {
    return foto;
  }

  private void setFoto(String s) {
    this.foto = s;
  }

  private void setEmail(String email) {
    this.email = email;
  }


  public String getFullName() {
    return getNombre() + " " + getApellido();
  }

//  public int calcularEdad() {
//    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//      Period periodo = Period.between(fechaNacimiento, LocalDate.now());
//      return periodo.getYears();
//    }
//    return 20;
//  }

  public int getEdadHardcodeada() {
    return (int) ((Math.random()*10) + 20);
  }
}