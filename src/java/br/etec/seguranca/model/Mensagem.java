/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.model;

import br.etec.utilitarios.Datas;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Claudemir Rtools
 */
@Entity
@Table(name = "mensagem")
public class Mensagem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "titulo", length = 255)
    private String titulo;
    @Column(name = "texto", length = 3000)
    private String texto;
    @JoinColumn(name = "id_pessoa_remetente", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaRemetente;
    @JoinColumn(name = "id_pessoa_destinatario", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoaDestinatario;
    @Temporal(TemporalType.DATE)
    @Column(name = "data")
    private Date data;
    @Column(name = "hora", length = 20)
    private String hora;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_visualizacao")
    private Date dataVisualizacao;
    @Column(name = "hora_visualizacao", length = 20)
    private String horaVisualizacao;

    public Mensagem() {
        this.id = -1;
        this.titulo = "";
        this.texto = "";
        this.pessoaRemetente = new Pessoa();
        this.pessoaDestinatario = new Pessoa();
        this.data = null;
        this.hora = "";
        this.dataVisualizacao = null;
        this.horaVisualizacao = "";
    }

    public Mensagem(int id, String titulo, String texto, Pessoa pessoaRemetente, Pessoa pessoaDestinatario, Date data, String hora, Date dataVisualizacao, String horaVisualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.pessoaRemetente = pessoaRemetente;
        this.pessoaDestinatario = pessoaDestinatario;
        this.data = data;
        this.hora = hora;
        this.dataVisualizacao = dataVisualizacao;
        this.horaVisualizacao = horaVisualizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Pessoa getPessoaRemetente() {
        return pessoaRemetente;
    }

    public void setPessoaRemetente(Pessoa pessoaRemetente) {
        this.pessoaRemetente = pessoaRemetente;
    }

    public Pessoa getPessoaDestinatario() {
        return pessoaDestinatario;
    }

    public void setPessoaDestinatario(Pessoa pessoaDestinatario) {
        this.pessoaDestinatario = pessoaDestinatario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDataString() {
        return Datas.converteData(data);
    }

    public void setDataString(String dataString) {
        this.data = Datas.converte(dataString);
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Date getDataVisualizacao() {
        return dataVisualizacao;
    }

    public void setDataVisualizacao(Date dataVisualizacao) {
        this.dataVisualizacao = dataVisualizacao;
    }

    public String getDataVisualizacaoString() {
        return Datas.converteData(dataVisualizacao);
    }

    public void setDataVisualizacaoString(String dataVisualizacaoString) {
        this.dataVisualizacao = Datas.converte(dataVisualizacaoString);
    }
    
    public String getHoraVisualizacao() {
        return horaVisualizacao;
    }

    public void setHoraVisualizacao(String horaVisualizacao) {
        this.horaVisualizacao = horaVisualizacao;
    }

}
