package com.salomaotech.autoatendimento.controller;

import com.salomaotech.autoatendimento.model.dto.ProdutoDTO;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
public class GetControllerTest {

    @Autowired
    private GetController getController;

    @Mock
    private HttpSession session;

    @Test
    public void testHomePage() {

        ModelAndView modelAndView = getController.home(session);
        assertEquals("pagina-catalogo", modelAndView.getViewName());
        List<ProdutoDTO> produtos = (List<ProdutoDTO>) modelAndView.getModel().get("produtos");
        assertNotNull(produtos);

    }

}
