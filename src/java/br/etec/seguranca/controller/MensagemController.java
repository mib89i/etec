/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.controller;

import br.etec.conexao.Dao;
import br.etec.seguranca.dao.MensagemDao;
import br.etec.seguranca.dao.UsuarioDao;
import br.etec.seguranca.model.Mensagem;
import br.etec.seguranca.model.MensagemResposta;
import br.etec.seguranca.model.Pessoa;
import br.etec.seguranca.model.Usuario;
import br.etec.utilitarios.Datas;
import br.etec.utilitarios.FormUpdate;
import br.etec.utilitarios.MensagemFlash;
import br.etec.utilitarios.Sessao;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Claudemir Rtools
 */
@ManagedBean
@SessionScoped
public class MensagemController implements Serializable {

    private List<Mensagem> listaMensagem = new ArrayList();
    private Mensagem mensagem = new Mensagem();
    private String descricaoPesquisaPessoa = "";
    private List<Pessoa> listaPessoaMensagem = new ArrayList();
    private MensagemResposta mensagemResposta = new MensagemResposta();
    private List<MensagemResposta> listaMensagemResposta = new ArrayList();

    public MensagemController() {
        loadListaMensagem();
    }

    public final void loadListaMensagem() {
        listaMensagem.clear();

        if (Sessao.exist("sessao_usuario")) {
            listaMensagem = new MensagemDao().listaMensagens((Usuario) Sessao.get("sessao_usuario"));
        }
    }

    public final void loadListaMensagemResposta() {
        listaMensagemResposta.clear();

        listaMensagemResposta = new MensagemDao().listaMensagemResposta(mensagem.getId());
    }

    public final void loadListaPessoaMensagem() {
        loadListaPessoaMensagem(true);
    }

    public void loadListaPessoaMensagem(Boolean limpar_pesquisa) {
        if (limpar_pesquisa) {
            descricaoPesquisaPessoa = "";
        }

        listaPessoaMensagem.clear();

        listaPessoaMensagem = new MensagemDao().listaPessoaParaMensagem(descricaoPesquisaPessoa);
    }

    public void ver(Mensagem m) {
        mensagem = m;
        if (m.getPessoaDestinatario().getUsuario().getId() == ((Usuario) Sessao.get("sessao_usuario")).getId() && mensagem.getDataVisualizacaoString().isEmpty()) {
            Dao dao = new Dao();

            dao.begin();

            mensagem.setDataVisualizacao(Datas.dataHoje());
            mensagem.setHoraVisualizacao(Datas.hora());

            if (!dao.update(mensagem)) {
                dao.rollback();
                MensagemFlash.fatal("", "ERRO AO ATUALIZAR VISUALIZAÇÃO!");
                return;
            }

            dao.commit();
        }
        loadListaMensagemResposta();
    }

    public void novaMensagem() {
        loadListaPessoaMensagem();
        mensagem = new Mensagem();
    }

    public void salvarMensagem() {
        if (mensagem.getPessoaDestinatario().getId() == -1) {
            MensagemFlash.fatal("", "SELECIONE UM USUÁRIO PARA ESTA MENSAGEM!");
            FormUpdate.update("form_mensagem:panel_nova_mensagem");
            return;
        }

        if (mensagem.getTitulo().isEmpty()) {
            MensagemFlash.fatal("", "DIGITE UM TÍTULO!");
            FormUpdate.update("form_mensagem:panel_nova_mensagem");
            return;
        }

        if (mensagem.getTexto().isEmpty()) {
            MensagemFlash.fatal("", "DIGITE UMA MENSAGEM!");
            FormUpdate.update("form_mensagem:panel_nova_mensagem");
            return;
        }

        Dao dao = new Dao();

        dao.begin();

        mensagem.setPessoaRemetente(new UsuarioDao().pesquisaPessoaUsuario(((Usuario) Sessao.get("sessao_usuario")).getId()));
        mensagem.setData(Datas.dataHoje());
        mensagem.setHora(Datas.hora());
        if (!dao.save(mensagem)) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ENVIAR MENSAGEM!");
            FormUpdate.update("form_mensagem:panel_nova_mensagem");
            return;
        }
        dao.commit();

        mensagem = new Mensagem();
        loadListaMensagem();
        MensagemFlash.info("", "MENSAGEM ENVIADA!");

        //FormUpdate.update("form_mensagem");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("painel_de_controle.xhtml");
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public void excluirMensagem() {

        Dao dao = new Dao();

        dao.begin();

        for (MensagemResposta mr : listaMensagemResposta) {
            if (!dao.remove(dao.find(mr))) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL EXCLUIR RESPOSTAS DESTA MENSAGEM!");
                FormUpdate.update("form_mensagem:panel_ver_mensagem");
                return;
            }
        }

        if (!dao.remove(mensagem)) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL EXCLUIR ESTA MENSAGEM!");
            FormUpdate.update("form_mensagem:panel_ver_mensagem");
            return;
        }
        
        dao.commit();
        
        mensagem = new Mensagem();
        loadListaMensagem();
        
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("painel_de_controle.xhtml");
        } catch (IOException ex) {
            ex.getMessage();
        }
        
    }

    public void responderMensagem() {
        if (mensagemResposta.getTexto().isEmpty()) {
            MensagemFlash.fatal("", "DIGITE UMA RESPOSTA!");
            return;
        }

        Dao dao = new Dao();

        dao.begin();

        mensagemResposta.setMensagem(mensagem);
        mensagemResposta.setPessoa(new UsuarioDao().pesquisaPessoaUsuario(((Usuario) Sessao.get("sessao_usuario")).getId()));
        mensagemResposta.setData(Datas.dataHoje());
        mensagemResposta.setHora(Datas.hora());

        if (!dao.save(mensagemResposta)) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ENVIAR MENSAGEM!");
            return;
        }

        dao.commit();

        mensagemResposta = new MensagemResposta();
        loadListaMensagemResposta();
    }

    public void selecionarPessoa(Pessoa p) {
        mensagem.setPessoaDestinatario(p);
    }

    public List<Mensagem> getListaMensagem() {
        return listaMensagem;
    }

    public void setListaMensagem(List<Mensagem> listaMensagem) {
        this.listaMensagem = listaMensagem;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    public String getDescricaoPesquisaPessoa() {
        return descricaoPesquisaPessoa;
    }

    public void setDescricaoPesquisaPessoa(String descricaoPesquisaPessoa) {
        this.descricaoPesquisaPessoa = descricaoPesquisaPessoa;
    }

    public List<Pessoa> getListaPessoaMensagem() {
        return listaPessoaMensagem;
    }

    public void setListaPessoaMensagem(List<Pessoa> listaPessoaMensagem) {
        this.listaPessoaMensagem = listaPessoaMensagem;
    }

    public MensagemResposta getMensagemResposta() {
        return mensagemResposta;
    }

    public void setMensagemResposta(MensagemResposta mensagemResposta) {
        this.mensagemResposta = mensagemResposta;
    }

    public List<MensagemResposta> getListaMensagemResposta() {
        return listaMensagemResposta;
    }

    public void setListaMensagemResposta(List<MensagemResposta> listaMensagemResposta) {
        this.listaMensagemResposta = listaMensagemResposta;
    }

}
