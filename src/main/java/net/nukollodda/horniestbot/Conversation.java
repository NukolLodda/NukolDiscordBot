package net.nukollodda.horniestbot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Conversation {
    private static final Random RANDOM = new Random();

    private final List<Message> messages;
    private final List<KeywordPair> pairs;
    private MessageChannelUnion channel;

    public Conversation() {
        messages = new ArrayList<>();
        pairs = new ArrayList<>();
    }

    public void setChannel(MessageChannelUnion channel) {
        this.channel = channel;
    }

    public void addResponse(Message message) {
        messages.add(message);
        String rawMsg = message.getContentRaw();
        int verbInd = Helpers.findIndexEitherOf(rawMsg,
                "shove", "shoving", "eat", "have sex", "has sex", "had sex", "fuck", "shit", "shat", "top", "bottom",
                "hump", "lick", "suck", "cum", "cvm", "burn", "hurt", "itch", "thought");
        if (verbInd >= 0) {
            KeywordPair pair = KeywordPair.pairMaker(rawMsg, verbInd);
            pairs.add(pair);
        }
        else pairs.clear();
    }

    private void sendMessage(String msg) {
        channel.sendMessage(msg).queue();
    }

    public void makeResponse() {
        Message message = messages.get(messages.size() - 1);
        KeywordPair pair = !pairs.isEmpty() ? pairs.get(pairs.size() - 1) : null;
        String rawMsg = message.getContentRaw();
        int nInd = Helpers.findIndexEitherOf(rawMsg, "no", "don", "wouldn");
        if (pair != null) {
            if (pair.containsVerb("fuck") || (pair.containsVerb("have", "had", "has") && pair.containsDirectObject("sex"))) {
                if (pair.containsDirectObject("troye", "troye sivan", "timothee", "timothee chalamet", "twink")) {
                    if (!pair.isPositive()) {
                        int n = RANDOM.nextInt(3);
                        switch (n) {
                            case 1 ->
                                    sendMessage("well i would totally bone the twink in all positions until his asshole cant take it any more");
                            case 2 -> {
                                sendMessage("hes a totally fuckable guy");
                                sendMessage("but fair ig");
                            }
                            default -> genericResponse();
                        }

                    } else if (pair.containsSubject("i")) {
                        int n = RANDOM.nextInt(3);
                        switch (n) {
                            case 1 -> sendMessage("omg we have so much in common");
                            case 2 -> {
                                sendMessage("me too bestie");
                                sendMessage("me too");
                            }
                            default -> genericResponse();
                        }
                    } else if (pair.containsSubject("you", "u") && pair.getType() == KeywordPair.SentenceType.SUBJUNCTIVE) {
                        int n = RANDOM.nextInt(8);
                        switch (n) {
                            case 1 -> {
                                sendMessage("yep");
                                sendMessage("i fuck twinks of all shapes and sizes");
                                sendMessage("if i can play with their dick like boing its all good");
                            }
                            case 2 -> sendMessage("def fr");
                            case 3 -> {
                                sendMessage("yup");
                                sendMessage("even as an orgy");
                            }
                            case 4 -> sendMessage("100%");
                            case 5 -> sendMessage("fuck yes");
                            case 6 -> {
                                sendMessage("yup");
                                sendMessage("and id rail him until he gets a hemorrhage");
                            }
                            case 7 -> {
                                sendMessage("certainly");
                                sendMessage("id even swallow his dick like a hot dog eating contest");
                            }
                            default -> sendMessage("probably");
                        }
                    } else if (pair.getType() == KeywordPair.SentenceType.CONDITIONAL) {
                        sendMessage("yep");
                        sendMessage("they clearly know what is fun");
                    } else {
                        sendMessage("oh hell yeah that sounds fun");
                    }
                }
            }
            if (Helpers.containsOne(rawMsg, "opinion on", "opinions on", "thoughts on")) {
                int oInd = Helpers.findIndexEitherOf(rawMsg, "opinion on", "opinions on");
                if (oInd >= 0) {
                    if (rawMsg.indexOf("twink") > oInd) {
                        int n = RANDOM.nextInt(5);
                        switch (n) {
                            case 1 -> sendMessage("hard smash");
                            case 2 -> {
                                sendMessage("smash");
                                sendMessage("but he better let me top him");
                            }
                            case 3 -> sendMessage("hot");
                            case 4 -> {
                                sendMessage("oral, anal, idc");
                                sendMessage("if he can provide good sex its all good");
                            }
                            default -> sendMessage("very fuckable");
                        }
                        sendMessage("smash");
                    } else if (rawMsg.indexOf("twunk") > oInd) {
                        int n = RANDOM.nextInt(3);
                        switch (n) {
                            case 1 -> {
                                sendMessage("ummm");
                                sendMessage("it depends ig");
                            }
                            case 2 -> {
                                sendMessage("uhhh id say");
                                sendMessage("fuckable");
                            }
                            default -> sendMessage("is he a better top or a better bottom");
                        }
                    } else if (rawMsg.indexOf("bear") > oInd) {
                        int n = RANDOM.nextInt(6);
                        switch (n) {
                            case 1 -> {
                                sendMessage("as in the gay man?");
                                sendMessage("if his hairs can scratch my itchy spots then im taken");
                            }
                            case 2 -> sendMessage("if hes a bear then my dick can be the salmon");
                            case 3 -> {
                                sendMessage("if were talking abt the animal");
                                sendMessage("then it doesnt concern me");
                            }
                            case 4 -> {
                                sendMessage("id have to be the bottom if hes a bear");
                                sendMessage("cant imagine penetrating his bushy ass hair forest");
                            }
                            case 5 -> {
                                sendMessage("well");
                                sendMessage("idk");
                            }
                            default -> {
                                sendMessage("possible smash");
                                sendMessage("assuming were talking about the man");
                            }
                        }
                    } else if (rawMsg.indexOf("woman") > oInd) {
                        sendMessage("i personally want a woman to top me");
                    } else if (rawMsg.indexOf("eurovision") > oInd) {
                        sendMessage("all the eurovision twinks are so hot");
                        sendMessage("i would absolutely get in an orgy with all the eurovision twinks");
                        sendMessage("and then women too ig, i wouldnt mind them");
                    }
                }
            }
            if (Helpers.containsOne(rawMsg, "arse", "ass")) {
                int aInd = Helpers.findIndexEitherOf(rawMsg, "arse", "ass");
                int cInd = rawMsg.indexOf("cheese");
                if (aInd > cInd && rawMsg.contains("cheese")) {
                    if (nInd < cInd && nInd >= 0) {
                        int n = RANDOM.nextInt(5);
                        switch (n) {
                            case 1 -> {
                                sendMessage("i mean cheese is pretty sticky ngl");
                                sendMessage("its a difficult task to wash ur ass after the cheese thing");
                            }
                            case 2 -> sendMessage("i mean tis very sticky");
                            case 3 -> {
                                sendMessage("tried before");
                                sendMessage("the twunk topping me got cheese inside his dick hole");
                            }
                            case 4 -> {
                                sendMessage("well");
                                sendMessage("cheese can get very moldy");
                                sendMessage("u dont want an ass infection");
                            }
                            default -> genericResponse();
                        }
                    } else if (Helpers.containsOne(rawMsg, "can't", "cant") && Helpers.findIndexEitherOf(rawMsg, "can't", "cant") < cInd) {
                        genericResponse();
                    } else if (rawMsg.indexOf("shov") < rawMsg.indexOf("cheese") && rawMsg.contains("shov")) {
                        int n = RANDOM.nextInt(9);
                        switch (n) {
                            case 1 -> {
                                sendMessage("i personally prefer that moldy sardinian cheese up my ass");
                                sendMessage("the maggots make me jizz");
                            }
                            case 2 -> sendMessage("dont shove parmesan up ur ass");
                            case 3 -> {
                                sendMessage("i mean if its blue cheese");
                                sendMessage("u gotta be careful");
                            }
                            case 4 -> sendMessage("i prefer sucking camembert cheese from a twinks dick");
                            case 5 -> {
                                sendMessage("if bottoming");
                                sendMessage("use the inside of soft brie");
                            }
                            case 6 -> sendMessage("cheese up the dick hole aint so bad either");
                            case 7 -> {
                                sendMessage("feta cheese is too grainy");
                                sendMessage("dont use feta");
                            }
                            case 8 -> {
                                sendMessage("if can");
                                sendMessage("make sure the mold isnt in the cheese");
                            }
                            default -> sendMessage("well the moldy kind is kinky");
                        }
                    }
                }
            }
        }
    }

    private void genericResponse() {
        int n = RANDOM.nextInt(8);
        String response = switch (n) {
            case 1 -> "understandable";
            case 2 -> "fair enough";
            case 3 -> "make sense";
            case 4 -> "yea";
            case 5 -> "ok have fun";
            case 6 -> "thats fair";
            case 7 -> "ok do as u will";
            default -> "tru";
        };
        sendMessage(response);
    }
}
