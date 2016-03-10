package io.koju.autopos.shared.struct.filter;

public class TextFilter implements FilterParamType<TextFilter> {

    public enum MatchType {
        EXACT,
        START,
        END,
        ANYWHERE
    }

    private String filterTerm;

    private MatchType matchType;

    private final static String WILD_CARD = "*";

    @Override
    public TextFilter setFieldsFromString(final String term) {
        final boolean wildcardInStart = term.startsWith(WILD_CARD);
        final boolean wildcardInEnd = term.endsWith(WILD_CARD);

        matchType = getMatchType(wildcardInStart, wildcardInEnd);
        filterTerm = term.replace(WILD_CARD, "");
        return this;
    }

    public String getFilterTerm() {
        return filterTerm;
    }

    public void setFilterTerm(String filterTerm) {
        this.filterTerm = filterTerm;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    private MatchType getMatchType(boolean wildcardInStart, boolean wildcardInEndd) {
        if (wildcardInStart && wildcardInEndd) {
            return MatchType.ANYWHERE;
        } else if (wildcardInStart) {
            return MatchType.END;
        } else if (wildcardInEndd) {
            return MatchType.START;
        } else {
            return MatchType.EXACT;
        }
    }
}
