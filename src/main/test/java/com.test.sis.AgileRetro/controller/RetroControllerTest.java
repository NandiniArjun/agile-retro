package com.test.sis.AgileRetro.controller;

import com.test.sis.AgileRetro.model.Retro;
import com.test.sis.AgileRetro.repository.RetroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RetroControllerTest {

    @InjectMocks
    RetroController retroController;

    @Mock
    RetroRepository retroRepository;

    @Test
    public void testAddRetros()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Retro retro = new Retro("1", "Sprint 1", new Date(), null, null);

        when(retroRepository.save(any(Retro.class))).thenReturn(retro);

        ResponseEntity<Retro> responseEntity = retroController.createRetro(retro);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertNotNull(responseEntity);
    }
}
