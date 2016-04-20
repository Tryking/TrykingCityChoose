package com.tryking.trykingcitychoose;

/**
 * Created by Tryking on 2016/4/20.
 */
public class CityItemBean {
    private String name;
    private String code;
    private boolean isSelected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
