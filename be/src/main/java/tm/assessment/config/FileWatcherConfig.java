package tm.assessment.config;

import java.io.File;
import java.time.Duration;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tm.assessment.component.MyFileChangeListener;

@Configuration
public class FileWatcherConfig {

    @Autowired
    MyFileChangeListener listener;

    @Value("${import-path}")
    private String importPath;

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        File directory = new File(importPath);
        if(!directory.exists())
            directory.mkdir();
        
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(5000L), Duration.ofMillis(3000L));
        fileSystemWatcher.addSourceDirectory(new File(importPath));
        fileSystemWatcher.addListener(listener);
        fileSystemWatcher.start();
        System.out.println("started fileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }


    // public 
    // WatchService watchService = FileSystems.getDefault().newWatchService();
    //     Path path = Paths.get("../../tm_assessment/import");
    //     path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
    //     WatchKey key;
    //     while ((key = watchService.take()) != null) {
    //         for (WatchEvent<?> event : key.pollEvents()) {
	// 			System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
	// 			if(event.kind().toString().equals("ENTRY_CREATE")) {
	// 				extrac.
	// 			}
    //         }
    //         key.reset();
    //     }

    //     watchService.close();
}
