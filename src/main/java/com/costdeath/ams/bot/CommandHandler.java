package com.costdeath.ams.bot;

import com.costdeath.ams.bot.cmd.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    private String prefix;
    private String staffRole;
    private boolean isStaff = false;

    public CommandHandler(String prefix, String staffRole) {
        this.prefix = prefix;
        this.staffRole = staffRole;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] args = msg.getContentRaw().split(" ");

        if(!event.getAuthor().isBot() && args[0].startsWith(this.prefix)) {
            args[0] = args[0].replaceFirst(this.prefix, "");
            System.out.println("User " + event.getAuthor().getAsTag() + " used the command \"" + msg.getContentRaw() + "\".");
            try { this.isStaff = event.getMember().getRoles().contains(event.getGuild().getRoleById(staffRole));
            } catch(Exception e) {System.out.println("No staff role defined. Try adding a staff role!");}

            switch(args[0]) {
                //Staff Commands
                case "indexrole": new IndexRole(event, this.prefix, args, isStaff); break;

                //Regular Commands
                case "ping": new Ping(event, this.prefix, args);
            }
        }
    }
}
