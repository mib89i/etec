/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.controller;

import br.etec.conexao.Dao;
import br.etec.seguranca.dao.PessoaDao;
import br.etec.seguranca.dao.UsuarioDao;
import br.etec.seguranca.model.Cargo;
import br.etec.seguranca.model.Grupo;
import br.etec.seguranca.model.Pessoa;
import br.etec.seguranca.model.PessoaComplemento;
import br.etec.seguranca.model.PessoaDependente;
import br.etec.seguranca.model.PessoaEmpresa;
import br.etec.seguranca.model.PessoaSindicatoFavorecido;
import br.etec.seguranca.model.Usuario;
import br.etec.utilitarios.Datas;
import br.etec.utilitarios.Jasper;
import br.etec.utilitarios.MensagemFlash;
import br.etec.utilitarios.Sessao;
import br.etec.utilitarios.jasper.FichaRegistroEmpregado;
import br.etec.utilitarios.jasper.FichaRegistroEmpregadoVerso;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Claudemir Rtools
 */
@ManagedBean
@SessionScoped
public class PessoaController implements Serializable {

    private Pessoa pessoa = new Pessoa();
    private PessoaComplemento pessoaComplemento = new PessoaComplemento();
    private PessoaEmpresa pessoaEmpresa = new PessoaEmpresa();
    private PessoaDependente pessoaDependente = new PessoaDependente();
    private PessoaSindicatoFavorecido pessoaSindicatoFavorecido = new PessoaSindicatoFavorecido();
    private Integer indexListaGrupo = 0;
    private List<SelectItem> listaGrupo = new ArrayList();
    private Integer indexListaCargo = 0;
    private List<SelectItem> listaCargo = new ArrayList();
    private String confirmeSenha = "";
    private List<Pessoa> listaPessoa = new ArrayList();

    private String porPesquisa = "";
    private String descricaoPesquisa = "";

    private Part arquivo;

    private List<PessoaDependente> listaPessoaDependente = new ArrayList();
    private Integer indexPessoaDependente = -1;

    private List<PessoaSindicatoFavorecido> listaPessoaSindicato = new ArrayList();
    private Integer indexPessoaSindicato = -1;

    private HtmlDataTable dataTableDependente = new HtmlDataTable();
    private HtmlDataTable dataTableSindicato = new HtmlDataTable();

    public PessoaController() {
        loadListaGrupo();
        loadListaCargo();
        loadListaPessoa();
    }

    public void adicionarSindicato() {
        if (pessoaSindicatoFavorecido.getSindicato().isEmpty()) {
            MensagemFlash.warn("", "DIGITE UM NOME PARA O SINDICATO");
            return;
        }

        if (pessoaSindicatoFavorecido.getAno() <= 0) {
            MensagemFlash.warn("", "DIGITE O ANO");
            return;
        }

        if (pessoaSindicatoFavorecido.getValorString().isEmpty()) {
            MensagemFlash.warn("", "DIGITE UM VALOR");
            return;
        }

        Dao dao = new Dao();
        dao.begin();

        if (pessoaSindicatoFavorecido.getId() == -1) {
            if (pessoa.getId() != -1) {
                pessoaSindicatoFavorecido.setPessoa(pessoa);
                if (!dao.save(pessoaSindicatoFavorecido)) {
                    dao.rollback();
                    MensagemFlash.warn("", "ERRO AO SALVAR PESSOA SINDICATO");
                    return;
                }

                dao.commit();
            }

            if (indexPessoaSindicato != -1) {
                listaPessoaSindicato.set(indexPessoaSindicato, pessoaSindicatoFavorecido);
                MensagemFlash.info("", "SINDICATO ATUALIZADO!");
            } else {
                listaPessoaSindicato.add(pessoaSindicatoFavorecido);
                MensagemFlash.info("", "SINDICATO ADICIONADO!");
            }

        } else {
            if (pessoa.getId() != -1) {
                if (!dao.update(pessoaSindicatoFavorecido)) {
                    dao.rollback();
                    MensagemFlash.warn("", "ERRO AO SALVAR PESSOA SINDICATO");
                    return;
                }

                dao.commit();
            }
            MensagemFlash.info("", "SINDICATO ATUALIZADO!");
        }

        pessoaSindicatoFavorecido = new PessoaSindicatoFavorecido();
        indexPessoaSindicato = -1;
    }

