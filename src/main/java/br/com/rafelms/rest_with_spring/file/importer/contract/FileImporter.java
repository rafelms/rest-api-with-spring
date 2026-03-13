package br.com.rafelms.rest_with_spring.file.importer.contract;

import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
