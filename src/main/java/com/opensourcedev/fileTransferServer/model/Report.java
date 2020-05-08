package com.opensourcedev.fileTransferServer.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
@Component
public class Report {

    List<HashMap<String, byte[]>> filesFromDir;

}