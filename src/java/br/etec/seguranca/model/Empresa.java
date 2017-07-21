package br.etec.seguranca.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nome", length = 1000)
    private String nome;
    @Column(name = "cnpj", length = 100)
    private String cnpj;
    @Column(name = "endereco", length = 1000)
    private String endereco;
    @Column(name = "endereco_bairro", length = 1000)
    private String enderecoBairro;
    @Column(name = "endereco_cidade", length = 1000)
    private String enderecoCidade;
    @Column(name = "endereco_uf", length = 1000)
    private String enderecoUF;

    public Empresa() {
        this.id = -1;
        this.nome = "";
        this.cnpj = "";
        this.endereco = "";
        this.enderecoBairro = "";
        this.enderecoCidade = "";
        this.enderecoUF = "";
    }

    public Empresa(int id, String nome, String cnpj, String endereco, String enderecoBairro, String enderecoCidade, String enderecoUF) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.enderecoBairro = enderecoBairro;
        this.enderecoCidade = enderecoCidade;
        this.enderecoUF = enderecoUF;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

}
