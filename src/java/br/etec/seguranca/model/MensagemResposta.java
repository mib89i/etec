/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.model;

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
@Table(name = "mensagem_resposta")
public class MensagemResposta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "texto", length = 3000)
    private String texto;
    @JoinColumn(name = "id_mensagem", referencedColumnName = "id")
    @ManyToOne
    private Mensagem mensagem;
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa pessoa;
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

    public MensagemResposta() {
        this.id = -1;
        this.texto = "";
        this.mensagem = new Mensagem();
        this.pessoa = new Pessoa();
        this.data = null;
        this.hora = "";
        this.dataVisualizacao = null;
        this.horaVisualizacao = "";        
    }

    public MensagemResposta(int id, String texto, Mensagem mensagem, Pessoa pessoa, Date data, String hora, Date dataVisualizacao, String horaVisualizacao) {
        this.id = id;
        this.texto = texto;
        this.mensagem = mensagem;
        this.pessoa = pessoa;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
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
