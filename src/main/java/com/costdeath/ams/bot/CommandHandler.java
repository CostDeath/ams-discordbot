package com.costdeath.ams.bot;

import com.costdeath.ams.bot.cmd.*;
import com.costdeath.ams.bot.dm.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Properties;

public class CommandHandler extends ListenerAdapter {
    private Properties bot;
    private Properties committeeCheck;

    public CommandHandler(Properties bot, Properties commiteeCheck) {
        this.bot = bot;
        this.committeeCheck = commiteeCheck;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Create Args
        String[] args = event.getMessage().getContentRaw().split(" ");

        //Commands
        if(!event.getAuthor().isBot() && args[0].startsWith(bot.getProperty("prefix")) && event.isFromGuild()) {
            args[0] = args[0].replaceFirst(bot.getProperty("prefix"), "");

            //Staff Commands
            if(event.getMember().getRoles().contains(event.getGuild().getRoleById(bot.getProperty("committeeRoleId")))) {
                switch(args[0]) {
                    case "ping":
                        new Ping(event, bot.getProperty("prefix"), args);
                        return;
                    case "close":
                        if (event.getTextChannel().getParent().getId().equals(bot.getProperty("dmCategoryId")) && !event.getAuthor().isBot())
                            {new DmClose(event, bot);}
                        return;
                }
            }

            //Regular Commands
            switch(args[0]) {
            }
        }

        //DM Category Message Handling
        try {
            if(event.getTextChannel().getParent().getId().equals(bot.getProperty("dmCategoryId")) && !event.getAuthor().isBot()) { new DmCategoryHandler(event, committeeCheck); }
        }catch(Exception e) {}
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        //DM Category Edit Handling
        try {
            if (event.getTextChannel().getParent().getId().equals(bot.getProperty("dmCategoryId")) && !event.getAuthor().isBot()) { new DmCategoryEditHandler(event, committeeCheck); }
        }catch(Exception e){}
    }
}
