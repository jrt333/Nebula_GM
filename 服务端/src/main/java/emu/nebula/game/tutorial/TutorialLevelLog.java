package emu.nebula.game.tutorial;

import dev.morphia.annotations.Entity;
import emu.nebula.proto.Public.TutorialLevel;
import lombok.Getter;

@Getter
@Entity(useDiscriminator = false)
public class TutorialLevelLog {
    private int id;
    private boolean claimed;
    
    @Deprecated // Morphia only
    public TutorialLevelLog() {
        
    }
    
    public TutorialLevelLog(int id) {
        this.id = id;
    }

    public void setClaimed(boolean value) {
        this.claimed = value;
    }

    // Proto

    public TutorialLevel toProto() {
        var proto = TutorialLevel.newInstance()
                .setLevelId(this.getId())
                .setPassed(true)
                .setRewardReceived(this.isClaimed());
        
        return proto;
    }
}
