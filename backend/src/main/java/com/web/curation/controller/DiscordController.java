package com.web.curation.controller;

import com.web.curation.service.DiscordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/discord")
@RequiredArgsConstructor
public class DiscordController {
    private final DiscordService discordService;

    @GetMapping("/test")
    public void test() {
        discordService.createChannel();
    }

    @DeleteMapping("/test")
    public void delete() {
        discordService.deleteChannel();
    }
}
