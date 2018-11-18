package org.panda_lang.panda.framework.language.interpreter.parser.bootstrap.interceptor;

import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.component.UniversalComponents;
import org.panda_lang.panda.framework.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.panda.framework.language.interpreter.parser.bootstrap.PandaParserBootstrap;
import org.panda_lang.panda.framework.language.interpreter.parser.bootstrap.UnifiedBootstrapParser;
import org.panda_lang.panda.framework.language.interpreter.parser.bootstrap.layer.InterceptorData;
import org.panda_lang.panda.framework.language.interpreter.pattern.token.TokenPattern;
import org.panda_lang.panda.framework.language.interpreter.pattern.token.TokenPatternMapping;
import org.panda_lang.panda.framework.language.interpreter.pattern.token.extractor.TokenExtractorResult;

public class TokenPatternInterceptor implements BootstrapInterceptor {

    private TokenPattern pattern;

    @Override
    public void initialize(PandaParserBootstrap bootstrap) {
        this.pattern = TokenPattern.builder()
                .compile(bootstrap.getPattern())
                .build();
    }

    @Override
    public InterceptorData handle(UnifiedBootstrapParser parser, ParserData data) {
        InterceptorData interceptorData = new InterceptorData();

        if (pattern != null) {
            TokenExtractorResult result = pattern.extract(data.getComponent(UniversalComponents.SOURCE_STREAM));

            if (!result.isMatched()) {
                data.getComponent(UniversalComponents.SOURCE_STREAM).updateCachedSource();
                throw new PandaParserFailure("Interceptor could not match token pattern, error: " + result.getErrorMessage(), data);
            }

            interceptorData.addElement(new TokenPatternMapping(result));
        }

        return interceptorData;
    }

}
