package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import emu.nebula.game.inventory.ItemParamMap;

import lombok.Getter;

@Getter
@ResourceType(name = "Production.json")
public class ProductionDef extends BaseDef {
    private int Id;
    private int UnlockWorldLevel;
    private int ProductionId;
    private int ProductionPerBatch;
    private int RawMaterialId1;
    private int RawMaterialCount1;
    
    private transient ItemParamMap materials;
    
    @Override
    public int getId() {
        return Id;
    }

    @Override
    public void onLoad() {
        this.materials = new ItemParamMap();
        
        if (this.RawMaterialId1 > 0) {
            this.materials.add(this.RawMaterialId1, this.RawMaterialCount1);
        }
    }
}