    public void selecionaSindicato(PessoaSindicatoFavorecido ps, Integer index) {
        pessoaSindicatoFavorecido = ps;
        indexPessoaSindicato = index;
    }

    public void removerSindicato() {
        if (pessoaSindicatoFavorecido.getId() == -1) {
            listaPessoaSindicato.remove(pessoaSindicatoFavorecido);
        } else {
            Dao dao = new Dao();

            dao.begin();
            if (!dao.remove(dao.find(pessoaSindicatoFavorecido))) {
                dao.rollback();
                pessoaSindicatoFavorecido = new PessoaSindicatoFavorecido();
                indexPessoaSindicato = -1;
                return;
            }
            listaPessoaSindicato.remove(pessoaSindicatoFavorecido);
            dao.commit();
            MensagemFlash.info("", "SINDICATO REMOVIDO!");
        }

        pessoaSindicatoFavorecido = new PessoaSindicatoFavorecido();
        indexPessoaSindicato = -1;
    }

    public boolean salvarListaSindicato(Dao dao) {
        if (!listaPessoaSindicato.isEmpty()) {
            for (PessoaSindicatoFavorecido ps : listaPessoaSindicato) {
                if (ps.getId() == -1) {
                    ps.setPessoa(pessoa);
                    if (!dao.save(ps)) {
                        MensagemFlash.error("", "ERRO AO SALVAR LISTA DE SINDICATOS!");
                        return false;
                    }
                } else if (!dao.update(ps)) {
                    MensagemFlash.error("", "ERRO AO ATUALIZAR LISTA DE SINDICATOS!");
                    return false;
                }
            }
        }
        return true;
    }

    public void adicionarDependente() {
        if (pessoaDependente.getNome().isEmpty()) {
            MensagemFlash.warn("", "DIGITE UM NOME PARA O DEPENDENTE");
            return;
        }

        if (pessoaDependente.getParentesco().isEmpty()) {
            MensagemFlash.warn("", "DIGITE O PARENTESCO DO DEPENDENTE");
            return;
        }

        if (pessoaDependente.getDataNascimentoString().isEmpty()) {
            MensagemFlash.warn("", "DIGITE A DATA DE NASCIMENTO DO DEPENDENTE");
            return;
        }

        Dao dao = new Dao();
        dao.begin();

        if (pessoaDependente.getId() == -1) {
            if (pessoa.getId() != -1) {
                pessoaDependente.setPessoa(pessoa);
                if (!dao.save(pessoaDependente)) {
                    dao.rollback();
                    MensagemFlash.warn("", "ERRO AO SALVAR PESSOA DEPENDENTE");
                    return;
                }

                dao.commit();
            }

            if (indexPessoaDependente != -1) {
                listaPessoaDependente.set(indexPessoaDependente, pessoaDependente);
                MensagemFlash.info("", "DEPENDENTE ATUALIZADO!");
            } else {
                listaPessoaDependente.add(pessoaDependente);
                MensagemFlash.info("", "DEPENDENTE ADICIONADO!");
            }

        } else {
            if (pessoa.getId() != -1) {
                if (!dao.update(pessoaDependente)) {
                    dao.rollback();
                    MensagemFlash.warn("", "ERRO AO SALVAR PESSOA DEPENDENTE");
                    return;
                }

                dao.commit();
            }
            MensagemFlash.info("", "DEPENDENTE ATUALIZADO!");
        }

        pessoaDependente = new PessoaDependente();
        indexPessoaDependente = -1;
    }

    public void selecionaDependente(PessoaDependente pd, Integer index) {
        pessoaDependente = pd;
        indexPessoaDependente = index;
    }

