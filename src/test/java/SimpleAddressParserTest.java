import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SimpleAddressParserTest {


    private final String expectedString = "{\"%s\",\"%s\"}";
    private SimpleAddressParser parser = new SimpleAddressParser();

    @Test
    public void testSimpleAddress() {
        List<String> addresses = Arrays.asList(
                "Winterallee 3;Winterallee;3",
                "Musterstrasse 45;Musterstrasse;45",
                "Blaufeldweg 123B;Blaufeldweg;123B");
        for (String address : addresses) {
            String[] data = address.split(";");
            Optional<String> result = parser.parse(data[0]);
            Assert.assertTrue(result.isPresent());
            Assert.assertTrue(result.get().equals(String.format(expectedString, data[1], data[2])));
        }
    }

    @Test
    public void testMediumComplexAddress() {
        List<String> addresses = Arrays.asList(
                "Am Bächle 23;Am Bächle;23",
                "Auf der Vogelwiese 23 b;Auf der Vogelwiese;23 b");
        for (String address : addresses) {
            String[] data = address.split(";");
            Optional<String> result = parser.parse(data[0]);
            Assert.assertTrue(result.isPresent());
            Assert.assertTrue(result.get().equals(String.format(expectedString, data[1], data[2])));
        }
    }

    @Test
    public void testComplexAddress() {
        List<String> addresses = Arrays.asList(
                "4, rue de la revolution;rue de la revolution;4",
                "200 Broadway Av;Broadway Av;200",
                "Calle Aduana, 29;Calle Aduana;29",
                "Calle 39 No 1540;Calle 39;No 1540");
        for (String address : addresses) {
            String[] data = address.split(";");
            Optional<String> result = parser.parse(data[0]);
            Assert.assertTrue(result.isPresent());
            Assert.assertTrue(result.get().equals(String.format(expectedString, data[1], data[2])));
        }
    }

    @Test
    public void testExtras() {
        List<String> addresses = Arrays.asList(
                "29 Avenue Road 45;29 Avenue Road;45",
                "200 Broadway Av,92;200 Broadway Av;92");
        for (String address : addresses) {
            String[] data = address.split(";");
            Optional<String> result = parser.parse(data[0]);
            Assert.assertTrue(result.isPresent());
            Assert.assertTrue(result.get().equals(String.format(expectedString, data[1], data[2])));
        }
    }



}