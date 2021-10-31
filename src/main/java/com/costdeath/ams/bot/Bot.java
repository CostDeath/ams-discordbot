package com.costdeath.ams.bot;

import net.dv8tion.jda.api.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Bot {

    public static void main(String[] arguments) throws Exception {
        InputStream botInfoFile = new FileInputStream(System.getProperty("user.dir") + "\\botinfo.properties");
        Properties bot = new Properties();
        bot.load(botInfoFile);

        JDA api = JDABuilder.createDefault(bot.getProperty("token"))
                .addEventListeners(new CommandHandler(bot.getProperty("prefix")))
                .build();
    }
}