    public void removerDependente() {
        if (pessoaDependente.getId() == -1) {
            listaPessoaDependente.remove(pessoaDependente);
        } else {
            Dao dao = new Dao();

            dao.begin();
            if (!dao.remove(dao.find(pessoaDependente))) {
                dao.rollback();
                pessoaDependente = new PessoaDependente();
                indexPessoaDependente = -1;
                return;
            }
            listaPessoaDependente.remove(pessoaDependente);
            dao.commit();
            MensagemFlash.info("", "DEPENDENTE REMOVIDO!");
        }

        pessoaDependente = new PessoaDependente();
        indexPessoaDependente = -1;
    }

    public boolean salvarListaDependente(Dao dao) {
        if (!listaPessoaDependente.isEmpty()) {
            for (PessoaDependente pd : listaPessoaDependente) {
                if (pd.getId() == -1) {
                    pd.setPessoa(pessoa);
                    if (!dao.save(pd)) {
                        MensagemFlash.error("", "ERRO AO SALVAR LISTA DE DEPENDENTE!");
                        return false;
                    }
                } else if (!dao.update(pd)) {
                    MensagemFlash.error("", "ERRO AO ATUALIZAR LISTA DE DEPENDENTE!");
                    return false;
                }
            }
        }
        return true;
    }

