package se.iths.webbshop.controllers.utilities;

public class ViewEntry {
    private Integer key;
    private Integer value;

    public ViewEntry() {
    }

    public ViewEntry(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }


    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
