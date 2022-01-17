package com.costdeath.ams.bot.cmd;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class Poll {
    public Poll(MessageReceivedEvent event, String[] args) {
        //if(args.length > 3 && !event.getMessage().getMentionedChannels().isEmpty()) {
        try {
            String[] opts = String.join(" ", args).substring(args[0].length() + args[1].length() + 1).split(", ");

            //Build Embed

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(opts[0])
                    .setColor(0xe3782b)
                    .setThumbnail("https://cdn.discordapp.com/icons/381495502275084289/cc894e3c3c81b2cf4bbde1edc52cfcb6.webp");
            for(int i = 1; i < opts.length; i++) {
                embed.addField(opts[i], "0 - 0%", true);
            }

            //Build Select Menu

            SelectionMenu.Builder menu = SelectionMenu.create("menu:class")
                    .setPlaceholder("Choose your answer")
                    .setRequiredRange(1, 1);
            for(int i = 1; i < opts.length; i++) {
                    menu.addOption(opts[i], opts[i]);
            }

            event.getMessage().getMentionedChannels().get(0).sendMessageEmbeds(embed.build()).setActionRow(menu.build()).queue((message) -> {
                // Make Storage File
                String id = message.getId();
                try {
                    File f = new File(System.getProperty("user.dir")
                            + System.getProperty("file.separator") + "polls"
                            + System.getProperty("file.separator") + id + ".properties");
                    f.createNewFile();
                } catch(Exception e) {}
            });

        } catch(Exception e) {
            new SyntaxError(event, "poll <channel> <question>, <option1>, <option2>, ...");
        }
    }
}
