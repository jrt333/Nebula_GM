package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;

import lombok.Getter;

@Getter
@ResourceType(name = "TutorialLevel.json")
public class TutorialLevelDef extends BaseDef {
    private int Id;
    private int WorldClass;
    private int Item1;
    private int Qty1;
    
    @Override
    public int getId() {
        return Id;
    }
}
