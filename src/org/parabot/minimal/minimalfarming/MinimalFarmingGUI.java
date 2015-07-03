package org.parabot.minimal.minimalfarming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinimalFarmingGUI extends JFrame
{
    private JComboBox<Seed> seedJComboBox;
    private JComboBox<Teleport> teleportJComboBox;

    public MinimalFarmingGUI()
    {
        setLayout(new BorderLayout());
        setSize(250, 170);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new MainPanel();
        JPanel buttonPanel = new ButtonPanel();

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public class MainPanel extends JPanel
    {
        public MainPanel()
        {
            seedJComboBox = new JComboBox<>();
            seedJComboBox.setFont(new Font("Helvetica", Font.PLAIN, 16));

            for (Seed s : Seed.values())
            {
                seedJComboBox.addItem(s);
            }

            teleportJComboBox = new JComboBox<>();
            teleportJComboBox.setFont(new Font("Helvetica", Font.PLAIN, 16));
            teleportJComboBox.addItem(Teleport.SKILLING_CANIFIS);
            teleportJComboBox.addItem(Teleport.SKILLING_CATHERBY);

            add(seedJComboBox);
            add(teleportJComboBox);
        }
    }

    public class ButtonPanel extends JPanel
    {
        public ButtonPanel()
        {
            JButton startButton = new JButton("Start");
            startButton.setFont(new Font("Helvetica", Font.BOLD, 20));
            startButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    dispose();
                }
            });

            add(startButton);
        }
    }

    public Seed getSeed()
    {
        return (Seed) seedJComboBox.getSelectedItem();
    }

    public Teleport getTeleport()
    {
        return (Teleport) teleportJComboBox.getSelectedItem();
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                MinimalFarmingGUI gui = new MinimalFarmingGUI();
                gui.setVisible(true);
            }
        });
    }
}