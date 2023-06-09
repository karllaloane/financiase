package com.construcao.financiase.users.controller;

import com.construcao.financiase.user.controller.UserController;
import com.construcao.financiase.user.dto.MessageDTO;
import com.construcao.financiase.user.dto.UserDTO;
import com.construcao.financiase.user.service.UserService;
import com.construcao.financiase.users.builder.UserDTOBuilder;
import com.construcao.financiase.utils.JsonConversionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String USERS_API_URL_PATH = "/api/v1/users";

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTItsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        UserDTO userToCreateDTO = userDTOBuilder.buildUserDTO();
        String creationMessage = "User gabriel with ID 9 is successfully created";
        MessageDTO creationMessageDTO = MessageDTO.builder().message(creationMessage).build();

        Mockito.when(userService.create(userToCreateDTO)).thenReturn(creationMessageDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(USERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(userToCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(creationMessage)));
    }

    @Test
    void whenPOSTItsCalledWithoutRequiredFiedlThanBadRequestShouldBeReturned() throws Exception {
        UserDTO userToCreateDTO = userDTOBuilder.buildUserDTO();
        userToCreateDTO.setUsername(null);

        mockMvc.perform(MockMvcRequestBuilders.post(USERS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConversionUtils.asJsonString(userToCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenDELETEIsCalledThenNoContentShoulBeInformed() throws Exception {
        UserDTO userToDeleteDTO = userDTOBuilder.buildUserDTO();
        var userToDeleteId= userToDeleteDTO.getId();

        Mockito.doNothing().when(userService).delete(userToDeleteId);

        mockMvc.perform(MockMvcRequestBuilders.delete(USERS_API_URL_PATH + "/" + userToDeleteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void whenPUTItsCalledThenOkStatusShouldBeReturned() throws Exception {
        UserDTO userToUpdateDTO = userDTOBuilder.buildUserDTO();
        userToUpdateDTO.setUsername("gabrielupdate");
        String updatedMessage = "User gabrielupdate with ID 9 is successfully updated";
        MessageDTO updatedMessageDTO = MessageDTO.builder().message(updatedMessage).build();

        var userToUpdateId = userToUpdateDTO.getId();

        Mockito.when(userService.update(userToUpdateId, userToUpdateDTO)).thenReturn(updatedMessageDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(USERS_API_URL_PATH + "/" + userToUpdateId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConversionUtils.asJsonString(userToUpdateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(updatedMessage)));
    }
}
