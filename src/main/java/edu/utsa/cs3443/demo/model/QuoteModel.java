package edu.utsa.cs3443.demo.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Stores motivational quotes and returns a random one.
 */
public class QuoteModel {

    private final ArrayList<String> quotes = new ArrayList<>();
    private final Random random = new Random();

    public QuoteModel() {
        loadQuotes();
    }

    private void loadQuotes() {

        // Original quotes
        quotes.add("Small steps every day lead to big results.");
        quotes.add("Your future is created by what you do today.");
        quotes.add("Stay consistent, stay patient, stay focused.");
        quotes.add("You don’t need to be perfect, just better than yesterday.");
        quotes.add("Discipline is choosing what you want most over what you want now.");

        // New quotes
        quotes.add("Success is built on the days you don’t feel like working but do it anyway.");
        quotes.add("Your only limit is the one you place on yourself.");
        quotes.add("Progress is progress, no matter how small.");
        quotes.add("Dreams don’t work unless you do.");
        quotes.add("Every accomplishment starts with the decision to try.");
        quotes.add("Focus on the step in front of you, not the whole staircase.");
        quotes.add("You are capable of more than you think.");
        quotes.add("Don’t wait for motivation — create your own momentum.");
        quotes.add("The expert in anything was once a beginner.");
        quotes.add("Your habits today shape your tomorrow.");
        quotes.add("A little progress each day adds up to big change.");
        quotes.add("If it matters to you, you’ll find a way.");
        quotes.add("Success is the sum of small efforts repeated daily.");
        quotes.add("Believe in the power of showing up.");
        quotes.add("You don’t have to be extreme, just consistent.");
        quotes.add("Your goals don’t care how you feel — keep going.");
        quotes.add("One hour of focus can change your entire day.");
        quotes.add("You’re building a future you’ll be proud of.");
        quotes.add("Start where you are. Use what you have. Do what you can.");
    }

    public String getRandomQuote() {
        if (quotes.isEmpty()) return "No quotes available.";
        return quotes.get(random.nextInt(quotes.size()));
    }
}
