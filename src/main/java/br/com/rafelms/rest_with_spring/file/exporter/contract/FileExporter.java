package br.com.rafelms.rest_with_spring.file.exporter.contract;

import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {
    Resource exportFile (List<PersonDTO> people) throws Exception;

}
