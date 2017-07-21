/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.model;

import br.com.rtools.utilitarios.Moeda;
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
@Table(name = "pessoa_empresa")
public class PessoaEmpresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoa;
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_admissao")
    private Date dataAdmissao;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_registro")
    private Date dataRegistro;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_opcao")
    private Date dataOpcao;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_retratacao")
    private Date dataRetratacao;
    @Column(name = "secao", length = 100)
    private String secao;
    @Column(name = "salario_inicial")
    private Float salarioInicial;
    @Column(name = "comissao")
    private Float comissao;
    @Column(name = "tarefa", length = 100)
    private String tarefa;
    @Column(name = "forma_pagamento", length = 100)
    private String formaPagamento;
    @Column(name = "optante", length = 100)
    private String optante;
    @Column(name = "hora_entrada", length = 10)
    private String horaEntrada;
    @Column(name = "hora_intervalo_almoco", length = 10)
    private String horaIntervaloAlmoco;
    @Column(name = "hora_saida", length = 10)
    private String horaSaida;
    @Column(name = "hora_descanso_semanal", length = 10)
    private String horaDescansoSemanal;
    @Column(name = "banco_depositario", length = 100)
    private String bancoDepositario;

    public PessoaEmpresa() {
        this.id = -1;
        this.pessoa = new Pessoa();
        this.empresa = null;
        this.dataAdmissao = null;
        this.dataRegistro = null;
        this.dataOpcao = null;
        this.dataRetratacao = null;
        this.secao = "";
        this.salarioInicial = new Float(0);
        this.comissao = new Float(0);
        this.tarefa = "";
        this.formaPagamento = "";
        this.optante = "";
        this.horaEntrada = "";
        this.horaIntervaloAlmoco = "";
        this.horaSaida = "";
        this.horaDescansoSemanal = "";
        this.bancoDepositario = "";
    }

    public PessoaEmpresa(int id, Pessoa pessoa, Empresa empresa, Date dataAdmissao, Date dataRegistro, Date dataOpcao, Date dataRetratacao, String secao, Float salarioInicial, Float comissao, String tarefa, String formaPagamento, String optante, String horaEntrada, String horaIntervaloAlmoco, String horaSaida, String horaDescansoSemanal, String bancoDepositario) {
        this.id = id;
        this.pessoa = pessoa;
        this.empresa = empresa;
        this.dataAdmissao = dataAdmissao;
        this.dataRegistro = dataRegistro;
        this.dataOpcao = dataOpcao;
        this.dataRetratacao = dataRetratacao;
        this.secao = secao;
        this.salarioInicial = salarioInicial;
        this.comissao = comissao;
        this.tarefa = tarefa;
        this.formaPagamento = formaPagamento;
        this.optante = optante;
        this.horaEntrada = horaEntrada;
        this.horaIntervaloAlmoco = horaIntervaloAlmoco;
        this.horaSaida = horaSaida;
        this.horaDescansoSemanal = horaDescansoSemanal;
        this.bancoDepositario = bancoDepositario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getDataAdmissaoString() {
        return Datas.converteData(dataAdmissao);
    }

    public void setDataAdmissaoString(String dataAdmissaoString) {
        this.dataAdmissao = Datas.converte(dataAdmissaoString);
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getDataRegistroString() {
        return Datas.converteData(dataRegistro);
    }

    public void setDataRegistroString(String dataRegistroString) {
        this.dataRegistro = Datas.converte(dataRegistroString);
    }

    public Date getDataOpcao() {
        return dataOpcao;
    }

    public void setDataOpcao(Date dataOpcao) {
        this.dataOpcao = dataOpcao;
    }

    public String getDataOpcaoString() {
        return Datas.converteData(dataOpcao);
    }

    public void setDataOpcaoString(String dataOpcaoString) {
        this.dataOpcao = Datas.converte(dataOpcaoString);
    }

    public Date getDataRetratacao() {
        return dataRetratacao;
    }

    public void setDataRetratacao(Date dataRetratacao) {
        this.dataRetratacao = dataRetratacao;
    }

    public String getDataRetratacaoString() {
        return Datas.converteData(dataRetratacao);
    }

    public void setDataRetratacaoString(String dataRetratacaoString) {
        this.dataRetratacao = Datas.converte(dataRetratacaoString);
    }

    public String getSecao() {
        return secao;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }

    public Float getSalarioInicial() {
        return salarioInicial;
    }

    public void setSalarioInicial(Float salarioInicial) {
        this.salarioInicial = salarioInicial;
    }
    
    public String getSalarioInicialString() {
        return Moeda.converteR$Float(salarioInicial);
    }

    public void setSalarioInicialString(String salarioInicialString) {
        this.salarioInicial = Moeda.converteUS$(salarioInicialString);
    }

    public Float getComissao() {
        return comissao;
    }

    public void setComissao(Float comissao) {
        this.comissao = comissao;
    }
    
    public String getComissaoString() {
        return Moeda.converteR$Float(comissao);
    }

    public void setComissaoString(String comissaoString) {
        this.comissao = Moeda.converteUS$(comissaoString);
    }
    

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getOptante() {
        return optante;
    }

    public void setOptante(String optante) {
        this.optante = optante;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraIntervaloAlmoco() {
        return horaIntervaloAlmoco;
    }

    public void setHoraIntervaloAlmoco(String horaIntervaloAlmoco) {
        this.horaIntervaloAlmoco = horaIntervaloAlmoco;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public String getHoraDescansoSemanal() {
        return horaDescansoSemanal;
    }

    public void setHoraDescansoSemanal(String horaDescansoSemanal) {
        this.horaDescansoSemanal = horaDescansoSemanal;
    }

    public String getBancoDepositario() {
        return bancoDepositario;
    }

    public void setBancoDepositario(String bancoDepositario) {
        this.bancoDepositario = bancoDepositario;
    }

}
