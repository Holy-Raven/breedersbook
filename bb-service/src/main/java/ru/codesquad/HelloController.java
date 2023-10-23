package ru.codesquad;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Tag(name = "AllUsers: приветствие", description = "Приветственная страница сайта")
public class HelloController {

    String file;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Загрузка приветственной странички приложения",
            description = "Первая страница, которую видит пользователь заходя на сайт"
    )
    public String home() throws IOException {

        file = "bb-service\\src\\main\\resources\\readme-resources\\home.txt";

        return readFile(file);
    }

    @GetMapping("private")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Загрузка приветственной странички для заводчиков",
            description = "Описание функционала приложения для заводчиков"
    )
    public String helloBreeder() throws IOException {

        file = "bb-service\\src\\main\\resources\\readme-resources\\hello_breeder.txt";

        return readFile(file);
    }

    @GetMapping("admin")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Загрузка приветственной странички для покупателей",
            description = "Описание функционала приложения для покупателей"
    )
    public String helloBuyer() throws IOException {

        file = "bb-service\\src\\main\\resources\\readme-resources\\hello_buyer.txt";

        return readFile(file);
    }

    @GetMapping("info")
    @ResponseStatus(value = HttpStatus.OK)
    public String getInfo(Principal principal){
        return principal.getName();
    }

    private String readFile(String file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        StringBuilder stringBuilder = new StringBuilder();

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();

        } finally {
            reader.close();
        }
    }
}