    public void imprimirFichaCadastro() {
        FacesContext faces = FacesContext.getCurrentInstance();

        List lista = new ArrayList();
        JasperReport jasper;
        List<JasperPrint> ljasper = new ArrayList();

        String caminho_foto = ((ServletContext) faces.getExternalContext().getContext()).getRealPath("/resources/imagens/pessoa/" + pessoa.getId() + "/" + pessoa.getFoto());

        if (!new File(caminho_foto).exists()) {
            caminho_foto = ((ServletContext) faces.getExternalContext().getContext()).getRealPath("/resources/images/sem_foto.png");
        }

        try {
            //* JASPER 1 *//
            lista.add(
                    new FichaRegistroEmpregado(
                            "", // empregador
                            "Avenida Geraldo Roquete, n.º 135 – Jardim Paulista", // empregador_endereco
                            caminho_foto, // caminho_foto
                            "000000".substring(0, 6 - String.valueOf(pessoa.getId()).length()) + String.valueOf(pessoa.getId()), // numero_origem
                            pessoa.getNome() + " " + pessoa.getSobreNome(), // nome_empregado
                            "", // numero_matricula
                            pessoa.getNomePai(), // nome_pai
                            pessoa.getNomeMae(), // nome_mae
                            pessoa.getNacionalidadePai(), // nacionalidade_pai
                            pessoa.getNacionalidadeMae(), // nacionalidade_mae
                            pessoa.getDataNascimento(), // data_nascimento
                            Datas.calcularIdade(pessoa.getDataNascimentoString()), // idade
                            pessoa.getNacionalidade(), // nacionalidade
                            pessoa.getEstadoCivil(), // estado_civil
                            pessoa.getNaturalidade(), // local_nascimento
                            pessoa.getNaturalidadeUF(), // uf_local_nascimento
                            pessoa.getRg(), // rg
                            pessoaComplemento.getCtps(), // ctps
                            pessoaComplemento.getCertificadoReservista(), // certificado_reservista
                            pessoaComplemento.getCategoria(), // categoria
                            pessoa.getDocumento(), // cpf
                            pessoaComplemento.getTituloEleitor(), // titulo_eleitor
                            pessoaComplemento.getZona(), // zona
                            pessoaComplemento.getSecao(), // secao
                            pessoa.getEndereco() + " - " + pessoa.getEnderecoBairro() + " - " + pessoa.getEnderecoCidade() + "/" + pessoa.getEnderecoUF(), // endereco
                            pessoaComplemento.getSerie(), // serie
                            pessoaComplemento.getCor(), // cor
                            pessoaComplemento.getAltura(), // altura
                            pessoaComplemento.getPeso(), // peso
                            pessoaComplemento.getCabelos(), // cabelos
                            pessoaComplemento.getOlhos(), // olhos
                            pessoaComplemento.getSinais(), // sinais
                            pessoaEmpresa.getDataAdmissao(), // data_admissao
                            pessoaEmpresa.getDataRegistro(), // data_registro
                            pessoa.getCargo().getNome(), // cargo
                            pessoaEmpresa.getSecao(), // empresa_secao
                            pessoaEmpresa.getSalarioInicial(), // salario_inicial
                            pessoaEmpresa.getComissao(), // comissoes
                            pessoaEmpresa.getTarefa(), // tarefa
                            pessoaEmpresa.getFormaPagamento(), // forma_pagamento
                            pessoaEmpresa.getOptante(), // optante
                            pessoaEmpresa.getDataOpcao(), // data_opcao
                            pessoaEmpresa.getDataRetratacao(), // data_retratacao
                            pessoaEmpresa.getHoraEntrada(), // hora_entrada
                            pessoaEmpresa.getHoraIntervaloAlmoco(), // hora_intervalo_almoco
                            pessoaEmpresa.getHoraSaida(), // hora_saida
                            pessoaEmpresa.getHoraDescansoSemanal(), // hora_descanso_semanal
                            pessoaEmpresa.getBancoDepositario() // banco_depositario
                    )
            );

            jasper = (JasperReport) JRLoader.loadObject(
                    new File(((ServletContext) faces.getExternalContext().getContext()).getRealPath("/resources/jasper/FICHA_REGISTRO_EMPREGADO.jasper"))
            );

            JRBeanCollectionDataSource dtSource = new JRBeanCollectionDataSource(lista);
            ljasper.add(JasperFillManager.fillReport(jasper, null, dtSource));
            // -----------------------------------------------------------------
            // -----------------------------------------------------------------

            //* JASPER 2 *//
            lista.clear();
            lista.add(
                    new FichaRegistroEmpregadoVerso(
                            trataPessoaSindicato(0).getAno(), // sindicato_ano1
                            trataPessoaSindicato(0).getSindicato(), // sindicato_nome1
                            trataPessoaSindicato(0).getValor(), // sindicato_valor1
                            trataPessoaSindicato(1).getAno(), // sindicato_ano2
                            trataPessoaSindicato(1).getSindicato(), // sindicato_nome2
                            trataPessoaSindicato(1).getValor(), // sindicato_valor2
                            trataPessoaSindicato(2).getAno(), // sindicato_ano3
                            trataPessoaSindicato(2).getSindicato(), // sindicato_nome3
                            trataPessoaSindicato(2).getValor(), // sindicato_valor3
                            trataPessoaSindicato(3).getAno(), // sindicato_ano4
                            trataPessoaSindicato(3).getSindicato(), // sindicato_nome4
                            trataPessoaSindicato(3).getValor(), // sindicato_valor4
                            trataPessoaSindicato(4).getAno(), // sindicato_ano5
                            trataPessoaSindicato(4).getSindicato(), // sindicato_nome5
                            trataPessoaSindicato(4).getValor() // sindicato_valor5
                    )
            );
            jasper = (JasperReport) JRLoader.loadObject(
                    new File(((ServletContext) faces.getExternalContext().getContext()).getRealPath("/resources/jasper/FICHA_REGISTRO_EMPREGADO_VERSO.jasper"))
            );

            dtSource = new JRBeanCollectionDataSource(lista);
            ljasper.add(JasperFillManager.fillReport(jasper, null, dtSource));
        } catch (Exception e) {
            e.getMessage();
            return;
        }
        Jasper.printListReports("Ficha Registro de Empregado", ljasper, new LinkedHashMap());
        //Jasper.printReports("FICHA_REGISTRO_EMPREGADO", "Ficha Registro de Empregado", lista, new LinkedHashMap());
    }

    public PessoaSindicatoFavorecido trataPessoaSindicato(Integer index) {
        try {
            return listaPessoaSindicato.get(index);
        } catch (Exception e) {
            return new PessoaSindicatoFavorecido();
        }
    }

