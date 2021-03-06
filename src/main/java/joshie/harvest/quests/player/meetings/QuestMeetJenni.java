package joshie.harvest.quests.player.meetings;

import com.google.common.collect.Sets;
import joshie.harvest.api.HFApi;
import joshie.harvest.api.calendar.Season;
import joshie.harvest.api.knowledge.Note;
import joshie.harvest.api.npc.NPCEntity;
import joshie.harvest.api.quests.HFQuest;
import joshie.harvest.buildings.HFBuildings;
import joshie.harvest.crops.HFCrops;
import joshie.harvest.knowledge.HFNotes;
import joshie.harvest.quests.Quests;
import joshie.harvest.quests.base.QuestMeeting;
import joshie.harvest.town.TownHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Set;

import static joshie.harvest.api.calendar.Season.AUTUMN;
import static joshie.harvest.api.calendar.Season.SUMMER;
import static joshie.harvest.npcs.HFNPCs.GS_OWNER;

@HFQuest("tutorial.supermarket")
public class QuestMeetJenni extends QuestMeeting {
    public QuestMeetJenni() {
        super(HFBuildings.SUPERMARKET, GS_OWNER);
    }

    @Override
    public String getDescription(World world, EntityPlayer player) {
        if (TownHelper.getClosestTownToEntity(player, false).hasBuilding(HFBuildings.CARPENTER) && HFApi.quests.hasCompleted(Quests.JADE_MEET, player)) {
            if (!hasBuilding(player)) return getLocalized("description.build");
            else return getLocalized("description.visit");
        } else return null;
    }

    @Override
    public ItemStack getCurrentIcon(World world, EntityPlayer player) {
        if (!hasBuilding(player)) return buildingStack;
        else return super.getCurrentIcon(world, player);
    }

    @Override
    public String getLocalizedScript(EntityPlayer player, NPCEntity entity) {
        //Jenni says hey there I'm the owner, welcome to the supermarket, here you can buy all kinds of things
        //From seeds to chocolate, If you need anything, just ask me
        //She then says we're open every weekday except wednesday from 9am to 5pm
        //We're also open on saturdays but only from 11am to 3pm
        //She says you're our first customer so here's a free gift
        return getLocalized("welcome.owner");
    }

    @Override
    public void onChatClosed(EntityPlayer player, NPCEntity entity, boolean wasSneaking) {
        complete(player);
    }

    @Override
    public Set<Note> getNotes() {
        return Sets.newHashSet(HFNotes.SUPERMARKET);
    }

    @Override
    public void onQuestCompleted(EntityPlayer player) {
        Season season = HFApi.calendar.getDate(player.world).getSeason();
        if (season == SUMMER) rewardItem(player, HFCrops.TOMATO.getSeedStack(4));
        else if (season == AUTUMN) rewardItem(player, HFCrops.EGGPLANT.getSeedStack(4));
        else rewardItem(player, HFCrops.CUCUMBER.getSeedStack(4));
    }
}