package com.hackerzhenya.datagrip;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void getSignIn() throws Exception {
        mvc.perform(get("/sign-in"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void getSignUp() throws Exception {
        mvc.perform(get("/sign-up"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void postSignUp() throws Exception {
        mvc.perform(post("/sign-up")
                .with(csrf()))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("text/html;charset=UTF-8"))
           .andExpect(xpath("/html/body/form/ul/li").nodeCount(3));
    }
}
