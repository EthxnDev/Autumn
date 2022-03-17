package gq.engo.utils;

 public class TPS implements Runnable
{
    public static int TICK_COUNT= 0;
    public static long[] TICKS= new long[600];
    public static long LAST_TICK= 0L;

    public static double getTPS()
    {
        return getTPS(100);
    }

    public static double getTPS(int ticks)
    {
        if (TICK_COUNT< ticks) {
            return 20.0D;
        }
        int target = (TICK_COUNT- 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0D);
    }

    public static double getRoundedTPS() {
        double tps = getTPS();
        return Math.max(0, Math.min(20, (Math.round(getTPS() * 100) / 100)));
    }

    public static String getRoundedTPSWithColour() {
        double tps = getRoundedTPS();
        String colour = "";
        if (tps > 14) {
            colour = "&a";
        } else {
            if (tps > 10) {
                colour = "&e";
            } else {
                colour = "&c";
            }
        }
        return colour+tps;
    }

    public static long getElapsed(int tickID)
    {
        if (TICK_COUNT- tickID >= TICKS.length)
        {
        }

        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    public void run()
    {
        TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();

        TICK_COUNT+= 1;
    }
}