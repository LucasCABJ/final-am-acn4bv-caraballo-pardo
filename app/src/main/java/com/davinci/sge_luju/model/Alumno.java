package com.davinci.sge_luju.model;

import android.os.Build;

import java.time.LocalDate;

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

  public Alumno(String nombre, String apellido, String email, String telefono) {
    setNombre(nombre);
    setApellido(apellido);
    setEmail(email);
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

  private void setFoto(String s) {
    this.foto = s;
  }

  private void setEmail(String email) {
    this.email = email;
  }


  private String getFullName() {
    return getNombre() + " " + getApellido();
  }

  private int calculateAge() {
    // TODO: Elevar el API a 26 ?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // <- Sugerencia del IDE
      return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
    return 20;
  }
}
