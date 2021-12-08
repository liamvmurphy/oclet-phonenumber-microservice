package component.com.ocelet.controller;

import com.oclet.controller.GlobalExceptionHandler;
import com.oclet.controller.dto.ApiError;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandlerTest {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();


    ApiError apiError = ApiError.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message("Internal server error")
            .build();
    ResponseEntity<Object> resp = new ResponseEntity<Object>(
            apiError, new HttpHeaders(), apiError.getStatus());

    @Test
    public void handleException_willHandle() {
        Assertions.assertThat(globalExceptionHandler.handleException()).isEqualTo(resp);
    }
}
