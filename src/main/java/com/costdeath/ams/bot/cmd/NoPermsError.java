package com.costdeath.ams.bot.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class NoPermsError {
    public NoPermsError(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Oops, you don't have sufficient permissions to execute this command.").queue();
        System.out.println("Insufficient Permissions Error! Try re-assigning the staff role if this is a mistake.");
    }
}
