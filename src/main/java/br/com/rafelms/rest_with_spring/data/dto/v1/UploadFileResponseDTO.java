package br.com.rafelms.rest_with_spring.data.dto.v1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponseDTO {
    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponseDTO(){}

    public UploadFileResponseDTO(String fileName, String fileDownloadUri, String fileType, long size){
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

}
