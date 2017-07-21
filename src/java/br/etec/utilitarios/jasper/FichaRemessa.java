/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.utilitarios.jasper;

/**
 *
 * @author Claudemir Rtools
 */
public class FichaRemessa {

    private Object copia;
    private Object numero;
    private Object ano;
    private Object para;
    private Object motivo;
    private Object ordem;
    private Object processo;
    private Object envio;
    private Object data_remessa;
    private Object usuario;
    private Object responsavel;

    public FichaRemessa(Object copia, Object numero, Object ano, Object para, Object motivo, Object ordem, Object processo, Object envio, Object data_remessa, Object usuario, Object responsavel) {
        this.copia = copia;
        this.numero = numero;
        this.ano = ano;
        this.para = para;
        this.motivo = motivo;
        this.ordem = ordem;
        this.processo = processo;
        this.envio = envio;
        this.data_remessa = data_remessa;
        this.usuario = usuario;
        this.responsavel = responsavel;
    }

    public Object getCopia() {
        return copia;
    }

    public void setCopia(Object copia) {
        this.copia = copia;
    }

    public Object getNumero() {
        return numero;
    }

    public void setNumero(Object numero) {
        this.numero = numero;
    }

    public Object getAno() {
        return ano;
    }

    public void setAno(Object ano) {
        this.ano = ano;
    }

    public Object getPara() {
        return para;
    }

    public void setPara(Object para) {
        this.para = para;
    }

    public Object getMotivo() {
        return motivo;
    }

    public void setMotivo(Object motivo) {
        this.motivo = motivo;
    }

    public Object getOrdem() {
        return ordem;
    }

    public void setOrdem(Object ordem) {
        this.ordem = ordem;
    }

    public Object getProcesso() {
        return processo;
    }

    public void setProcesso(Object processo) {
        this.processo = processo;
    }

    public Object getEnvio() {
        return envio;
    }

    public void setEnvio(Object envio) {
        this.envio = envio;
    }

    public Object getData_remessa() {
        return data_remessa;
    }

    public void setData_remessa(Object data_remessa) {
        this.data_remessa = data_remessa;
    }

    public Object getUsuario() {
        return usuario;
    }

    public void setUsuario(Object usuario) {
        this.usuario = usuario;
    }

    public Object getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Object responsavel) {
        this.responsavel = responsavel;
    }

}
