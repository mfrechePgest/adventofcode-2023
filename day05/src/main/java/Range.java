import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record Range (long begin, long end) {
    
    public boolean overlap(Range range2) {
        return begin <= range2.end && range2.begin <= end;
    }

    public Stream<Range> splitOverlaps(Range range2) {
        List<Range> result = new ArrayList<>();
        Range r = new Range(begin, range2.begin > begin ? range2.begin - 1 : Math.min(range2.end, end));
        assert r.begin <= r.end;
        result.add(r);
        if ( r.end != end ) {
            r = new Range(r.end + 1, Math.max(r.end + 1, Math.min(range2.end, end)));
            assert r.begin <= r.end;
            result.add(r);
        }
        if ( r.end != end ) {
            r = new Range(r.end + 1, end);
            assert r.begin <= r.end;
            result.add(r);
        }
        return result.stream();
    }
    
}