package lk.anan.ri.dataviewer.business;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class FileIndexerTest {

    @InjectMocks
    private FileIndexer fileIndexer;

    @Mock
    private WatchService watchService;

    @Mock
    private WatchKey watchKey;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }
    @Disabled
    @Test
    public void testWatchDirectory() throws Exception {
        Path path = Paths.get("target");

        // Mock the behavior of the WatchService
        when(watchService.take()).thenReturn(watchKey);
        when(watchKey.pollEvents()).thenReturn(Collections.singletonList(
            new WatchEvent<Path>() {
                @Override
                public Kind<Path> kind() {
                    return StandardWatchEventKinds.ENTRY_CREATE;
                }

                @Override
                public int count() {
                    return 1;
                }

                @Override
                public Path context() {
                    return Paths.get("test-file.txt");
                }
            }
        ));
        when(watchKey.reset()).thenReturn(true);

        // Call the method to test
        fileIndexer.watchDirectory(path);

        // Verify that the WatchService was used
        verify(watchService, atLeastOnce()).take();
        verify(watchKey, atLeastOnce()).pollEvents();
        verify(watchKey, atLeastOnce()).reset();
    }
}