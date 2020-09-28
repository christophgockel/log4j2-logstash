package se.fnord.logtags.log4j2_logstash.taggedmessage;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.fail;

public class TestTags {
    @Test
    public void testForEach() {
        Tags.empty()
                .forEach(null, TagsUtil.wrapConsumer((k, v, s) -> fail("Empty tags should not invoke function")));

        TagsUtil.assertForEach(Tags.of("a", "b")
                .add("c", "d")
                .add( "e", "f", "g", "h"),
                TagsUtil.tag("a", "b"), TagsUtil.tag("c", "d"), TagsUtil.tag("e", "f"), TagsUtil.tag("g", "h"));
    }

    @Test
    public void testOf() {
        TagsUtil.assertForEach(Tags.of("a", 1), TagsUtil.tag("a", 1L));
        TagsUtil.assertForEach(Tags.of("a", true), TagsUtil.tag("a", true));
        TagsUtil.assertForEach(Tags.of("a", 1.0), TagsUtil.tag("a", 1.0));
        TagsUtil.assertForEach(Tags.of("a", "b"), TagsUtil.tag("a", "b"));
        TagsUtil.assertForEach(Tags.of("a", "b", "c", "d"), TagsUtil.tag("a", "b"), TagsUtil.tag("c", "d"));
        TagsUtil.assertForEach(Tags.of("a", "b", "c", "d", "e", "f"), TagsUtil.tag("a", "b"), TagsUtil.tag("c", "d"), TagsUtil
            .tag("e", "f"));
        TagsUtil.assertForEach(Tags.of("a", "b", "c", "d", "e", "f", "g", "h"), TagsUtil.tag("a", "b"), TagsUtil
            .tag("c", "d"), TagsUtil.tag("e", "f"), TagsUtil.tag("g", "h"));
    }

    @Test
    public void testAdd() {
        TagsUtil.assertForEach(Tags.empty().add("a", 1), TagsUtil.tag("a", 1L));
        TagsUtil.assertForEach(Tags.empty().add("a", true), TagsUtil.tag("a", true));
        TagsUtil.assertForEach(Tags.empty().add("a", 1.0), TagsUtil.tag("a", 1.0));
        TagsUtil.assertForEach(Tags.empty().add("a", "b"), TagsUtil.tag("a", "b"));
        TagsUtil.assertForEach(Tags.empty().add("a", "b"), TagsUtil.tag("a", "b"));
        TagsUtil.assertForEach(Tags.empty().add("a", "b", "c", "d"), TagsUtil.tag("a", "b"), TagsUtil.tag("c", "d"));
        TagsUtil
            .assertForEach(Tags.empty().add("a", "b", "c", "d", "e", "f"), TagsUtil.tag("a", "b"), TagsUtil.tag("c", "d"), TagsUtil
                .tag("e", "f"));
        TagsUtil
            .assertForEach(Tags.empty().add("a", "b", "c", "d", "e", "f", "g", "h"), TagsUtil.tag("a", "b"), TagsUtil
                .tag("c", "d"), TagsUtil.tag("e", "f"), TagsUtil.tag("g", "h"));
    }

    @Test
    public void testAddComposite() {
        TagsUtil.assertForEach(Tags.of("a", 1, "b", 2).add(Tags.of("c", 3, "d", 4)),
            TagsUtil.tag("a", 1L), TagsUtil.tag("b", 2L), TagsUtil.tag("c", 3L), TagsUtil.tag("d", 4L));
    }

    @Test
    public void testNormalisation() {
        TagsUtil.assertForEach(Tags.empty().add("a", singletonList(32)), TagsUtil.tag("a", "[32]"));
        TagsUtil.assertForEach(Tags.of("a", singletonList(32)), TagsUtil.tag("a", "[32]"));

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("a", singletonList(32));

        TagsUtil.assertForEach(Tags.of(m), TagsUtil.tag("a", "[32]"));
    }

    @Test
    public void testTagsFromMap() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("a", 1);
        m.put("b", "c");

        TagsUtil.assertForEach(Tags.empty().add(m), TagsUtil.tag("a", 1L), TagsUtil.tag("b", "c"));
        TagsUtil.assertForEach(Tags.of(m), TagsUtil.tag("a", 1L), TagsUtil.tag("b", "c"));
    }
}
