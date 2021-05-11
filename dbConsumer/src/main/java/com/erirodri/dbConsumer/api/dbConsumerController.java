package com.erirodri.dbConsumer.api;

import com.erirodri.dbConsumer.model.FotballMatchApi;
import com.erirodri.dbConsumer.model.UsuarioTb;
import com.erirodri.dbConsumer.service.interf.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/***********************************************************
 *   Clase donde se define la interfaz de la API
 *     1. Se crean los metodos de acceso
 *     2. Dentro de cada metodo se define actividad a realizar
 *        de manera interna en la aplicación
 *
 ***********************************************************/
@RestController
@RequestMapping(value= "/api/v1/dbConsumer/")  //Es la ruta base para el acceso a los metodos
public class dbConsumerController {

    private static Logger log = LoggerFactory.getLogger(dbConsumerController.class);
    private final UsuarioService usuarioService;

    @Autowired  //Se indica este envellecedor para generar una persistencia (injectar el elemento)
    public dbConsumerController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/apiFotballMatch/{page}")
    public ResponseEntity<FotballMatchApi> getFotballMatch(@PathVariable("page") String page){
        String apiUrl = "https://jsonmock.hackerrank.com/api/football_matches?page="+page;
        RestTemplate restTempl = new RestTemplate();
        FotballMatchApi apiResult = restTempl.getForObject(apiUrl,FotballMatchApi.class);
        log.info(apiResult.getData().toString());

        return new ResponseEntity<FotballMatchApi>(apiResult, HttpStatus.OK);
    }
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioTb> getByIdUser(@NotNull @PathVariable("id") Long idToFind){
        log.info("Usuario a buscar: "+idToFind.toString());
        HttpHeaders headerHttp = new HttpHeaders();

        UsuarioTb userFind = usuarioService.getById(idToFind);
        if (userFind != null) {
            return new ResponseEntity<UsuarioTb>(userFind, HttpStatus.OK);
        }
        headerHttp.add("Response","ELEMENT NOT FOUND");
        return new ResponseEntity<UsuarioTb>(userFind, headerHttp, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/usuarios/findAll")
    public List<UsuarioTb> findAllUsers(){
        log.info("Buscando Todos los registros");
        List<UsuarioTb> listResult = usuarioService.getAll();

        return listResult;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioTb> insertUser(@Valid @RequestBody UsuarioTb userToSave){
        log.info("Llamado a insert");
        HttpHeaders headerHttp = new HttpHeaders();
        int result = usuarioService.save(userToSave);
        if(result==1){
            headerHttp.add("Response","USER ["+userToSave.getUser()+"] ADDED");
            return new ResponseEntity<UsuarioTb>(userToSave, headerHttp, HttpStatus.OK);
        }else{
            headerHttp.add("Response", "ERROR TO SAVE USER");
            return new ResponseEntity<UsuarioTb>(userToSave, headerHttp, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long idUserToDelete){
        log.info("Llamado a Delete Usuario");
        HttpHeaders headerHttp = new HttpHeaders();
        int result = usuarioService.delete(idUserToDelete);
        if(result==1){
            headerHttp.add("Response", "USER DELETED");
            return new ResponseEntity<Long>(idUserToDelete, headerHttp, HttpStatus.OK);
        }else{
            headerHttp.add("Response", "ERROR TO DELETE USER");
            return new ResponseEntity<Long>(idUserToDelete, headerHttp, HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioTb> updateUser(@PathVariable("id") Long idToUpdate, @Valid @RequestBody UsuarioTb usuarioToUpdate){
        log.info("Llamando a Update Usuario");
        HttpHeaders httpResponse = new HttpHeaders();
        int result = usuarioService.update(idToUpdate,usuarioToUpdate);
        if(result==1){
            httpResponse.set("Response", "User "+usuarioToUpdate.getUser()+" UPDATED");
            return new ResponseEntity<UsuarioTb>(usuarioToUpdate,httpResponse,HttpStatus.OK);
        }else if(result==2){
            httpResponse.set("Response", "User "+usuarioToUpdate.getUser()+" NOT FOUND");
            return new ResponseEntity<UsuarioTb>(usuarioToUpdate,httpResponse,HttpStatus.NOT_MODIFIED);
        }else{
            httpResponse.set("Response", "User "+usuarioToUpdate.getUser()+" ERROR TO UPDATE");
            return new ResponseEntity<UsuarioTb>(usuarioToUpdate,httpResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
