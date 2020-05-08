package com.opensourcedev.fileTransferServer.service.bulk_fileTransfer_service;

import com.opensourcedev.fileTransferServer.model.Report;
import com.opensourcedev.fileTransferServer.service.fileUtils.FileUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
@Service
public class FilesInAllDirectoriesSearchImpl implements FilesInAllDirectoriesSearch {

    @Value("${folder.root.folder}")
    private String rootDir;
    private FileUtils fileUtils;
    private Report reports;

    @Autowired
    public FilesInAllDirectoriesSearchImpl(FileUtils fileUtils, Report reports) {
        this.fileUtils = fileUtils;
        this.reports = reports;
    }

    @Override
    public Report searchFilesInAllDirs() {

        List<HashMap<String, byte[]>> filesFromDirectory = new ArrayList<>();
        HashMap<String, byte[]> filesAsMap = new HashMap<>();
        List<String> filesInFolder = null;

        if (fileUtils.folderExist(rootDir)) {
            log.debug("[+]Specified folder exists...");
            filesInFolder = new ArrayList<>(fileUtils.listAllFilesInFolder(rootDir));

            filesInFolder.forEach(file -> {
                byte[] fileAsBytes = fileUtils.extractFileFromDirectory(file);
                Path pathOfFile = Paths.get(file);
                String fileName = pathOfFile.getFileName().toString();

                filesAsMap.put(fileName, fileAsBytes);

                log.debug("Original file name: " + fileName);
            });

            if (!filesAsMap.isEmpty()) {
                filesFromDirectory.add(filesAsMap);
                reports.setFilesFromDir(filesFromDirectory);
            }
            log.debug("filesFromDirectory: " + filesFromDirectory);
            log.debug("[+]All files stored in: " + rootDir + " have been successfully stored...");

        } else {
            log.debug("[!]Specified folder doesn't exists");
        }

        if (reports != null && !reports.getFilesFromDir().isEmpty()) {
            return reports;
        } else {
            log.debug("[!]Empty report...");
            return new Report();
        }
    }
}