package ru.otus.hw01Maven;

import com.google.common.html.HtmlEscapers;

import java.util.Arrays;

public class HelloOtus {

    public static void main(String... args) {
        if (args != null && args.length > 0) {
            Arrays.stream(args).forEach(s -> System.out.println("Вы передали параметр: " + HtmlEscapers.htmlEscaper().escape(s)));
        } else {
            System.out.println("Вы не передали ни одного параметра, ну и ладно! ¯\\_(ツ)_/¯");
        }
    }

}
