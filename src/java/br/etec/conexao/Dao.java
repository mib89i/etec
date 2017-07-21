package br.etec.conexao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

public class Dao extends Conexao {

    public void begin() {
        getEntityManager().getTransaction().begin();
    }

    public void commit() {
        getEntityManager().getTransaction().commit();
    }

    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public boolean remove(Object object) {
        try {
            getEntityManager().remove(find(object));
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    public boolean save(Object object) {
        try {
            getEntityManager().persist(object);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    public boolean update(Object object) {
        try {
            getEntityManager().merge(object);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    public Object find(Object object, Object id) {
        try {
            return getEntityManager().find(object.getClass(), id);
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public Object find(Object object) {
        try {
            Class classe = object.getClass();
            Method metodo = classe.getMethod("getId", new Class[]{});
            Integer id = (Integer) metodo.invoke(object, (Object[]) null);
            return getEntityManager().find(object.getClass(), id);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.getMessage();
        }
        return null;
    }
    
    public List list(Object className) {
        return list(className, "");
    }
    
    public List list(Object className, String objectOrderBy) {
        String queryString = "SELECT ob FROM " + className.getClass().getSimpleName() + " AS ob";
        
        if (!objectOrderBy.isEmpty()){
            queryString += " ORDER BY " + objectOrderBy;
        }
        try {
            Query qry = getEntityManager().createQuery(queryString);
            return qry.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return new ArrayList();
    }    
    
        
}
