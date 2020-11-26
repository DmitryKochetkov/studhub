package com.studhub.controller.front;

import com.studhub.dto.PageDto;
import com.studhub.dto.UserDto;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminUserController {
    @Value("${server.address}")
    public String HOST;

    @Value("${server.port}")
    public String PORT;

    @GetMapping(value = "/admin/users", produces = "text/html")
    public ModelAndView users(@RequestParam(defaultValue = "1") Integer page) {
        ModelAndView result = new ModelAndView("admin_users");
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://" + HOST + ":" + PORT + "/api/admin/users?page=" + page;
        try {
            ResponseEntity<PageDto<UserDto>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<PageDto<UserDto>>() {
                    });
            result.addObject("page", response.getBody());
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        }
        catch (HttpClientErrorException.BadRequest e) {
            throw new BadRequestException();
        }
        return result;
    }
}
