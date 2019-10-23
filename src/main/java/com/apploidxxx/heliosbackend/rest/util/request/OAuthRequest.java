package com.apploidxxx.heliosbackend.rest.util.request;


import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Arthur Kupriyanov
 */
@Component
public class OAuthRequest {

    private final UserRepository userRepository;

    private final static Logger logger = LoggerFactory.getLogger(OAuthRequest.class);

    public OAuthRequest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static <T> RequestModel<T> request(String path, Class<T> model, Object object, HttpMethod method, String ... args){
        switch (method){
            case GET: return new RequestModel<>(path, model, object, args).setOperation((p, m, obj, argz) -> new Request().get(p, m, argz));
            case PUT: return new RequestModel<>(path, model, object, args).setOperation((p, m, obj, argz) -> {
                Request.put(p, obj, argz);
                return null;
            });
            case POST: return new RequestModel<>(path, model, object, args).setOperation((p, m, obj, argz) -> new Request().post(p, m, argz));
            case DELETE: return new RequestModel<>(path, model, object, args).setOperation((p, m, obj, argz) -> {
                Request.delete(p, argz);
                return null;
            });
            default:
                logger.error("The Http Method is not allowed now : " + method.name());
                return null;
        }
    }

    public static <T> ResponseEntity requsetWithAccessTokenAutoUpdate(User user, String path, Class<T> model, Object object, HttpMethod method, String ... args) throws Throwable {
                RequestModel requestModel = request(path, model, object, method, args);
                if (requestModel != null)
                    return requestModel.onFail((pathx, modelx, objectx, e, argsx) ->   {
                        if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                            updateUserToken(user);
                            RequestModel req = request(path, model, object, method, args);
                            if (req != null ) return req.execute();
                            else return null;
                        } else {
                            throw e.getCause();
                        }
                    }).execute();

                else return null;
    }

    private static void updateUserToken(User user){
    }

}
