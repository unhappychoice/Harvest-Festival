package joshie.harvest.calendar.packet;

import io.netty.buffer.ByteBuf;
import joshie.harvest.api.HFApi;
import joshie.harvest.api.calendar.CalendarDate;
import joshie.harvest.api.calendar.Season;
import joshie.harvest.api.calendar.Weekday;
import joshie.harvest.calendar.CalendarHelper;
import joshie.harvest.core.HFTrackers;
import joshie.harvest.core.helpers.MCClientHelper;
import joshie.harvest.core.network.Packet;
import joshie.harvest.core.network.Packet.Side;
import joshie.harvest.core.network.PenguinPacket;
import net.minecraft.entity.player.EntityPlayer;

@Packet(Side.CLIENT)
public class PacketSyncCalendar extends PenguinPacket {
    private int daysPerSeason;
    private Weekday weekday;
    private int day;
    private Season season;
    private int year;
    
    public PacketSyncCalendar() {}
    public PacketSyncCalendar(CalendarDate date) {
        this.daysPerSeason = CalendarDate.DAYS_PER_SEASON;
        this.weekday = date.getWeekday();
        this.day = date.getDay();
        this.season = date.getSeason();
        this.year = date.getYear();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(daysPerSeason);
        buf.writeByte(weekday.ordinal());
        buf.writeInt(day);
        buf.writeByte(season.ordinal());
        buf.writeInt(year);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        daysPerSeason = buf.readInt();
        weekday = CalendarHelper.DAYS[buf.readByte()];
        day = buf.readInt();
        season = CalendarHelper.SEASONS[buf.readByte()];
        year = buf.readInt();
    }
    
    @Override
    public void handlePacket(EntityPlayer player) {
        CalendarDate.DAYS_PER_SEASON = daysPerSeason;
        CalendarDate date = HFApi.calendar.getDate(player.world);
        Season previous = date.getSeason();
        date.setDate(weekday, day, season, year);

        //Refresh all Blocks in Render range
        //If the seasons are not the same, re-render the client
        if (previous != season) {
            HFTrackers.getCalendar(player.world).onSeasonChanged();
            MCClientHelper.refresh();
        }
    }
}