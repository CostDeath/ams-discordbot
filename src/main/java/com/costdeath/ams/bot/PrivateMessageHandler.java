package com.costdeath.ams.bot;

import com.costdeath.ams.bot.dm.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Properties;

public class PrivateMessageHandler extends ListenerAdapter {

     private Properties bot;

     public PrivateMessageHandler(Properties bot) {
         this.bot = bot;
     }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        //Check if Message is valid
        if(!event.getAuthor().isBot() && !event.getAuthor().isSystem()) { new DmPrivateHandler(event, bot); }
    }

    @Override
    public void onPrivateMessageUpdate(PrivateMessageUpdateEvent event) {
         new DmPrivateEditHandler(event, bot);
    }
}
