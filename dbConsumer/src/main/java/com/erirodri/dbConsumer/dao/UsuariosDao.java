package com.erirodri.dbConsumer.dao;

import com.erirodri.dbConsumer.model.UsuarioTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/*************************************************************************
 *  Es guardado dentro de DAO ya que es Data Access Object
 *   Es un @Repository que extiende las funciones de JpaRepository
 *      para poder hacer las consultas Basicas a BD
 *   Esta interface tiene que ser llamado por el SERVICIO que contendra
 *      toda la logica para cada consulta a BD
 *
 *************************************************************************/

@Repository
public interface UsuariosDao extends JpaRepository<UsuarioTb, Long> {

}
