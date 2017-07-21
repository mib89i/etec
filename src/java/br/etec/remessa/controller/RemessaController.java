/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.remessa.controller;

import br.etec.conexao.Dao;
import br.etec.seguranca.controller.UsuarioController;
import br.etec.remessa.dao.RemessaDao;
import br.etec.seguranca.dao.UsuarioDao;
import br.etec.seguranca.model.Pessoa;
import br.etec.remessa.model.Remessa;
import br.etec.remessa.model.RemessaDestino;
import br.etec.remessa.model.RemessaDestinoPessoa;
import br.etec.remessa.model.RemessaDetalhe;
import br.etec.utilitarios.Datas;
import br.etec.utilitarios.Jasper;
import br.etec.utilitarios.MensagemFlash;
import br.etec.utilitarios.Sessao;
import br.etec.utilitarios.jasper.FichaRemessa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

/**
 *
 * @author Claudemir Rtools
 */
@ManagedBean
@SessionScoped
public class RemessaController implements Serializable {

    private Integer indexRemessaDestino = 0;
    private List<SelectItem> listaRemessaDestino = new ArrayList();
    private Remessa remessa = new Remessa();
    private RemessaDetalhe remessaDetalhe = new RemessaDetalhe();

    // TABLE REMESSA DETALHE --
    private List<RemessaDetalhe> listaRemessaDetalhe = new ArrayList();
    private Integer indexRemessaDetalhe = -1;
    private HtmlDataTable dataTableRemessaDetalhe = new HtmlDataTable();

    private List<Remessa> listaRemessa = new ArrayList();

    private RemessaDestino remessaDestino = new RemessaDestino();
    private List<RemessaDestino> listaRemessaDestinoTable = new ArrayList();

    private RemessaDestinoPessoa remessaDestinoPessoa = new RemessaDestinoPessoa();
    private List<RemessaDestinoPessoa> listaRemessaDestinoPessoa = new ArrayList();

    private Boolean novoDestino = false;

    private List<SelectItem> listaPessoaResponsavelSetor = new ArrayList();
    private Integer indexPessoaResponsavelSetor = 0;
    private Boolean remessaSalva = false;
    
    // PESQUISA --
    private String numeroPesquisa = "";
    private List<SelectItem> listaResponsavelPesquisa = new ArrayList();
    private Integer indexListaResponsavelPesquisa = 0;
    
    private List<SelectItem> listaDestinoPesquisa = new ArrayList();
    private Integer indexListaDestinoPesquisa = 0;
    // --
    
    public RemessaController() {
        loadListaRemessaDestino();
        loadListaRemessaDestinoTable();
        loadRemessa();
        
        loadListaPessoaResponsavelSetor();
        
        // PESQUISA --
        loadListaResponsavelPesquisa();
        loadListaDestinoPesquisa();
        // --
        loadListaRemessa();
    }
    
    public final void loadListaResponsavelPesquisa(){
        listaResponsavelPesquisa.clear();
        indexListaResponsavelPesquisa = 0;
        
        List<Pessoa> result = new RemessaDao().listaResponsaveisPelaRemessa();
        
        listaResponsavelPesquisa.add(new SelectItem(0, "TODOS RESPONSÁVEIS", null));
        for (int i = 0; i < result.size(); i++){
            listaResponsavelPesquisa.add(new SelectItem(i + 1, result.get(i).getNome(), Integer.toString(result.get(i).getId())));
        }
    }
    
    public final void loadListaDestinoPesquisa(){
        listaDestinoPesquisa.clear();
        indexListaDestinoPesquisa = 0;
        
        listaDestinoPesquisa.add(new SelectItem(0, "TODOS SETORES", null));
        for (int i = 0; i < listaRemessaDestino.size(); i++){
            listaDestinoPesquisa.add(new SelectItem(i + 1, listaRemessaDestino.get(i).getLabel(), listaRemessaDestino.get(i).getDescription()));
        }
    }

