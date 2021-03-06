/*
 * Copyright (c) 2021 dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package panda.interpreter.syntax.scope;

import panda.interpreter.architecture.dynamic.AbstractExecutableStatement;
import panda.interpreter.architecture.expression.Expression;
import panda.interpreter.architecture.expression.ExpressionUtils;
import panda.interpreter.logging.Logger;
import panda.interpreter.source.Localizable;
import panda.interpreter.runtime.ProcessStack;
import panda.utilities.text.Joiner;

import java.util.List;

final class LogStatement extends AbstractExecutableStatement {

    private final Logger logger;
    private final List<Expression> expressions;

    LogStatement(Localizable localizable, Logger logger, List<Expression> expressions) {
        super(localizable);
        this.logger = logger;
        this.expressions = expressions;
    }

    @Override
    public Object execute(ProcessStack stack, Object instance) throws Exception {
        Object[] values = ExpressionUtils.evaluate(stack, instance, expressions);
        logger.info(Joiner.on(", ").join(values).toString());
        return values;
    }

}
