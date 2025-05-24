package stackover.resource.service.rest.out;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.dto.responce.UserResponseDto;
import stackover.resource.service.service.dto.UserResponseDtoService;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "ResourceUserController", description = "Контроллер для взаимодействия с пользователем")
public class ResourceUserController {

    private final UserResponseDtoService userResponseDtoService;

    @GetMapping("/{userId}")
    @Operation(summary = "Получение UserDto по userId")
    @ApiResponse(responseCode = "400", description = "Неверный запрос — пользователя с таким id не существует")
    @ApiResponse(responseCode = "200", description = "OK" )
    public ResponseEntity<UserResponseDto> getUserDtoByUserId
            (@PathVariable @Parameter(description = "Идентификатор пользователя") Long userId) {
        UserResponseDto userResponseDto = userResponseDtoService.getUserResponseDtoByUserId(userId);

        if (userResponseDto.id() != null) {
            log.info(userResponseDto.toString());
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

