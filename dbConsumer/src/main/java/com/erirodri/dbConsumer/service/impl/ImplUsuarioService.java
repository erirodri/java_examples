package com.erirodri.dbConsumer.service.impl;

import com.erirodri.dbConsumer.dao.UsuariosDao;
import com.erirodri.dbConsumer.model.UsuarioTb;
import com.erirodri.dbConsumer.service.interf.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*********************************************************************************
 *      Este es un @Service el cual se utiliza para ejecutar toda la logica
 *          de negocio a impementar cuando se hace el llamado desde el
 *          @RestController (interfaz de API)
 *
 *      Se hace extends de Serializable para volver la consulta un Stream String
 *          y asi se almacene en la memoria java
 *      Se hace el implement de su interfaz, ya que por medio de ella es como
 *          se manda a llamar dicho servicio.
 *
 * @param <T>  //Se define T para indicar que espera algun objeto de tipo variable
 * @param <ID>
 ***********************************************************************************/

@Service
public class ImplUsuarioService<T, ID extends Serializable> implements UsuarioService<T,ID> {
    private static Logger log = LoggerFactory.getLogger(ImplUsuarioService.class);

    private final UsuariosDao daoUsuario;

    @Autowired //Se indica este envellecedor para generar una persistencia (injectar el elemento)
    public ImplUsuarioService(UsuariosDao daoUsuario) { // Constructor
        this.daoUsuario = daoUsuario;
    }

    @Override
    public UsuarioTb getById(Long idToFind) {
        log.info(idToFind.toString());
        if(daoUsuario.existsById(idToFind)){
            Optional<UsuarioTb> userResult= daoUsuario.findById(idToFind);
            return userResult.get();
        }
        return null;
    }

    @Override
    public List<UsuarioTb> getAll() {
        List<UsuarioTb> listUserFinds = new ArrayList<UsuarioTb>();
        daoUsuario.findAll().stream().forEach( user -> {
            log.info(user.toString());
            listUserFinds.add(user);
        });
        return listUserFinds;
    }

    @Override
    public int save(UsuarioTb userToSave) {
        try{
            daoUsuario.save(userToSave);
            return 1;
        }catch (DataAccessException ex){
            ex.getStackTrace();
            return 0;
        }

    }

    @Override
    public int delete(long IdToDelete) {
        try{
            daoUsuario.deleteById(IdToDelete);
            return 1;
        }catch (DataAccessException ex){
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(long idToUpdate, UsuarioTb userInfoToUpdate) {
        if(daoUsuario.existsById(idToUpdate)){
            try{
                userInfoToUpdate.setId(idToUpdate);
                daoUsuario.save(userInfoToUpdate);
            }catch (DataAccessException ex){
                ex.getStackTrace();
                return 0;
            }
            return 1;
        }else{
            return 2;
        }
    }
}
