package com.techlabs.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	void uploadFile(MultipartFile file1, Long userId);

}
