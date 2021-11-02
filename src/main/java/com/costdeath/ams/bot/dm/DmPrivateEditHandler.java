package com.costdeath.ams.bot.dm;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageUpdateEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class DmPrivateEditHandler {
    public DmPrivateEditHandler(PrivateMessageUpdateEvent event, Properties bot) {
        //Refresh dmChannels List
        Properties dmChannels = new Properties();
        try{dmChannels.load(new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "dmchannels.properties"));
        }catch(Exception e) {System.out.println("Please create a dmchannels.properties!");}

        //Send Message
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Message Updated:")
                .setDescription(event.getMessage().getContentRaw())
                .setColor(0x1d65e0)
                .setAuthor(event.getAuthor().getAsTag(), null, event.getAuthor().getAvatarUrl())
                .setFooter("Message id: " + event.getMessageId())
                .setTimestamp(event.getMessage().getTimeEdited());
        if (!event.getMessage().getAttachments().isEmpty() && event.getMessage().getAttachments().get(0).isImage()) { embed.setImage(event.getMessage().getAttachments().get(0).getUrl()); }
        if(!event.getMessage().getEmbeds().isEmpty()) {embed.setImage(event.getMessage().getEmbeds().get(0).getThumbnail().getUrl());}
        event.getJDA().getGuildById(bot.getProperty("serverId")).getTextChannelById(dmChannels.getProperty(event.getAuthor().getId())).sendMessage(embed.build()).queue();
    }
}
