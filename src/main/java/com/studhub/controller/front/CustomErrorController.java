package com.studhub.controller.front;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping(value = "errors")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("custom_error");
        String errorMsg = "Неизвестная ошибка.";
        Integer httpErrorCode = getErrorCode(httpRequest);
        if (httpErrorCode == null) {
            httpErrorCode = 404;
        }
        String errorDescription = "";

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Некорретный запрос.";
                break;
            }
            case 401: {
                errorMsg = "Unauthorized.";
                break;
            }
            case 404: {
                errorMsg = "Запрашиваемый ресурс не существует.";
                break;
            }

            default:
                if (HttpStatus.resolve(httpErrorCode).is4xxClientError())
                    errorMsg = "Ошибка клиента.";
                if (HttpStatus.resolve(httpErrorCode).is5xxServerError())
                    errorMsg = "Ошибка сервера.";
                if (httpErrorCode == HttpStatus.INTERNAL_SERVER_ERROR.value())
                    errorMsg = "Внутренняя ошибка сервера.";
                break;
        }

        errorPage.addObject("errorCode", httpErrorCode);
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private Integer getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return "/errors";
    }
}

//class CustomErrorController {
//
//}