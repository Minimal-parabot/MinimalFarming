package org.parabot.minimal.minimalfarming;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.SceneObject;

public class PlantSeeds implements Strategy
{
    private Seed seed;

    public PlantSeeds(Seed seed)
    {
        this.seed = seed;
    }

    private static final int[] PATCH_IDS = Seed.getPatchIds();
    
    @Override
    public boolean activate()
    {
        return MinimalFarming.isClear && Inventory.contains(seed.getId())
                && SceneObjects.getNearest(PATCH_IDS).length > 0;
    }

    @Override
    public void execute()
    {
        Item seedItem = Inventory.getItem(seed.getId());
        SceneObject herbPatch = SceneObjects.getClosest(PATCH_IDS);

        if (seedItem != null && herbPatch != null)
        {
            Logger.addMessage("Planting seed", true);

            seedItem.interact(Items.Option.USE_WITH);

            Time.sleep(250);

            Menu.sendAction(62, herbPatch.getHash(), herbPatch.getLocalRegionX(), herbPatch.getLocalRegionY(), herbPatch.getId(), 0);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return Players.getMyPlayer().getAnimation() != -1;
                }
            }, 2000);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return Players.getMyPlayer().getAnimation() == -1;
                }
            }, 2500);
        }
    }
}