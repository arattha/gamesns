//package com.web.curation.config;
//
//import net.dv8tion.jda.api.JDA;
//import net.dv8tion.jda.api.JDABuilder;
//import net.dv8tion.jda.api.OnlineStatus;
//import net.dv8tion.jda.api.Permission;
//import net.dv8tion.jda.api.entities.*;
//import net.dv8tion.jda.api.requests.restaction.ChannelAction;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.EnumSet;
//import java.util.List;
//
//@Configuration
//public class JdaConfig {
//    private @Value("${jda.discord.guild.id}")
//    Long guildId;
//
//    private @Value("${jda.discord.guild.category.id}")
//    Long categoryId;
//
//    private JDA jda;
//
//    public JdaConfig(@Value("${jda.discord.token}") String discordToken) {
//        System.out.println(discordToken);
//
//        try {
//            jda = JDABuilder.createDefault(discordToken)
//                    .setStatus(OnlineStatus.DO_NOT_DISTURB).build();
//
//            jda.awaitReady();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void createVoiceChannel(String channelName) {
//        Guild guild = jda.getGuildById(guildId);
//
//        Category category = guild.getCategoryById(categoryId);
//
//        ChannelAction<VoiceChannel> voiceChannelChannelAction = category
//                .createVoiceChannel(channelName);
//        voiceChannelChannelAction
//                .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
//                .reason("매칭 완료 방 생성")
//                .queue();
//    }
//
//    public void deleteVoiceChannel() {
//        Guild guild = jda.getGuildById(guildId);
//        System.out.println("현재 길드(서버) : " + guild.toString());
//
//        List<VoiceChannel> channelList = guild.getVoiceChannels();
//
//        System.out.println("채널 목록   ------> ");
//        for (VoiceChannel guildChannel : channelList) {
//            System.out.print(guildChannel);
//
//            List<Member> memberList = guildChannel.getMembers();
//            if (memberList.size() == 0 || memberList.isEmpty()) {
//                System.out.print(" ---> 삭제완료");
//                guildChannel.delete().reason("사용자가 없으므로 제거").queue();
//            }
//
//            System.out.println();
//        }
//        System.out.println("채널 목록 끝 ------ ");
//    }
//}