    public void upload() {
        UUID uuidX = UUID.randomUUID();
        String nameTemp = uuidX.toString().replace("-", "_");
        try {
            ServletContext servletContext = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
            String path_criar = servletContext.getRealPath("/resources/imagens/pessoa/" + pessoa.getId());
            if (!new File(path_criar).exists()) {
                File file = new File(path_criar);
                if (!file.mkdirs()) {
                    return;
                }
            }

            String path_upload = servletContext.getRealPath("") + "resources/imagens/pessoa/" + pessoa.getId() + "/" + nameTemp + ".png";
            File file = new File(path_upload);

            byte[] bytes = IOUtils.toByteArray(arquivo.getInputStream());

            try {
                FileUtils.writeByteArrayToFile(file, bytes);
            } catch (IOException e) {
                e.getMessage();
            }

            //pessoa.setCaminhoFoto("/resources/imagens/pessoa/" + pessoa.getId() + "/" + nameTemp + ".png");
            pessoa.setFoto(nameTemp + ".png");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void excluirFoto() {
        ServletContext servletContext = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());

        // EXCLUI SE TIVER FOTO TEMPORÁRIA
        File file = new File(servletContext.getRealPath("") + "resources/imagens/pessoa/-1/" + pessoa.getFoto());
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }

        // EXCLUI SE TIVER FOTO SALVA
        File file_save = new File(servletContext.getRealPath("") + "resources/imagens/pessoa/" + pessoa.getId() + "/" + pessoa.getFoto());
        if (file_save.exists()) {
            FileUtils.deleteQuietly(file);
        }
        
        pessoa.setFoto("");
        
        if (pessoa.getId() != -1){
            Dao dao = new Dao();
            dao.begin();
            dao.update(pessoa);
            dao.commit();
        }
    }

    public final void loadListaGrupo() {
        listaGrupo.clear();

        List<Grupo> result = new PessoaDao().listaGrupo();

        for (int i = 0; i < result.size(); i++) {
            listaGrupo.add(
                    new SelectItem(
                            i,
                            result.get(i).getNome(),
                            Integer.toString(result.get(i).getId())
                    )
            );
        }
    }

    public final void loadListaCargo() {
        listaCargo.clear();

        List<Cargo> result = new PessoaDao().listaCargo();

        for (int i = 0; i < result.size(); i++) {
            listaCargo.add(
                    new SelectItem(
                            i,
                            result.get(i).getNome(),
                            Integer.toString(result.get(i).getId())
                    )
            );
        }
    }

    public final void loadListaPessoa() {
        listaPessoa.clear();

        //listaPessoa = new PessoaDao().listaPessoa();
        listaPessoa = new PessoaDao().listaPesquisaPessoa(porPesquisa, descricaoPesquisa);

        descricaoPesquisa = "";
    }

    public Boolean validaSalvarEditarUsuario() {
        // USUÁRIO NÃO É OBRIGATÓRIO
        if (!pessoa.getUsuario().getUsuario().isEmpty() || !pessoa.getUsuario().getSenha().isEmpty() || !confirmeSenha.isEmpty()) {
            if (pessoa.getUsuario().getUsuario().isEmpty()) {
                MensagemFlash.fatal("", "DIGITE UM USUÁRIO PARA ESTA PESSOA!");
                return false;
            }

            if (pessoa.getUsuario().getSenha().isEmpty()) {
                MensagemFlash.fatal("", "DIGITE UMA SENHA DE USUÁRIO PARA ESTA PESSOA!");
                return false;
            }

            if (!pessoa.getUsuario().getSenha().equals(confirmeSenha)) {
                MensagemFlash.fatal("", "SENHAS NÃO CORRESPONDEM!");
                return false;
            }

            UsuarioDao u_dao = new UsuarioDao();

            if (pessoa.getId() == -1) {
                Usuario u = u_dao.pesquisaUsuarioLoginExiste(null, pessoa.getUsuario().getUsuario());
                if (u != null) {
                    MensagemFlash.fatal("", "NOME DE USUÁRIO JÁ EXISTE, ESCOLHA OUTRO!");
                    return false;
                }
            } else {
                Usuario u = u_dao.pesquisaUsuarioLoginExiste(pessoa.getUsuario().getId(), pessoa.getUsuario().getUsuario());
                if (u != null) {
                    MensagemFlash.fatal("", "NOME DE USUÁRIO JÁ EXISTE, ESCOLHA OUTRO!");
                    return false;
                }
            }
        }
        return true;
    }

