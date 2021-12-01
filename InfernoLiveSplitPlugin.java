package net.runelite.client.plugins.infernolivesplit;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "[N] Inferno LiveSplits",
        enabledByDefault = false,
        description = "Formats the wave for the LOS tool"
)
public class InfernoLiveSplitPlugin extends Plugin {

    private boolean checkNextTick = false;
    private int lastTick = 0;
    private boolean waveFinished = false;

    private long timeLastTick = 0;

    @Inject
    public Client client;

    @Subscribe
    public void onGameTick(GameTick event) {

        if (lastTick != -1 && isInCaves()) {

            System.out.println(client.getTickCount() - lastTick);
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event) {

        // Just for debugging and to show the tick count for scouting
        if (!event.getGameState().equals(GameState.LOGGED_IN)) {

            if (client.getTickCount() == 0) {
                lastTick = -1;
            } else {
                lastTick = client.getTickCount();
            }

            waveFinished = false;
        } else if (event.getGameState().equals(GameState.LOGIN_SCREEN)) {
            timeLastTick = 0;
        }
    }

    public boolean isInCaves() {
        return client.getVarbitValue(11878) == 1;
    }

}
