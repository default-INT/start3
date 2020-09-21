package by.epam.inner;

import by.epam.inner.beans.Trial;
import by.epam.inner.data.TrialFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Runner {
    private final static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        final ToIntFunction< Trial> summator = trial -> trial.getMark1() + trial.getMark2();

        Type type = new TypeToken<List<JsonObject>>(){}.getType();

        try (Reader reader = new FileReader(args[0])) {

            List<JsonObject> jsonObjects = new Gson().fromJson(reader, type);

            // 1. Create the List implementation from a json file. Use Gson library to parse it and to
            // identify trial entities.
            List<Trial> trials = jsonObjects.stream()
                    .map(TrialFactory::getTrial)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            // 2. Print the collection content (one element per line).
            logger.info("Print the collection content ");
            trials.forEach(logger::info);
            // 3. Print the number of passed trials.
            logger.info("Count passed trials " + trials.stream().filter(Trial::isResult).count());
            // 4. Sort the collection by the sum of first and second marks.
            logger.info("Sort the collection by the sum of first and second marks.");
            trials.sort(Comparator.comparingInt(summator));
            trials.forEach(logger::info);
            logger.info("=====================");
            // 5. Print sums of first and second marks from the collection (one sum per line).
            logger.info("Sums of first and second marks from the collection.");
            trials.stream().mapToInt(summator)
                .forEach(logger::info);
            logger.info("=====================");
            // 6. Create a new collection from unpassed trials, clear all marks and print this collection.
            // Check whether all trials are failed (the result type is boolean).
            logger.info("Unpassed trials");
            List<Trial> unpassedTrials = trials.stream().filter(t -> !t.isResult())
                    .map(Trial::copy)
                    .peek(Trial::clearMarks)
                    .peek(logger::info)
                    .collect(Collectors.toList());
            logger.info("=====================");
            // 7. Create a numeric array from sums of first and second marks of sorted collection (see item 4)
            // and print it in the format: sum[0], sum[1], … , sum[sum.length - 1].
            int[] sums = trials.stream().mapToInt(summator).toArray();
            logger.info(Arrays.stream(sums)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(", ")));

        } catch (IOException e) {
            logger.error("File not found " + e);
        } catch (Exception e) {
            logger.info("Something went wrong ... ");
            logger.fatal(e);
        }
    }
}
