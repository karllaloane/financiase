package com.construcao.financiase.user.controller;

import com.construcao.financiase.user.dto.MessageDTO;
import com.construcao.financiase.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Users Management")
public interface UserControllerDocs {

    @Operation(summary = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success user creation"),
            @ApiResponse(responseCode = "400", description = "Missing required field or an erros on validation field")
                    })
    MessageDTO create(UserDTO userToCreateDTO);
}
