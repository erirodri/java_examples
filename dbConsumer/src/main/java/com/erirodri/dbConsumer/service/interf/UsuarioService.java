package com.erirodri.dbConsumer.service.interf;

import com.erirodri.dbConsumer.model.UsuarioTb;

import java.io.Serializable;
import java.util.List;

/*********************************************************************
 *      Se define la interfaz del @Servicio a utilizar para definir
 *          toda la logica de negocio que en este caso sera para
 *          el flujo de Usuarios.
 *
 *      En la interfaz solo se definen los metodos a implementar
 *          ya que esta es la ventana para mandar a llamar la
 *          implementación desde algun otra clase
 * @param <T>
 * @param <ID>
 *********************************************************************/

public interface UsuarioService<T, ID extends Serializable> {

    UsuarioTb getById(Long IdToFind);

    List<UsuarioTb> getAll();

    int save(UsuarioTb userToSave);

    int delete(long IdToDelete);

    int update(long IdToUpdate, UsuarioTb userInfoToUpdate);
}
