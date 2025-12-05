package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import emu.nebula.game.inventory.ItemParamMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.Getter;

@Getter
@ResourceType(name = "StorySetSection.json")
public class StorySetSectionDef extends BaseDef {
    private int Id;
    private int ChapterId;
    
    private int RewardItem1Tid;
    private int RewardItem1Qty;
    
    private transient ItemParamMap rewards;
    
    @Getter
    private static IntSet chapterIds = new IntOpenHashSet();
    
    @Override
    public int getId() {
        return Id;
    }
    
    @Override
    public void onLoad() {
        // Add to chapter ids
        chapterIds.add(this.getChapterId());
        
        // Parse rewards
        this.rewards = new ItemParamMap();
        
        if (this.RewardItem1Tid > 0) {
            this.rewards.add(this.RewardItem1Tid, this.RewardItem1Qty);
        }
    }
}
