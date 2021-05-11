package com.erirodri.dbConsumer.model;

import javax.persistence.*;

/***********************************************************
 *   Clase donde se define el modelo de la tabla Usuarios
 *     1. Se crea el modelo para poder ser usado desde java
 *          como un objeto dentro de Spring
 *
 *     -- Se implementa JPA::Hibernet --
 *
 ***********************************************************/

@Table(name="USUARIOS")
@Entity()
public class UsuarioTb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "NOMBRE", length = 254)
    private String nombre;
    @Column(name = "AP_PATERNO", length = 254)
    private String apPaterno;
    @Column(name = "AP_MATERNO", length = 254)
    private String apMaterno;
    @Column(name = "USER", length = 8)
    private String user;
    @Column(name = "PASSWORD", length = 12)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
