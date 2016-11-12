package joshie.harvest.core.gui.stats.collection.page;

import joshie.harvest.core.base.gui.BookPage;
import joshie.harvest.mining.HFMining;
import joshie.harvest.mining.item.ItemMaterial.Material;
import net.minecraft.item.ItemStack;

import static joshie.harvest.core.gui.stats.CollectionHelper.isInMiningCollection;

public class PageMining extends PageShipping {
    public static final BookPage INSTANCE = new PageMining();

    private PageMining() {
        super("mining", HFMining.MATERIALS.getStackFromEnum(Material.ADAMANTITE));
    }

    @Override
    boolean qualifies(ItemStack stack) {
        return isInMiningCollection(stack);
    }
}
