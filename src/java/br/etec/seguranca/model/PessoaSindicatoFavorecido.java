/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.model;

import br.com.rtools.utilitarios.Moeda;
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
@Table(name = "pessoa_sindicato_favorecido")
public class PessoaSindicatoFavorecido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "sindicato")
    private String sindicato;
    @Column(name = "valor")
    private Float valor;
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoa;

    public PessoaSindicatoFavorecido() {
        this.id = -1;
        this.ano = null;
        this.sindicato = "";
        this.valor = new Float(0);
        this.pessoa = new Pessoa();
    }

    public PessoaSindicatoFavorecido(int id, Integer ano, String sindicato, Float valor, Pessoa pessoa) {
        this.id = id;
        this.ano = ano;
        this.sindicato = sindicato;
        this.valor = valor;
        this.pessoa = pessoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getSindicato() {
        return sindicato;
    }

    public void setSindicato(String sindicato) {
        this.sindicato = sindicato;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }
    
    public String getValorString() {
        return Moeda.converteR$Float(valor);
    }

    public void setValorString(String valorString) {
        this.valor = Moeda.converteUS$(valorString);
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
