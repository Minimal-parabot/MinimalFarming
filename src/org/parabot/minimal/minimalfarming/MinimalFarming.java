package org.parabot.minimal.minimalfarming;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Random;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.Loader;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;
import org.rev317.min.api.methods.Skill;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;

@ScriptManifest(author = "Minimal",
        category = Category.FARMING,
        description = "Plants herbs in Ikov and instantly picks them for quick exp and decent money.",
        name = "Minimal Farming",
        servers = {"Ikov"},
        version = 1.0)

public class MinimalFarming extends Script implements Paintable, MessageListener
{
    private final ArrayList<Strategy> strategies = new ArrayList<>();

    private Timer timer = new Timer();

    private static final Color[] colors = { new Color(36, 123, 160), new Color(112, 193, 179),
            new Color(2, 128, 144), new Color(0, 168, 150),
            new Color(240, 243, 189), new Color(112, 193, 179),
            new Color(17, 75, 95), new Color(228, 253, 255),
            new Color(244, 241, 187), new Color(230, 235, 224) };

    private static final Color TITLE_COLOR = colors[Random.between(0, colors.length - 1)];

    private static final int STARTING_EXPERIENCE = Skill.FARMING.getExperience();
    private int herbsCollected = 0;

    public static boolean isClear = true;
    public static boolean isPlanted = false;

    @Override
    public boolean onExecute()
    {
        MinimalFarmingGUI gui = new MinimalFarmingGUI();
        gui.setVisible(true);

        while (gui.isVisible())
        {
            Time.sleep(50);
        }

        Seed seed = gui.getSeed();

        strategies.add(new Relog());
        strategies.add(new Wait());
        strategies.add(new Teleporter());
        strategies.add(new Banker());
        strategies.add(new CollectHerbs());
        strategies.add(new PlantSeeds(seed));
        provide(strategies);
        return true;
    }
    
    @Override
    public void paint(Graphics g)
    {
        // Draws the Minimal paint without getting an image from online
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Helvetica", Font.PLAIN, 14));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2d.setColor(TITLE_COLOR);
        g2d.fillRect(551, 216, 175, 40);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.setColor(Color.WHITE);
        g2d.fillRect(551, 256, 175, 197);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Draws the title
        g.setFont(new Font("Helvetica", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Minimal Farming", 566, 244);

        // Draws the stats
        g.setFont(new Font("Helvetica", Font.PLAIN, 14));
        g.setColor(new Color(31, 34, 50));
        g.drawString("Time: " + timer.toString(), 555, 271);
        g.drawString("Exp(hr): " + getExperience(), 560, 359);
        g.drawString("Herbs(hr): " + herbsCollected + "(" + formatNumber(timer.getPerHour(herbsCollected)) + ")", 560, 448);
    }
    
    @Override
    public void messageReceived(MessageEvent m)
    {
        String message = m.getMessage().toLowerCase();
        
        if (m.getType() == 0)
        {
            if (message.contains("object") || message.contains("not in a")
                    || message.contains("is already on") || message.contains("exist"))
            {
                Logger.addMessage("Account was nulled", true);

                forceLogout();
            }
            else if (message.contains("can't plant a seed"))
            {
                isClear = false;
                isPlanted = true;
            }
            else if (message.contains("clear the"))
            {
                Logger.addMessage("Is clear", true);

                isClear = true;
                isPlanted = false;
            }
            else if (message.contains("will be ready"))
            {
                Logger.addMessage("Is planted", true);

                isClear = false;
                isPlanted = true;
            }
            else if (message.contains("harvest the"))
            {
                herbsCollected++;

                isClear = true;
                isPlanted = false;
            }
        }
    }

    private void forceLogout()
    {
        try
        {
            Method m = Loader.getClient().getClass().getDeclaredMethod("W");
            m.setAccessible(true);

            m.invoke(Loader.getClient());
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    private String formatNumber(double number)
    {
        DecimalFormat compact = new DecimalFormat("#,###.0");

        if (number >= 1000000)
        {
            return compact.format(number / 1000000) + "M";
        }
        else if (number >= 1000
                && number < 1000000)
        {
            return compact.format(number / 1000) + "K";
        }

        return "" + number;
    }
    
    private String getExperience()
    {
        int experience = Skill.FARMING.getExperience() - STARTING_EXPERIENCE;

        return formatNumber(experience) + "(" + formatNumber(timer.getPerHour(experience)) + ")";
    }
}