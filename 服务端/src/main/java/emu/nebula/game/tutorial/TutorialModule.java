package emu.nebula.game.tutorial;

import emu.nebula.Nebula;
import emu.nebula.data.GameData;
import emu.nebula.game.GameContext;
import emu.nebula.game.GameContextModule;
import emu.nebula.game.player.Player;
import emu.nebula.game.player.PlayerChangeInfo;

public class TutorialModule extends GameContextModule {

    public TutorialModule(GameContext context) {
        super(context);
    }
    
    public boolean settle(Player player, int id) {
        // Check if the tutorial was completed
        if (player.getProgress().getTutorialLog().containsKey(id)) {
            return true;
        }
        
        // Get data
        var data = GameData.getTutorialLevelDataTable().get(id);
        if (data == null) {
            return false;
        }
        
        // Create log
        var log = new TutorialLevelLog(id);

        // Add to progress tutorial map
        player.getProgress().getTutorialLog().put(id, log);
        
        // Save to database
        Nebula.getGameDatabase().update(player.getProgress(), player.getUid(), "tutorialLog." + log.getId(), log);
        
        // Success
        return true;
    }
    
    public PlayerChangeInfo recvReward(Player player, int id) {
        // Get tutorial log
        var log = player.getProgress().getTutorialLog().get(id);
        if (log == null || log.isClaimed()) {
            return null;
        }
        
        // Get data
        var data = GameData.getTutorialLevelDataTable().get(id);
        if (data == null) {
            return null;
        }
        
        // Set claim state
        log.setClaimed(true);
        
        // Save to database
        Nebula.getGameDatabase().update(player.getProgress(), player.getUid(), "tutorialLog." + log.getId(), log);
        
        // Add reward item
        return player.getInventory().addItem(data.getItem1(), data.getQty1());
    }
}
