package com.opensourcedev.fileTransferServer.service.common_files_fileTransfer_service;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
@Service
public class FilesInCommonDirectoriesSearchImpl implements FilesInCommonDirectoriesSearch{

    @Value("${folder.sourcedir.salary}")
    private String salaryDir;
    @Value("${folder.sourcedir.attendance}")
    private String attendanceDir;
    @Value("${folder.sourcedir.performance}")
    private String performanceDir;

    private FileUtils fileUtils;

    @Autowired
    public FilesInCommonDirectoriesSearchImpl(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }


    @Override
    public Report searchFilesInSalary() {
        return fileUtils.getFilesFromFolder(salaryDir);
    }

    @Override
    public Report searchFilesInAttendance() {
        return fileUtils.getFilesFromFolder(attendanceDir);
    }

    @Override
    public Report searchFilesInPerformance() {
        return fileUtils.getFilesFromFolder(performanceDir);
    }
}
