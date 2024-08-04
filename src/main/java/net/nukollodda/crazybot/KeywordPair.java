package net.nukollodda.crazybot;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class KeywordPair {
    private final String subject;
    private final String verb;
    private final String directObject;
    private final String indirectObject;
    private boolean positive;
    private SentenceType type;

    private KeywordPair(String fullSentence, String subject, String verb, String directObject, String indirectObject) {
        int ind = Helpers.findIndexEitherOf("no", "not", "never");
        int verbInd = fullSentence.indexOf(verb);
        int intInd = Helpers.findIndexEitherOf("can", "could");
        int nIntInd = Helpers.findIndexEitherOf("cant", "can't", "couldnt", "couldn't");
        int subInd = Helpers.findIndexEitherOf("would", "should", "shall", "will");
        int nSubInd = Helpers.findIndexEitherOf("wouldn't", "wouldnt", "shouldn't", "shouldnt", "shan't", "shant", "won't", "wont");
        int condInd = Helpers.findIndexEitherOf("if", "what would", "wat would", "wut would", "what wud", "wat wud", "wut wud");
        if (intInd >= 0) {
            if (intInd < verbInd) {
                this.type = SentenceType.INTERROGATIVE;
                this.positive = true;
            } else if (nIntInd < verbInd) {
                this.type = SentenceType.INTERROGATIVE;
                this.positive = false;
            } else if (condInd < verbInd) {
                this.type = SentenceType.CONDITIONAL;
            } else if (subInd < verbInd) {
                this.type = SentenceType.SUBJUNCTIVE;
                this.positive = true;
            } else if (nSubInd < verbInd) {
                this.type = SentenceType.SUBJUNCTIVE;
                this.positive = false;
            }
        } else {
            this.type = SentenceType.INDICATIVE;
        }
        if (ind >= 0 && ind < verbInd) {
            this.positive = false;
        }
        this.subject = subject.toLowerCase();
        this.verb = verb.toLowerCase();
        this.directObject = directObject.toLowerCase();
        this.indirectObject = indirectObject.toLowerCase();
    }

    public String getSubject() {
        return subject;
    }

    public String getVerb() {
        return verb;
    }

    public String getDirectObject() {
        return directObject;
    }

    public String getIndirectObject() {
        return indirectObject;
    }

    public SentenceType getType() {
        return type;
    }

    public boolean isPositive() {
        return positive;
    }

    public boolean containsSubject(String... strs) {
        return Helpers.forLoopBoolean(subject::contains, strs);
    }

    public boolean containsVerb(String... strs) {
        return Helpers.forLoopBoolean(verb::contains, strs);
    }

    public boolean containsDirectObject(String... strs) {
        return directObject != null && Helpers.forLoopBoolean(directObject::contains, strs);
    }

    public boolean containsIndirectObject(String... strs) {
        return indirectObject != null && Helpers.forLoopBoolean(indirectObject::contains, strs);
    }

    @Override
    public String toString() {
        return "Subject: " + subject + "\nVerb: " + verb + "\nDirect Object: " + directObject + "\nIndirect Object: " + indirectObject;
    }

    @NotNull
    @Contract("_, _ -> new")
    public static KeywordPair pairMaker(@NotNull String sentence, int indOfVerb) {
        String sub = sentence.substring(0, indOfVerb);
        String dirObj = "";
        String indObj = "";
        String verb = sentence.substring(indOfVerb);
        int ind = sentence.indexOf(' ', indOfVerb);
        if (ind > indOfVerb) {
            String obj = sentence.substring(ind + 1);
            int prepInd = Helpers.findIndexEitherOf(obj, "into", "to", "from");
            int nextSpace = obj.indexOf(' ', prepInd);
            dirObj = prepInd >= 0 ? obj.substring(0, prepInd) : obj;
            indObj = prepInd >= 0 ? obj.substring(nextSpace) : "";
            verb = sentence.substring(indOfVerb, ind);
        }
        return new KeywordPair(sentence, sub, verb, dirObj, indObj);
    }

    public enum SentenceType {
        INDICATIVE, // normal
        INTERROGATIVE, // what would, can, could, how
        CONDITIONAL, // if
        OPTATIVE, // hope, wish
        POTENTIAL, // maybe, probably, possibly
        SUBJUNCTIVE // would
    }
}
