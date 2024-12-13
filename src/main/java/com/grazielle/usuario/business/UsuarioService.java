package com.grazielle.usuario.business;


import com.grazielle.usuario.business.converter.UsuarioConverter;
import com.grazielle.usuario.business.dto.UsuarioDTO;
import com.grazielle.usuario.infrastructure.entity.Usuario;
import com.grazielle.usuario.infrastructure.exceptions.ConflictException;
import com.grazielle.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.grazielle.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {

        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email ja cadastrado." + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email ja cadastrado.", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) { //chama o metodo da repository
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found." + email));

    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}
