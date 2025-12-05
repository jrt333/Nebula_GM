package emu.nebula.data.resources;

import java.util.List;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;

@Getter
@ResourceType(name = "StarTowerBookFateCardBundle.json")
public class StarTowerBookFateCardBundleDef extends BaseDef {
    private int Id;
    private int BundleId;
    
    private transient List<FateCardDef> cards;
    
    public StarTowerBookFateCardBundleDef() {
        this.cards = new ObjectArrayList<>();
    }
    
    @Override
    public int getId() {
        return Id;
    }

    protected void addCard(FateCardDef card) {
        this.getCards().add(card);
    }
}
