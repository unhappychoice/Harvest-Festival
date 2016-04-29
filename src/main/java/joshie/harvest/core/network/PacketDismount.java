package joshie.harvest.core.network;

import io.netty.buffer.ByteBuf;
import joshie.harvest.api.animals.IAnimalTracked;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketDismount implements IMessage, IMessageHandler<PacketDismount, IMessage> {
    public PacketDismount() {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(PacketDismount message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        EntityAnimal entity = (EntityAnimal) player.getRidingEntity();
        entity.startRiding(null);
        entity.rotationPitch = player.rotationPitch;
        entity.rotationYaw = player.rotationYaw;
        entity.moveRelative(0F, 1.0F, 1.25F);
        ((IAnimalTracked) entity).getData().dismount(player);
        return null;
    }
}