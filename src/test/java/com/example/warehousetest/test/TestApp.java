package com.example.warehousetest.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestApp extends WarehousetestApplication {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/test")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void whenFileUploaded_thenVerifyStatus()
            throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "sample.csv",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        mockMvc.perform(multipart("/uploadFile").file(file))
//                .andExpect(status().isOk());
    }
}
