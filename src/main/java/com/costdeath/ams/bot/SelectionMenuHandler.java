package com.costdeath.ams.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SelectionMenuHandler extends ListenerAdapter {
    @Override
    public void onSelectionMenu(SelectionMenuEvent event) {
        // Load Properties

        Properties votes = new Properties();
        String id = event.getMessageId();
        try {
            votes.load(new FileInputStream(System.getProperty("user.dir")
                    + System.getProperty("file.separator") + "polls"
                    + System.getProperty("file.separator") + id + ".properties"));
        } catch(Exception e) {}

        // Update user vote

        String oldVote = "";
        if(votes.containsKey(event.getUser().getId())) {
            oldVote = votes.getProperty(event.getUser().getId());
            List<String> oldVoteList = new ArrayList<>(Arrays.asList(votes.getProperty(oldVote).split(", ")));
            oldVoteList.remove(event.getUser().getId());
            votes.setProperty(oldVote, oldVoteList.toString().substring(1, oldVoteList.toString().length() - 1));
        }

        votes.setProperty(event.getUser().getId(), event.getSelectedOptions().get(0).getValue());
        if(votes.containsKey(event.getSelectedOptions().get(0).getValue())) {
            List<String> currVoteList = new ArrayList<>(Arrays.asList(votes.getProperty(event.getSelectedOptions().get(0).getValue()).split(", ")));
            currVoteList.add(event.getUser().getId());
            currVoteList.remove("");
            votes.setProperty(event.getSelectedOptions().get(0).getValue(), currVoteList.toString().substring(1, currVoteList.toString().length() - 1));
        }
        else {
            votes.setProperty(event.getSelectedOptions().get(0).getValue(), event.getUser().getId());
        }

        // Count Maths

        int totalVotes = 0;
        int currVoteAmount = 0;
        int oldVoteAmount = 0;
        MessageEmbed oldEmbed = event.getMessage().getEmbeds().get(0);
        for(int i = 0; i < oldEmbed.getFields().toArray().length; i++) {
            if(oldEmbed.getFields().get(i).getName().equals(event.getSelectedOptions().get(0).getValue())) {
                currVoteAmount = votes.getProperty(event.getSelectedOptions().get(0).getValue()).split(", ").length;
                totalVotes += currVoteAmount;
            }
            else if(oldEmbed.getFields().get(i).getName().equals(oldVote)) {
                if(!votes.getProperty(oldVote).equals("")) {
                    oldVoteAmount = votes.getProperty(oldVote).split(", ").length;
                    totalVotes += oldVoteAmount;
                }
            }
            else {
                totalVotes += Integer.parseInt(oldEmbed.getFields().get(i).getValue().split(" ")[0]);
                System.out.println(oldEmbed.getFields().get(i).getValue().split(" ")[0]);
            }
        }

        // Remake embed structurediscord

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(oldEmbed.getTitle())
                .setColor(oldEmbed.getColor())
                .setThumbnail(oldEmbed.getThumbnail().getUrl());

        // Change option values, if statement changes affected field

        for(int i = 0; i < oldEmbed.getFields().toArray().length; i++) {
            if(oldEmbed.getFields().get(i).getName().equals(event.getSelectedOptions().get(0).getValue())) {
                    embed.addField(event.getSelectedOptions().get(0).getValue(),currVoteAmount + " - " + ((currVoteAmount * 100 / totalVotes)) + "%", true);
                    System.out.println(currVoteAmount + ", " + totalVotes);
            }
            else if(oldEmbed.getFields().get(i).getName().equals(oldVote)) {
                embed.addField(oldVote,oldVoteAmount + " - " + ((oldVoteAmount * 100 / totalVotes)) + "%", true);
            }
            else {
                int voteCount = Integer.parseInt(oldEmbed.getFields().get(i).getValue().split(" ")[0]);
                embed.addField(oldEmbed.getFields().get(i).getName(),voteCount + " - " + ((voteCount * 100 / totalVotes)) + "%", true);
            }
        }

        // Store Updated Votes
        event.editMessageEmbeds(embed.build()).queue();
        try {
            votes.store(new FileOutputStream(System.getProperty("user.dir")
                    + System.getProperty("file.separator") + "polls"
                    + System.getProperty("file.separator") + id + ".properties"), "Updated votes");
        } catch(Exception e) {}
    }
}
