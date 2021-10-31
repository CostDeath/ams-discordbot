package com.costdeath.ams.bot.cmd;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class SetStaffRole {

    public String roleId;
    private Role role;

    public SetStaffRole(MessageReceivedEvent event, String prefix, String[] args) {
        if(!event.getMember().isOwner()) { new NoPermsError(event); }
        else if(args.length == 2 && !event.getMessage().getMentionedRoles().isEmpty()) {
            Properties bot = new Properties();
            try {
                bot.load(new FileInputStream(System.getProperty("user.dir") + "\\botinfo.properties"));
                this.role = event.getMessage().getMentionedRoles().get(0);
            } catch (Exception e) {System.out.println(e);}

            if(bot.getProperty("staffRoleId").equals(role.getId())) {
                event.getChannel().sendMessage("That is already the registered staff role!").queue();
                System.out.println("Role was already set as staff in the system.");
            }
            else {
                bot.setProperty("staffRoleId", role.getId());
                try {
                    bot.store(new FileOutputStream(System.getProperty("user.dir") + "\\botinfo.properties"), "Updated Staff role");
                    this.roleId = role.getId();

                    event.getChannel().sendMessage("Successfully registered role <@&" + role.getId() + "> as the staff role.").queue();
                    System.out.println("Role " + role.getName() + " registered as new staff role.");
                } catch (Exception e) {System.out.println(e);}
            }
        }
        else {
            new SyntaxError(event, prefix + "setstaffrole <role @mention>");
        }
    }
}
