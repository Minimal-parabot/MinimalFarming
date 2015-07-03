package org.parabot.minimal.minimalfarming;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.SceneObject;

import java.util.Arrays;
import java.util.List;

public class Banker implements Strategy
{
    private List<Integer> exceptions = Arrays.asList(Seed.getSeedIds());

    private static final int BANK_BOOTH_ID = 2213;

    @Override
    public boolean activate()
    {
        return Inventory.getCount() >= 20;
    }

    @Override
    public void execute()
    {
        if (SceneObjects.getNearest(BANK_BOOTH_ID).length == 0
                || SceneObjects.getClosest(BANK_BOOTH_ID).distanceTo() > 10)
        {
            Teleport.HOME_HOME.Teleport();

            Time.sleep(new SleepCondition()
            {
                @Override
                public boolean isValid()
                {
                    return SceneObjects.getNearest(BANK_BOOTH_ID).length > 0;
                }
            }, 5000);
        }

        if (SceneObjects.getNearest(BANK_BOOTH_ID).length > 0)
        {
            if (!Bank.isOpen())
            {
                SceneObject bankBooth = SceneObjects.getClosest(BANK_BOOTH_ID);

                if (bankBooth != null)
                {
                    bankBooth.interact(SceneObjects.Option.FIRST);

                    Time.sleep(new SleepCondition()
                    {
                        @Override
                        public boolean isValid()
                        {
                            return Bank.isOpen();
                        }
                    }, 5000);
                }
            }

            if (Bank.isOpen())
            {
                for (Item i : Inventory.getItems())
                {
                    if (!exceptions.contains(i.getId()))
                    {
                        Menu.sendAction(431, i.getId() - 1, i.getSlot(), 5064);

                        Time.sleep(500);

                        break;
                    }
                }
            }
        }
    }
}