package io.shiftleft.overflowdb.structure;

import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OverflowTest {

//  @Test
  // only run manually since the timings vary depending on the environment
  // without overflow we can hold ~11k nodes in 256m memory
  // with overflow that number should be tremendously larger, because only the reference wrappers are helt in memory
  // it'll be much slower due to the serialization to disk, but should not crash
  // important: use all the following vm opts:  `-Xms256m -Xmx256m`
  public void shouldAllowGraphsLargerThanMemory() {
    OdbConfig config = OdbConfig.withoutOverflow();
//    OdbConfig config = OdbConfig.withDefaults();
    int nodeCount = 100_000;
    int currentInt = 0;
    try(OdbGraph graph = newGraph(config)) {
      for (long i = 0; i < nodeCount; i++) {
        if (i % 1000 == 0) {
          System.out.println(i + " nodes created");
        }
        Vertex v = graph.addVertex(OdbTestNode.LABEL);
        List<Integer> ints = new ArrayList<>();
        for (int j = 0; j < 1000; j++) {
          ints.add(currentInt++);
        }
        v.property(OdbTestNode.INT_LIST_PROPERTY, ints);
      }
    }
  }

  private OdbGraph newGraph(OdbConfig config) {
    return OdbGraph.open(
        config,
        Arrays.asList(OdbTestNode.factory),
        Arrays.asList()
    );
  }

}
