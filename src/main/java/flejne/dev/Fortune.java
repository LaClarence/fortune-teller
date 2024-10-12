package flejne.dev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Fortune {

    private static final Logger LOGGER = Logger.getLogger(Fortune.class.getName());
    private static final Random RANDOM = new Random();
    private final List<String> fortunes = new ArrayList<>();

    public Fortune() throws JsonProcessingException {
        // Scan the file into the array of fortunes
        try(InputStream fortuneJson = Fortune.class.getClassLoader().getResourceAsStream("fortunes.json")) {
            if (fortuneJson == null) {
                LOGGER.severe(() -> "Resource not found!");
                System.exit(1);
            }
            String json = readInputStream(fortuneJson);
            ObjectMapper omap = new ObjectMapper();
            JsonNode root = omap.readTree(json);
            JsonNode data = root.get("data");
            Iterator<JsonNode> elements = data.elements();
            while (elements.hasNext()) {
                JsonNode quote = elements.next().get("quote");
                fortunes.add(quote.asText());
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private String readInputStream(InputStream is) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new UncheckedIOException(e);
        }
    }

    /// Pick a random fortune and print out the fortune s.l.o.w.l.y
    private void printRandomFortune() throws InterruptedException {
        String f = randomFortune();
        for (char c : f.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100);
        }
        System.out.println();
    }

    /// Pick a random number and
    /// use the random number to pick a random fortune
    /// @return a random fortune
    public String randomFortune() {
        int r = RANDOM.nextInt(fortunes.size());
        return fortunes.get(r);
    }

    /// @param args the command line arguments
    /// @throws com.fasterxml.jackson.core.JsonProcessingException
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        Fortune fortune = new Fortune();
        fortune.printRandomFortune();
    }
}