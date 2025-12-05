package emu.nebula.game.agent;

import emu.nebula.game.inventory.ItemParamMap;

public class AgentResult {
    private Agent agent;
    private ItemParamMap rewards;
    private ItemParamMap bonus;
    
    public AgentResult(Agent agent) {
        this.agent = agent;
    }

    public Agent getAgent() {
        return this.agent;
    }

    public ItemParamMap getRewards() {
        if (this.rewards == null) {
            return ItemParamMap.EMPTY;
        }
        
        return this.rewards;
    }

    public ItemParamMap getBonus() {
        if (this.bonus == null) {
            return ItemParamMap.EMPTY;
        }
        
        return this.bonus;
    }

    public void setRewards(ItemParamMap rewards) {
        this.rewards = rewards;
    }

    public void setBonus(ItemParamMap bonus) {
        this.bonus = bonus;
    }
}