    public void salvarEditarUsuario() {
        if (!validaSalvarEditarUsuario()) {
            return;
        }

        Dao dao = new Dao();
        dao.begin();

        if (!dao.update(pessoa.getUsuario())) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ATUALIZAR USUÁRIO, TENTE NOVAMENTE!");
            return;
        }

        MensagemFlash.info("", "USUÁRIO ATUALIZADO COM SUCESSO!");
        dao.commit();

        // ALTERA A PESSOA SE A MESMA ESTIVER LOGADA NO SISTEMA
        if (pessoa.getId() == ((UsuarioController) Sessao.get("usuarioController")).getPessoa().getId()) {
            ((UsuarioController) Sessao.get("usuarioController")).setUsuario(pessoa.getUsuario());
        }
    }

    public Boolean validaSalvar() {
        if (pessoa.getNome().isEmpty()) {
            MensagemFlash.fatal("", "DIGITE UM NOME PARA PESSOA!");
            return false;
        }

        validaSalvarEditarUsuario();
        return true;
    }

    public void salvar() {
        if (!validaSalvar()) {
            return;
        }

        Dao dao = new Dao();
        dao.begin();

        pessoa.getUsuario().setGrupo((Grupo) dao.find(new Grupo(), Integer.valueOf(listaGrupo.get(indexListaGrupo).getDescription())));
        pessoa.setCargo((Cargo) dao.find(new Cargo(), Integer.valueOf(listaCargo.get(indexListaCargo).getDescription())));
        if (pessoa.getId() == -1) {
            pessoa.setDataCadastro(Datas.dataHoje());
            pessoa.setHoraCadastro(Datas.hora());

            if (!dao.save(pessoa.getUsuario())) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL SALVAR USUÁRIO, TENTE NOVAMENTE!");
                return;
            }

            if (!dao.save(pessoa)) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL SALVAR PESSOA, TENTE NOVAMENTE!");
                return;
            }

            pessoaComplemento.setPessoa(pessoa);

            if (!dao.save(pessoaComplemento)) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL SALVAR PESSOA COMPLEMENTO, TENTE NOVAMENTE!");
                return;
            }

            pessoaEmpresa.setPessoa(pessoa);

            if (!dao.save(pessoaEmpresa)) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL SALVAR PESSOA EMPRESA, TENTE NOVAMENTE!");
                return;
            }
            MensagemFlash.info("", "PESSOA SALVA COM SUCESSO!");
        } else {
            if (!dao.update(pessoa)) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ATUALIZAR PESSOA, TENTE NOVAMENTE!");
                return;
            }

            if (!dao.update(pessoaComplemento)) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ATUALIZAR PESSOA COMPLEMENTO, TENTE NOVAMENTE!");
                return;
            }

            if (!dao.update(pessoaEmpresa)) {
                dao.rollback();
                MensagemFlash.fatal("", "NÃO FOI POSSÍVEL ATUALIZAR PESSOA EMPRESA, TENTE NOVAMENTE!");
                return;
            }

            MensagemFlash.info("", "PESSOA ATUALIZADA COM SUCESSO!");
        }

        if (!salvarListaDependente(dao)) {
            dao.rollback();
            return;
        }

        if (!salvarListaSindicato(dao)) {
            dao.rollback();
            return;
        }

        ServletContext servletContext = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());

        String file_temp = servletContext.getRealPath("") + "resources/imagens/pessoa/-1/" + pessoa.getFoto();
        File file = new File(file_temp);

        if (file.exists()) {
            File file_destino = new File(servletContext.getRealPath("") + "resources/imagens/pessoa/" + pessoa.getId());
            try {
                FileUtils.moveFileToDirectory(file, file_destino, true);
            } catch (IOException e) {
                e.getMessage();
            }
        }

        dao.commit();

        // ALTERA A PESSOA SE A MESMA ESTIVER LOGADA NO SISTEMA
        if (pessoa.getId() == ((UsuarioController) Sessao.get("usuarioController")).getPessoa().getId()) {
            ((UsuarioController) Sessao.get("usuarioController")).setPessoa(pessoa);
        }
    }

    public String editar(Pessoa p) {
        PessoaDao dao = new PessoaDao();
        pessoa = p;
        confirmeSenha = p.getUsuario().getSenha();
        pessoaComplemento = dao.pesquisaPessoaComplementoPorPessoa(pessoa.getId());
        pessoaEmpresa = dao.pesquisaPessoaEmpresaPorPessoa(pessoa.getId());
        listaPessoaDependente = dao.listaPessoaDependente(pessoa.getId());
        listaPessoaSindicato = dao.listaPessoaSindicato(pessoa.getId());

        for (int i = 0; i < listaGrupo.size(); i++) {
            if (pessoa.getUsuario().getGrupo().getId() == Integer.valueOf(listaGrupo.get(i).getDescription())) {
                indexListaGrupo = i;
            }
        }

        for (int i = 0; i < listaCargo.size(); i++) {
            if (pessoa.getCargo().getId() == Integer.valueOf(listaCargo.get(i).getDescription())) {
                indexListaCargo = i;
            }
        }
        return "pessoa";
    }

    public String linkCadastroPessoa() {
        novo();
        return "pessoa";
    }

    public String linkListaDePessoas() {
        loadListaPessoa();
        return "lista_pessoas";
    }

    public void excluir() {
        if (pessoa.getId() == ((UsuarioController) Sessao.get("usuarioController")).getPessoa().getId()) {
            MensagemFlash.fatal("", "VOCÊ NÃO PODE EXCLUIR SEU PRÓPRIO USUÁRIO!");
            return;
        }

        Dao dao = new Dao();
        dao.begin();

        if (!dao.remove(dao.find(pessoaComplemento))) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL EXCLUIR ESTA PESSOA COMPLEMENTO, TENTE NOVAMENTE!");
            return;
        }

        if (!dao.remove(dao.find(pessoaEmpresa))) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL EXCLUIR ESTA PESSOA EMPRESA, TENTE NOVAMENTE!");
            return;
        }

        if (!listaPessoaDependente.isEmpty()) {
            for (PessoaDependente pd : listaPessoaDependente) {
                if (pd.getId() != -1) {
                    if (!dao.remove(dao.find(pd))) {
                        dao.rollback();
                        MensagemFlash.fatal("", "NÃO FOI POSSÍVEL EXCLUIR ESTE LISTA DE DEPENDENTES, TENTE NOVAMENTE!");
                        return;
                    }
                }
            }
        }

        if (!dao.remove(dao.find(pessoa))) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL EXCLUIR ESTA PESSOA, TENTE NOVAMENTE!");
            return;
        }

        if (!dao.remove(dao.find(pessoa.getUsuario()))) {
            dao.rollback();
            MensagemFlash.fatal("", "NÃO FOI POSSÍVEL EXCLUIR ESTE USUÁRIO, TENTE NOVAMENTE!");
            return;
        }

        dao.commit();
        MensagemFlash.info("", "PESSOA EXCLUÍDA COM SUCESSO!");
        novo();
    }

    public void novo() {
        pessoa = new Pessoa();
        pessoaComplemento = new PessoaComplemento();
        pessoaEmpresa = new PessoaEmpresa();
        pessoaDependente = new PessoaDependente();
        pessoaSindicatoFavorecido = new PessoaSindicatoFavorecido();
        confirmeSenha = "";
        indexListaGrupo = 0;
        indexListaCargo = 0;

        listaPessoaDependente.clear();
        indexPessoaDependente = -1;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<SelectItem> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<SelectItem> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public Integer getIndexListaGrupo() {
        return indexListaGrupo;
    }

    public void setIndexListaGrupo(Integer indexListaGrupo) {
        this.indexListaGrupo = indexListaGrupo;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

    public List<Pessoa> getListaPessoa() {
        return listaPessoa;
    }

    public void setListaPessoa(List<Pessoa> listaPessoa) {
        this.listaPessoa = listaPessoa;
    }

    public String getPorPesquisa() {
        return porPesquisa;
    }

    public void setPorPesquisa(String porPesquisa) {
        this.porPesquisa = porPesquisa;
    }

    public String getDescricaoPesquisa() {
        return descricaoPesquisa;
    }

    public void setDescricaoPesquisa(String descricaoPesquisa) {
        this.descricaoPesquisa = descricaoPesquisa;
    }

    public Part getArquivo() {
        return arquivo;
    }

    public void setArquivo(Part arquivo) {
        this.arquivo = arquivo;
    }

    public PessoaComplemento getPessoaComplemento() {
        return pessoaComplemento;
    }

    public void setPessoaComplemento(PessoaComplemento pessoaComplemento) {
        this.pessoaComplemento = pessoaComplemento;
    }

    public Integer getIndexListaCargo() {
        return indexListaCargo;
    }

    public void setIndexListaCargo(Integer indexListaCargo) {
        this.indexListaCargo = indexListaCargo;
    }

    public List<SelectItem> getListaCargo() {
        return listaCargo;
    }

    public void setListaCargo(List<SelectItem> listaCargo) {
        this.listaCargo = listaCargo;
    }

    public PessoaEmpresa getPessoaEmpresa() {
        return pessoaEmpresa;
    }

    public void setPessoaEmpresa(PessoaEmpresa pessoaEmpresa) {
        this.pessoaEmpresa = pessoaEmpresa;
    }

    public PessoaDependente getPessoaDependente() {
        return pessoaDependente;
    }

    public void setPessoaDependente(PessoaDependente pessoaDependente) {
        this.pessoaDependente = pessoaDependente;
    }

    public PessoaSindicatoFavorecido getPessoaSindicatoFavorecido() {
        return pessoaSindicatoFavorecido;
    }

    public void setPessoaSindicatoFavorecido(PessoaSindicatoFavorecido pessoaSindicatoFavorecido) {
        this.pessoaSindicatoFavorecido = pessoaSindicatoFavorecido;
    }

    public List<PessoaDependente> getListaPessoaDependente() {
        return listaPessoaDependente;
    }

    public void setListaPessoaDependente(List<PessoaDependente> listaPessoaDependente) {
        this.listaPessoaDependente = listaPessoaDependente;
    }

    public Integer getIndexPessoaDependente() {
        return indexPessoaDependente;
    }

    public void setIndexPessoaDependente(Integer indexPessoaDependente) {
        this.indexPessoaDependente = indexPessoaDependente;
    }

    public List<PessoaSindicatoFavorecido> getListaPessoaSindicato() {
        return listaPessoaSindicato;
    }

    public void setListaPessoaSindicato(List<PessoaSindicatoFavorecido> listaPessoaSindicato) {
        this.listaPessoaSindicato = listaPessoaSindicato;
    }

    public Integer getIndexPessoaSindicato() {
        return indexPessoaSindicato;
    }

    public void setIndexPessoaSindicato(Integer indexPessoaSindicato) {
        this.indexPessoaSindicato = indexPessoaSindicato;
    }

    public HtmlDataTable getDataTableDependente() {
        return dataTableDependente;
    }

    public void setDataTableDependente(HtmlDataTable dataTableDependente) {
        this.dataTableDependente = dataTableDependente;
    }

    public HtmlDataTable getDataTableSindicato() {
        return dataTableSindicato;
    }

    public void setDataTableSindicato(HtmlDataTable dataTableSindicato) {
        this.dataTableSindicato = dataTableSindicato;
    }
}
