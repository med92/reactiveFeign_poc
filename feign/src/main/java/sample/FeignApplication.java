package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableReactiveFeignClients
@EnableFeignClients
public class FeignApplication {

    @Autowired
    private GreetingReactive reactiveFeignClient;

    @Autowired
    private GreetingReactiveWOtherName reactiveFeignClientOther;

    @Autowired
    private GreetingReactiveWithUrl reactiveFeignClientWithUrl;


    @Autowired
    private Greeting feignClient;

    @Autowired
    private GreetingWOtherName feignClient2;

    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }

    @GetMapping("/greetingReactive")
    public Mono<String> greetingReactive() {
        return reactiveFeignClient.greeting().map(s -> "reactive feign! : " + s);
    }

    @PostMapping(value ="/uploadExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<String> greetingReactive(@RequestPart("files") FilePart filePart) {

        return reactiveFeignClient.uploadFile(filePart) ;

       /* MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        ByteArrayResource contentsAsResource = null;
        try {
            contentsAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        multiValueMap.add("file", contentsAsResource);
        return reactiveFeignClient.uploadFile(multiValueMap);*/
    }

    @GetMapping("/greetingReactiveWithParam")
    public Mono<String> greetingReactiveWithParam(@RequestParam(value = "id") Long id) {
        return reactiveFeignClient.greetingWithParam(id).map(s -> "reactive feign with param! : " + s);
    }

    @GetMapping("/greetingReactiveWithUrl")
    public Mono<String> greetingReactiveWithUrl() {
        return reactiveFeignClientWithUrl.greeting().map(s -> "reactive feign with url! : " + s);
    }

    @GetMapping("/greetingReactiveOther")
    public Mono<String> greetingReactiveOther() {
        return reactiveFeignClientOther.greeting().map(s -> "reactive feign other! : " + s);
    }

    @GetMapping("/greeting")
    public Mono<String> greeting() {
        return Mono.fromCallable(() -> "feign! : " + feignClient.greeting())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/greetingOther")
    public Mono<String> greetingOther() {
        return Mono.fromCallable(() -> "feign! : " + feignClient2.greeting())
                .subscribeOn(Schedulers.boundedElastic());
    }

}
