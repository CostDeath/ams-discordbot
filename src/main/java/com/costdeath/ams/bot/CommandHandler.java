package com.costdeath.ams.bot;

import com.costdeath.ams.bot.cmd.Ping;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    private String prefix;

    public CommandHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] args = msg.getContentRaw().split(" ");

        if(!event.getAuthor().isBot() && args[0].startsWith(this.prefix)) {
            args[0] = args[0].replaceFirst(this.prefix, "");
            System.out.println("User " + event.getAuthor().getAsTag() + " used the command \"" + msg.getContentRaw() + "\".");

            switch(args[0]) {
                case "ping": new Ping(event, this.prefix, args);
            }
        }
    }
}
