/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.controller;

import br.etec.conexao.Dao;
import br.etec.seguranca.dao.GrupoDao;
import br.etec.seguranca.model.Grupo;
import br.etec.seguranca.model.Pagina;
import br.etec.seguranca.model.Permissao;
import br.etec.seguranca.model.PermissaoGrupo;
import br.etec.utilitarios.MensagemFlash;
import br.etec.utilitarios.Sessao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Claudemir Rtools
 */
@ManagedBean
@SessionScoped
public class GrupoController implements Serializable {

    private Grupo grupo = new Grupo();
    private List<Grupo> listaGrupo = new ArrayList();
    private List<ListaPagina> listaPagina = new ArrayList();
    private String descricaoPesquisa = "";

    public GrupoController() {
        loadListaGrupo();
    }
    
    public final void loadPagina() {
        if (!FacesContext.getCurrentInstance().isPostback()){
            Sessao.remove("grupoController");
            loadListaGrupo();
        }
    }
    

    public final void loadListaGrupo() {
        listaGrupo.clear();

        listaGrupo = new Dao().list(new Grupo());

        grupo = listaGrupo.get(0);

        loadListaPagina();
    }

    public final void loadListaPagina() {
        listaPagina.clear();
        
        List<Pagina> result =  new GrupoDao().listaPagina(descricaoPesquisa);
        //List<Pagina> result = new Dao().list(new Pagina());
        
        for (Pagina p : result) {
            List<Object[]> result_p = new GrupoDao().listaPermissaoGrupo(grupo.getId(), p.getId());

            List<ListaPermissaoCheck> lpc = new ArrayList();

            for (Object[] l : result_p) {
                lpc.add(new ListaPermissaoCheck((Boolean) l[2], (Permissao) new Dao().find(new Permissao(), (Integer) l[0])));
            }

            if (!lpc.isEmpty()) {
                listaPagina.add(
                        new ListaPagina(
                                p,
                                lpc
                        )
                );
            }
        }
    }

    public String linkGrupo() {
        loadListaGrupo();
        return "grupo";
    }

    public void editar(Grupo g) {
        grupo = g;
        loadListaPagina();
    }

    public void editarPermissaoPagina(ListaPermissaoCheck lpc) {
        Dao dao = new Dao();

        PermissaoGrupo pg = new GrupoDao().pesquisaPermissaoGrupo(grupo.getId(), lpc.getPermissao().getId());

        dao.begin();
        if (lpc.getCheck()) {
            if (pg != null) {
                dao.rollback();
                MensagemFlash.fatal("", "PERMISSÃO JÁ INSERIDA, TENTE NOVAMENTE!");
                return;
            }
            
            pg = new PermissaoGrupo(-1, lpc.getPermissao(), grupo);

            if (!dao.save(pg)) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ATUALIZAR PERMISSÃO DO GRUPO, TENTE NOVAMENTE!");
                return;
            }
        } else {
            if (pg == null) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ENCONTRAR PERMISSÃO, TENTE NOVAMENTE!");
                return;
            }

            if (!dao.remove(dao.find(pg))) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ATUALIZAR PERMISSÃO DO GRUPO, TENTE NOVAMENTE!");
                return;
            }
        }

        dao.commit();
        MensagemFlash.info("", "PERMISSÃO ALTERADA!");
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public List<ListaPagina> getListaPagina() {
        return listaPagina;
    }

    public void setListaPagina(List<ListaPagina> listaPagina) {
        this.listaPagina = listaPagina;
    }

    public String getDescricaoPesquisa() {
        return descricaoPesquisa;
    }

    public void setDescricaoPesquisa(String descricaoPesquisa) {
        this.descricaoPesquisa = descricaoPesquisa;
    }

    public class ListaPagina {

        private Pagina pagina;
        private List<ListaPermissaoCheck> listaPermissaoCheck;

        public ListaPagina() {
            this.pagina = new Pagina();
            this.listaPermissaoCheck = new ArrayList();
        }

        public ListaPagina(Pagina pagina, List<ListaPermissaoCheck> listaPermissaoCheck) {
            this.pagina = pagina;
            this.listaPermissaoCheck = listaPermissaoCheck;
        }

        public Pagina getPagina() {
            return pagina;
        }

        public void setPagina(Pagina pagina) {
            this.pagina = pagina;
        }

        public List<ListaPermissaoCheck> getListaPermissaoCheck() {
            return listaPermissaoCheck;
        }

        public void setListaPermissaoCheck(List<ListaPermissaoCheck> listaPermissaoCheck) {
            this.listaPermissaoCheck = listaPermissaoCheck;
        }
    }

    public class ListaPermissaoCheck {

        private Boolean check;
        private Permissao permissao;

        public ListaPermissaoCheck() {
            this.check = false;
            this.permissao = new Permissao();
        }

        public ListaPermissaoCheck(Boolean check, Permissao permissao) {
            this.check = check;
            this.permissao = permissao;
        }

        public Boolean getCheck() {
            return check;
        }

        public void setCheck(Boolean check) {
            this.check = check;
        }

        public Permissao getPermissao() {
            return permissao;
        }

        public void setPermissao(Permissao permissao) {
            this.permissao = permissao;
        }
    }

}
