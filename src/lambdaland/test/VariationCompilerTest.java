package lambdaland.test;

import lambdaland.VariationCompiler;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rikkigibson on 4/11/16.
 */
public class VariationCompilerTest {
    @Test
    public void compile() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printy = new PrintStream(baos);

        List<String> inputs = Files.readAllLines(Paths.get("src/lambdaland/test/testdata/test1.in.vjava"));
        StringBuilder builder = new StringBuilder();
        for (String line : inputs) {
            builder.append(line);
            builder.append("\n");
        }
        String source = builder.toString();
        VariationCompiler.compile(printy, source, new HashMap<>());

        List<String> outputs = Files.readAllLines(Paths.get("src/lambdaland/test/testdata/test1.out.vjava"));
        builder = new StringBuilder();
        for (String line : outputs) {
            builder.append(line);
            builder.append("\n");
        }
        String expected = builder.toString();
        String actual = baos.toString();
        assertEquals(expected, actual);
    }

}