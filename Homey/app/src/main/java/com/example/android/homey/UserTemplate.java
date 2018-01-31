package com.example.android.homey;

/**
 * Created by user on 28-Jan-18.
 */

public class UserTemplate {
    private boolean gen_info_fill;

    public UserTemplate() {
    }

    public UserTemplate(boolean gen_info_fill) {
        this.gen_info_fill = gen_info_fill;
    }

    public boolean getGen_info_fill() {
        return gen_info_fill;
    }

    public void setGen_info_fill(boolean gen_info_fill) {
        this.gen_info_fill = gen_info_fill;
    }

}
