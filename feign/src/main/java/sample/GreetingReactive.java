package sample;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sample.dto.User;

import java.util.List;

//if you name feign client with eureka app name than you may omit ribbon configuration
@ReactiveFeignClient(name = "web-flux-app")
public interface GreetingReactive {

    @GetMapping("/greeting")
    Mono<String> greeting();

    @GetMapping("/greetingWithParam")
    Mono<String> greetingWithParam(@RequestParam(value = "id") Long id);


    @PostMapping(value="/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Flux<String> uploadFile(@RequestPart FilePart file) ;
}
