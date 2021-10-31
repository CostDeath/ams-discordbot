package com.costdeath.ams.bot.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping {

    public Ping(MessageReceivedEvent event, String prefix, String[] args) {
        if(args.length == 1) {
            event.getChannel().sendMessage("Pong!").queue();
            System.out.println("Pong!");
        }
        else {
            new SyntaxError(event, prefix + "ping");
        }
    }
}
