package emu.nebula.game.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

public enum UseMode {
    UseModeManual   (1),
    UseModeAuto     (2),
    UseModeNot      (3);
    
    @Getter
    private final int value;
    private final static Int2ObjectMap<UseMode> map = new Int2ObjectOpenHashMap<>();
    
    static {
        for (UseMode type : UseMode.values()) {
            map.put(type.getValue(), type);
        }
    }
    
    private UseMode(int value) {
        this.value = value;
    }
    
    public static UseMode getByValue(int value) {
        return map.get(value);
    }
}
