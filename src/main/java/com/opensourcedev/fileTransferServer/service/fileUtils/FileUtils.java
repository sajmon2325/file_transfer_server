package com.opensourcedev.fileTransferServer.service.fileUtils;

import com.opensourcedev.fileTransferServer.model.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class FileUtils {

    public List<String> listAllFilesInFolder(String directory) {
        List<String> filesInFolder = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {
            filesInFolder = walk.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            filesInFolder.forEach(System.out::println);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        return filesInFolder;
    }

    public byte[] extractFileFromDirectory(String fileAsString) {
        byte[] fileAsBytes;

        Path path = Paths.get(fileAsString);
        log.debug("Paths contains: " + path);
        try {
            fileAsBytes = Files.readAllBytes(path);
            return fileAsBytes;

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        return new byte[0];
    }

    public boolean folderExist(String folder) {
        Path path = Paths.get(folder);
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    }

    public Report getFilesFromFolder(String directory) {
        List<HashMap<String, byte[]>> filesFromDirectory = new ArrayList<>();
        HashMap<String, byte[]> filesAsMap = new HashMap<>();
        Report report = new Report();

        if (folderExist(directory)) {
            log.debug("[+]Specified folder exists...");
            List<String> filesInFolder = new ArrayList<>(listAllFilesInFolder(directory));
            log.debug("files in folder names: " + filesInFolder.toString());

            filesInFolder.forEach(file -> {
                Path pathOfFile = Paths.get(file);
                String fileName = pathOfFile.getFileName().toString();

                filesAsMap.put(fileName, extractFileFromDirectory(file));

                log.debug("Original file name: " + fileName);

            });

            if (!filesAsMap.isEmpty()) {
                filesFromDirectory.add(filesAsMap);
                report.setFilesFromDir(filesFromDirectory);
            }

            log.debug("Files stored in " + directory + ": " + filesFromDirectory);
            log.debug("[+]All files stored in: " + directory + " have been successfully stored...");
        } else {
            log.debug("[!]Specified folder doesn't exists");
        }

        if (!report.getFilesFromDir().isEmpty()) {
            return report;
        } else {
            log.debug("[!]Empty report...");
            return new Report();
        }
    }
}
