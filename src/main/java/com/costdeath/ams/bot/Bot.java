package com.costdeath.ams.bot;

import net.dv8tion.jda.api.*;

import java.io.FileInputStream;
import java.util.Properties;

public class Bot {

    public static void main(String[] arguments) throws Exception {
        Properties bot = new Properties();
        bot.load(new FileInputStream(System.getProperty("user.dir") + "\\botinfo.properties"));

        JDA api = JDABuilder.createDefault(bot.getProperty("token"))
                .addEventListeners(new CommandHandler(bot.getProperty("prefix"), bot.getProperty("staffRoleId")))
                .build();
    }
}
