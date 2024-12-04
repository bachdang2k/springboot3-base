package vivas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vivas.common.AppConf;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server appServer = new Server();
        appServer.setUrl(AppConf.OPEN_API_SERVER_URL);
        appServer.setDescription("APPLICATION SERVER");

        //Contact contact = new Contact();
        //contact.setEmail("haiph@vivas.vn");
        //contact.setName("Pham Hong Hai");
        //contact.setUrl("https://vivas.vn");

        //License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("VIVAS APPLICATION API")
                .version("1.0")
                //.contact(contact)
                .description("VIVAS APPLICATION API.")
                //.termsOfService("https://www.pasanabeysekara.com")
                //.license(mitLicense)
                ;

        return new OpenAPI().info(info).servers(List.of(appServer));
    }
}
