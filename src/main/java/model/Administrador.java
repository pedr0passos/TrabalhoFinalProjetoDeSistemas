/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.UUID;

/**
 *
 * @author pedro
 */

public class Administrador extends Usuario {

    private UUID id;
    private UUID idUsuario;

    public Administrador() {}

    public Administrador(UUID idUsuario) {
        this.id = UUID.randomUUID();
        this.idUsuario = idUsuario;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }
}
