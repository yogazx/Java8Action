package ch3;

public class Letter {

    public static String addHeader(String text) {
        return "aaa" + text;
    }

    public static String addFooter(String text) {
        return text + "bbb";
    }

    public static String checkSpelling(String text) {
        return text.replaceAll("labda", "lambda");
    }
}
