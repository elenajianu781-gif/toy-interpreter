package interpreter;

import interpreter.view.Examples;
import interpreter.view.TextMenu;

public class Main {

    public static void main(String[] args) {
        TextMenu menu = new TextMenu(Examples.getPrograms());
        menu.show();
    }
}
