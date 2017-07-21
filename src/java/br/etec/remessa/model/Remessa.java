/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.remessa.model;

import br.etec.seguranca.model.Usuario;
import br.etec.utilitarios.Datas;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Claudemir Rtools
 */
@Entity
@Table(name = "remessa")
public class Remessa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "numero")
    private Integer numero;
    @JoinColumn(name = "id_remessa_destino", referencedColumnName = "id")
    @ManyToOne
    private RemessaDestino remessaDestino;
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    @ManyToOne
    private Usuario responsavel;
    @Column(name = "data_remessa")
    @Temporal(TemporalType.DATE)
    private Date dataRemessa;
    @JoinColumn(name = "id_remessa_destino_pessoa", referencedColumnName = "id")
    @ManyToOne
    private RemessaDestinoPessoa remessaDestinoPessoa;

    public Remessa() {
        this.id = -1;
        this.numero = 0;
        this.remessaDestino = new RemessaDestino();
        this.responsavel = new Usuario();
        this.dataRemessa = Datas.dataHoje();
        this.remessaDestinoPessoa = null;
    }

    public Remessa(int id, Integer numero, RemessaDestino remessaDestino, Usuario responsavel, Date dataRemessa, RemessaDestinoPessoa remessaDestinoPessoa) {
        this.id = id;
        this.numero = numero;
        this.remessaDestino = remessaDestino;
        this.responsavel = responsavel;
        this.dataRemessa = dataRemessa;
        this.remessaDestinoPessoa = remessaDestinoPessoa;
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

    public RemessaDestino getRemessaDestino() {
        return remessaDestino;
    }

    public void setRemessaDestino(RemessaDestino remessaDestino) {
        this.remessaDestino = remessaDestino;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public Date getDataRemessa() {
        return dataRemessa;
    }

    public void setDataRemessa(Date dataRemessa) {
        this.dataRemessa = dataRemessa;
    }

    public String getDataRemessaString() {
        return Datas.converteData(dataRemessa);
    }

    public void setDataRemessaString(String dataRemessaString) {
        this.dataRemessa = Datas.converte(dataRemessaString);
    }

    public RemessaDestinoPessoa getRemessaDestinoPessoa() {
        return remessaDestinoPessoa;
    }

    public void setRemessaDestinoPessoa(RemessaDestinoPessoa remessaDestinoPessoa) {
        this.remessaDestinoPessoa = remessaDestinoPessoa;
    }
}
