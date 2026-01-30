package dippy.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest {
    @Test
    public void indent_letters_indented() {
        String input = "a\nb\nc";
        String expected = "    a\n    b\n    c\n";
        assertEquals(expected, Ui.indent(input));
    }

    @Test
    public void wrap_paragraph_wrapped() {
        String input = """
            Peter Piper sells a pack of pickled peppers.
            She sells seashells by the seashore.
            Abrakadabra.
            """;
        String expected = """
            ______________________________________________________________________
            Peter Piper sells a pack of pickled peppers.
            She sells seashells by the seashore.
            Abrakadabra.
            ______________________________________________________________________
            """;
        assertEquals(expected, Ui.wrap(input));
    }
}
