package com.erirodri.dbConsumer.jwt;

/* Esta clase se crea para definir el objeto Datos Usuarios
       ya que siempre seran igual al solicitar la
       autentificación.
*/
public class UsernameAndPasswordAuthenticationRequest {

    private String username;
    private String password;

    public UsernameAndPasswordAuthenticationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
