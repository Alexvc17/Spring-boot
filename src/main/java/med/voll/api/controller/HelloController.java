package med.voll.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//para decirle que es un controler lo anotamos como controler
@RestController
@RequestMapping("/hello")
public class HelloController {
    //vamos a mapear el metodo get en la ruta /hello
    @GetMapping
    public String helloWorld(){
        return "Hello World form Netherlands";
    }
}
