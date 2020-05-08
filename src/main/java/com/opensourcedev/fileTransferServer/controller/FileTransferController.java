package com.opensourcedev.fileTransferServer.controller;

import com.opensourcedev.fileTransferServer.model.Report;
import com.opensourcedev.fileTransferServer.service.bulk_fileTransfer_service.FilesInAllDirectoriesSearch;
import com.opensourcedev.fileTransferServer.service.common_files_fileTransfer_service.FilesInCommonDirectoriesSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ft_api")
public class FileTransferController {

    FilesInAllDirectoriesSearch filesInAllDirectoriesSearch;
    FilesInCommonDirectoriesSearch filesInCommonDirectoriesSearch;


    public FileTransferController(FilesInAllDirectoriesSearch filesInAllDirectoriesSearch,
                                             FilesInCommonDirectoriesSearch filesInCommonDirectoriesSearch) {
        this.filesInAllDirectoriesSearch = filesInAllDirectoriesSearch;
        this.filesInCommonDirectoriesSearch = filesInCommonDirectoriesSearch;
    }


    @GetMapping(value = "/help")
    public @ResponseBody
    String getHelp(){

        return  "Welcome to the  File Transfer API \n" +
                "Here is the complete list of all available GET requests that can be made: \n\n" +
                "/ft_api/getSalaryFiles - returns all files from salary folder \n" +
                "/ft_api/getAttendanceFiles - returns all files from attendance folder \n" +
                "/ft_api/getPerformanceFiles - returns all files from performance folder \n" +
                "/ft_api/getAllFiles - returns all files from folders mentioned above together\n"
                + "\n\nthis request is useful for creating or downloading backup from all folders together so\n"
                + "you don't have to run requests for all folders \n";
    }

    @GetMapping(value = "/getAllFiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Report> getFilesFromAllKnownDirectories(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(filesInAllDirectoriesSearch.searchFilesInAllDirs());
    }


    @GetMapping(value = "/getSalaryFiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Report> getFilesFromAbilitDir(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(filesInCommonDirectoriesSearch.searchFilesInSalary());
    }

    @GetMapping(value = "/getAttendanceFiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Report> getFilesFromAdsDir(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(filesInCommonDirectoriesSearch.searchFilesInAttendance());
    }

    @GetMapping(value = "/getPerformanceFiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Report> getFilesFromAgDruckDir(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(filesInCommonDirectoriesSearch.searchFilesInPerformance());
    }

}
