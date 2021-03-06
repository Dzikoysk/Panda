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

package panda.interpreter.syntax.type;

import org.jetbrains.annotations.Nullable;
import panda.interpreter.architecture.module.Imports;
import panda.interpreter.architecture.type.Reference;
import panda.interpreter.architecture.type.signature.GenericSignature;
import panda.interpreter.architecture.type.signature.Relation;
import panda.interpreter.architecture.type.signature.Signature;
import panda.interpreter.architecture.type.signature.TypedSignature;
import panda.interpreter.parser.Context;
import panda.interpreter.parser.Contextual;
import panda.interpreter.parser.PandaParserFailure;
import panda.interpreter.parser.Parser;
import panda.interpreter.token.PandaSourceStream;
import panda.interpreter.token.Snippet;
import panda.interpreter.token.SourceStream;
import panda.interpreter.resource.syntax.separator.Separators;
import panda.interpreter.syntax.PandaSourceReader;
import panda.std.Option;
import panda.std.Pair;
import panda.std.Result;

import java.util.ArrayList;
import java.util.List;

public final class SignatureParser implements Parser {

    @Override
    public String name() {
        return "signature";
    }

    public Signature parse(Contextual<?> contextual, SignatureSource signatureSource, boolean root, @Nullable Signature signatureContextValue) {
        Context<?> context = contextual.toContext();
        Imports imports = context.getImports();

        Option<Signature> signatureContext = Option.of(signatureContextValue);
        String name = signatureSource.getName().getValue();

        Result<Reference, Signature> type = imports.forType(name)
                .map(importedType -> {
                    //noinspection Convert2MethodRef
                    return Result.<Reference, Signature> ok(importedType);
                })
                .orElseGet(() -> {
                    if (!name.equals(name.toUpperCase())) {
                        throw new PandaParserFailure(context, signatureSource.getName(), "Abstract generic name has to be declared uppercase");
                    }

                    Signature signature = signatureContext
                            .flatMap(ctx -> ctx.findGeneric(name))
                            .map(Pair::getSecond)
                            .orElseGet(() -> new GenericSignature(context.getTypeLoader(), signatureContextValue, name, null, new Signature[0], Relation.DIRECT, signatureSource.getName()));

                    return Result.error(signature);
                });

        Signature subParent = (!root && signatureContext.isEmpty() && type.isOk())
                ? type.get().fetchType().getSignature()
                : signatureContextValue;

        Signature[] generics = signatureSource.getGenerics().stream()
                .map(genericSignature -> parse(context, genericSignature, root, signatureContextValue))
                .toArray(Signature[]::new);

        return type
                .map(reference -> (Signature) new TypedSignature(subParent, reference, generics, Relation.DIRECT, signatureSource.getName()))
                .orElseGet(genericSignature -> genericSignature);
    }

    public List<SignatureSource> readSignatures(Snippet source) {
        Snippet[] sources = source.split(Separators.COMMA);
        List<SignatureSource> signatures = new ArrayList<>((source.size() / 3) + 1);

        for (Snippet signatureSource : sources) {
            SourceStream signatureSourceStream = new PandaSourceStream(signatureSource);
            PandaSourceReader signatureReader = new PandaSourceReader(signatureSourceStream);

            signatures.add(signatureReader.readSignature().orThrow(() -> {
                throw new PandaParserFailure(source, signatureSource, "Invalid signature");
            }));
        }

        return signatures;
    }



}
