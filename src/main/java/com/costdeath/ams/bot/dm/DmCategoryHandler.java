package com.costdeath.ams.bot.dm;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Properties;

public class DmCategoryHandler {

    public DmCategoryHandler(MessageReceivedEvent event, Properties committeeCheck) {
        //Get User Committee Role
        String committeeRole = event.getMember().getRoles().get(0).getName();
        if(committeeCheck.containsKey(event.getAuthor().getId())) {
            committeeRole = committeeCheck.getProperty(event.getAuthor().getId());
        }

        //Get DM Channel
        String [] userId = event.getChannel().getName().split("-");
        userId[0] = userId[userId.length - 1];
        PrivateChannel channel = event.getJDA().retrieveUserById(userId[0]).flatMap(User::openPrivateChannel).complete();

        //Send Message
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(event.getMessage().getContentRaw())
                .setColor(event.getMember().getColor())
                .setAuthor(event.getAuthor().getAsTag(), null, event.getAuthor().getAvatarUrl())
                .setFooter(committeeRole)
                .setTimestamp(event.getMessage().getTimeCreated());

        if(!event.getMessage().getAttachments().isEmpty() && event.getMessage().getAttachments().get(0).isImage()) {
            embed.setImage(event.getMessage().getAttachments().get(0).getUrl());
        }
        if(!event.getMessage().getEmbeds().isEmpty()) {
            embed.setImage(event.getMessage().getEmbeds().get(0).getThumbnail().getUrl());
        }

        channel.sendMessage(embed.build()).queue();
    }
}
