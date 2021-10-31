package com.costdeath.ams.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    public String prefix;

    public CommandHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] args = msg.getContentRaw().split(" ");

        if(!event.getAuthor().isBot() && args[0].startsWith(this.prefix)) {
            Commands cmd = new Commands(event, this.prefix, args);
            args[0] = args[0].replaceFirst(this.prefix, "");
            System.out.println("User " + event.getAuthor().getAsTag() + " used the command \"" + args[0] + "\".");
            switch(args[0]) {
                case "ping": cmd.ping();
            }
        }
    }
}
