package se.iths.webbshop.views;

import se.iths.webbshop.utilities.Entry;

public class ViewEntry {
    int key;
    int value;

    public ViewEntry() {
    }

    public ViewEntry(Entry entry) {
        this.key = entry.getKey().getId();
        this.value = entry.getValue();
    }

    public ViewEntry(Integer key, int value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ViewEntry getTest(int x) {
        this.value = x;
        return this;
    }
}
