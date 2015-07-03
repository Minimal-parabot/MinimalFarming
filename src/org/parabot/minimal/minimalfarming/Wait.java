package org.parabot.minimal.minimalfarming;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Players;

public class Wait implements Strategy
{
    @Override
    public boolean activate()
    {
        return Players.getMyPlayer().getAnimation() != -1;
    }

    @Override
    public void execute()
    {
        Logger.addMessage("Sleeping", true);

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