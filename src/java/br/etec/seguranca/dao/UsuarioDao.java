/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.dao;

import br.etec.conexao.Conexao;
import br.etec.seguranca.model.Pessoa;
import br.etec.seguranca.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Claudemir Rtools
 */
public class UsuarioDao extends Conexao {

    public Usuario pesquisaUsuario(String usuario, String senha) {
        try {
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT u.* FROM usuario u WHERE u.usuario = '" + usuario + "' AND u.senha = '" + senha + "'", Usuario.class
            );

            return (Usuario) qry.getSingleResult();
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Pessoa pesquisaPessoaUsuario(Integer id_usuario) {
        try {
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT p.* \n"
                    + "  FROM usuario u \n"
                    + " INNER JOIN pessoa p ON p.id_usuario = u.id \n"
                    + " WHERE u.id = " + id_usuario, Pessoa.class
            );

            return (Pessoa) qry.getSingleResult();
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Usuario pesquisaUsuarioLoginExiste(Integer id_usuario, String nome_usuario) {
        String text
                = "SELECT u.* \n"
                + "  FROM usuario u \n"
                + (id_usuario != null ? " WHERE u.id <> " + id_usuario + " AND u.usuario = '" + nome_usuario + "'" : " WHERE u.usuario = '" + nome_usuario + "'");
        try {
            Query qry = getEntityManager().createNativeQuery(
                    text, Usuario.class
            );

            return (Usuario) qry.getSingleResult();
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public List<Object[]> listaPermissaoUsuario(Integer id_grupo, Integer id_usuario, String descricao_permissao) {
        String text
                = "SELECT p.* \n"
                + "  FROM permissao_grupo pg \n"
                + " INNER JOIN permissao p ON p.id = pg.id_permissao \n"
                + " WHERE pg.id_grupo = " + id_grupo + " \n"
                + "   AND p.descricao = '" + descricao_permissao + "' \n"
                + "UNION \n"
                + "SELECT p.* \n"
                + "  FROM permissao_usuario pu \n"
                + " INNER JOIN permissao p ON p.id = pu.id_permissao \n"
                + " WHERE pu.id_usuario = " + id_usuario + " \n"
                + "   AND p.descricao = '" + descricao_permissao + "'";
        try {
            Query qry = getEntityManager().createNativeQuery(
                    text
            );

            return qry.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }
}
