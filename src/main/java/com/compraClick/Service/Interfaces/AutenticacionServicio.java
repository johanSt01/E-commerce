package com.compraClick.Service.Interfaces;

import com.compraClick.DTO.Authentication.LoginDTO;
import com.compraClick.DTO.Authentication.TokenDTO;

public interface AutenticacionServicio {
    TokenDTO login(LoginDTO loginDTO) throws Exception;
}
