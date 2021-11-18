package com.costdeath.ams.bot.dm;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class DmClose {
    public DmClose(MessageReceivedEvent event, Properties bot) {
        //Get DM Channel
        String[] userId = event.getChannel().getName().split("-");
        userId[0] = userId[userId.length - 1];
        PrivateChannel channel = event.getJDA().retrieveUserById(userId[0]).flatMap(User::openPrivateChannel).complete();

        //Send Message
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("This conversation has been closed.", null)
                .setDescription("Thank you for contacting the AMS committee! \nWe hope to have solved any issues you may have had. \nLooking forward too seeing you at our next event! " + event.getJDA().getEmoteById(bot.getProperty("blobheart")).getAsMention())
                .setThumbnail("https://cdn.discordapp.com/icons/381495502275084289/cc894e3c3c81b2cf4bbde1edc52cfcb6.webp")
                .setColor(0xe3782b);
        channel.sendMessageEmbeds(embed.build()).queue();

        //Refresh dmChannels List
        Properties dmChannels = new Properties();
        try{dmChannels.load(new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "dmchannels.properties"));
        }catch(Exception e) {System.out.println("Please create a dmchannels.properties!");}

        //Remove User from DB
        dmChannels.remove(userId[0]);
        try{dmChannels.store(new FileOutputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "dmchannels.properties"), "Removed channel for " + event.getAuthor().getName());
        }catch(Exception e) {System.out.println("Please create a dmchannels.properties!");}

        //Delete Channel
        event.getTextChannel().delete().queue();
    }
}
