/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.controller;

import br.etec.seguranca.dao.UsuarioDao;
import br.etec.seguranca.model.Pessoa;
import br.etec.seguranca.model.Usuario;
import br.etec.utilitarios.MensagemFlash;
import br.etec.utilitarios.Sessao;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Claudemir Rtools
 */
@ManagedBean
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario usuario = new Usuario();
    private String sessaoMessageLogin = "";
    private Pessoa pessoa = new Pessoa();

    public String entrar() {
        limpar_sessao_antes_de_iniciar();

        UsuarioDao dao = new UsuarioDao();

        Usuario u = dao.pesquisaUsuario(usuario.getUsuario(), usuario.getSenha());

        if (u != null) {

            pessoa = dao.pesquisaPessoaUsuario(u.getId());

            if (pessoa == null) {
                MensagemFlash.fatal("", "USUÁRIO SEM PESSOA CADASTRADA!");
                return null;
            }

            Sessao.put("sessao_usuario", u);
            Object redirect_page = Sessao.get("sessao_redirect_page", true);

            sessaoMessageLogin = "";
            usuario = new Usuario();

            if (redirect_page != null) {
                return redirect_page.toString().replace(".xhtml", "");
            } else {
                return "painel_de_controle";
            }
        } else {
            MensagemFlash.fatal("", "USUÁRIO E/OU SENHA INVÁLIDOS");
            return null;
        }
    }

    public String sair() {
        FacesContext conext = FacesContext.getCurrentInstance();
        //Verifica a sessao e a grava na variavel
        HttpSession session = (HttpSession) conext.getExternalContext().getSession(false);
        //Fecha/Destroi sessao
        session.invalidate();
        return "index";
    }

    public void limpar_sessao_antes_de_iniciar() {
        Sessao.remove("grupoController");
        Sessao.remove("mensagemController");
        Sessao.remove("notaController");
        Sessao.remove("pessoaController");
        Sessao.remove("cargoController");
    }

    public void validacao() throws IOException {
        HttpServletRequest pagina_requerida = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String uri_pagina = pagina_requerida.getRequestURI().replace("/etec/", "");

        if (!Sessao.exist("sessao_usuario")) {
            Sessao.put("sessao_redirect_page", uri_pagina);
            Sessao.put("sessao_message_login", "ENTRE NO SISTEMA PARA TER ACESSO A ESSA PÁGINA");

            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }

        valida_permissao_tela(uri_pagina);
//        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("indicaAcesso") == null) {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("/Sindical/acessoNegado.jsf");
//            return null;
//        }
    }

    public void valida_permissao_tela(String pagina) throws IOException {
        switch (pagina) {
            case "painel_de_controle.xhtml":
                return;
            case "lista_pessoas.xhtml":
                if (temPermissao("listar_pessoas")) {
                    return;
                }
                break;
            case "pessoa.xhtml":
                if (temPermissao("pessoa")) {
                    return;
                }
                break;
            case "lista_empresas.xhtml":
                if (temPermissao("listar_empresas")) {
                    return;
                }
                break;
            case "grupo.xhtml":
                if (temPermissao("listar_grupos")) {
                    return;
                }
                break;
            case "lista_cargos.xhtml":
                if (temPermissao("listar_cargos")) {
                    return;
                }
                break;
            case "cargo.xhtml":
                if (temPermissao("cargo")) {
                    return;
                }
                break;
            case "empresa.xhtml":
                if (temPermissao("empresa")) {
                    return;
                }
                break;
            case "remessa.xhtml":
                if (temPermissao("remessa")) {
                    return;
                }
                break;
            case "lista_remessas.xhtml":
                if (temPermissao("listar_remessas")) {
                    return;
                }
                break;
            case "lista_protocolo.xhtml":
                if (temPermissao("listar_protocolo")) {
                    return;
                }
                break;
            case "protocolo.xhtml":
                if (temPermissao("protocolo")) {
                    return;
                }
                break;
            default:
                //Object redirect_page = Sessao.get("sessao_redirect_page", true);

                //if (redirect_page != null) {
                //FacesContext.getCurrentInstance().getExternalContext().redirect(redirect_page.toString());
                //} else {
                //}
                break;
//                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//                try {
//                    response.sendRedirect("");
//                    FacesContext.getCurrentInstance().responseComplete();
//                } catch (Exception ex) {
//                    ex.getMessage();
//                }
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("painel_de_controle.xhtml");
    }

    public Boolean temPermissao(String descricao_permissao) {
        if (!Sessao.exist("sessao_usuario")) {
            return false;
        }

        Usuario u = (Usuario) Sessao.get("sessao_usuario");
        if (u.getAdministrador() == true) {
            return true;
        }
        // SE LISTA DE PERMISSAO FOR VAZIA ENTÃO NÃO TEM PERMISSÃO
        // SE VIER RESULTADO NA LISTA DE PERMISSÃO ENTÃO TEM PERMISSÃO

        return !new UsuarioDao().listaPermissaoUsuario(u.getGrupo().getId(), u.getId(), descricao_permissao).isEmpty();

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the sessaoMessageLogin
     */
    public String getSessaoMessageLogin() {
        if (Sessao.exist("sessao_message_login")) {
            sessaoMessageLogin = (String) Sessao.get("sessao_message_login", true);
        }
        return sessaoMessageLogin;
    }

    /**
     * @param sessaoMessageLogin the sessaoMessageLogin to set
     */
    public void setSessaoMessageLogin(String sessaoMessageLogin) {
        this.sessaoMessageLogin = sessaoMessageLogin;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Usuario getUsuarioSessao() {
        return (Usuario) Sessao.get("sessao_usuario");
    }
    
    public static Usuario usuarioSessao() {
        return (Usuario) Sessao.get("sessao_usuario");
    }
}
