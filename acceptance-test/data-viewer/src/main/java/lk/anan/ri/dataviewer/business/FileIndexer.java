package lk.anan.ri.dataviewer.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.repository.FileEntityRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Component
public class FileIndexer {
    @Value("${filescan.path}")
    private String fileScanPath;
    @Autowired
    private FileEntityRepository fileEntityRepository;

    private final WatchService watchService;
    private final Map<WatchKey, Path> watchKeys;

    public FileIndexer() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.watchKeys = new HashMap<>();
    }

    private void scanAndIndexFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(fileScanPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".xml"))
                    .filter(path -> {
                        Path parent = path.getParent();
                        return parent == null || !Files.exists(parent.resolve(".indexed"));
                    })
                    .forEach(path -> {
                        try (Stream<Path> stream = Files.list(path.getParent())) {
                            stream.filter(p -> p.toString().endsWith(".xml"))
                                  .forEach(p -> {
                                    FileEntity fileEntity = new FileEntity();
                                    fileEntity.setPath(path.toString());
                                    fileEntity.setDatatype(path.getParent().getParent().getFileName().toString());
                                    // fileEntity.setConfirmedBy("System");
                                    fileEntityRepository.save(fileEntity);
                                    try {
                                        Path indexedFile = path.getParent().resolve(".indexed");
                                        if (Files.exists(indexedFile)) {
                                            Files.write(indexedFile, ("," + fileEntity.getId().toString()).getBytes(), StandardOpenOption.APPEND);
                                        } else {
                                            Files.write(indexedFile, fileEntity.getId().toString().getBytes());
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                  });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        
                        
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() throws IOException, InterruptedException {
        scanAndIndexFiles();
        watchDirectory(Paths.get(fileScanPath));
    }


    public void watchDirectory(Path startPath) throws IOException, InterruptedException {
        registerAll(startPath);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            Path dir = watchKeys.get(key);
            if (dir == null) {
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path name = ev.context();
                Path child = dir.resolve(name);

                System.out.println("Event kind:" + kind + ". File affected: " + child + ".");

                // If a directory is created, and watching recursively, then register it and its sub-directories
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // Ignore to keep sample readbale
                    }
                }

                // if (Files.isRegularFile(child) && child.toString().endsWith(".xml")) {
                    scanAndIndexFiles();
                // }
            }

            boolean valid = key.reset();
            if (!valid) {
                watchKeys.remove(key);

                // All directories are inaccessible
                if (watchKeys.isEmpty()) {
                    break;
                }
            }
        }
    }

    private void registerAll(final Path start) throws IOException {
        // Register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        watchKeys.put(key, dir);
    }
}