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
@Table(name = "remessa_detalhe")
public class RemessaDetalhe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ordem")
    private String ordem;
    @Column(name = "processo")
    private String processo;
    @Column(name = "envio", length = 2000)
    private String envio;
    @JoinColumn(name = "id_remessa", referencedColumnName = "id")
    @ManyToOne
    private Remessa remessa;

    public RemessaDetalhe() {
        this.id = -1;
        this.ordem = "";
        this.processo = "";
        this.envio = "";
        this.remessa = new Remessa();
    }

    public RemessaDetalhe(int id, String ordem, String processo, String envio, Remessa remessa) {
        this.id = id;
        this.ordem = ordem;
        this.processo = processo;
        this.envio = envio;
        this.remessa = remessa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getEnvio() {
        return envio;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }

    public Remessa getRemessa() {
        return remessa;
    }

    public void setRemessa(Remessa remessa) {
        this.remessa = remessa;
    }

}
