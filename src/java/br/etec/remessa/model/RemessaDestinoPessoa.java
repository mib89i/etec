/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.remessa.model;

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
@Table(name = "remessa_destino_pessoa")
public class RemessaDestinoPessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "id_remessa_destino", referencedColumnName = "id")
    @ManyToOne
    private RemessaDestino remessaDestino;
    @Column(name = "nome_responsavel")
    private String nome_responsavel;

    public RemessaDestinoPessoa() {
        this.id = -1;
        this.remessaDestino = new RemessaDestino();
        this.nome_responsavel = "";
    }
    
    public RemessaDestinoPessoa(int id, RemessaDestino remessaDestino, String nome_responsavel) {
        this.id = id;
        this.remessaDestino = remessaDestino;
        this.nome_responsavel = nome_responsavel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RemessaDestino getRemessaDestino() {
        return remessaDestino;
    }

    public void setRemessaDestino(RemessaDestino remessaDestino) {
        this.remessaDestino = remessaDestino;
    }

    public String getNome_responsavel() {
        return nome_responsavel;
    }

    public void setNome_responsavel(String nome_responsavel) {
        this.nome_responsavel = nome_responsavel;
    }
    
}
