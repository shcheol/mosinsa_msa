package com.mosinsa.reaction.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewReactionController.class)
@Import(ReactionPresentationObjectFactory.class)
class ViewReactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void total() throws Exception {

        mockMvc.perform(get("/reactions/total")
                        .header("customer-info", """
                                "{"name":"name","id":"id"}"
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("reactionCnt").value(10))
                .andExpect(jsonPath("hasReacted").value(true))
                .andDo(print());
    }

}