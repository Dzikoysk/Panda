package org.panda_lang.panda.interpreter.parser.implementation.general.expression;

import org.junit.jupiter.api.Assertions;
import org.panda_lang.panda.framework.design.interpreter.token.Tokens;
import org.panda_lang.panda.framework.design.interpreter.token.stream.SourceStream;
import org.panda_lang.panda.framework.language.interpreter.lexer.PandaLexer;
import org.panda_lang.panda.framework.language.interpreter.pattern.PandaTokenPattern;
import org.panda_lang.panda.framework.language.interpreter.pattern.lexical.elements.LexicalPatternElement;
import org.panda_lang.panda.framework.language.interpreter.pattern.token.TokenPattern;
import org.panda_lang.panda.framework.language.interpreter.pattern.token.extractor.ExtractorResult;
import org.panda_lang.panda.framework.language.interpreter.source.PandaSource;
import org.panda_lang.panda.framework.language.interpreter.token.stream.PandaSourceStream;
import org.panda_lang.panda.framework.language.resource.PandaSyntax;

class PandaPatternTester {

    protected static void test(String patternContent, String source, Wildcard... expected) {
        ExtractorResult result = build(patternContent, source);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isMatched());
        Assertions.assertNotNull(result.getWildcards());
        Assertions.assertEquals(expected.length, result.getWildcards().size());

        for (Wildcard wildcard : expected) {
            Assertions.assertEquals(wildcard.expected, result.getWildcards().get(wildcard.name).asString());
        }
    }

    protected static ExtractorResult build(String patternContent, String source) {
        System.out.println("");
        System.out.println("src: " + source);

        TokenPattern pattern = PandaTokenPattern.builder()
                .compile(patternContent)
                .build();

        LexicalPatternElement content = pattern.getPatternContent();
        Assertions.assertNotNull(content);

        Tokens tokenizedSource = new PandaLexer(PandaSyntax.getInstance(), new PandaSource("PandaPatternTester", source)).convert();
        SourceStream stream = new PandaSourceStream(tokenizedSource);
        ExtractorResult result = pattern.extract(stream);

        if (result.hasErrorMessage()) {
            System.out.println("Error message: " + result.getErrorMessage());
        }

        if (result.isMatched()) {
            System.out.println(result.getWildcards());

            if (stream.hasUnreadSource()) {
                System.out.println("Unread source: " + stream.toTokenizedSource().asString());
            }
        }

        return result;
    }

    static class Wildcard {

        private final String name;
        private final String expected;

        private Wildcard(String name, String expected) {
            this.name = name;
            this.expected = expected;
        }

        public static Wildcard of(String name, String expected) {
            return new Wildcard(name, expected);
        }

    }

}
