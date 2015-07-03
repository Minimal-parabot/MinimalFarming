package org.parabot.minimal.minimalfarming;

public enum Seed
{
    GUAM(5292, 9),
    MARRENTILL(5293, 14),
    TARROMIN(5294, 19),
    HARRALANDER(5295, 26),
    RANARR(5296, 32),
    TOADFLAX(5297, 38),
    IRIT(5298, 44),
    AVANTOE(5299, 50),
    KWUARM(5300, 56),
    SNAPDRAGON(5301, 62),
    CADANTINE(5302, 67),
    LANTADYME(5303, 73),
    DWARF_WEED(5304, 79);
    
    private int id;
    private int level;
    
    Seed(int id, int level)
    {
        this.id = id;
        this.level = level;
    }

    /**
     * gets the id of the seed selected from the gui
     * @return id
     */
    public int getId()
    {
        return id;
    }

    /**
     * gets the level required to plant the seed
     * @return level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * gets the patch ids
     * @return patchIds
     */
    public static int[] getPatchIds()
    {
        return new int[] { 8151, 8152, 8153 };
    }

    /**
     * gets the ids for all seeds
     * @return seedIds
     */
    public static Integer[] getSeedIds()
    {
        Seed[] seeds = values();
        Integer[] seedIds = new Integer[seeds.length + 2];

        for (int i = 0; i < seeds.length; i++)
        {
            seedIds[i] = seeds[i].getId();
        }

        seedIds[seedIds.length - 2] = 5342;
        seedIds[seedIds.length - 1] = 5344;

        return seedIds;
    }

    @Override
    public String toString()
    {
        return name().charAt(0) + name().substring(1).toLowerCase().replace("_", " ") + " (" + level + ")";
    }
}
