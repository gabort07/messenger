package org.progmatic.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.progmatic.messenger.Controllers.MessageController;
import org.progmatic.messenger.DTO.CreateMessageDTO;
import org.progmatic.messenger.DTO.MessageDTOWiev;
import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.modell.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testProfile_1")

class MessengerApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


//    @Test
//    void createUser() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
//        .with(SecurityMockMvcRequestPostProcessors.csrf())
//
//
//    }

    @Test
    @WithMockUser(username = "user")
    void userShouldReAbleToCreateMessage() throws Exception {
        CreateMessageDTO message = new CreateMessageDTO();
        message.setText("Hello teszt text");
//        message.setReceiverID(1);
//        message.setReceiverID(1);
//        message.setTopicID(1);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/addmessage/create")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(MockMvcResultMatchers.status().isOk());


//        MvcResult mvcResult =
//                mockMvc.perform(
//                MockMvcRequestBuilders.get("/rest/allmessages"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                        .andExpect(MockMvcResultMatchers.content()
//                                .string(StringContains.containsString("Hello teszt text")));
//                .andReturn();
//        MessageDTOWiev messageDTOWiev= objectMapper.readValue(mvcResult.getResponse()
//                .getContentAsString(StandardCharsets.UTF_8), MessageDTOWiev.class);
//        Assertions.assertFalse(messageDTOWiev.getText().isEmpty());

    }
}
