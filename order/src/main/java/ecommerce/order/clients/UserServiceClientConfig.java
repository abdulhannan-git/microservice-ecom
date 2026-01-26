package ecommerce.order.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
public class UserServiceClientConfig {

    @Bean
    public UserServiceClient webUserClientInterface(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder
                .baseUrl("http://user-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        (response) -> Mono.empty())
                .build();
        WebClientAdapter adapter = WebClientAdapter
                                    .create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                                    .builderFor(adapter).build();
        return factory.createClient(UserServiceClient.class);
    }
}
