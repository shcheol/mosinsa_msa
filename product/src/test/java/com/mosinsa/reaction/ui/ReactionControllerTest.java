package com.mosinsa.reaction.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReactionController.class)
@Import(ReactionPresentationObjectFactory.class)
class ReactionControllerTest {

    @Autowired
    MockMvc mockMvc;

//    @Test
//    void addUserReaction() throws Exception {
//        mockMvc.perform(post("/reactions"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void cancelUserReaction() {
//    }
}