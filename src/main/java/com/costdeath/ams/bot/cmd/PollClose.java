package com.costdeath.ams.bot.cmd;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;

public class PollClose {
    public PollClose(MessageReceivedEvent event) {
        try {
            event.getMessage().getReferencedMessage().getActionRows().get(0);
            event.getMessage().getReferencedMessage().editMessage("This poll has been closed.").setActionRows().queue();

            String id = event.getMessage().getReferencedMessage().getId();
            System.out.println(id);
            File f = new File(System.getProperty("user.dir")
                    + System.getProperty("file.separator") + "polls"
                    + System.getProperty("file.separator") + id + ".properties");
        } catch(Exception e) {
            event.getMessage().reply("This message is not an active poll.").queue();
        }
    }
}
