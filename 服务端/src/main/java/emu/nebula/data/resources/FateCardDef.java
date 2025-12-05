package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@ResourceType(name = "FateCard.json")
public class FateCardDef extends BaseDef {
    private int Id;
    
    private boolean IsTower;
    private boolean IsVampire;
    private boolean IsVampireSpecial;
    private boolean Removable;
    
    @Setter(AccessLevel.PROTECTED)
    private transient int bundleId;
    
    @Override
    public int getId() {
        return Id;
    }
}
