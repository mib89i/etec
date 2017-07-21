/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.protocolo.model;

import br.etec.remessa.model.RemessaDestino;
import br.etec.seguranca.model.Usuario;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Claudemir Rtools
 */
@Entity
@Table(name = "protocolo")
public class Protocolo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "enviado_por")
    private String enviado_por;
    @JoinColumn(name = "id_remessa_destino", referencedColumnName = "id")
    @ManyToOne
    private RemessaDestino remessaDestino;
    @Column(name = "detalhes")
    private String detalhes;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuario;

    public Protocolo() {
        this.id = -1;
        this.numero = 0;
        this.enviado_por = "";
        this.remessaDestino = null;
        this.detalhes = "";
        this.usuario = new Usuario();
    }

    public Protocolo(int id, Integer numero, String enviado_por, RemessaDestino remessaDestino, String detalhes, Usuario usuario) {
        this.id = id;
        this.numero = numero;
        this.enviado_por = enviado_por;
        this.remessaDestino = remessaDestino;
        this.detalhes = detalhes;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getNumeroString() {
        return "0000".substring(0, 4 - numero.toString().length()) + numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEnviado_por() {
        return enviado_por;
    }

    public void setEnviado_por(String enviado_por) {
        this.enviado_por = enviado_por;
    }

    public RemessaDestino getRemessaDestino() {
        return remessaDestino;
    }

    public void setRemessaDestino(RemessaDestino remessaDestino) {
        this.remessaDestino = remessaDestino;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
