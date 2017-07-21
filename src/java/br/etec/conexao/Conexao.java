/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Claudemir Rtools
 */
public class Conexao {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        try {
            if (entityManager == null) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
                entityManager = emf.createEntityManager();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
