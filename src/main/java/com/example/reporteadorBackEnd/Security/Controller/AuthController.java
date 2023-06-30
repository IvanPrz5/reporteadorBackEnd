package com.example.reporteadorBackEnd.Security.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.reporteadorBackEnd.Entity.Nomina.EmpresasUsersEntity;
import com.example.reporteadorBackEnd.Security.Auth.AuthCredentials;
import com.example.reporteadorBackEnd.Security.Config.TokenUtils;
import com.example.reporteadorBackEnd.Security.Config.UserDetailImp;
import com.example.reporteadorBackEnd.Security.Repository.UsuariosRepository;
import com.example.reporteadorBackEnd.Security.Usuarios.UsuariosEntity;
import com.example.reporteadorBackEnd.Security.Utils.ResultObjectResponse;
import com.example.reporteadorBackEnd.Service.Nomina.EmpresasUsersService;


@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
@RestController
@RequestMapping("/authentication")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    EmpresasUsersService empresasUsersService;

    @PostMapping("/login")
    public ResultObjectResponse authenticateUser(@RequestBody AuthCredentials authCredentials) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authCredentials.getEmail(), authCredentials.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.getPrincipal() != null) {
                UserDetailImp userDetails = (UserDetailImp) authentication.getPrincipal();
                String token = TokenUtils.createToken(userDetails.getNombre() + " " + userDetails.getUser().getApPaterno() + " " + 
                userDetails.getUser().getApMaterno(), userDetails.getUsername());
                // String token = TokenUtils.createToken(userDetails.getNombre(), userDetails.getUsername());
                
                Optional<UsuariosEntity> usuariosObject = usuariosRepository.findOneByEmail(authCredentials.getEmail());
                List<String> idEmpresas = empresasUsersService.getEmpresasByIdUsuario(usuariosObject.get().getId());

                HashMap<String, Object> response = new HashMap<>();
                response.put("Authorization", "Bearer " + token);
                response.put("IdEmpresas", idEmpresas);

                return new ResultObjectResponse(1, false, "Success", response);
            } else {
                return new ResultObjectResponse(0, true, "Verifique los datos de acceso e intentelo nuevamente.", null);
            }
        } catch (Exception ex) {
            return new ResultObjectResponse(0, true, "Verifique los datos de acceso e intentelo nuevamente aqui.", null);
        }
    }
}
