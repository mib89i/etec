/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.protocolo.controller;

import br.etec.protocolo.model.Protocolo;
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
public class ProtocoloController implements Serializable {

    private String descricaoPesquisa = "";
    private String porPesquisa = "numero";
    
    private String numero = "";
    
    private List<Protocolo> listaProtocolo = new ArrayList();
            
    public ProtocoloController(){
        loadListaProtocolo();
    }
    
    public final void loadListaProtocolo(){
        listaProtocolo.clear();
    }
    
    public String linkCadastroProtocolo() {
        novo();
        return "protocolo";
    }

    public void novo() {

    }

    public String getDescricaoPesquisa() {
        return descricaoPesquisa;
    }

    public void setDescricaoPesquisa(String descricaoPesquisa) {
        this.descricaoPesquisa = descricaoPesquisa;
    }

    public String getPorPesquisa() {
        return porPesquisa;
    }

    public void setPorPesquisa(String porPesquisa) {
        this.porPesquisa = porPesquisa;
    }

    public List<Protocolo> getListaProtocolo() {
        return listaProtocolo;
    }

    public void setListaProtocolo(List<Protocolo> listaProtocolo) {
        this.listaProtocolo = listaProtocolo;
    }
}
