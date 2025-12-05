package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import lombok.Getter;

@Getter
@ResourceType(name = "StarTowerBookFateCardQuest.json")
public class StarTowerBookFateCardQuestDef extends BaseDef {
    private int Id;
    
    @Override
    public int getId() {
        return Id;
    }
}
