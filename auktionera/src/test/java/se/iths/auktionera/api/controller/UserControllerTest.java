package se.iths.auktionera.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.auktionera.business.model.User;
import se.iths.auktionera.business.service.IUserService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", roles = "USER")
class UserControllerTest {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private IUserService userService;

    @Test
    void getUsers(@Autowired MockMvc mvc) throws Exception {
        User user1 = User.builder().id(1).userName("Corey").build();
        User user2 = User.builder().id(2).userName("Mick").build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userService.getUsers(any(), any())).thenReturn(users);

        mvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'userName':'Corey'},{'id':2,'userName':'Mick'}]"));
    }

    @Test
    void getOneUser(@Autowired MockMvc mvc) throws Exception {
        User user = User.builder().id(1).userName("Corey").createdAt(Instant.now()).build();

        when(userService.getUserById(anyLong())).thenReturn(user);

        mvc.perform(get("/api/users/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1, 'userName':'Corey'}"));
    }
}