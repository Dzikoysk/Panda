package org.panda_lang.panda.core.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ParserCenter {

    private final List<Pattern> patterns = new ArrayList<>();

    public void registerParser(ParserLayout parserLayout) {
        registerPatterns(parserLayout.getPatterns());
    }

    public void registerPatterns(Collection<Pattern> patternCollection) {
        patterns.addAll(patternCollection);
        Collections.sort(patterns);
    }

    public Parser getParser(Atom atom, String s) {
        final String defaultPattern = atom.getPatternExtractor().extract(s, PatternExtractor.DEFAULT);
        for (Pattern pattern : getPatterns()) {
            String matchedPattern = pattern.getCharset() != null ? atom.getPatternExtractor().extract(s, pattern.getCharset()) : defaultPattern;
            if (pattern.match(matchedPattern)) {
                atom.setVariant(pattern);
                return pattern.getParser();
            }
        }
        return null;
    }

    public List<Pattern> getPatterns() {
        return patterns;
    }

}
