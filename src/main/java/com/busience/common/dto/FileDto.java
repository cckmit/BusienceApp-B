package com.busience.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileDto {
	
	private String OriginalFileName;
	
	private String FileName;
	
	private String SavePath;
	
	private long FileSize;
}
