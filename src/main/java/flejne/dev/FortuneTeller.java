package flejne.dev;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

public class FortuneTeller {

    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    private record Quote(String quote, String author) {
        private Quote {
            author = Objects.requireNonNullElse(author, "");
        }
    }

    private record Fortunes(List<Quote> fortunes) {}

    private final List<Quote> fortunes;

    public FortuneTeller() {
        try (InputStream fortuneJson = FortuneTeller.class.getClassLoader().getResourceAsStream("fortunes.json")) {
            if (fortuneJson == null) {
                throw new IllegalStateException("Resource fortunes.json not found!");
            }
            fortunes = new ObjectMapper().readValue(fortuneJson, Fortunes.class).fortunes();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /// Pick a random fortune and print it out s.l.o.w.l.y, with attribution when known
    private void printRandomFortune() throws InterruptedException {
        Quote quote = pickRandomQuote();
        for (char c : quote.quote().toCharArray()) {
            IO.print(c);
            Thread.sleep(100);
        }
        IO.println(quote.author().isEmpty() ? "\n" :"\n\t-- " + quote.author());
    }

    private Quote pickRandomQuote() {
        return fortunes.get(RANDOM.nextInt(fortunes.size()));
    }

    public static void main(String[] args) throws InterruptedException {
        new FortuneTeller().printRandomFortune();
    }
}