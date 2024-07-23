package com.mosinsa.reaction.ui;

import com.mosinsa.reaction.ReactionTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReactionController.class)
@Import(ReactionTestObjectFactory.class)
@ExtendWith(MockitoExtension.class)
class ReactionControllerTest {

    @Autowired
    MockMvc mockMvc;

//    @Test
//    void addUserReaction() throws Exception {
//        mockMvc.perform(get("/reactions"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void cancelUserReaction() {
//    }
}