package br.com.rafelms.rest_with_spring.file.importer.impl;

import br.com.rafelms.rest_with_spring.data.dto.v1.PersonDTO;
import br.com.rafelms.rest_with_spring.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {


    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader() // usa o cabeçalho
                .setSkipHeaderRecord(true) // pula o cabeçalho
                .setIgnoreEmptyLines(true) // se tiver linha vazia, ignorar
                .setTrim(true) // se tiver valor em branco antes ou depois do valor, elimina
                .build();
        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return parseRecordsToPersonDTOs(records);

    }

    private List<PersonDTO> parseRecordsToPersonDTOs(Iterable<CSVRecord> records) {
        List<PersonDTO> people = new ArrayList<>();

        for (CSVRecord record: records){
            PersonDTO person = new PersonDTO();
            person.setFirstName(record.get("first_name"));
            person.setLastName(record.get("last_name"));
            person.setAddress(record.get("address"));
            person.setGender(record.get("gender"));
            person.setEnabled(true);
            people.add(person);
        }
        return people;

    }
}
