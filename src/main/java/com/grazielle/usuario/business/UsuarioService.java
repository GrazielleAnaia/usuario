package com.grazielle.usuario.business;


import com.grazielle.usuario.business.converter.UsuarioConverter;
import com.grazielle.usuario.business.dto.UsuarioDTO;
import com.grazielle.usuario.infrastructure.entity.Usuario;
import com.grazielle.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}