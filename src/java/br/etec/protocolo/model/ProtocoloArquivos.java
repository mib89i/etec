/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.protocolo.model;

import br.etec.remessa.model.RemessaDestino;
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
@Table(name = "protocolo_arquivos")
public class ProtocoloArquivos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "nome_arquivo")
    private String nome_arquivo;
    @JoinColumn(name = "id_protocolo", referencedColumnName = "id")
    @ManyToOne
    private Protocolo protocolo;

    public ProtocoloArquivos() {
        this.id = -1;
        this.nome = "";
        this.protocolo = new Protocolo();
    }

    public ProtocoloArquivos(int id, String nome, Protocolo protocolo) {
        this.id = id;
        this.nome = nome;
        this.protocolo = protocolo;
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

    public Protocolo getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
    }

}
