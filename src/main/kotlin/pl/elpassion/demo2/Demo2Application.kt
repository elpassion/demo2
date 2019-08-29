package pl.elpassion.demo2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class Demo2Application {

    @Bean
    fun restClient(): RestTemplate = RestTemplate()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Demo2Application>(*args)
        }
    }
}


