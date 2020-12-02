package org.panda_lang.language.architecture.type.signature;

import org.panda_lang.language.interpreter.token.Snippet;
import org.panda_lang.utilities.commons.function.Option;
import org.panda_lang.utilities.commons.function.Result;

public interface Signature {

    Result<? extends Signature, String> merge(Signature inheritor);

    boolean isAssignableFrom(Signature inheritor);

    boolean isGeneric();

    GenericSignature toGeneric();

    boolean isTyped();

    TypedSignature toTyped();

    default Option<GenericSignature> findGeneric(GenericSignature identifier) {
        return findGeneric(identifier.getLocalIdentifier());
    }

    Option<GenericSignature> findGeneric(String identifier);

    Snippet getSource();

    Signature[] getGenerics();

    Relation getRelation();

    Object getSubject();

    Option<Signature> getParent();

}