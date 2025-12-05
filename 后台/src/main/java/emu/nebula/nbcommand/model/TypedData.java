package emu.nebula.nbcommand.model;

/**
 * 带类型的通用数据模型
 */
public record TypedData(String id, String name, String type) {

    @Override
    public String toString() {
        return id + " - " + name;
    }
}