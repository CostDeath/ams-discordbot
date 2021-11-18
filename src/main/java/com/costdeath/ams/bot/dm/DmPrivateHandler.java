package com.costdeath.ams.bot.dm;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class DmPrivateHandler {
    public DmPrivateHandler(PrivateMessageReceivedEvent event, Properties bot) {
        //Refresh dmChannels List
        Properties dmChannels = new Properties();
        try{dmChannels.load(new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "dmchannels.properties"));
        }catch(Exception e) {System.out.println("Please create a dmchannels.properties!");}

        //New DM Category User
        if (!dmChannels.containsKey(event.getAuthor().getId())) {
            //Register User in dmChannels && Create New Text Channel
            dmChannels.setProperty(event.getAuthor().getId(), event.getJDA().getGuildById(bot.getProperty("serverId")).createTextChannel(event.getAuthor().getName() + "-" + event.getAuthor().getId(), event.getJDA().getCategoryById(bot.getProperty("dmCategoryId"))).complete().getId());
            try {
                dmChannels.store(new FileOutputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "dmchannels.properties"), "Added a channel for " + event.getAuthor().getName());
            } catch (Exception e) { System.out.println("Please create a dmchannels.properties!"); }

            //Send Initiation Message to User
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Message was sent!", null)
                    .setDescription("Thank you for contacting the AMS committee! We'll get back to you as soon as we can! " + event.getJDA().getEmoteById(bot.getProperty("blobheart")).getAsMention())
                    .setThumbnail("https://cdn.discordapp.com/icons/381495502275084289/cc894e3c3c81b2cf4bbde1edc52cfcb6.webp")
                    .setColor(0xe3782b);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();

            //Send Initiation Message to dmChannels
            embed.setTitle("New conversation with " + event.getAuthor().getName() + "!")
                    .setDescription("Do your best to help 'em out! " + event.getJDA().getEmoteById(bot.getProperty("blobheart")).getAsMention());
            event.getJDA().getGuildById(bot.getProperty("serverId")).getTextChannelById(dmChannels.getProperty(event.getAuthor().getId())).sendMessageEmbeds(embed.build()).queue();
        }

        //Send Message
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(event.getMessage().getContentRaw())
                .setColor(0x1d65e0)
                .setAuthor(event.getAuthor().getAsTag(), null, event.getAuthor().getAvatarUrl())
                .setFooter("Message id: " + event.getMessageId())
                .setTimestamp(event.getMessage().getTimeCreated());

        if (!event.getMessage().getAttachments().isEmpty() && event.getMessage().getAttachments().get(0).isImage()) {
            embed.setImage(event.getMessage().getAttachments().get(0).getUrl());
        }
        if(!event.getMessage().getEmbeds().isEmpty()) {
            embed.setImage(event.getMessage().getEmbeds().get(0).getThumbnail().getUrl());
        }

        event.getJDA().getGuildById(bot.getProperty("serverId")).getTextChannelById(dmChannels.getProperty(event.getAuthor().getId())).sendMessageEmbeds(embed.build()).queue();
    }
}
