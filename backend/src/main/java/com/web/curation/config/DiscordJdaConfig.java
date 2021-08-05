package com.web.curation.config;

import io.swagger.models.auth.In;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateUserLimitEvent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;
import java.util.List;

@Configuration
public class DiscordJdaConfig {
    private @Value("${jda.discord.guild.id}")
    Long guildId;

    private @Value("${jda.discord.guild.category.id}")
    Long categoryId;

    private JDA jda;

    public DiscordJdaConfig(@Value("${jda.discord.token}") String discordToken) {
        try {
            jda = JDABuilder.createDefault(discordToken)
                    .setStatus(OnlineStatus.DO_NOT_DISTURB).build();

            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createVoiceChannel(String channelName) {
        Guild guild = jda.getGuildById(guildId);

        Category category = guild.getCategoryById(categoryId);

        category.createVoiceChannel(channelName)
//                .addPermissionOverride(guild.getPublicRole(), EnumSet.of(Permission.VOICE_CONNECT), EnumSet.of(Permission.VIEW_CHANNEL))
                .addPermissionOverride(guild.getPublicRole(), EnumSet.of(Permission.VOICE_CONNECT), null)
                .reason("매칭 완료 방 생성")
                .queue((r) -> {
                    r.createInvite().setMaxAge(300).queue((rr) -> {
                        System.out.println(rr.getUrl());
                    });

                    r.getManager().setUserLimit(5).queue();
                });
    }

    public void deleteVoiceChannel() {
        Guild guild = jda.getGuildById(guildId);
        System.out.println("현재 길드(서버) : " + guild.toString());

        List<VoiceChannel> channelList = guild.getVoiceChannels();

        System.out.println("채널 목록   ------> ");
        for (VoiceChannel guildChannel : channelList) {
            System.out.print(guildChannel);

            List<Member> memberList = guildChannel.getMembers();
            if (memberList.size() == 0 || memberList.isEmpty()) {
                System.out.print(" ---> 삭제완료");
                guildChannel.delete().reason("사용자가 없으므로 제거").queue();
            }

            System.out.println();
        }
        System.out.println("채널 목록 끝 ------ ");
    }
}
