package com.apploidxxx.heliosbackend.rest.util;

import com.apploidxxx.heliosbackend.data.entity.Token;
import com.apploidxxx.heliosbackend.rest.model.UserModel;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * @author Arthur Kupriyanov
 */
@Component
public class UserRestGetter {

    /**
     * Получить информацию о пользователе через внешний API Helios, сделав запрос
     *
     * Запрос делается с помощью {@link Request} методом {@link Request#get(String, Class, String...)}
     *
     * @param token для получения токена доступа
     * @return Информация о пользователе
     * @throws HttpStatusCodeException если произошла ошибка при запросе
     */
    public static UserModel getUser(Token token) throws HttpStatusCodeException {
         return new Request().get("user", UserModel.class, "access_token", token.getAccessToken()).getBody();
    }
}