    public final void loadListaPessoaResponsavelSetor() {
        listaPessoaResponsavelSetor.clear();
        indexPessoaResponsavelSetor = 0;

        RemessaDao dao = new RemessaDao();

        List<RemessaDestinoPessoa> result = new ArrayList();
        if (listaRemessaDestino.get(indexRemessaDestino).getDescription() != null){
            result = dao.listaRemessaDestinoPessoa(Integer.valueOf(listaRemessaDestino.get(indexRemessaDestino).getDescription()));
        }
        
        if (result.isEmpty()) {
            listaPessoaResponsavelSetor.add(new SelectItem(0, "NENHUM RESPONSÁVEL ADICIONADO", null));
        } else {
            listaPessoaResponsavelSetor.add(new SelectItem(0, "SEM RESPONSÁVEL", null));
            
            for (Integer i = 0; i < result.size(); i++) {
                listaPessoaResponsavelSetor.add(new SelectItem(i + 1, result.get(i).getNome_responsavel(), Integer.toString(result.get(i).getId())));
            }
            
            indexPessoaResponsavelSetor = 1;
        }

    }

    public void adicionarRemessaDestinoPessoa() {
        remessaDestinoPessoa.setRemessaDestino(remessaDestino);
        remessaDestinoPessoa.setNome_responsavel(remessaDestinoPessoa.getNome_responsavel().toUpperCase());

        Dao dao = new Dao();
        dao.begin();

        if (remessaDestinoPessoa.getId() == -1) {
            if (!dao.save(remessaDestinoPessoa)) {
                MensagemFlash.warn("", "ERRO AO ADICIONAR RESPONSÁVEL");
                dao.rollback();
                return;
            }
            MensagemFlash.info("", "RESPONSÁVEL ADICIONADO");
        } else {
            if (!dao.update(remessaDestinoPessoa)) {
                MensagemFlash.warn("", "ERRO AO ATUALIZAR RESPONSÁVEL");
                dao.rollback();
                return;
            }
            MensagemFlash.info("", "RESPONSÁVEL ATUALIZADO");
        }
        dao.commit();

        remessaDestinoPessoa = new RemessaDestinoPessoa();
        loadListaRemessaDestinoPessoa();
        loadListaPessoaResponsavelSetor();
    }

    public void selecionaRemessaDestinoPessoa(RemessaDestinoPessoa rdp) {
        remessaDestinoPessoa = rdp;
    }

    public void deletaRemessaDestinoPessoa(RemessaDestinoPessoa rdp) {
        Dao dao = new Dao();
        dao.begin();

        if (!dao.remove(rdp)) {
            MensagemFlash.warn("", "ERRO AO EXCLUIR RESPONSÁVEL, VERIFIQUE OS VINCULOS");
            dao.rollback();
            return;
        }

        MensagemFlash.info("", "RESPONSÁVEL EXCLUÍDO");
        dao.commit();

        remessaDestinoPessoa = new RemessaDestinoPessoa();
        loadListaRemessaDestinoPessoa();
        loadListaPessoaResponsavelSetor();
    }

    public final void loadListaRemessaDestinoPessoa() {
        listaRemessaDestinoPessoa.clear();

        listaRemessaDestinoPessoa = new RemessaDao().listaRemessaDestinoPessoa(remessaDestino.getId());
    }

    public void novaRemessaDestino(Boolean novo) {
        remessaDestino = new RemessaDestino();
        remessaDestinoPessoa = new RemessaDestinoPessoa();
        novoDestino = novo;
    }

    public void salvarRemessaDestino() {
        Dao dao = new Dao();

        if (remessaDestino.getDestino().isEmpty()) {
            MensagemFlash.warn("", "DIGITE UM NOME PARA O DESTINO");
            return;
        }

        remessaDestino.setDestino(remessaDestino.getDestino().toUpperCase());

        dao.begin();

        if (remessaDestino.getId() == -1) {
            if (!dao.save(remessaDestino)) {
                dao.rollback();
                MensagemFlash.error("", "ERRO AO SALVAR REMESSA DESTINO");
                return;
            }
        } else if (!dao.update(remessaDestino)) {
            dao.rollback();
            MensagemFlash.error("", "ERRO AO ATUALIZAR REMESSA DESTINO");
            return;
        }

        dao.commit();

        MensagemFlash.info("", "DESTINO REMESSA SALVO");

        loadListaRemessaDestinoPessoa();
        loadListaRemessaDestino();
        loadListaRemessaDestinoTable();
    }

