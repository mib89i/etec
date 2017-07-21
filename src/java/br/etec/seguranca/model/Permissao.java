/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.model;

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
@Table(name = "permissao")
public class Permissao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nome", length = 255)
    private String nome;
    @Column(name = "descricao", length = 255)
    private String descricao;
    @JoinColumn(name = "id_pagina", referencedColumnName = "id")
    @ManyToOne
    private Pagina pagina;       

    public Permissao() {
        this.id = -1;
        this.nome = "";
        this.descricao = "";
        this.pagina = new Pagina();
    }
    

    public Permissao(int id, String nome, String descricao, Pagina pagina) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.pagina = pagina;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }
    
    
}
