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
package org.trellisldp.binary;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.commons.io.IOUtils;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.trellisldp.api.BinaryService;
import org.trellisldp.api.IdentifierService;
import org.trellisldp.id.UUIDGenerator;

/**
 * @author acoburn
 */
@RunWith(JUnitPlatform.class)
public class FileBasedBinaryServiceTest {

    private final static String testDoc = "test.txt";

    private final static RDF rdf = new SimpleRDF();

    private final static String directory = new File(FileBasedBinaryService.class.getResource("/" + testDoc).getPath())
        .getParent();

    private final static IRI file = rdf.createIRI("file:" + testDoc);
    private final IdentifierService idService = new UUIDGenerator();

    @Mock
    private InputStream mockInputStream;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFileExists() {
        final BinaryService resolver = new FileBasedBinaryService(directory, idService.getSupplier("file:"));
        assertTrue(resolver.exists(file));
        assertFalse(resolver.exists(rdf.createIRI("file:fake.txt")));
    }

    @Test
    public void testFilePurge() {
        final BinaryService resolver = new FileBasedBinaryService(directory, idService.getSupplier("file:"));
        final IRI fileIRI = rdf.createIRI("file:" + randomFilename());
        final InputStream inputStream = new ByteArrayInputStream("Some data".getBytes(UTF_8));
        resolver.setContent(fileIRI, inputStream);
        assertTrue(resolver.exists(fileIRI));
        resolver.purgeContent(fileIRI);
        assertFalse(resolver.exists(fileIRI));
    }

    @Test
    public void testIdSupplier() {
        final BinaryService resolver = new FileBasedBinaryService(directory, idService.getSupplier("file:"));
        assertTrue(resolver.getIdentifierSupplier().get().startsWith("file:"));
        assertNotEquals(resolver.getIdentifierSupplier().get(),
                resolver.getIdentifierSupplier().get());
    }

    @Test
    public void testFileContent() {
        final BinaryService resolver = new FileBasedBinaryService(directory, idService.getSupplier("file:"));
        assertTrue(resolver.getContent(file).isPresent());
        assertEquals("A test document.\n", resolver.getContent(file).map(this::uncheckedToString).get());
    }

    @Test
    public void testSetFileContent() {
        final String contents = "A new file";
        final BinaryService resolver = new FileBasedBinaryService(directory, idService.getSupplier("file:"));
        final IRI fileIRI = rdf.createIRI("file:" + randomFilename());
        final InputStream inputStream = new ByteArrayInputStream(contents.getBytes(UTF_8));
        resolver.setContent(fileIRI, inputStream);
        assertTrue(resolver.getContent(fileIRI).isPresent());
        assertEquals(contents, resolver.getContent(fileIRI).map(this::uncheckedToString).get());
    }

    @Test
    public void testGetFileContentError() throws IOException {
        final BinaryService resolver = new FileBasedBinaryService(directory, idService.getSupplier("file:"));
        final IRI fileIRI = rdf.createIRI("file:" + randomFilename());
        assertThrows(UncheckedIOException.class, () -> resolver.getContent(fileIRI));
    }

    @Test
    public void testSetFileContentError() throws IOException {
        when(mockInputStream.read(any(byte[].class))).thenThrow(new IOException("Expected error"));
        final BinaryService resolver = new FileBasedBinaryService(directory, idService.getSupplier("file:"));
        final IRI fileIRI = rdf.createIRI("file:" + randomFilename());
        assertThrows(UncheckedIOException.class, () -> resolver.setContent(fileIRI, mockInputStream));
    }

    @Test
    public void testBase64Digest() throws IOException {
        final byte[] data = "Some data".getBytes(UTF_8);
        when(mockInputStream.read(any(), anyInt(), anyInt())).thenThrow(new IOException("Expected Error"));

        final BinaryService service = new FileBasedBinaryService(directory, idService.getSupplier("file:"));
        assertEquals(of("W4L4v03yv7DmbMqnMG/QJA=="), service.digest("MD5", new ByteArrayInputStream(data)));
        assertEquals(of("jXJFPxAHmvPfx/z8QQmx7VXhg58="), service.digest("SHA", new ByteArrayInputStream(data)));
        assertEquals(of("jXJFPxAHmvPfx/z8QQmx7VXhg58="), service.digest("SHA-1", new ByteArrayInputStream(data)));
        assertFalse(service.digest("MD5", mockInputStream).isPresent());
    }

    private String uncheckedToString(final InputStream is) {
        try {
            return IOUtils.toString(is, UTF_8);
        } catch (final IOException ex) {
            return null;
        }
    }

    private static String randomFilename() {
        final SecureRandom random = new SecureRandom();
        final String filename = new BigInteger(50, random).toString(32);
        return filename + ".json";
    }
}