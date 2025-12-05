package emu.nebula.game.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

public enum UseAction {
    Drop        (1),
    Item        (2),
    Pick        (3),
    No          (4);
    
    @Getter
    private final int value;
    private final static Int2ObjectMap<UseAction> map = new Int2ObjectOpenHashMap<>();
    
    static {
        for (UseAction type : UseAction.values()) {
            map.put(type.getValue(), type);
        }
    }
    
    private UseAction(int value) {
        this.value = value;
    }
    
    public static UseAction getByValue(int value) {
        return map.get(value);
    }
}
