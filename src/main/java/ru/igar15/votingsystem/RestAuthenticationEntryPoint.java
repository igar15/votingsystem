package ru.igar15.votingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.igar15.votingsystem.util.exception.ErrorInfo;
import ru.igar15.votingsystem.web.json.JacksonObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.igar15.votingsystem.util.exception.ErrorType.UNAUTHORIZED_ERROR;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public static final String NOT_AUTHORIZED = "You are not authorized";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ErrorInfo responseEntity = new ErrorInfo(request.getRequestURL(), UNAUTHORIZED_ERROR,
                UNAUTHORIZED_ERROR.getErrorCode(), NOT_AUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ServletOutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = JacksonObjectMapper.getMapper();
        mapper.writeValue(outputStream, responseEntity);
        outputStream.flush();
    }
}