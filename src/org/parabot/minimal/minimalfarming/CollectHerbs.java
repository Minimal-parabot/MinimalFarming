package org.parabot.minimal.minimalfarming;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.SceneObject;

public class CollectHerbs implements Strategy
{
    private static final int[] PATCH_IDS = Seed.getPatchIds();

    @Override
    public boolean activate()
    {
        return MinimalFarming.isPlanted && SceneObjects.getNearest(PATCH_IDS).length > 0;
    }

    @Override
    public void execute()
    {
        SceneObject herbPatch = SceneObjects.getClosest(PATCH_IDS);

        if (herbPatch != null)
        {
            Logger.addMessage("Collecting herbs", true);

            herbPatch.interact(SceneObjects.Option.FIRST);

            Time.sleep(1000);

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return Players.getMyPlayer().getAnimation() == -1;
                }
            }, 5000);
        }
    }
}