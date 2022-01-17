package com.costdeath.ams.bot;

import net.dv8tion.jda.api.*;

import java.io.FileInputStream;
import java.util.Properties;

public class Bot {

    public static void main(String[] arguments) throws Exception {
        //Load Properties
        Properties bot = new Properties();
        Properties committeeCheck = new Properties();

        bot.load(new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "botinfo.properties"));
        committeeCheck.load(new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "committeeroles.properties"));

        //Load Bot API
        JDA api = JDABuilder.createDefault(bot.getProperty("token"))
                .addEventListeners(new PrivateMessageHandler(bot))
                .addEventListeners(new CommandHandler(bot, committeeCheck))
                .addEventListeners(new SelectionMenuHandler())
                .build();
    }
}
