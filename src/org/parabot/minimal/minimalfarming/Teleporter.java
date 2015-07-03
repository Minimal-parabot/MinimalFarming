package org.parabot.minimal.minimalfarming;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;

public class Teleporter implements Strategy
{
    private static final int[] PATCH_IDS = Seed.getPatchIds();

    @Override
    public boolean activate()
    {
        return Inventory.getCount() < 20 && SceneObjects.getNearest(PATCH_IDS).length == 0;
    }

    @Override
    public void execute()
    {
        Teleport.SKILLING_CATHERBY.Teleport();

        Time.sleep(new SleepCondition()
        {
            @Override
            public boolean isValid()
            {
                return Players.getMyPlayer().getAnimation() == -1
                    && SceneObjects.getNearest(PATCH_IDS).length > 0;
            }
        }, 5000);
    }
}