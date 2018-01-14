import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleAddressParser {

    private static final List<Pattern> patterns = new ArrayList<Pattern>() {{
        add(Pattern.compile("(?<name>\\D+) (?<number>\\d+)")); //Winterallee 3, Musterstrasse 45
        add(Pattern.compile("(?<number>\\d+) (?<name>\\D+)")); //4, rue de la revolution
        add(Pattern.compile("(?<name>\\D+ \\d+) (?<number>\\D+ \\d+)")); // Calle 39 No 1540
        add(Pattern.compile("(?<name>\\D+) (?<number>\\d+\\D+)")); // Auf der Vogelwiese 23 b
        add(Pattern.compile("(?<name>\\d+ .*) (?<number>\\d+)")); //29 Avenue Street 30
    }};

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Illegal Argument Usage : java -jar <jar file> '<address>'");
            System.exit(1);
        }

        Optional<String> result = new SimpleAddressParser().parse(args[0]);
        if (result.isPresent())
            System.out.println(result.get());
        else
            System.out.println("Not able to parse address");
    }

    private String preProcess(String address) {
        return address.replaceAll(",", " ").replaceAll("\\s+", " ").trim();
    }

    public Optional<String> parse(String address) {
        address = preProcess(address);
        Optional<String> result = Optional.empty();

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(address);
            if (matcher.matches()) {
                result = Optional.of(String.format("{\"%s\",\"%s\"}", matcher.group("name"), matcher.group("number")));
                break;
            }
        }
        return result;
    }
}
