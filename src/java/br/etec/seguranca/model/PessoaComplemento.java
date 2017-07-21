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
@Table(name = "pessoa_complemento")
public class PessoaComplemento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ctps", length = 50)
    private String ctps;
    @Column(name = "serie", length = 50)
    private String serie;
    @Column(name = "certificado_reservista", length = 50)
    private String certificadoReservista;
    @Column(name = "categoria", length = 50)
    private String categoria;
    @Column(name = "titulo_eleitor", length = 50)
    private String tituloEleitor;
    @Column(name = "zona", length = 50)
    private String zona;
    @Column(name = "secao", length = 50)
    private String secao;
    @Column(name = "cor", length = 50)
    private String cor;
    @Column(name = "altura", length = 50)
    private String altura;
    @Column(name = "cabelos", length = 50)
    private String cabelos;
    @Column(name = "olhos", length = 50)
    private String olhos;
    @Column(name = "sinais", length = 50)
    private String sinais;
    @Column(name = "peso")
    private Integer peso;
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoa;

    public PessoaComplemento() {
        this.id = -1;
        this.ctps = "";
        this.serie = "";
        this.certificadoReservista = "";
        this.categoria = "";
        this.tituloEleitor = "";
        this.zona = "";
        this.secao = "";
        this.cor = "";
        this.altura = "";
        this.cabelos = "";
        this.olhos = "";
        this.sinais = "";
        this.peso = null;
        this.pessoa = new Pessoa();
    }

    public PessoaComplemento(int id, String ctps, String serie, String certificadoReservista, String categoria, String tituloEleitor, String zona, String secao, String cor, String altura, String cabelos, String olhos, String sinais, Integer peso, Pessoa pessoa) {
        this.id = id;
        this.ctps = ctps;
        this.serie = serie;
        this.certificadoReservista = certificadoReservista;
        this.categoria = categoria;
        this.tituloEleitor = tituloEleitor;
        this.zona = zona;
        this.secao = secao;
        this.cor = cor;
        this.altura = altura;
        this.cabelos = cabelos;
        this.olhos = olhos;
        this.sinais = sinais;
        this.peso = peso;
        this.pessoa = pessoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCertificadoReservista() {
        return certificadoReservista;
    }

    public void setCertificadoReservista(String certificadoReservista) {
        this.certificadoReservista = certificadoReservista;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSecao() {
        return secao;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getCabelos() {
        return cabelos;
    }

    public void setCabelos(String cabelos) {
        this.cabelos = cabelos;
    }

    public String getOlhos() {
        return olhos;
    }

    public void setOlhos(String olhos) {
        this.olhos = olhos;
    }

    public String getSinais() {
        return sinais;
    }

    public void setSinais(String sinais) {
        this.sinais = sinais;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
