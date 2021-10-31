package com.costdeath.ams.bot;

import net.dv8tion.jda.api.*;

import java.io.File;
import java.util.Scanner;

public class Bot {
    public String token;
    public String prefix;

    public void assignInfo(String[] line) {
        switch(line[0]) {
            case "token": this.token = line[1];
            case "prefix": this.prefix = line[1];
        }
    }

    public static void main(String[] arguments) throws Exception {
        Bot bot = new Bot();
        File botInfoFile = new File(System.getProperty("user.dir") + "\\botinfo.txt");
        Scanner reader = new Scanner(botInfoFile);
        while(reader.hasNextLine()) {
            String[] line = reader.nextLine().split("=");
            bot.assignInfo(line);
        }

        JDA api = JDABuilder.createDefault(bot.token)
                .addEventListeners(new Commands(bot.prefix))
                .build();
    }
}
