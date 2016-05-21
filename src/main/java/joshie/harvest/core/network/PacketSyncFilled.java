package joshie.harvest.core.network;

import io.netty.buffer.ByteBuf;
import joshie.harvest.blocks.tiles.TileFillable;
import joshie.harvest.core.helpers.generic.MCClientHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketSyncFilled extends AbstractPacketLocation {
    private int isFilled;

    public PacketSyncFilled() {}
    public PacketSyncFilled(TileEntity tile, int isFilled) {
        super(tile);
        this.isFilled = isFilled;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        super.toBytes(buffer);
        buffer.writeByte(isFilled);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        super.fromBytes(buffer);
        isFilled = buffer.readByte();
    }

    public void handlePacket(EntityPlayer player) {
        TileEntity tile = MCClientHelper.getTile(this);
        if (tile instanceof TileFillable) {
            ((TileFillable) tile).setFilled(isFilled);
            MCClientHelper.updateRender(pos);
        }
    }
}