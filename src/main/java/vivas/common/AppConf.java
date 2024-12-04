package vivas.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AppConf {
    public static String JWT_SECRET;
    public static int JWT_EXPIRATION_DAY;
    public static String OPEN_API_SERVER_URL;
    //

    @Value("${app.jwt.jwtSecret}")
    public void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
        JWT_SECRET += JWT_SECRET;
        JWT_SECRET += JWT_SECRET;
        JWT_SECRET += JWT_SECRET;
        JWT_SECRET += JWT_SECRET;
        JWT_SECRET += JWT_SECRET;
    }
    @Value("${app.jwt.jwtExpirationDay}")
    public void setJwtExpirationDay(int day) {
        JWT_EXPIRATION_DAY = day;
    }
    @Value("${app.open-api.server-url}")
    public void setOpenApiDevUrl(String v) {
        OPEN_API_SERVER_URL = v;
    }
}
