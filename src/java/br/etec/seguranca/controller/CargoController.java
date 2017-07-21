/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.controller;

import br.etec.conexao.Dao;
import br.etec.seguranca.model.Cargo;
import br.etec.utilitarios.MensagemFlash;
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
public class CargoController {

    private List<Cargo> listaCargo = new ArrayList();
    private Cargo cargo = new Cargo();

    public CargoController() {
        loadListaCargo();
    }

    public void salvar() {
        if (!validaSalvar()) {
            return;
        }

        Dao dao = new Dao();
        dao.begin();

        if (cargo.getId() == -1) {
            if (!dao.save(cargo)) {
                MensagemFlash.fatal("", "ERRO AO SALVAR CARGO!");
                dao.rollback();
                return;
            }
            MensagemFlash.info("", "CARGO SALVO!");
        } else {
            if (!dao.update(cargo)) {
                MensagemFlash.fatal("", "ERRO AO ATUALIZAR CARGO!");
                dao.rollback();
                return;
            }
            MensagemFlash.info("", "CARGO ATUALIZADO!");
        }

        dao.commit();
        novo();
    }

    public Boolean validaSalvar() {
        if (cargo.getNome().isEmpty()) {
            MensagemFlash.fatal("", "DIGITE UM NOME PARA O CARGO!");
            return false;
        }
        return true;
    }

    public void excluir() {
        Dao dao = new Dao();
        dao.begin();

        if (!dao.remove(dao.find(cargo))) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL EXCLUIR CARGO, VERIFIQUE SE ELE JÁ ESTA SENDO UTILIZADO E TENTE NOVAMENTE!");
            return;
        }

        dao.commit();
        novo();
        MensagemFlash.info("", "CARGO EXCLUÍDO COM SUCESSO!");
    }

    public void novo() {
        cargo = new Cargo();
    }

    public String editar(Cargo c) {
        cargo = c;
        return "cargo";
    }

    public String linkCadastroCargo() {
        novo();
        return "cargo";
    }

    public final void loadListaCargo() {
        listaCargo.clear();

        listaCargo = new Dao().list(new Cargo(), "ob.nome");
    }

    public String linkListaDeCargos() {
        loadListaCargo();
        return "lista_cargos";
    }

    public List<Cargo> getListaCargo() {
        return listaCargo;
    }

    public void setListaCargo(List<Cargo> listaCargo) {
        this.listaCargo = listaCargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
