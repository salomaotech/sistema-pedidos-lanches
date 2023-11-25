package com.salomaotech.autoatendimento.services;

import com.salomaotech.autoatendimento.model.dto.LoginDTO;
import jakarta.servlet.http.HttpSession;

public class LoginService {

    public boolean authenticateUser(LoginDTO loginDTO, HttpSession session) {

        boolean isAdmin = loginDTO.getLogin().equals("admin") && loginDTO.getPassword().equals("123");
        session.setAttribute("isAdmin", isAdmin);
        session.setAttribute("mensagemlogin", "Acesso negado!");
        return isAdmin;

    }

    public void logoutUser(HttpSession session) {

        session.invalidate();

    }

}
