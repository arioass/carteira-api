package br.com.alura.carteira.infra.security;

import br.com.alura.carteira.dto.LoginInDTO;
import br.com.alura.carteira.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public String autenticar(LoginInDTO loginInDTO) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginInDTO.getLogin(),
                loginInDTO.getSenha()
        );

        authentication = authenticationManager.authenticate(authentication);

        return tokenService.gerarToken(authentication);
    }
}