package emu.nebula.data.resources;

import java.util.Set;
import java.util.TreeSet;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@ResourceType(name = "TalentGroup.json")
public class TalentGroupDef extends BaseDef {
    private int Id;
    private int CharId;
    private int PreGroup;
    
    @Setter
    private transient TalentDef mainTalent;
    private transient Set<TalentDef> talents;
    
    public TalentGroupDef() {
        this.talents = new TreeSet<>();
    }
    
    @Override
    public int getId() {
        return Id;
    }
}
