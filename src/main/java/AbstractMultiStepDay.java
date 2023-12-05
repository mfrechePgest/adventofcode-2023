import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public abstract class AbstractMultiStepDay<STEP1, STEP2> extends AbstractDay {

    private final List<String> emojis = Arrays.asList(
            "🎅",
            "🤶",
            "🧝",
            "🦌",
            "❄️",
            "☃️",
            "⛄",
            "🎄",
            "🎁",
            "🔔");

    public AbstractMultiStepDay(String fileName) {
        super(fileName);
    }

    public abstract STEP1 resultStep1();

    public abstract STEP2 resultStep2();

    protected void fullRun() throws IOException {
        System.out.println("\uD83D\uDD53 Reading ...");
        Instant start = Instant.now();
        this.readFile();
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("  ↪  Time elapsed  = " + formatDuration(duration));

        stepAndPrintResult(1, this::resultStep1);
        stepAndPrintResult(2, this::resultStep2);
    }

    private void stepAndPrintResult(int stepIndex, Supplier<?> stepResultSupplier) {
        Instant start = Instant.now();
        Object result = stepResultSupplier.get();
        Instant end = Instant.now();
        System.out.println(
                randomEmoji() + " Result step " + stepIndex + " = " + formatResult(result));
        Duration duration = Duration.between(start, end);
        System.out.println("  ↪  Time elapsed  = " + formatDuration(duration));
    }

    private static String formatDuration(Duration duration) {
        String s = (duration.toHoursPart() > 0 ? duration.toHoursPart() + "h" : "")
                + (duration.toMinutesPart() > 0 ? duration.toMinutesPart() + "m" : "")
                + (duration.toSecondsPart() > 0 ? duration.toSecondsPart() + "s" : "")
                + (duration.toMillisPart() > 0 ? duration.toMillisPart() + "ms" : "");
        if (s.isEmpty()) {
            s = duration.toNanosPart() + " nanos";
        }
        return s;

    }

    private String formatResult(Object result) {
        return ConsoleColors.coloredString(result, ConsoleColors.GREEN);
    }

    private String randomEmoji() {
        return emojis.get(ThreadLocalRandom.current().nextInt(emojis.size()));
    }
}
