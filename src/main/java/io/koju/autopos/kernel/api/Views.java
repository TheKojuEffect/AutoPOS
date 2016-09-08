package io.koju.autopos.kernel.api;

public class Views {

    public interface Id {
    }

    public interface Versioned extends Id {
    }

    public interface DateTimeAudited extends Versioned {
    }

}
