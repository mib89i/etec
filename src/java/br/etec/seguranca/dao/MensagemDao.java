/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.dao;

import br.etec.conexao.Conexao;
import br.etec.conexao.Dao;
import br.etec.seguranca.model.Mensagem;
import br.etec.seguranca.model.MensagemResposta;
import br.etec.seguranca.model.Pessoa;
import br.etec.seguranca.model.Usuario;
import br.etec.utilitarios.AnaliseString;
import br.etec.utilitarios.Sessao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Claudemir Rtools
 */
public class MensagemDao extends Conexao {

    public List<Mensagem> listaMensagens(Usuario usuario) {
        Pessoa p = new UsuarioDao().pesquisaPessoaUsuario(usuario.getId());
        String text
                = "SELECT m.* \n "
                + "  FROM mensagem m \n "
                + " WHERE m.id_pessoa_destinatario = " + p.getId() + "\n "
                + "    OR m.id_pessoa_remetente = " + p.getId() + "\n "
                + " ORDER BY m.data DESC, m.hora DESC \n "
                + " LIMIT 50";
        try {
            Query qry = getEntityManager().createNativeQuery(
                    text, Mensagem.class
            );

            return qry.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }

        return new ArrayList();
    }

    public List<Pessoa> listaPessoaParaMensagem(String descricao_pesquisa_pessoa) {
        if (descricao_pesquisa_pessoa.isEmpty()) {
            return new ArrayList();
        }

        descricao_pesquisa_pessoa = "%" + AnaliseString.normalizeLower(descricao_pesquisa_pessoa) + "%";

        String text
                = "SELECT p.* \n "
                + "  FROM pessoa p \n "
                + " INNER JOIN usuario u ON u.id = p.id_usuario \n "
                + " WHERE LOWER(p.nome) LIKE '" + descricao_pesquisa_pessoa + "' \n "
                + "    OR LOWER(u.usuario) LIKE '" + descricao_pesquisa_pessoa + "' \n "
                + "   AND u.id <> " + ((Usuario) Sessao.get("sessao_usuario")).getId() + " \n "
                + " ORDER BY p.nome, u.usuario \n"
                + " LIMIT 10";
        try {
            Query qry = getEntityManager().createNativeQuery(
                    text, Pessoa.class
            );

            return qry.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }

        return new ArrayList();
    }

    public List<MensagemResposta> listaMensagemResposta(Integer id_mensagem) {
        String text
                = "SELECT mr.* \n "
                + "  FROM mensagem_resposta mr \n "
                + " WHERE mr.id_mensagem = " + id_mensagem;
        try {
            Query qry = getEntityManager().createNativeQuery(
                    text, MensagemResposta.class
            );

            return qry.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }

        return new ArrayList();
    }
}
