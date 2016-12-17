package com.kapilkoju.autopos.kernel.json;

public class Views {

    public interface Id {
    }

    public interface Versioned extends Id {
    }

    public interface DateTimeAudited extends Versioned {
    }

    public interface Summary extends DateTimeAudited {
    }

}
