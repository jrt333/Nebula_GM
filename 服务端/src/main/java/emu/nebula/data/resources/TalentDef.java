package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.GameData;
import emu.nebula.data.ResourceType;
import emu.nebula.data.ResourceType.LoadPriority;
import lombok.Getter;

@Getter
@ResourceType(name = "Talent.json", loadPriority = LoadPriority.LOW)
public class TalentDef extends BaseDef {
    private int Id;
    private int Index;
    private int Type;
    private int GroupId;
    private int Sort;
    
    @Override
    public int getId() {
        return Id;
    }
    
    @Override
    public void onLoad() {
        var talentGroup = GameData.getTalentGroupDataTable().get(this.getGroupId());
        if (talentGroup == null) {
            return;
        }
        
        if (this.Type == 1) {
            talentGroup.setMainTalent(this);
        } else if (this.Type == 2) {
            talentGroup.getTalents().add(this);
        }
    }
}
