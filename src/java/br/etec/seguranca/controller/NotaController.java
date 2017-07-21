/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.controller;

import br.etec.conexao.Dao;
import br.etec.seguranca.dao.NotaDao;
import br.etec.seguranca.model.Nota;
import br.etec.seguranca.model.Usuario;
import br.etec.utilitarios.MensagemFlash;
import br.etec.utilitarios.Sessao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Claudemir Rtools
 */
@ManagedBean
@SessionScoped
public class NotaController implements Serializable {
    private Nota nota = new Nota();
    private List<Nota> listaNota = new ArrayList();
    
    public NotaController(){
        loadListaNota();
    }
    
    public final void loadListaNota(){
        listaNota = new NotaDao().listaNotaUsuario(((Usuario) Sessao.get("sessao_usuario")).getId());
    }
    
    public void salvarNota(){
        Dao dao = new Dao();
        dao.begin();
        if (nota.getId() == -1){
            nota.setUsuario((Usuario) Sessao.get("sessao_usuario"));
            if(!dao.save(nota)){
                MensagemFlash.fatal("", "ERRO AO SALVAR NOTA, TENTE NOVAMENTE!");
                dao.rollback();
                return;
            }
            
            MensagemFlash.info("", "NOTA SALVA COM SUCESSO!");
        }else{
            if(!dao.update(nota)){
                MensagemFlash.error("", "ERRO AO ATUALIZAR NOTA, TENTE NOVAMENTE!");
                dao.rollback();
                return;
            }
            
            MensagemFlash.info("", "NOTA ATUALIZADA COM SUCESSO!");
        }
         
        dao.commit();
        loadListaNota();
        nota = new Nota();
    }
    
    public void editarNota(Nota n){
        nota = n;
    }
    
    public void excluirNota(){
        Dao dao = new Dao();
        dao.begin();
        if (nota.getId() != -1){
            if(!dao.remove(nota)){
                MensagemFlash.error("", "ERRO AO EXCLUIR NOTA, TENTE NOVAMENTE!");
                dao.rollback();
                return;
            }
        }
        
        dao.commit();
        
        loadListaNota();
        nota = new Nota();
        MensagemFlash.info("", "NOTA EXCLUÍDA COM SUCESSO!");
    }
    
    public void novaNota(){
        nota = new Nota();
    }

    
    
    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    /**
     * @return the listaNota
     */
    public List<Nota> getListaNota() {
        return listaNota;
    }

    /**
     * @param listaNota the listaNota to set
     */
    public void setListaNota(List<Nota> listaNota) {
        this.listaNota = listaNota;
    }
}
