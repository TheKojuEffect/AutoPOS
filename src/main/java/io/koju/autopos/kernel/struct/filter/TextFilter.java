package io.koju.autopos.kernel.struct.filter;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;

public class TextFilter extends FilterParam<String, StringPath> {

    private MatchType matchType;

    public TextFilter(String requestParam) {
        super(requestParam);
    }

    public enum MatchType {
        EXACT {
            @Override
            Predicate toQueryDslPredicate(StringPath path, String value) {
                return path.equalsIgnoreCase(value);
            }
        },
        START {
            @Override
            Predicate toQueryDslPredicate(StringPath path, String value) {
                return path.startsWithIgnoreCase(value);
            }
        },
        END {
            @Override
            Predicate toQueryDslPredicate(StringPath path, String value) {
                return path.endsWithIgnoreCase(value);
            }
        },
        ANYWHERE {
            @Override
            Predicate toQueryDslPredicate(StringPath path, String value) {
                return path.containsIgnoreCase(value);
            }
        };

        static MatchType getByWildcardPosition(boolean wildcardInStart, boolean wildcardInEnd) {
            if (wildcardInStart && wildcardInEnd) {
                return MatchType.ANYWHERE;
            } else if (wildcardInStart) {
                return MatchType.END;
            } else if (wildcardInEnd) {
                return MatchType.START;
            } else {
                return MatchType.EXACT;
            }
        }

        abstract Predicate toQueryDslPredicate(StringPath path, String value);
    }

    private final static String WILD_CARD = "*";

    public void populateWithRequestParam(String requestParam) {
        final boolean wildcardInStart = requestParam.startsWith(WILD_CARD);
        final boolean wildcardInEnd = requestParam.endsWith(WILD_CARD);

        matchType = MatchType.getByWildcardPosition(wildcardInStart, wildcardInEnd);
        setFilter(requestParam.replace(WILD_CARD, ""));
    }

    public Predicate toQueryDslPredicate(StringPath path) {
        return matchType.toQueryDslPredicate(path, filter);
    }

}
