package com.hackerzhenya.datagrip;

import com.hackerzhenya.datagrip.requests.SignUpRequest;
import com.hackerzhenya.datagrip.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("testing")
public class ConnectionControllerTests {
    @Autowired
    private MockMvc mvc;

    @BeforeAll
    public static void addTestUser(@Autowired UserService userService) {
        var request = new SignUpRequest();

        request.setUsername("testing");
        request.setPassword("testing");

        userService.register(request);
    }

    @Test
    void getIndex() throws Exception {
        mvc.perform(get("/connections"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void getAdd() throws Exception {
        mvc.perform(get("/connections/add"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void postAdd() throws Exception {
        mvc.perform(post("/connections/add")
                .param("host", "localhost")
                .param("port", "5432")
                .param("username", "postgres")
                .param("password", "postgres")
                .param("database", "postgres")
                .with(csrf()))
           .andDo(print())
           .andExpect(status().is3xxRedirection());
    }
}
