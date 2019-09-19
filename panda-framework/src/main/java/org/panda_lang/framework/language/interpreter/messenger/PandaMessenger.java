/*
 * Copyright (c) 2015-2019 Dzikoysk
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

package org.panda_lang.framework.language.interpreter.messenger;

import org.panda_lang.framework.PandaFramework;
import org.panda_lang.framework.PandaFrameworkException;
import org.panda_lang.framework.design.interpreter.messenger.Messenger;
import org.panda_lang.framework.design.interpreter.messenger.MessengerFormatter;
import org.panda_lang.framework.design.interpreter.messenger.MessengerLevel;
import org.panda_lang.framework.design.interpreter.messenger.MessengerMessage;
import org.panda_lang.framework.design.interpreter.messenger.MessengerMessageTranslator;
import org.panda_lang.framework.design.interpreter.messenger.MessengerOutputListener;
import org.panda_lang.utilities.commons.iterable.ReversedIterable;

import java.util.ArrayList;
import java.util.List;

public class PandaMessenger implements Messenger {

    private final MessengerFormatter formatter = new PandaMessengerFormatter(this);
    private final List<MessengerMessageTranslator> translators = new ArrayList<>();
    private MessengerOutputListener outputListener = new PandaMessengerOutputListener(PandaFramework.getLogger());

    @Override
    @SuppressWarnings("unchecked")
    public boolean send(Object message) {
        MessengerMessageTranslator translator = null;

        for (MessengerMessageTranslator messageTranslator : new ReversedIterable<>(translators)) {
            if (messageTranslator.getType().isAssignableFrom(message.getClass())) {
                if (translator != null && messageTranslator.getType().isAssignableFrom(translator.getType())) {
                    continue;
                }

                translator = messageTranslator;
            }
        }

        if (translator == null) {
            if (message instanceof Exception) {
                ((Exception) message).printStackTrace();
            }

            throw new PandaFrameworkException("Cannot translate a message - translator for " + message.getClass() + " not found");
        }

        return translator.handle(this, message);
    }

    @Override
    public void sendMessage(MessengerLevel level, String message) {
        MessengerMessage generatedMessage = new PandaMessengerMessage(level, message);
        sendMessage(generatedMessage);
    }

    @Override
    public void sendMessage(MessengerMessage message) {
        outputListener.onMessage(message);
    }

    @Override
    public void addMessageTranslator(MessengerMessageTranslator translator) {
        translators.add(translator);
    }

    @Override
    public void setOutputListener(MessengerOutputListener listener) {
        this.outputListener = listener;
    }

    @Override
    public MessengerFormatter getMessengerFormatter() {
        return formatter;
    }

}
