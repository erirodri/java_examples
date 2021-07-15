package com.erirodri.dbConsumer.security;

        import com.erirodri.dbConsumer.jwt.JwtTokenVerifier;
        import com.erirodri.dbConsumer.jwt.JwtUsernameAndPasswordAuthenticationFilter;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.http.HttpMethod;
        import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.security.provisioning.InMemoryUserDetailsManager;

        import static com.erirodri.dbConsumer.security.ApplicationUserRole.*;

@Configuration //Para indicar a Spring que sera un componente de configuración
@EnableWebSecurity //Habilitar la seguridad de acuerdo a nuestra configuración indicada en esta clase
@EnableGlobalMethodSecurity(prePostEnabled = true) //Se utiliza para que en RestController se pueda hacer uso de @PreAuthorize
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //=========== CONFIGURACIÓN PARA UA AUTENTIFICACIÓN BASICA =================
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                /*
//                 // EJEMPLO BASICO PARA PEDIR AUTH A CUALQUIER URI
//                .authorizeRequests()//Indica que es necesario autenticas
//                .anyRequest() //Cualquier tipo de request se va a autenticar
//                .authenticated() //Se autentifica por User and pass
//                .and()
//                .httpBasic(); //Sera una autentificación basica por http
//                 */
//
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/","index","/css/*","/js/*").permitAll() //Estas rutas pueden ser accedidas sin auth
//                .antMatchers("/api/v1/dbConsumer/apiFotballMatch/*").hasRole(APIEXTERNAL.name()) //Esta ruta solo sera accedida por aquellos que tengan el rol correspondiente
//                /*Hay que declarar antMatcher por metodo a indicar
//                * El orden en como asignamos el antMacher si importa ya que si antes indicamos uno general (sin metodo) entonces tendria todos los permisos a pesar que despues indiquemos el metodo
//                * Se puede cambiar todo lo que se definio abajo, indicando en RestController @PreAuthorize() y agregando la etiqueta al inicio de esta clase
//                */
//                /*
//                .antMatchers(HttpMethod.POST,"/api/v1/dbConsumer/usuarios").hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.DELETE,"/api/v1/dbConsumer/usuarios/*").hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.PUT,"/api/v1/dbConsumer/usuarios/*").hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.GET,"/api/v1/dbConsumer/usuarios/*").hasAnyRole(ADMIN.name(),STUDENT.name())
//                 */
//                .anyRequest()
//                .authenticated()
//                .and()
//                // .httpBasic(); // Basic Authentication -> PopUpLogin
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .defaultSuccessUrl("/accessSuccessful",true);
//    }

    //=========== CONFIGURACIÓN PARA USAR JWT =================

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager())) // Con esta linea agregamos el filtro para validar la autentificación
                .addFilterAfter(new JwtTokenVerifier(),JwtUsernameAndPasswordAuthenticationFilter.class) // Asignamos el nuevo filtro creado: Validar el Token enviado en la petición
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll() //Estas rutas pueden ser accedidas sin auth
                .antMatchers("/api/v1/dbConsumer/apiFotballMatch/*").hasRole(APIEXTERNAL.name()) //Esta ruta solo sera accedida por aquellos que tengan el rol correspondiente
                .anyRequest()
                .authenticated();


    }

    // Crear Usuarios en memoria cada vez que se inicia la app
    @Override
    @Bean //Instanciar el objeto @Bean
    protected UserDetailsService userDetailsService() {
        // 1. Se crea el usuario:
        UserDetails erirodriUser = User.builder()
                .username("erirodri")
                .password(passwordEncoder.encode("admin")) // Para que sea aceptado por Spring es necesario hacer un ENCODE del password
                // Esta es una asignación basica de un rol -> .roles("STUDENT") // -> Spring lo transforma a "ROLE_STUDENT"
                .roles(STUDENT.name()) //Se declara una configuración de roles con permisos para ser asignados al momento de crear el usuario
                .build();
        UserDetails apiExternalConsumer = User.builder()
                .username("apiExternal")
                .password(passwordEncoder.encode("admin"))
                .roles(APIEXTERNAL.name())
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                // Esta es una asignación basica de un rol -> .roles("ADMIN") // -> ROLE_ADMIN
                .roles(ADMIN.name())
                .build();
        // 2. Se manda a guardar en memoria
        return new InMemoryUserDetailsManager(
                erirodriUser,
                adminUser,
                apiExternalConsumer
        );
    }
}

