package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Este es el inicio del filter");
        //Obtener el token del header
        var authHeader = request.getHeader("Authorization");//validacion de la firma
        if(authHeader !=null){
            var token = authHeader.replace("Bearer ", "");
            //tenemos que verificar que ese subject esta logeado en mi sistema
            var nombreUsuario = tokenService.getSubjetc(token);
            //si subject no llega nulo es que el token ya esta valido
            if(nombreUsuario!=null){
                //Token valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                //aqui autentico al usuario, y forzamos el inicio de sesion
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                //invoco a una clase de string
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request,response);

    }
}
