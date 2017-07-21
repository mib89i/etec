/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.dao;

import br.etec.conexao.Conexao;
import br.etec.conexao.Dao;
import br.etec.seguranca.model.Pagina;
import br.etec.seguranca.model.PermissaoGrupo;
import br.etec.utilitarios.AnaliseString;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Claudemir Rtools
 */
public class GrupoDao extends Conexao {

    public List<Object[]> listaPermissaoGrupo(Integer id_grupo, Integer id_pagina) {
        try {
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT p.id, pa.nome, true \n"
                    + "  FROM permissao p \n"
                    + " INNER JOIN pagina pa ON pa.id = p.id_pagina \n"
                    + " WHERE p.id IN (SELECT pg.id_permissao FROM permissao_grupo pg WHERE pg.id_grupo = " + id_grupo + ") \n"
                    + "   AND p.id_pagina = " + id_pagina + " \n"
                    + "UNION \n"
                    + "SELECT p.id, pa.nome, false \n"
                    + "  FROM permissao p \n"
                    + " INNER JOIN pagina pa ON pa.id = p.id_pagina \n"
                    + " WHERE p.id NOT IN (SELECT pg.id_permissao FROM permissao_grupo pg WHERE pg.id_grupo = " + id_grupo + ") \n"
                    + "   AND p.id_pagina = " + id_pagina + " \n"
                    + "ORDER BY 1, 2"
            );

            return qry.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }

    public PermissaoGrupo pesquisaPermissaoGrupo(Integer id_grupo, Integer id_permissao) {
        try {
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT pg.* \n"
                    + "  FROM permissao_grupo pg \n"
                    + " WHERE pg.id_grupo = " + id_grupo + "\n"
                    + "   AND pg.id_permissao = " + id_permissao + "\n"
                    + " ", PermissaoGrupo.class
            );

            return (PermissaoGrupo) qry.getSingleResult();
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public List<Pagina> listaPagina(String descricao_pesquisa) {
        try {
            String text;

            if (descricao_pesquisa.isEmpty()) {
                text 
                        = "SELECT p.* \n"
                        + "  FROM pagina p \n"
                        + " ORDER BY p.nome";
            } else {
                descricao_pesquisa = "%" + AnaliseString.normalizeLower(descricao_pesquisa) + "%";
                text
                        = "SELECT p.* \n"
                        + "  FROM pagina p \n"
                        + " WHERE LOWER(p.nome) LIKE '" + descricao_pesquisa + "' \n"
                        + "UNION \n"
                        + " SELECT p.* \n"
                        + "  FROM permissao pe \n"
                        + " INNER JOIN pagina p ON p.id = pe.id_pagina \n"
                        + " WHERE LOWER(pe.nome) LIKE '" + descricao_pesquisa + "'"
                        + " ORDER BY 1";
            }

            Query qry = getEntityManager().createNativeQuery(text, Pagina.class);
            return qry.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }

}
