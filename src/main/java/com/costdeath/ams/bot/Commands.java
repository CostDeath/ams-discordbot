package com.costdeath.ams.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands {

    private MessageReceivedEvent event;
    private String prefix;
    private String[] args;

    public Commands(MessageReceivedEvent event, String prefix, String[] args) {
        this.event = event;
        this.prefix = prefix;
        this.args = args;
    }

    public String syntaxError(String syntax) {
        return("Oops, you tried to use incorrect syntax! Correct syntax is: ```" +
                this.prefix +
                syntax + "```");
    }

    public void sendMessage(MessageReceivedEvent event, String msg) {
        event.getChannel().sendMessage(msg).queue();
    }

    public void ping() {
        if(args.length == 1) {
            this.sendMessage(event, "Pong!");
            System.out.println("Pong!");
        }
        else {
            this.sendMessage(event, syntaxError("ping"));
            System.out.println("Syntax Error.");
        }
    }
}
