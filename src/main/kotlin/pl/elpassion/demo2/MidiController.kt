package pl.elpassion.demo2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MidiController {
    @GetMapping("/midis")
    fun listMidis(): Array<Any> {
        return arrayOf("")
    }
}