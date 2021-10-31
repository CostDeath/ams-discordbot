package com.costdeath.ams.bot.cmd;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class IndexRole {

    public IndexRole(MessageReceivedEvent event, String prefix, String[] args, boolean isStaff) {
        if(!isStaff) { new NoPermsError(event); }
        else if(args.length == 2 && !event.getMessage().getMentionedRoles().isEmpty()) {
            Properties rolesList = new Properties();
            try { rolesList.load(new FileInputStream(System.getProperty("user.dir") + "\\botroles.properties"));
            } catch (Exception e) {System.out.println(e);}
            Role role = event.getMessage().getMentionedRoles().get(0);

            if(rolesList.containsKey(role.getName()) && rolesList.getProperty(role.getName()).equals(role.getId())) {
                event.getChannel().sendMessage("That role is already indexed in the system!").queue();
                System.out.println("Role was already indexed in the system.");
            }
            else {
                rolesList.setProperty(role.getName(), role.getId());
                try { rolesList.store(new FileOutputStream(System.getProperty("user.dir") + "\\botroles.properties"), "Updated " + role.getName());
                } catch (Exception e) {System.out.println(e);}
                event.getChannel().sendMessage("Successfully registered role <@&" + role.getId() + ">.").queue();

                System.out.println("Role " + role.getName() + " indexed as id " + role.getId());
            }
        }
        else {
            new SyntaxError(event, prefix + "indexrole <role @mention>");
        }
    }
}
