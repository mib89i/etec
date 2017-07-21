/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.model;

import br.etec.utilitarios.Datas;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import javax.faces.context.FacesContext;
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
import javax.persistence.Transient;
import javax.servlet.ServletContext;

/**
 *
 * @author Claudemir Rtools
 */
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nome", length = 255)
    private String nome;
    @Column(name = "sobre_nome", length = 255)
    private String sobreNome;
    @Column(name = "documento", length = 30)
    private String documento;
    @Column(name = "rg", length = 30)
    private String rg;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuario;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    @Column(name = "nacionalidade", length = 100)
    private String nacionalidade;
    @Column(name = "naturalidade", length = 100)
    private String naturalidade;
    @Column(name = "naturalidade_uf", length = 2)
    private String naturalidadeUF;
    @Column(name = "estado_civil", length = 50)
    private String estadoCivil;
    @Column(name = "endereco", length = 1000)
    private String endereco;
    @Column(name = "endereco_bairro", length = 1000)
    private String enderecoBairro;
    @Column(name = "endereco_cidade", length = 1000)
    private String enderecoCidade;
    @Column(name = "endereco_uf", length = 1000)
    private String enderecoUF;
    @Column(name = "nome_pai", length = 300)
    private String nomePai;
    @Column(name = "nome_mae", length = 300)
    private String nomeMae;
    @Column(name = "nacionalidade_pai", length = 100)
    private String nacionalidadePai;
    @Column(name = "nacionalidade_mae", length = 100)
    private String nacionalidadeMae;
    @Column(name = "foto", length = 1000)
    private String foto;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    @Column(name = "hora_cadastro", length = 10)
    private String horaCadastro;
    @JoinColumn(name = "id_cargo", referencedColumnName = "id")
    @ManyToOne
    private Cargo cargo;
    @Transient
    private String caminhoFoto;
    
    public Pessoa() {
        this.id = -1;
        this.nome = "";
        this.sobreNome = "";
        this.documento = "";
        this.rg = "";
        this.usuario = new Usuario();
        this.dataNascimento = null;
        this.nacionalidade = "";
        this.naturalidade = "";
        this.naturalidadeUF = "";
        this.estadoCivil = "";
        this.endereco = "";
        this.enderecoBairro = "";
        this.enderecoCidade = "";
        this.enderecoUF = "";
        this.nomePai = "";
        this.nomeMae = "";
        this.nacionalidadePai = "";
        this.nacionalidadeMae = "";
        this.foto = "";
        this.dataCadastro = null;
        this.horaCadastro = "";
        this.cargo = new Cargo();
    }

    public Pessoa(int id, String nome, String sobreNome, String documento, String rg, Usuario usuario, Date dataNascimento, String nacionalidade, String naturalidade, String naturalidadeUF, String estadoCivil, String endereco, String enderecoBairro, String enderecoCidade, String enderecoUF, String nomePai, String nomeMae, String nacionalidadePai, String nacionalidadeMae, String foto, Date dataCadastro, String horaCadastro, Cargo cargo) {
        this.id = id;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.documento = documento;
        this.rg = rg;
        this.usuario = usuario;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
        this.naturalidade = naturalidade;
        this.naturalidadeUF = naturalidadeUF;
        this.estadoCivil = estadoCivil;
        this.endereco = endereco;
        this.enderecoBairro = enderecoBairro;
        this.enderecoCidade = enderecoCidade;
        this.enderecoUF = enderecoUF;
        this.nomePai = nomePai;
        this.nomeMae = nomeMae;
        this.nacionalidadePai = nacionalidadePai;
        this.nacionalidadeMae = nacionalidadeMae;
        this.foto = foto;
        this.dataCadastro = dataCadastro;
        this.horaCadastro = horaCadastro;
        this.cargo = cargo;
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

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataNascimentoString() {
        return Datas.converteData(dataNascimento);
    }

    public void setDataNascimentoString(String dataNascimentoString) {
        this.dataNascimento = Datas.converte(dataNascimentoString);
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNaturalidadeUF() {
        return naturalidadeUF;
    }

    public void setNaturalidadeUF(String naturalidadeUF) {
        this.naturalidadeUF = naturalidadeUF;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEnderecoBairro() {
        return enderecoBairro;
    }

    public void setEnderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
    }

    public String getEnderecoCidade() {
        return enderecoCidade;
    }

    public void setEnderecoCidade(String enderecoCidade) {
        this.enderecoCidade = enderecoCidade;
    }

    public String getEnderecoUF() {
        return enderecoUF;
    }

    public void setEnderecoUF(String enderecoUF) {
        this.enderecoUF = enderecoUF;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNacionalidadePai() {
        return nacionalidadePai;
    }

    public void setNacionalidadePai(String nacionalidadePai) {
        this.nacionalidadePai = nacionalidadePai;
    }

    public String getNacionalidadeMae() {
        return nacionalidadeMae;
    }

    public void setNacionalidadeMae(String nacionalidadeMae) {
        this.nacionalidadeMae = nacionalidadeMae;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getHoraCadastro() {
        return horaCadastro;
    }

    public void setHoraCadastro(String horaCadastro) {
        this.horaCadastro = horaCadastro;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    public String getCaminhoFoto(){
        ServletContext servletContext = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
        if ( (this.id == -1 && this.foto.isEmpty()) || (this.id != -1 && this.foto.isEmpty())){
            return caminhoFoto = "/resources/images/sem_foto.png";
        }
        
        if (this.id == -1 && !this.foto.isEmpty()){
            String path_foto = servletContext.getRealPath("/resources/imagens/pessoa/-1/" + this.foto);
            if (new File(path_foto).exists()) {
                return caminhoFoto = "/resources/imagens/pessoa/-1/" + this.foto;
            } else {
                return caminhoFoto = "/resources/images/sem_foto.png";
            }
        }
        
        if (this.id != -1 && !this.foto.isEmpty()){
            String path_foto = servletContext.getRealPath("/resources/imagens/pessoa/" + this.id + "/" + this.foto);
            if (new File(path_foto).exists()) {
                return caminhoFoto = "/resources/imagens/pessoa/" + this.id + "/"  + this.foto;
            } else {
                return caminhoFoto = "/resources/images/sem_foto.png";
            }
        }
        return caminhoFoto;
    }
    
    public void setCaminhoFoto(String caminhoFoto){
        this.caminhoFoto = caminhoFoto;
    }
}
