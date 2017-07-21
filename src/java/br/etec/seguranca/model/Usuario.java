package br.etec.seguranca.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "usuario", length = 255)
    private String usuario;
    @Column(name = "senha", length = 255)
    private String senha;
    @Column(name = "administrador")
    private Boolean administrador;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id")
    @ManyToOne
    private Grupo grupo;

    public Usuario() {
        this.id = -1;
        this.usuario = "";
        this.senha = "";
        this.administrador = false;
        this.grupo = new Grupo();        
    }

    public Usuario(int id, String usuario, String senha, Boolean administrador, Grupo grupo) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.administrador = administrador;
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

}
