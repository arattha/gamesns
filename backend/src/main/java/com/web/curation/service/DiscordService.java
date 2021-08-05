package com.web.curation.service;

import com.web.curation.config.DiscordJdaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DiscordService {
    private final DiscordJdaConfig jdaConfig;

    public void createChannel() {
        jdaConfig.createVoiceChannel("Test");
    }

    public void deleteChannel() {
        jdaConfig.deleteVoiceChannel();
    }
}
