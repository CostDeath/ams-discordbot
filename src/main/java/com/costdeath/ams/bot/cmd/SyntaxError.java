package com.costdeath.ams.bot.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SyntaxError {
    protected SyntaxError(MessageReceivedEvent event, String syntax) {
        event.getMessage().reply("Oops, you tried to use incorrect syntax! Correct syntax is: ```" +
                syntax + "```").queue();
    }
}
