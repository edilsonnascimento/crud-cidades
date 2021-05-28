package br.edu.utfpr.cp.espjava.crudcidades.config;

import br.edu.utfpr.cp.espjava.crudcidades.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsusaioDetailsSerice implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        var usuario = usuarioRepository.findByNome(userName);

        if(usuario == null)
            throw new UsernameNotFoundException("Usuário não encontrato!");

        return usuario;
    }
}
