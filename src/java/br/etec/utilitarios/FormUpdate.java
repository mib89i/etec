/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.etec.utilitarios;

import javax.faces.context.FacesContext;

/**
 *
 * @author Claudemir Rtools
 */
public class FormUpdate {
    public static void update(String component){
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(component);
    }
}
