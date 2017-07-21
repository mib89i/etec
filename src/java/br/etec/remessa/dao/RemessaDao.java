/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.remessa.dao;

import br.etec.conexao.Conexao;
import br.etec.conexao.Dao;
import br.etec.seguranca.model.Pessoa;
import br.etec.remessa.model.Remessa;
import br.etec.remessa.model.RemessaDestino;
import br.etec.remessa.model.RemessaDestinoPessoa;
import br.etec.remessa.model.RemessaDetalhe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Claudemir Rtools
 */
public class RemessaDao extends Conexao {

    public Remessa pesquisaUltimaRemessa() {
        String text
                = "SELECT max(r.id) \n "
                + "  FROM remessa r ";
        try {
            Query qry = getEntityManager().createNativeQuery(text);

            List list = qry.getResultList();
            if (list.isEmpty()) {
                return null;
            }

            Integer id = (Integer) list.get(0);
            Remessa r = (Remessa) new Dao().find(new Remessa(), id);
            return r;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public List<RemessaDetalhe> listaRemessaDetalhe(Integer id_remessa) {
        String text
                = "SELECT rd.* \n "
                + "  FROM remessa_detalhe rd \n"
                + " WHERE rd.id_remessa = " + id_remessa;
        try {
            Query qry = getEntityManager().createNativeQuery(text, RemessaDetalhe.class);

            return qry.getResultList();

        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }

    public List<Remessa> listaRemessa(Integer numero, Integer destino_id, Integer responsavel_id) {
        String text
                = "SELECT r.* \n "
                + "  FROM remessa r \n"
                + " INNER JOIN usuario u ON r.id_responsavel = u.id \n"
                + " INNER JOIN pessoa p ON p.id_usuario = u.id \n";

        List<String> list_where = new ArrayList();

        if (numero != null) {
            list_where.add(" r.numero = " + numero);
        }

        if (destino_id != null) {
            list_where.add(" r.id_remessa_destino = " + destino_id);
        }

        if (responsavel_id != null) {
            list_where.add(" p.id = " + responsavel_id);
        }

        String where = "";
        for (int i = 0; i < list_where.size(); i++) {
            if (where.isEmpty()) {
                where = " WHERE " + list_where.get(i) + " \n ";
            } else {
                where += " AND " + list_where.get(i) + " \n ";
            }
        }

        String order
                = " ORDER BY r.data_remessa DESC";
        try {
            Query qry = getEntityManager().createNativeQuery(text + where + order, Remessa.class);

            return qry.getResultList();

        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }

    public List<RemessaDestino> listaRemessaDestino() {
        String text
                = "SELECT rd.* \n "
                + "  FROM remessa_destino rd \n"
                + " ORDER BY rd.destino";
        try {
            Query qry = getEntityManager().createNativeQuery(text, RemessaDestino.class);

            return qry.getResultList();

        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }

    public List<RemessaDestinoPessoa> listaRemessaDestinoPessoa(Integer id_remessa_destino) {
        String text
                = "SELECT rdp.* \n "
                + "  FROM remessa_destino_pessoa rdp \n"
                + " WHERE rdp.id_remessa_destino = " + id_remessa_destino + " \n"
                + " ORDER BY rdp.nome_responsavel";
        try {
            Query qry = getEntityManager().createNativeQuery(text, RemessaDestinoPessoa.class);

            return qry.getResultList();

        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }

    public List<Pessoa> listaResponsaveisPelaRemessa() {
        String text
                = "SELECT p.* \n"
                + "  FROM remessa r\n"
                + " INNER JOIN usuario u ON r.id_responsavel = u.id\n"
                + " INNER JOIN pessoa p ON p.id_usuario = u.id\n"
                + " GROUP BY p.id\n"
                + " ORDER BY p.nome";
        try {
            Query qry = getEntityManager().createNativeQuery(text, Pessoa.class);

            return qry.getResultList();

        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }
}
