package joshie.harvest.blocks.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import joshie.harvest.core.util.Translate;
import joshie.harvest.core.util.base.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockStone extends ItemBlockBase {
    public ItemBlockStone(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        return "mine_wall";
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
    		if(stack.getItemDamage()==1)
			list.add(Translate.translate("tooltip.dirt"));
	}
}
