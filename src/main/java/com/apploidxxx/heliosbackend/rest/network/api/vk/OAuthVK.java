package com.apploidxxx.heliosbackend.rest.network.api.vk;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @author Arthur Kupriyanov
 */
@Getter
@Component
public class OAuthVK {

    private static String VK_AUTHORIZE_URI = "https://oauth.vk.com/authorize";
    private static String VK_ACCESS_URI = "https://oauth.vk.com/access_token";
    private static String clientId = "7051053"; // System.getProperties().getProperty("VK_CLIENT_ID");
    private static String clientSecret = "mrdMYL4yRv3rLQRvPczX"; //System.getProperties().getProperty("VK_CLIENT_SECRET");
    private static String redirectUri = "http://localhost:8080/api/vk/receiver";
    //    private static String redirectUri = "https://itmo-helios.herokuapp.com/api/vk/receiver";
    private static String groupIds = "185406943";
    /*
        page — форма авторизации в отдельном окне;
        popup — всплывающее окно;
     */
    private static String display = "page";
    /*
        notify - 1
        Добавление ссылки на приложение в меню слева - 256
        offline - 65536
        email - 4194304
     */
    private static String scope = String.valueOf(1 + 256 + 65536 + 4194304);

    private static String responseType = "code";
    private static String version = "5.101";

    public static String getCodeTokenPath(String state) {
        return String.format("%s?client_id=%s" +
                        "&redirect_uri=%s" +
                        "&display=%s" +
                        "&scope=%s" +
                        "&response_type=%s" +
                        "&v=%s" +
                        "&state=%s",
                VK_AUTHORIZE_URI, clientId, redirectUri, display, scope, responseType, version, state);
    }

    public static String getAccessTokenPath(String code) {
        return String.format("%s?client_id=%s" +
                        "&client_secret=%s" +
                        "&redirect_uri=%s" +
                        "&code=%s",
                VK_ACCESS_URI, clientId, clientSecret, redirectUri, code);
    }

}
