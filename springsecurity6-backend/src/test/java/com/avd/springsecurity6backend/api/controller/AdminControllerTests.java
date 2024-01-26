package com.avd.springsecurity6backend.api.controller;
import com.avd.springsecurity6backend.demo.AdminController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTests {

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/api/v1/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPost() throws Exception {
        mockMvc.perform(post("/api/v1/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPut() throws Exception {
        mockMvc.perform(put("/api/v1/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/admin"))
                .andExpect(status().isOk());
    }
}