    public void excluirRemessaDestino() {
        if (remessaDestino.getId() != -1) {
            Dao dao = new Dao();

            dao.begin();

            List<RemessaDestinoPessoa> list_r = new RemessaDao().listaRemessaDestinoPessoa(remessaDestino.getId());
            for (RemessaDestinoPessoa rdp : list_r) {
                if (!dao.remove(rdp)) {
                    dao.rollback();
                    MensagemFlash.error("", "ERRO AO EXCLUIR REMESSA DESTINO PESSOA");
                    return;
                }
            }

            if (!dao.remove(remessaDestino)) {
                dao.rollback();
                MensagemFlash.error("", "ERRO AO EXCLUIR REMESSA DESTINO, VERIFIQUE OS VINCULOS");
                return;
            }

            dao.commit();

            MensagemFlash.info("", "DESTINO REMESSA EXCLUÍDO");

            novaRemessaDestino(true);

            loadListaRemessaDestino();
            loadListaRemessaDestinoTable();
        }
    }

    public void selecionaRemessaDestino(RemessaDestino rd) {
        remessaDestino = rd;
        novoDestino = true;

        loadListaRemessaDestinoPessoa();
    }

    public final void loadListaRemessaDestinoTable() {
        listaRemessaDestinoTable.clear();

        listaRemessaDestinoTable = new RemessaDao().listaRemessaDestino();
    }

    public final void loadListaRemessa() {
        listaRemessa.clear();

        Integer numero = null, responsavel_id = null, destino_id = null;
        
        if (!numeroPesquisa.isEmpty()){
            numero = Integer.valueOf(numeroPesquisa);
        }
        
        if (listaDestinoPesquisa.get(indexListaDestinoPesquisa).getDescription() != null){
            destino_id = Integer.valueOf(listaDestinoPesquisa.get(indexListaDestinoPesquisa).getDescription());
        }
        
        if (listaResponsavelPesquisa.get(indexListaResponsavelPesquisa).getDescription() != null){
            responsavel_id = Integer.valueOf(listaResponsavelPesquisa.get(indexListaResponsavelPesquisa).getDescription());
        }
        
        listaRemessa = new RemessaDao().listaRemessa(numero, destino_id, responsavel_id);
    }
    
    public void adicionarRemessaDetalhe() {
        if (remessaDetalhe.getOrdem().isEmpty() && remessaDetalhe.getProcesso().isEmpty()) {
            MensagemFlash.warn("", "DIGITE UM NÚMERO DE ORDEM OU PROCESSO");
            return;
        }

        if (remessaDetalhe.getEnvio().isEmpty()) {
            MensagemFlash.warn("", "DIGITE A DESCRIÇÃO DO ENVIO");
            return;
        }

        Dao dao = new Dao();

        if (remessaDetalhe.getId() == -1) {
            if (remessa.getId() != -1) {
                remessaDetalhe.setRemessa(remessa);
                dao.begin();
                if (!dao.save(remessaDetalhe)) {
                    dao.rollback();
                    MensagemFlash.warn("", "ERRO AO SALVAR REMESSA DETALHE");
                    return;
                }
                dao.commit();
            }

            if (indexRemessaDetalhe != -1) {
                listaRemessaDetalhe.set(indexRemessaDetalhe, remessaDetalhe);
                MensagemFlash.info("", "REMESSA DETALHE ATUALIZADA!");
            } else {
                listaRemessaDetalhe.add(remessaDetalhe);
                MensagemFlash.info("", "REMESSA DETALHE ADICIONADO!");
            }
        } else {
            dao.begin();
            if (!dao.update(remessaDetalhe)) {
                dao.rollback();
                MensagemFlash.warn("", "ERRO AO ATUALIZAR REMESSA DETALHE");
                return;
            }
            dao.commit();

            listaRemessaDetalhe.set(indexRemessaDetalhe, remessaDetalhe);
            MensagemFlash.info("", "REMESSA ATUALIZADA!");
        }

        remessaDetalhe = new RemessaDetalhe();
        indexRemessaDetalhe = -1;
    }

    public void removerRemessaDetalhe() {
        if (remessaDetalhe.getId() == -1) {
            listaRemessaDetalhe.remove(remessaDetalhe);
        } else {
            Dao dao = new Dao();

            dao.begin();
            if (!dao.remove(remessaDetalhe)) {
                dao.rollback();
                remessaDetalhe = new RemessaDetalhe();
                indexRemessaDetalhe = -1;
                return;
            }
            listaRemessaDetalhe.remove(remessaDetalhe);
            dao.commit();
            MensagemFlash.info("", "REMESSA DETALHE REMOVIDO!");
        }

        remessaDetalhe = new RemessaDetalhe();
        indexRemessaDetalhe = -1;
    }

