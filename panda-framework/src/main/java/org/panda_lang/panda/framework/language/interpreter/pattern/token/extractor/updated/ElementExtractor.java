package org.panda_lang.panda.framework.language.interpreter.pattern.token.extractor.updated;

import org.panda_lang.panda.framework.language.interpreter.pattern.token.TokenDistributor;

interface ElementExtractor<T> {

    ExtractorResult extract(T element, TokenDistributor distributor);

}