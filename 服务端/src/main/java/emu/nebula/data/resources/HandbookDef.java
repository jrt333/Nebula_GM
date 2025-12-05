package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import lombok.Getter;

@Getter
@ResourceType(name = "Handbook.json")
public class HandbookDef extends BaseDef {
    private int Id;
    private int Index;
    private int Type;
    
    @Override
    public int getId() {
        return Id;
    }
}