    public void selecionaRemessaDetalhe(RemessaDetalhe rd, Integer index) {
        remessaDetalhe = rd;
        indexRemessaDetalhe = index;
    }

    public void limparRemessaDetalhe() {
        remessaDetalhe = new RemessaDetalhe();
        indexRemessaDetalhe = -1;
    }

    public boolean salvarListaRemessaDetalhe(Dao dao) {
        if (!listaRemessaDetalhe.isEmpty()) {
            for (RemessaDetalhe rd : listaRemessaDetalhe) {
                if (rd.getId() == -1) {
                    rd.setRemessa(remessa);
                    if (!dao.save(rd)) {
                        MensagemFlash.error("", "ERRO AO SALVAR LISTA DE REMESSA!");
                        return false;
                    }
                } else if (!dao.update(rd)) {
                    MensagemFlash.error("", "ERRO AO ATUALIZAR LISTA DE REMESSA!");
                    return false;
                }
            }
        }
        return true;
    }

    public void salvar() {
        Dao dao = new Dao();
        if (listaRemessaDestino.get(indexRemessaDestino).getDescription() == null){
            MensagemFlash.fatal("", "CADASTRE UM DESTINO ANTES DE SALVAR!");
            remessaSalva = false;
            return;
        }
        
        remessa.setRemessaDestino((RemessaDestino) dao.find(new RemessaDestino(), Integer.valueOf(listaRemessaDestino.get(indexRemessaDestino).getDescription())));
        if (listaPessoaResponsavelSetor.get(indexPessoaResponsavelSetor).getDescription() != null) {
            remessa.setRemessaDestinoPessoa((RemessaDestinoPessoa) dao.find(new RemessaDestinoPessoa(), Integer.valueOf(listaPessoaResponsavelSetor.get(indexPessoaResponsavelSetor).getDescription())));
        } else {
            remessa.setRemessaDestinoPessoa(null);
        }

        dao.begin();
        if (remessa.getId() == -1) {
            remessa.setResponsavel(UsuarioController.usuarioSessao());
            if (!dao.save(remessa)) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL SALVAR REMESSA, TENTE NOVAMENTE!");
                remessaSalva = false;
                return;
            }
        } else if (!dao.update(remessa)) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ATUALIZAR REMESSA, TENTE NOVAMENTE!");
            remessaSalva = false;
            return;
        }

        if (!salvarListaRemessaDetalhe(dao)) {
            dao.rollback();
            remessaSalva = false;
            return;
        }

        dao.commit();

        MensagemFlash.info("", "REMESSA SALVA!");
        remessaSalva = true;
    }

    public String editar(Remessa r) {
        remessa = r;
        remessaDetalhe = new RemessaDetalhe();

        listaRemessaDetalhe = new RemessaDao().listaRemessaDetalhe(r.getId());

        for (Integer i = 0; i < listaRemessaDestino.size(); i++) {
            if (r.getRemessaDestino().getId() == Integer.valueOf(listaRemessaDestino.get(i).getDescription())) {
                indexRemessaDestino = i;
            }
        }

        loadListaPessoaResponsavelSetor();

        if (r.getRemessaDestinoPessoa() != null) {
            for (Integer i = 0; i < listaPessoaResponsavelSetor.size(); i++) {
                if (listaPessoaResponsavelSetor.get(i).getDescription() != null) {
                    if (r.getRemessaDestinoPessoa().getId() == Integer.valueOf(listaPessoaResponsavelSetor.get(i).getDescription())) {
                        indexPessoaResponsavelSetor = i;
                    }
                }
            }
        } else {
            indexPessoaResponsavelSetor = 0;
        }
        return "remessa";
    }

    public void excluir() {
        Dao dao = new Dao();

        dao.begin();

        if (remessa.getId() != -1) {
            List<RemessaDetalhe> result = new RemessaDao().listaRemessaDetalhe(remessa.getId());

            for (RemessaDetalhe rd : result) {
                if (!dao.remove(rd)) {
                    dao.rollback();
                    MensagemFlash.warn("", "ERRO AO EXCLUIR LISTA DE DETALHES!");
                    return;
                }
            }

            if (!dao.remove(remessa)) {
                dao.rollback();
                MensagemFlash.warn("", "ERRO AO EXCLUIR REMESSA!");
                return;
            }

            dao.commit();

            loadRemessa();
            loadListaRemessa();
            loadListaPessoaResponsavelSetor();

            MensagemFlash.info("", "REMESSA EXCLUÍDA!");
        }
    }

    public final void loadListaRemessaDestino() {
        indexRemessaDestino = 0;
        listaRemessaDestino.clear();

        List<RemessaDestino> result = new RemessaDao().listaRemessaDestino();
        if (result.isEmpty()){
            listaRemessaDestino.add(
                    new SelectItem(0, "NENHUM DESTINO CADASTRADO", null)
            );
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            listaRemessaDestino.add(
                    new SelectItem(
                            i,
                            result.get(i).getDestino(),
                            "" + result.get(i).getId()
                    )
            );
        }
    }

    public final void loadRemessa() {
        remessa = new Remessa();
        listaRemessaDetalhe = new ArrayList();

        Remessa r = new RemessaDao().pesquisaUltimaRemessa();

        if (r == null) {
            remessa.setNumero(324);
        } else {
            remessa.setNumero(r.getNumero() + 1);
        }
    }

    public String linkNovaRemessa() {
        Sessao.put("remessaController", new RemessaController());
        return "remessa";
    }

    public String linkListaRemessa() {
        loadListaRemessa();
        return "lista_remessas";
    }

    public void imprimirRemessa() {
        imprimir(remessa);
    }

    public void imprimir(Remessa re) {
        RemessaDao dao = new RemessaDao();
        FacesContext faces = FacesContext.getCurrentInstance();
        HashMap params = new LinkedHashMap();

        params.put("logo", ((ServletContext) faces.getExternalContext().getContext()).getRealPath("/resources/images/logo_remessa.jpg"));
        params.put("serrilha", ((ServletContext) faces.getExternalContext().getContext()).getRealPath("/resources/images/serrilha.GIF"));

        List<RemessaDetalhe> result = dao.listaRemessaDetalhe(re.getId());
        List<FichaRemessa> lista = new ArrayList();

        try {
            for (Integer copia = 0; copia < 3; copia++) {
                for (RemessaDetalhe rd : result) {
                    lista.add(
                            new FichaRemessa(
                                    copia,
                                    re.getNumeroString(),
                                    Datas.DataToArrayInt(re.getDataRemessa())[2],
                                    re.getRemessaDestino().getDestino(),
                                    "011 – ENCAMINHAR",
                                    (rd.getOrdem() != null) ? rd.getOrdem().isEmpty() ? "-" : rd.getOrdem() : "-",
                                    (rd.getProcesso() != null) ? rd.getProcesso().isEmpty() ? "-" : rd.getProcesso() : "-",
                                    rd.getEnvio(),
                                    re.getDataRemessa(),
                                    new UsuarioDao().pesquisaPessoaUsuario(re.getResponsavel().getId()).getNome(),
                                    (re.getRemessaDestinoPessoa() != null) ? re.getRemessaDestinoPessoa().getNome_responsavel() : null
                            )
                    );
                }
            }

            Jasper.printReports("REMESSA", "Remessa", lista, params);
            // "0796/2011"
            // "- Processo de Contagem de Tempo do docente Thiago Cruz para concessão de ATS."
        } catch (SecurityException | IllegalArgumentException e) {
            e.getMessage();
        }
    }

    public Remessa getRemessa() {
        return remessa;
    }

    public void setRemessa(Remessa remessa) {
        this.remessa = remessa;
    }

    public RemessaDetalhe getRemessaDetalhe() {
        return remessaDetalhe;
    }

    public void setRemessaDetalhe(RemessaDetalhe remessaDetalhe) {
        this.remessaDetalhe = remessaDetalhe;
    }

    public List<RemessaDetalhe> getListaRemessaDetalhe() {
        return listaRemessaDetalhe;
    }

    public void setListaRemessaDetalhe(List<RemessaDetalhe> listaRemessaDetalhe) {
        this.listaRemessaDetalhe = listaRemessaDetalhe;
    }

    public Integer getIndexRemessaDetalhe() {
        return indexRemessaDetalhe;
    }

    public void setIndexRemessaDetalhe(Integer indexRemessaDetalhe) {
        this.indexRemessaDetalhe = indexRemessaDetalhe;
    }

    public HtmlDataTable getDataTableRemessaDetalhe() {
        return dataTableRemessaDetalhe;
    }

    public void setDataTableRemessaDetalhe(HtmlDataTable dataTableRemessaDetalhe) {
        this.dataTableRemessaDetalhe = dataTableRemessaDetalhe;
    }

    public List<Remessa> getListaRemessa() {
        return listaRemessa;
    }

    public void setListaRemessa(List<Remessa> listaRemessa) {
        this.listaRemessa = listaRemessa;
    }

    public List<SelectItem> getListaRemessaDestino() {
        return listaRemessaDestino;
    }

    public void setListaRemessaDestino(List<SelectItem> listaRemessaDestino) {
        this.listaRemessaDestino = listaRemessaDestino;
    }

    public Integer getIndexRemessaDestino() {
        return indexRemessaDestino;
    }

    public void setIndexRemessaDestino(Integer indexRemessaDestino) {
        this.indexRemessaDestino = indexRemessaDestino;
    }

    public RemessaDestino getRemessaDestino() {
        return remessaDestino;
    }

    public void setRemessaDestino(RemessaDestino remessaDestino) {
        this.remessaDestino = remessaDestino;
    }

    public List<RemessaDestino> getListaRemessaDestinoTable() {
        return listaRemessaDestinoTable;
    }

    public void setListaRemessaDestinoTable(List<RemessaDestino> listaRemessaDestinoTable) {
        this.listaRemessaDestinoTable = listaRemessaDestinoTable;
    }

    public List<RemessaDestinoPessoa> getListaRemessaDestinoPessoa() {
        return listaRemessaDestinoPessoa;
    }

    public void setListaRemessaDestinoPessoa(List<RemessaDestinoPessoa> listaRemessaDestinoPessoa) {
        this.listaRemessaDestinoPessoa = listaRemessaDestinoPessoa;
    }

    public RemessaDestinoPessoa getRemessaDestinoPessoa() {
        return remessaDestinoPessoa;
    }

    public void setRemessaDestinoPessoa(RemessaDestinoPessoa remessaDestinoPessoa) {
        this.remessaDestinoPessoa = remessaDestinoPessoa;
    }

    public Boolean getNovoDestino() {
        return novoDestino;
    }

    public void setNovoDestino(Boolean novoDestino) {
        this.novoDestino = novoDestino;
    }

    public List<SelectItem> getListaPessoaResponsavelSetor() {
        return listaPessoaResponsavelSetor;
    }

    public void setListaPessoaResponsavelSetor(List<SelectItem> listaPessoaResponsavelSetor) {
        this.listaPessoaResponsavelSetor = listaPessoaResponsavelSetor;
    }

    public Integer getIndexPessoaResponsavelSetor() {
        return indexPessoaResponsavelSetor;
    }

    public void setIndexPessoaResponsavelSetor(Integer indexPessoaResponsavelSetor) {
        this.indexPessoaResponsavelSetor = indexPessoaResponsavelSetor;
    }

    public String getNumeroPesquisa() {
        return numeroPesquisa;
    }

    public void setNumeroPesquisa(String numeroPesquisa) {
        this.numeroPesquisa = numeroPesquisa;
    }

    public List<SelectItem> getListaResponsavelPesquisa() {
        return listaResponsavelPesquisa;
    }

    public void setListaResponsavelPesquisa(List<SelectItem> listaResponsavelPesquisa) {
        this.listaResponsavelPesquisa = listaResponsavelPesquisa;
    }

    public Integer getIndexListaResponsavelPesquisa() {
        return indexListaResponsavelPesquisa;
    }

    public void setIndexListaResponsavelPesquisa(Integer indexListaResponsavelPesquisa) {
        this.indexListaResponsavelPesquisa = indexListaResponsavelPesquisa;
    }

    public List<SelectItem> getListaDestinoPesquisa() {
        return listaDestinoPesquisa;
    }

    public void setListaDestinoPesquisa(List<SelectItem> listaDestinoPesquisa) {
        this.listaDestinoPesquisa = listaDestinoPesquisa;
    }

    public Integer getIndexListaDestinoPesquisa() {
        return indexListaDestinoPesquisa;
    }

    public void setIndexListaDestinoPesquisa(Integer indexListaDestinoPesquisa) {
        this.indexListaDestinoPesquisa = indexListaDestinoPesquisa;
    }

    public Boolean getRemessaSalva() {
        return remessaSalva;
    }

    public void setRemessaSalva(Boolean remessaSalva) {
        this.remessaSalva = remessaSalva;
    }
}
