package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.GameData;
import emu.nebula.data.ResourceType;
import emu.nebula.data.ResourceType.LoadPriority;
import lombok.Getter;

@Getter
@ResourceType(name = "StarTowerBookFateCard.json", loadPriority = LoadPriority.LOW)
public class StarTowerBookFateCardDef extends BaseDef {
    private int Id;
    private int BundleId;
    
    @Override
    public int getId() {
        return Id;
    }
    
    @Override
    public void onLoad() {
        // Set card bundle id
        var card = GameData.getFateCardDataTable().get(this.getId());
        if (card == null) {
            return;
        }
        
        card.setBundleId(this.BundleId);
        
        // Add to bundle
        var bundle = GameData.getStarTowerBookFateCardBundleDataTable().get(this.getBundleId());
        if (bundle == null) {
            return;
        }
        
        bundle.addCard(card);
    }
}
