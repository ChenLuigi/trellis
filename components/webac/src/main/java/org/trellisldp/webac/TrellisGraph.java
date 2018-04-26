/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trellisldp.webac;

import static org.slf4j.LoggerFactory.getLogger;

import org.apache.commons.rdf.api.Graph;
import org.slf4j.Logger;
import org.trellisldp.api.RuntimeTrellisException;

/**
 * @author acoburn
 */
class TrellisGraph implements AutoCloseable {

    private static final Logger LOGGER = getLogger(TrellisGraph.class);

    private final Graph graph;

    /**
     * Create a new graph.
     *
     * @param graph the graph
     */
    public TrellisGraph(final Graph graph) {
        this.graph = graph;
    }

    @Override
    public void close() {
        try {
            graph.close();
        } catch (final Exception ex) {
            LOGGER.error("Error closing graph: {}", ex.getMessage());
            throw new RuntimeTrellisException("Error closing graph", ex);
        }
    }

    /**
     * Get the underlying graph.
     *
     * @return the graph
     */
    public Graph asGraph() {
        return graph;
    }
}