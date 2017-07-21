/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.seguranca.dao;

import br.etec.conexao.Conexao;
import br.etec.conexao.Dao;
import br.etec.seguranca.model.Nota;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Claudemir Rtools
 */
public class NotaDao extends Conexao {

    public List<Nota> listaNotaUsuario(Integer id_usuario) {
        try {
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT n.* FROM nota n WHERE n.id_usuario = " + id_usuario + " ORDER BY n.id", Nota.class
            );

            return qry.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }
}
