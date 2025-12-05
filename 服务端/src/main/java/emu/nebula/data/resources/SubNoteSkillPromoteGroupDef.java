package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import emu.nebula.game.inventory.ItemParamMap;
import lombok.Getter;

@Getter
@ResourceType(name = "SubNoteSkillPromoteGroup.json")
public class SubNoteSkillPromoteGroupDef extends BaseDef {
    private int Id;
    private String SubNoteSkills;
    
    private transient ItemParamMap items;
    
    @Override
    public int getId() {
        return Id;
    }
    
    @Override
    public void onLoad() {
        this.items = ItemParamMap.fromJsonString(this.SubNoteSkills);
    }
}
