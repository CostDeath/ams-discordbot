package com.costdeath.ams.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    public String prefix;

    public Commands(String prefix) {
        this.prefix = prefix;
    }

    public String syntaxError(String syntax) {
        return("Oops, you tried to use incorrect syntax! Correct syntax is: ```" +
                this.prefix +
                syntax + "```");
    }

    public void sendMessage(MessageReceivedEvent event, String msg) {
        event.getChannel().sendMessage(msg).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Received Message!");
        Message msg = event.getMessage();
        String[] args = msg.getContentRaw().split(" ");

        if(!event.getAuthor().isBot() && args[0].startsWith(this.prefix)) {
            args[0] = args[0].replaceFirst(this.prefix, "");
            System.out.println("Recognised Command! args[0] is " + args[0]);
            switch(args[0]) {
                case "ping":
                    if(args.length == 1) {
                        this.sendMessage(event, "Pong!");
                    }
                    else {
                        this.sendMessage(event, syntaxError("ping"));
                    }
            }
        }
    }
}
